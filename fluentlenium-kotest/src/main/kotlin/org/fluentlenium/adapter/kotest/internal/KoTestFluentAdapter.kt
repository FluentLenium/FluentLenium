package org.fluentlenium.adapter.kotest.internal

import io.kotest.common.ExperimentalKotest
import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.Spec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.core.test.TestStatus
import io.kotest.core.test.TestType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.fluentlenium.adapter.DefaultSharedMutator
import org.fluentlenium.adapter.FluentAdapter
import org.fluentlenium.adapter.FluentTestRunnerAdapter
import org.fluentlenium.adapter.IFluentAdapter
import org.fluentlenium.adapter.TestRunnerCommon.deleteCookies
import org.fluentlenium.adapter.TestRunnerCommon.doHtmlDump
import org.fluentlenium.adapter.TestRunnerCommon.doScreenshot
import org.fluentlenium.adapter.TestRunnerCommon.getTestDriver
import org.fluentlenium.adapter.TestRunnerCommon.quitMethodAndThreadDrivers
import org.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer
import org.fluentlenium.configuration.Configuration
import org.fluentlenium.utils.ScreenshotUtil
import org.fluentlenium.utils.SeleniumVersionChecker
import java.util.concurrent.atomic.AtomicReference

internal class KoTestFluentAdapter constructor(var useConfigurationOverride: () -> Configuration = { throw IllegalStateException() }) :
    IFluentAdapter,
    FluentAdapter() {

    val sharedMutator = DefaultSharedMutator()

    /**
     * Kotest will use the same thread for all tests in a spec as long as Kotests configuration option
     * dispatcherAffinity == true. With this assumption its safe to use an AtomicReference here to
     * store the name of the currently active test. we will check that configuration and fail the execution if set
     * to false.
     */
    val currentTestName = AtomicReference<String>()

    private val configurationOverride: Configuration by lazy { useConfigurationOverride() }

    override fun getConfiguration(): Configuration = configurationOverride

    val listener: TestListener = object : TestListener {
        override suspend fun beforeSpec(spec: Spec) =
            this@KoTestFluentAdapter.beforeSpec()

        override suspend fun beforeTest(testCase: TestCase) {
            this@KoTestFluentAdapter.beforeTest(testCase)
        }

        override suspend fun afterSpec(spec: Spec) {
            this@KoTestFluentAdapter.afterSpec(spec)
        }
    }

    suspend fun beforeSpec() {
        withContext(Dispatchers.IO) {
            SeleniumVersionChecker.checkSeleniumVersion()
        }
    }

    @ExperimentalKotest
    fun beforeTest(testCase: TestCase) {
        if (testCase.type == TestType.Container)
            return

        val singleThreadPerTest =
            testCase.spec.dispatcherAffinity ?: testCase.spec.dispatcherAffinity()
                ?: io.kotest.core.config.configuration.dispatcherAffinity

        require(singleThreadPerTest) {
            "fluentlenium-kotest is incompatible with dispatcherAffinity=false. set to true!"
        }

        val thisListener = this
        val testClass = testCase.spec.javaClass
        val testName = testCase.displayName

        currentTestName.set(testName)

        val theTestInstance =
            testCase.spec as? IFluentAdapter ?: throw IllegalArgumentException()

        val driver =
            getTestDriver(
                testCase.spec.javaClass,
                testName,
                theTestInstance::newWebDriver,
                this@KoTestFluentAdapter::failed,
                configuration,
                sharedMutator.getEffectiveParameters(testClass, testName, driverLifecycle)
            )

        initFluent(driver.driver, testCase.spec)
    }

    fun afterSpec(spec: Spec) {
        FluentTestRunnerAdapter.classDriverCleanup(spec.javaClass)
    }

    fun afterTest(testCase: TestCase, result: TestResult) {

        if (testCase.type == TestType.Container)
            return

        val testClass = testCase.spec.javaClass
        val testName = testCase.displayName

        if (result.status == TestStatus.Error || result.status == TestStatus.Failure) {
            failed(result.error, testClass, testName)
        }

        val sharedWebDriver = SharedWebDriverContainer.INSTANCE
            .getDriver(sharedMutator.getEffectiveParameters(testClass, testName, driverLifecycle))

        quitMethodAndThreadDrivers(driverLifecycle, sharedWebDriver)
        deleteCookies(sharedWebDriver, configuration)
        releaseFluent()

        currentTestName.set(null)
    }

    private fun failed(error: Throwable?, testClass: Class<*>, testName: String) {
        if (isFluentControlAvailable && !ScreenshotUtil.isIgnoredException(error)) {
            doScreenshot(testClass, testName, this@KoTestFluentAdapter, configuration)
            doHtmlDump(testClass, testName, this@KoTestFluentAdapter, configuration)
        }
    }

    fun ensureTestStarted() {
        if (currentTestName.get() == null) {
            throw IllegalStateException("FluentLenium is not yet available! Make sure to use FluentLenium only within the innermost Kotest test block!")
        }
    }
}
