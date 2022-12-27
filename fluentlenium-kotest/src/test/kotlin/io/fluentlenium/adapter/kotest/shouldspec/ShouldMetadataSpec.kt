package io.fluentlenium.adapter.kotest.shouldspec

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.fluentlenium.adapter.exception.AnnotationNotFoundException
import org.fluentlenium.adapter.kotest.FluentShouldSpec
import org.fluentlenium.adapter.kotest.MyAnnotation
import org.fluentlenium.adapter.kotest.OtherAnnotation

@MyAnnotation
class ShouldMetadataSpec : FluentShouldSpec({

    context("Annotated Spec") {
        should("can access existing annotation") {
            getClassAnnotation(MyAnnotation::class.java).shouldNotBeNull()
        }

        should("access non existing annotation fails") {
            shouldThrow<_root_ide_package_.io.fluentlenium.adapter.exception.AnnotationNotFoundException> {
                getClassAnnotation(OtherAnnotation::class.java)
            }
        }

        should("getMethodAnnotation should fail") {
            shouldThrow<_root_ide_package_.io.fluentlenium.adapter.exception.AnnotationNotFoundException> {
                getMethodAnnotation(MyAnnotation::class.java)
            }
        }

        should("methodName should be available") {
            testMethodName shouldBe "methodName should be available"
        }

        should("spec should be available") {
            testClass shouldBe ShouldMetadataSpec::class.java
        }
    }
})
