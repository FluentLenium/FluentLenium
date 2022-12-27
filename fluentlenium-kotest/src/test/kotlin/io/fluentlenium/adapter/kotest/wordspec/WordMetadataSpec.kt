package io.fluentlenium.adapter.kotest.wordspec

import io.fluentlenium.adapter.exception.AnnotationNotFoundException
import io.fluentlenium.adapter.kotest.FluentWordSpec
import io.fluentlenium.adapter.kotest.MyAnnotation
import io.fluentlenium.adapter.kotest.OtherAnnotation
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

@MyAnnotation
class WordMetadataSpec : FluentWordSpec({
    "Annotated Spec" should {

        "can access existing annotation" {
            getClassAnnotation(MyAnnotation::class.java).shouldNotBeNull()
        }

        "access non existing annotation fails" {
            shouldThrow<AnnotationNotFoundException> {
                getClassAnnotation(OtherAnnotation::class.java)
            }
        }

        "getMethodAnnotation should fail" {
            shouldThrow<AnnotationNotFoundException> {
                getMethodAnnotation(MyAnnotation::class.java)
            }
        }

        "methodName should be available" {
            testMethodName shouldBe "methodName should be available"
        }

        "spec should be available" {
            testClass shouldBe WordMetadataSpec::class.java
        }
    }
})
