package org.fluentlenium.adapter.kotest

import io.kotest.core.spec.style.WordSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import org.fluentlenium.adapter.IFluentAdapter
import org.fluentlenium.adapter.TestRunnerAdapter
import org.fluentlenium.adapter.exception.AnnotationNotFoundException
import org.fluentlenium.adapter.kotest.internal.KoTestFluentAdapter
import org.fluentlenium.configuration.Configuration
import org.fluentlenium.configuration.ConfigurationFactoryProvider
import org.fluentlenium.core.inject.ContainerFluentControl

abstract class FluentWordSpec internal constructor(
    private val fluentAdapter: KoTestFluentAdapter,
    body: FluentWordSpec.() -> Unit
) : WordSpec({}),
    IFluentAdapter by fluentAdapter,
    TestRunnerAdapter {

    constructor(body: FluentWordSpec.() -> Unit = {}) : this(KoTestFluentAdapter(), body)

    init {
        fluentAdapter.useConfigurationOverride = { configuration }

        listener(fluentAdapter.listener)

        body()
    }

    private val config: Configuration by lazy {
        ConfigurationFactoryProvider.newConfiguration(javaClass)
    }

    override fun getConfiguration(): Configuration = config

    override fun getTestClass(): Class<*> = javaClass

    override fun getTestMethodName(): String =
        fluentAdapter.currentTestName.get()

    override fun <T : Annotation?> getClassAnnotation(annotation: Class<T>?): T =
        javaClass.getAnnotation(annotation) ?: throw AnnotationNotFoundException()

    override fun <T : Annotation?> getMethodAnnotation(annotation: Class<T>?): T {
        throw AnnotationNotFoundException()
    }

    override fun getFluentControl(): ContainerFluentControl {
        fluentAdapter.ensureTestStarted()

        return super.getFluentControl()
    }

    final override fun afterTest(testCase: TestCase, result: TestResult) {
        try {
            doAfterTest(testCase, result)
        } finally {
            fluentAdapter.afterTest(testCase, result)

            super.afterTest(testCase, result)
        }
    }

    open fun doAfterTest(testCase: TestCase, result: TestResult) {}
}
