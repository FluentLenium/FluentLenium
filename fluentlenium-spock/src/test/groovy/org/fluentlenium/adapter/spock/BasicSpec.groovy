package org.fluentlenium.adapter.spock

import io.github.bonigarcia.wdm.managers.ChromeDriverManager
import org.spockframework.runtime.ConditionNotSatisfiedError
import spock.lang.FailsWith

class BasicSpec extends FluentSpecification {

    def setupSpec() {
        ChromeDriverManager.chromedriver().setup()
    }

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
    def 'test failure'() {
        expect:
        false
    }

}
