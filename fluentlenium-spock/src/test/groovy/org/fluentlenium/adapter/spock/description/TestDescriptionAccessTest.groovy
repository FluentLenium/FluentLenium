package org.fluentlenium.adapter.spock.description

import org.fluentlenium.adapter.spock.FluentSpecification

import static org.assertj.core.api.Assertions.assertThat

class TestDescriptionAccessTest extends FluentSpecification {

    def TEST_METHOD = "shouldRetrieveNames"
    def TEST_CLASS = TestDescriptionAccessTest.class

    def setup() {
        assertThat(getTestClass()).isEqualTo(TEST_CLASS)
        assertThat(getTestMethodName()).isEqualTo(TEST_METHOD)
    }

    def shouldRetrieveNames() {
        when:
        'all set'

        then:
        assertThat(getTestClass()).isEqualTo(TEST_CLASS)
        assertThat(getTestMethodName()).isEqualTo(TEST_METHOD)
    }

    def cleanup() {
        assertThat(getTestClass()).isEqualTo(TEST_CLASS)
        assertThat(getTestMethodName()).isEqualTo(TEST_METHOD)
    }

}
