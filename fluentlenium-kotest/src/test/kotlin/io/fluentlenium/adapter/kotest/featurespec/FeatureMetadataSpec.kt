package io.fluentlenium.adapter.kotest.featurespec

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.fluentlenium.adapter.exception.AnnotationNotFoundException
import org.fluentlenium.adapter.kotest.FluentFeatureSpec
import org.fluentlenium.adapter.kotest.MyAnnotation
import org.fluentlenium.adapter.kotest.OtherAnnotation

@MyAnnotation
class FeatureMetadataSpec : FluentFeatureSpec({
    feature("Annotated Feature Spec") {
        scenario("can access existing annotation") {
            getClassAnnotation(MyAnnotation::class.java).shouldNotBeNull()
        }

        scenario("access non existing annotation fails") {
            shouldThrow<_root_ide_package_.io.fluentlenium.adapter.exception.AnnotationNotFoundException> {
                getClassAnnotation(OtherAnnotation::class.java)
            }
        }

        scenario("getMethodAnnotation should fail") {
            shouldThrow<_root_ide_package_.io.fluentlenium.adapter.exception.AnnotationNotFoundException> {
                getMethodAnnotation(MyAnnotation::class.java)
            }
        }

        scenario("methodName should be available") {
            testMethodName shouldBe "methodName should be available"
        }

        scenario("spec should be available") {
            testClass shouldBe FeatureMetadataSpec::class.java
        }
    }
})
