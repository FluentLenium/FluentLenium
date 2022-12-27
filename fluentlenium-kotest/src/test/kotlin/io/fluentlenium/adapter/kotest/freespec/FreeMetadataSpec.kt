package io.fluentlenium.adapter.kotest.freespec

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.fluentlenium.adapter.exception.AnnotationNotFoundException
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
            shouldThrow<_root_ide_package_.io.fluentlenium.adapter.exception.AnnotationNotFoundException> {
                getClassAnnotation(OtherAnnotation::class.java)
            }
        }

        "getMethodAnnotation should fail" {
            shouldThrow<_root_ide_package_.io.fluentlenium.adapter.exception.AnnotationNotFoundException> {
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
