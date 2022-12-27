package io.fluentlenium.adapter.kotest.expectspec

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.fluentlenium.adapter.exception.AnnotationNotFoundException
import io.fluentlenium.adapter.kotest.FluentExpectSpec
import io.fluentlenium.adapter.kotest.MyAnnotation
import io.fluentlenium.adapter.kotest.OtherAnnotation

@MyAnnotation
class ExpectMetadataSpec : FluentExpectSpec({

    context("Annotated Spec") {
        expect("can access existing annotation") {
            getClassAnnotation(MyAnnotation::class.java).shouldNotBeNull()
        }

        expect("access non existing annotation fails") {
            shouldThrow<AnnotationNotFoundException> {
                getClassAnnotation(OtherAnnotation::class.java)
            }
        }

        expect("getMethodAnnotation should fail") {
            shouldThrow<AnnotationNotFoundException> {
                getMethodAnnotation(MyAnnotation::class.java)
            }
        }

        expect("methodName should be available") {
            testMethodName shouldBe "methodName should be available"
        }

        expect("spec should be available") {
            testClass shouldBe ExpectMetadataSpec::class.java
        }
    }
})
