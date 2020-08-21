package org.fluentlenium.adapter.spock

class BasicSpec extends FluentSpecification {
    def "Run basic given-when-then test without exception "() {
        when:
        ''
        then:
        true
    }

    def "Run basic when-then test without exception"() {
        given:
        when:
        ''
        then:
        true
    }

}
