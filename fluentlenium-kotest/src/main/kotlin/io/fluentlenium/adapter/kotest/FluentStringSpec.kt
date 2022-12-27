package io.fluentlenium.adapter.kotest

import io.kotest.core.spec.style.StringSpec
import io.fluentlenium.adapter.IFluentAdapter
import io.fluentlenium.adapter.TestRunnerAdapter
import io.fluentlenium.adapter.exception.AnnotationNotFoundException
import org.fluentlenium.adapter.kotest.internal.KoTestFluentAdapter
import io.fluentlenium.configuration.Configuration
import io.fluentlenium.configuration.ConfigurationFactoryProvider
import io.fluentlenium.core.inject.ContainerFluentControl

abstract class FluentStringSpec internal constructor(
    private val fluentAdapter: KoTestFluentAdapter,
    body: FluentStringSpec.() -> Unit
) : StringSpec({}),
    _root_ide_package_.io.fluentlenium.adapter.IFluentAdapter by fluentAdapter,
    _root_ide_package_.io.fluentlenium.adapter.TestRunnerAdapter {

    constructor(body: FluentStringSpec.() -> Unit = {}) : this(KoTestFluentAdapter(), body)

    init {
        fluentAdapter.useConfigurationOverride = { configuration }

        register(fluentAdapter.extension)

        body()
    }

    private val config: _root_ide_package_.io.fluentlenium.configuration.Configuration by lazy {
        _root_ide_package_.io.fluentlenium.configuration.ConfigurationFactoryProvider.newConfiguration(javaClass)
    }

    override fun getConfiguration(): _root_ide_package_.io.fluentlenium.configuration.Configuration = config

    override fun getTestClass(): Class<*> = javaClass

    override fun getTestMethodName(): String =
        fluentAdapter.currentTestName.get()

    override fun <T : Annotation?> getClassAnnotation(annotation: Class<T>): T =
        javaClass.getAnnotation(annotation) ?: throw _root_ide_package_.io.fluentlenium.adapter.exception.AnnotationNotFoundException()

    override fun <T : Annotation?> getMethodAnnotation(annotation: Class<T>): T {
        throw _root_ide_package_.io.fluentlenium.adapter.exception.AnnotationNotFoundException()
    }

    override fun getFluentControl(): _root_ide_package_.io.fluentlenium.core.inject.ContainerFluentControl {
        fluentAdapter.ensureTestStarted()

        return super.getFluentControl()
    }
}
