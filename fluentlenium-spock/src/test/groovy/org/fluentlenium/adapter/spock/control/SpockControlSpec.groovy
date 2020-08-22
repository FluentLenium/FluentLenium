package org.fluentlenium.adapter.spock.control

import org.fluentlenium.adapter.spock.FluentSpecification
import org.fluentlenium.core.WrongDriverException
import org.openqa.selenium.WebDriver

class SpockControlSpec extends FluentSpecification {

    def 'should return driver'() {
        expect:
        getDriver() instanceof WebDriver
    }

    def 'should throw on getting appium driver'() {
        when:
        getAppiumDriver()

        then:
        thrown(WrongDriverException)
    }

    def 'should return capabilities'() {
        expect:
        capabilities().getCapability("browserName") == "htmlunit"
    }

    def 'should return browser'() {
        expect:
        getWebDriver() == "htmlunit"
    }

    def 'should set property'() {
        when:
        setCustomProperty("key", "value")
        then:
        getCustomProperty("key") == "value"
        true
    }

    def 'should return capabilities from config'() {
        expect:
        getCapabilities().getCapability("javascriptEnabled") == "true"
    }

}
