package io.fluentlenium.adapter.kotest.annotationspec

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.fluentlenium.adapter.exception.AnnotationNotFoundException
import org.fluentlenium.adapter.kotest.FluentAnnotationSpec
import org.fluentlenium.adapter.kotest.MyAnnotation
import org.fluentlenium.adapter.kotest.OtherAnnotation

@MyAnnotation
class AnnotationMetadataSpec : FluentAnnotationSpec() {

    @Test
    fun testClass() {
        testClass shouldBe javaClass
    }

    @Test
    fun testMethodName() {
        testMethodName shouldBe "testMethodName"
    }

    @Test
    fun testGetClassAnnotation() {
        getClassAnnotation(MyAnnotation::class.java).shouldNotBeNull()

        shouldThrow<_root_ide_package_.io.fluentlenium.adapter.exception.AnnotationNotFoundException> {
            getClassAnnotation(OtherAnnotation::class.java).shouldNotBeNull()
        }
    }

    @Test
    @MyAnnotation
    fun testGetMethodAnnotation() {
        getMethodAnnotation(MyAnnotation::class.java).shouldNotBeNull()

        shouldThrow<_root_ide_package_.io.fluentlenium.adapter.exception.AnnotationNotFoundException> {
            getMethodAnnotation(OtherAnnotation::class.java).shouldNotBeNull()
        }
    }
}
