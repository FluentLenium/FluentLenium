package io.fluentlenium.adapter.kotest.behaviorspec

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.fluentlenium.adapter.exception.AnnotationNotFoundException
import org.fluentlenium.adapter.kotest.FluentBehaviorSpec
import org.fluentlenium.adapter.kotest.MyAnnotation
import org.fluentlenium.adapter.kotest.OtherAnnotation

@MyAnnotation
class BehaviorMetadataSpec : FluentBehaviorSpec({
    given("Annotated Fluent Behavior Spec") {
        `when`("accessing class annotations") {
            then("can access existing annotation") {
                getClassAnnotation(MyAnnotation::class.java).shouldNotBeNull()
            }

            then("access non existing annotation fails") {
                shouldThrow<_root_ide_package_.io.fluentlenium.adapter.exception.AnnotationNotFoundException> {
                    getClassAnnotation(OtherAnnotation::class.java)
                }
            }
        }

        `when`("accessing method annotations") {
            then("should fail") {
                shouldThrow<_root_ide_package_.io.fluentlenium.adapter.exception.AnnotationNotFoundException> {
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
