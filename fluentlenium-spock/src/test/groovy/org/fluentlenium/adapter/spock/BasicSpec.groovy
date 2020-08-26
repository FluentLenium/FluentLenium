package org.fluentlenium.adapter.spock

import org.spockframework.runtime.ConditionNotSatisfiedError
import spock.lang.FailsWith

class BasicSpec extends FluentSpecification {
    def "Run basic given-when-then test without exception "() {
        when:
        ''
        then:
        true
    }

    def "Run basic expect test without exception"() {
        expect:
        true
    }

    @FailsWith(ConditionNotSatisfiedError)
    def 'test failure'(){
        expect:
        false
    }

}
