package org.fluentlenium.adapter.spock.smoketest

import org.fluentlenium.adapter.spock.FluentSpecification
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.openqa.selenium.support.events.EventFiringWebDriver

import static org.assertj.core.api.Assertions.assertThat

class SmokeTestGetWebDriverSpec extends FluentSpecification {

    @Override
    String getWebDriver() {
        return "htmlunit"
    }

    def "smokeTest" () {
        expect:
        assertThat(getDriver()).isInstanceOf(EventFiringWebDriver.class)
        EventFiringWebDriver driver = (EventFiringWebDriver) getDriver()
        assertThat(driver.getWrappedDriver()).isInstanceOf(HtmlUnitDriver.class)
    }

}

