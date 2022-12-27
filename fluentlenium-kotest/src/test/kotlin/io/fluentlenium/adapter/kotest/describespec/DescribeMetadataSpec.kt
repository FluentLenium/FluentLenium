package io.fluentlenium.adapter.kotest.describespec

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.fluentlenium.adapter.exception.AnnotationNotFoundException
import io.fluentlenium.adapter.kotest.FluentDescribeSpec
import io.fluentlenium.adapter.kotest.MyAnnotation
import io.fluentlenium.adapter.kotest.OtherAnnotation

@MyAnnotation
class DescribeMetadataSpec : FluentDescribeSpec({

    it("testClass") {
        testClass shouldBe DescribeMetadataSpec::class.java
    }

    it("testMethodName") {
        testMethodName shouldBe "testMethodName"
    }

    it("testGetClassAnnotation") {
        getClassAnnotation(MyAnnotation::class.java).shouldNotBeNull()

        shouldThrow<AnnotationNotFoundException> {
            getClassAnnotation(OtherAnnotation::class.java).shouldNotBeNull()
        }
    }

    it("testGetMethodAnnotation") {
        shouldThrow<AnnotationNotFoundException> {
            getMethodAnnotation(OtherAnnotation::class.java).shouldNotBeNull()
        }
    }
})
