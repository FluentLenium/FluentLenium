package io.fluentlenium.adapter.kotest

import io.fluentlenium.adapter.IFluentAdapter
import io.fluentlenium.adapter.TestRunnerAdapter
import io.fluentlenium.adapter.exception.AnnotationNotFoundException
import io.fluentlenium.adapter.kotest.internal.KoTestFluentAdapter
import io.fluentlenium.configuration.Configuration
import io.fluentlenium.configuration.ConfigurationFactoryProvider
import io.fluentlenium.core.inject.ContainerFluentControl
import io.kotest.core.spec.style.FunSpec

abstract class FluentFunSpec internal constructor(
    private val fluentAdapter: KoTestFluentAdapter,
    body: FluentFunSpec.() -> Unit
) : FunSpec({ }),
    IFluentAdapter by fluentAdapter,
    TestRunnerAdapter {

    constructor(body: FluentFunSpec.() -> Unit = {}) : this(KoTestFluentAdapter(), body)

    init {
        fluentAdapter.useConfigurationOverride = { configuration }

        register(fluentAdapter.extension)

        body()
    }

    private val config: Configuration by lazy {
        ConfigurationFactoryProvider.newConfiguration(javaClass)
    }

    override fun getConfiguration(): Configuration = config

    override fun getTestClass(): Class<*> = javaClass

    override fun getTestMethodName(): String =
        fluentAdapter.currentTestName.get()

    override fun <T : Annotation?> getClassAnnotation(annotation: Class<T>): T =
        javaClass.getAnnotation(annotation) ?: throw AnnotationNotFoundException()

    override fun <T : Annotation?> getMethodAnnotation(annotation: Class<T>): T {
        throw AnnotationNotFoundException()
    }

    override fun getFluentControl(): ContainerFluentControl {
        fluentAdapter.ensureTestStarted()

        return super.getFluentControl()
    }
}
