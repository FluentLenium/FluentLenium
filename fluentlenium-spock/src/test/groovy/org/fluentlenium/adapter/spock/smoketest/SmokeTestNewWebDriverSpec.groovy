package org.fluentlenium.adapter.spock.smoketest

import org.fluentlenium.adapter.spock.FluentSpecification
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver

import static org.assertj.core.api.Assertions.assertThat

class SmokeTestNewWebDriverSpec extends FluentSpecification {

    @Override
    WebDriver newWebDriver() {
        return new HtmlUnitDriver()
    }

    def "smokeTest" () {
        expect:
        assertThat(getDriver()).isInstanceOf(HtmlUnitDriver.class)
    }

}
