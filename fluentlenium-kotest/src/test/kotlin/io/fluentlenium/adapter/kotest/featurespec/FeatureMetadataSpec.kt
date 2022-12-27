package io.fluentlenium.adapter.kotest.featurespec

import io.fluentlenium.adapter.exception.AnnotationNotFoundException
import io.fluentlenium.adapter.kotest.FluentFeatureSpec
import io.fluentlenium.adapter.kotest.MyAnnotation
import io.fluentlenium.adapter.kotest.OtherAnnotation
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

@MyAnnotation
class FeatureMetadataSpec : FluentFeatureSpec({
    feature("Annotated Feature Spec") {
        scenario("can access existing annotation") {
            getClassAnnotation(MyAnnotation::class.java).shouldNotBeNull()
        }

        scenario("access non existing annotation fails") {
            shouldThrow<AnnotationNotFoundException> {
                getClassAnnotation(OtherAnnotation::class.java)
            }
        }

        scenario("getMethodAnnotation should fail") {
            shouldThrow<AnnotationNotFoundException> {
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
