package org.fluentlenium.adapter.spock.annotation

import io.github.bonigarcia.wdm.managers.ChromeDriverManager
import org.fluentlenium.adapter.exception.AnnotationNotFoundException
import org.fluentlenium.adapter.spock.FluentSpecification

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
        thrown(AnnotationNotFoundException)
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
        thrown(AnnotationNotFoundException)
    }

}
