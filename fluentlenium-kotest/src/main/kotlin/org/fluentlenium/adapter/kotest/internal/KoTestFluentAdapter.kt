package org.fluentlenium.adapter.kotest.internal

import io.kotest.common.ExperimentalKotest
import io.kotest.core.extensions.Extension
import io.kotest.core.listeners.AfterEachListener
import io.kotest.core.listeners.AfterSpecListener
import io.kotest.core.listeners.BeforeSpecListener
import io.kotest.core.listeners.BeforeTestListener
import io.kotest.core.spec.Spec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.core.test.TestType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.fluentlenium.adapter.DefaultSharedMutator
import org.fluentlenium.adapter.FluentAdapter
import org.fluentlenium.adapter.FluentTestRunnerAdapter
import org.fluentlenium.adapter.IFluentAdapter
import org.fluentlenium.adapter.TestRunnerCommon.*
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

    val extension: Extension = object : BeforeSpecListener, AfterSpecListener, BeforeTestListener, AfterEachListener {

        override suspend fun beforeSpec(spec: Spec) =
            this@KoTestFluentAdapter.beforeSpec()

        override suspend fun afterSpec(spec: Spec) {
            this@KoTestFluentAdapter.afterSpec(spec)
        }

        override suspend fun beforeTest(testCase: TestCase) {
            this@KoTestFluentAdapter.beforeTest(testCase)
        }

        override suspend fun afterEach(testCase: TestCase, result: TestResult) {
            this@KoTestFluentAdapter.afterEach(testCase, result)
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
                ?: io.kotest.core.config.Defaults.dispatcherAffinity

        require(singleThreadPerTest) {
            "fluentlenium-kotest is incompatible with dispatcherAffinity=false. set to true!"
        }

        val testClass = testCase.spec.javaClass
        val testName = testCase.name.testName

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

    fun afterEach(testCase: TestCase, result: TestResult) {
        if (testCase.type == TestType.Container)
            return

        val testClass = testCase.spec.javaClass
        val testName = testCase.name.testName

        when (result) {
            is TestResult.Error -> failed(result.errorOrNull, testClass, testName)
            is TestResult.Failure -> failed(result.errorOrNull, testClass, testName)
            else -> Unit
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
        checkNotNull(currentTestName.get()) {
            "FluentLenium is not yet available! Make sure to use FluentLenium only within the innermost Kotest test block!"
        }
    }
}
