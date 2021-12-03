package org.fluentlenium.adapter.kotest.describespec

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.fluentlenium.adapter.exception.AnnotationNotFoundException
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.adapter.kotest.MyAnnotation
import org.fluentlenium.adapter.kotest.OtherAnnotation

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
