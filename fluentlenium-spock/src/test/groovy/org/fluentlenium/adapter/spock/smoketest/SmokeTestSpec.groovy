package org.fluentlenium.adapter.spock.smoketest

import io.github.bonigarcia.wdm.managers.ChromeDriverManager
import org.fluentlenium.adapter.spock.FluentSpecification
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.events.EventFiringWebDriver

import static org.assertj.core.api.Assertions.assertThat

class SmokeTestSpec extends FluentSpecification {

    def setupSpec() {
        ChromeDriverManager.chromedriver().setup()
    }

    def "smokeTest" () {
        expect:
        assertThat(getDriver()).isInstanceOf(EventFiringWebDriver.class)
        EventFiringWebDriver driver = (EventFiringWebDriver) getDriver()
        assertThat(driver.getWrappedDriver()).isInstanceOf(ChromeDriver.class)
    }

}

