package org.fluentlenium.adapter.kotest.freespec

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.fluentlenium.adapter.exception.AnnotationNotFoundException
import org.fluentlenium.adapter.kotest.FluentFreeSpec
import org.fluentlenium.adapter.kotest.MyAnnotation
import org.fluentlenium.adapter.kotest.OtherAnnotation

@MyAnnotation
class FreeMetadataSpec : FluentFreeSpec({
    "Annotated Free Spec" - {
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
            testClass shouldBe FreeMetadataSpec::class.java
        }
    }
})
