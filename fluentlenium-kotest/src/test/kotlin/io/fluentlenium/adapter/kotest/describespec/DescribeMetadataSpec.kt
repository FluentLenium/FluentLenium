package io.fluentlenium.adapter.kotest.describespec

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.fluentlenium.adapter.exception.AnnotationNotFoundException
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

        shouldThrow<_root_ide_package_.io.fluentlenium.adapter.exception.AnnotationNotFoundException> {
            getClassAnnotation(OtherAnnotation::class.java).shouldNotBeNull()
        }
    }

    it("testGetMethodAnnotation") {
        shouldThrow<_root_ide_package_.io.fluentlenium.adapter.exception.AnnotationNotFoundException> {
            getMethodAnnotation(OtherAnnotation::class.java).shouldNotBeNull()
        }
    }
})
