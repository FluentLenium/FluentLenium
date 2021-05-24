package org.fluentlenium.adapter.kotest

import io.kotest.core.spec.style.AnnotationSpec
import org.fluentlenium.adapter.IFluentAdapter
import org.fluentlenium.adapter.TestRunnerAdapter
import org.fluentlenium.adapter.exception.AnnotationNotFoundException
import org.fluentlenium.adapter.kotest.internal.KoTestFluentAdapter
import org.fluentlenium.configuration.Configuration
import org.fluentlenium.configuration.ConfigurationFactoryProvider

abstract class FluentAnnotationSpec internal constructor(
    private val fluentAdapter: KoTestFluentAdapter
) :
    AnnotationSpec(),
    IFluentAdapter by fluentAdapter,
    TestRunnerAdapter {

    constructor() : this(KoTestFluentAdapter())

    init {
        fluentAdapter.useConfigurationOverride = { configuration }

        listener(fluentAdapter.listener())
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
        val currentTestMethod = javaClass.declaredMethods.find {
            it.name == fluentAdapter.currentTestName.get()
        } ?: throw IllegalStateException()

        return currentTestMethod.getAnnotation(annotation) ?: throw AnnotationNotFoundException()
    }
}
