package io.fluentlenium.adapter.spock.description

import io.github.bonigarcia.wdm.managers.ChromeDriverManager
import io.fluentlenium.adapter.spock.FluentSpecification

import static org.assertj.core.api.Assertions.assertThat

class TestDescriptionAccessSpec extends FluentSpecification {

    def TEST_METHOD = "shouldRetrieveNames"
    def TEST_CLASS = TestDescriptionAccessSpec.class

    def setupSpec() {
        ChromeDriverManager.chromedriver().setup()
    }

    def setup() {
        assertThat(getTestClass()).isEqualTo(TEST_CLASS)
        assertThat(getTestMethodName()).isEqualTo(TEST_METHOD)
    }

    def shouldRetrieveNames() {
        expect:
        assertThat(getTestClass()).isEqualTo(TEST_CLASS)
        assertThat(getTestMethodName()).isEqualTo(TEST_METHOD)
    }

    def cleanup() {
        assertThat(getTestClass()).isEqualTo(TEST_CLASS)
        assertThat(getTestMethodName()).isEqualTo(TEST_METHOD)
    }

}
