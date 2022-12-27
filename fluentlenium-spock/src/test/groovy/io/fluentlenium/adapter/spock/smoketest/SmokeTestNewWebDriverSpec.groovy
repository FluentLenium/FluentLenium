package io.fluentlenium.adapter.spock.smoketest

import io.fluentlenium.adapter.spock.FluentSpecification
import io.github.bonigarcia.wdm.managers.ChromeDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

import static org.assertj.core.api.Assertions.assertThat

class SmokeTestNewWebDriverSpec extends FluentSpecification {

    def setupSpec() {
        ChromeDriverManager.chromedriver().setup()
    }

    @Override
    WebDriver newWebDriver() {
        ChromeOptions chromeOptions = new ChromeOptions()
        chromeOptions.setHeadless(true)
        return new ChromeDriver(chromeOptions)
    }

    def "smokeTest" () {
        expect:
        assertThat(getDriver()).isInstanceOf(ChromeDriver.class)
    }

}
