package io.fluentlenium.adapter.kotest.shouldspec

import io.fluentlenium.adapter.exception.AnnotationNotFoundException
import io.fluentlenium.adapter.kotest.FluentShouldSpec
import io.fluentlenium.adapter.kotest.MyAnnotation
import io.fluentlenium.adapter.kotest.OtherAnnotation
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

@MyAnnotation
class ShouldMetadataSpec : FluentShouldSpec({

    context("Annotated Spec") {
        should("can access existing annotation") {
            getClassAnnotation(MyAnnotation::class.java).shouldNotBeNull()
        }

        should("access non existing annotation fails") {
            shouldThrow<AnnotationNotFoundException> {
                getClassAnnotation(OtherAnnotation::class.java)
            }
        }

        should("getMethodAnnotation should fail") {
            shouldThrow<AnnotationNotFoundException> {
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
