package org.fluentlenium.adapter.spock.control

import io.github.bonigarcia.wdm.managers.ChromeDriverManager
import org.fluentlenium.adapter.spock.FluentSpecification
import org.fluentlenium.adapter.spock.page.Page1
import org.fluentlenium.adapter.spock.page.Page2
import org.fluentlenium.core.WrongDriverException
import org.fluentlenium.core.action.KeyboardActions
import org.fluentlenium.core.action.MouseActions
import org.fluentlenium.core.css.CssSupport
import org.fluentlenium.core.events.EventsRegistry
import org.fluentlenium.core.performance.PerformanceTiming
import org.fluentlenium.utils.chromium.ChromiumApi
import org.openqa.selenium.NoAlertPresentException
import org.openqa.selenium.WebDriver

import static org.hamcrest.CoreMatchers.not
import static org.hamcrest.CoreMatchers.is
import static spock.util.matcher.HamcrestSupport.expect

class SpockControlSpec extends FluentSpecification {

    def setupSpec() {
        ChromeDriverManager.chromedriver().setup()
    }

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

    def 'should throw on getting chromium API'() {
        expect:
        getChromiumApi() instanceof ChromiumApi
    }

    def 'should return capabilities'() {
        expect:
        capabilities().getCapability("browserName") == "chrome"
    }

    def 'should return browser'() {
        expect:
        getWebDriver() == "chrome"
    }

    def 'should set property'() {
        when:
        setCustomProperty("key", "value")
        then:
        getCustomProperty("key") == "value"
        true
    }

    def 'should return capabilities from config'() {
        when:
        def caps = getCapabilities().getCapability("goog:chromeOptions")

        then:
        expect caps, is(not(null))
    }

    def 'shuld return performance timing'() {
        expect:
        performanceTiming() instanceof PerformanceTiming
    }

    def 'shuld return keyboard'() {
        expect:
        keyboard() instanceof KeyboardActions
    }

    def 'shuld throw on alert'() {
        when:
        alert()

        then:
        thrown(NoAlertPresentException)
    }

    def 'shuld return mouse'() {
        expect:
        mouse() instanceof MouseActions
    }

    def 'shuld return css support'() {
        expect:
        css() instanceof CssSupport
    }

    def 'shuld return events registry'() {
        expect:
        eventsEnabled
        events() instanceof EventsRegistry
    }

    def 'should execute script'() {
        setup:
        Page1 page1 = newInstance(Page1)
        goTo(page1)

        when:
        executeScript("window.open('', '_blank');")

        then:
        getDriver().getWindowHandles().size() == 2
        true
    }

    def 'should switch windows'() {
        setup:
        Page1 page1 = newInstance(Page1)
        Page2 page2 = newInstance(Page2)

        and:
        goTo(page1)
        String initialWindow = getDriver().getWindowHandle()

        when:
        goToInNewTab(page2.getUrl())

        then:
        url().contains("page2.html")
        window().switchTo(initialWindow)
        url().contains("page1.html")
    }

    def 'should change config values'() {
        when:
        setAwaitAtMost(10l)
        setDeleteCookies(false)

        then:
        awaitAtMost == 10l
        !deleteCookies
    }

}
