package io.fluentlenium.adapter.kotest.behaviorspec

import io.fluentlenium.adapter.exception.AnnotationNotFoundException
import io.fluentlenium.adapter.kotest.FluentBehaviorSpec
import io.fluentlenium.adapter.kotest.MyAnnotation
import io.fluentlenium.adapter.kotest.OtherAnnotation
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

@MyAnnotation
class BehaviorMetadataSpec : FluentBehaviorSpec({
    given("Annotated Fluent Behavior Spec") {
        `when`("accessing class annotations") {
            then("can access existing annotation") {
                getClassAnnotation(MyAnnotation::class.java).shouldNotBeNull()
            }

            then("access non existing annotation fails") {
                shouldThrow<AnnotationNotFoundException> {
                    getClassAnnotation(OtherAnnotation::class.java)
                }
            }
        }

        `when`("accessing method annotations") {
            then("should fail") {
                shouldThrow<AnnotationNotFoundException> {
                    getMethodAnnotation(MyAnnotation::class.java)
                }
            }
        }

        `when`("accessing test metadata") {
            then("methodName should be available") {
                testMethodName shouldBe "methodName should be available"
            }

            then("spec should be available") {
                testClass shouldBe BehaviorMetadataSpec::class.java
            }
        }
    }
})
