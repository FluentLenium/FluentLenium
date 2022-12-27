package io.fluentlenium.adapter.spock.annotation

import io.fluentlenium.adapter.spock.FluentSpecification
import io.github.bonigarcia.wdm.managers.ChromeDriverManager

@CustomAnnotation("for_class")
class TestAnnotationAccessSpec extends FluentSpecification {

    def setupSpec() {
        ChromeDriverManager.chromedriver().setup()
    }

    def shouldGetClassAnnotation() {
        expect:
        getClassAnnotation(CustomAnnotation).value() == "for_class"
    }

    def shouldThrowOnEmptyClassAnnotation() {
        when:
        getClassAnnotation(DummyAnnotation)

        then:
        thrown(io.fluentlenium.adapter.exception.AnnotationNotFoundException)
    }

    @CustomAnnotation("for_method")
    def shouldGetMethodAnnotation() {
        expect:
        getMethodAnnotation(CustomAnnotation).value() == "for_method"
    }

    def shouldThrowOnEmptyMethodAnnotation() {
        when:
        getMethodAnnotation(CustomAnnotation)

        then:
        thrown(io.fluentlenium.adapter.exception.AnnotationNotFoundException)
    }

}
