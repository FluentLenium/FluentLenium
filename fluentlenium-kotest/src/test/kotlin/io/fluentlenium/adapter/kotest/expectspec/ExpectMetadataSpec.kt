package io.fluentlenium.adapter.kotest.expectspec

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.fluentlenium.adapter.exception.AnnotationNotFoundException
import org.fluentlenium.adapter.kotest.FluentExpectSpec
import org.fluentlenium.adapter.kotest.MyAnnotation
import org.fluentlenium.adapter.kotest.OtherAnnotation

@MyAnnotation
class ExpectMetadataSpec : FluentExpectSpec({

    context("Annotated Spec") {
        expect("can access existing annotation") {
            getClassAnnotation(MyAnnotation::class.java).shouldNotBeNull()
        }

        expect("access non existing annotation fails") {
            shouldThrow<_root_ide_package_.io.fluentlenium.adapter.exception.AnnotationNotFoundException> {
                getClassAnnotation(OtherAnnotation::class.java)
            }
        }

        expect("getMethodAnnotation should fail") {
            shouldThrow<_root_ide_package_.io.fluentlenium.adapter.exception.AnnotationNotFoundException> {
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
