package io.fluentlenium.adapter.kotest.funspec

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.fluentlenium.adapter.exception.AnnotationNotFoundException
import org.fluentlenium.adapter.kotest.FluentFunSpec
import org.fluentlenium.adapter.kotest.MyAnnotation
import org.fluentlenium.adapter.kotest.OtherAnnotation

@MyAnnotation
class FunMetadataSpec : FluentFunSpec({
    test("can access existing annotation") {
        getClassAnnotation(MyAnnotation::class.java).shouldNotBeNull()
    }

    test("access non existing annotation fails") {
        shouldThrow<_root_ide_package_.io.fluentlenium.adapter.exception.AnnotationNotFoundException> {
            getClassAnnotation(OtherAnnotation::class.java)
        }
    }

    test("getMethodAnnotation should fail") {
        shouldThrow<_root_ide_package_.io.fluentlenium.adapter.exception.AnnotationNotFoundException> {
            getMethodAnnotation(MyAnnotation::class.java)
        }
    }

    test("methodName should be available") {
        testMethodName shouldBe "methodName should be available"
    }

    test("spec should be available") {
        testClass shouldBe FunMetadataSpec::class.java
    }
})
