package io.fluentlenium.example.spock

import io.github.bonigarcia.wdm.managers.ChromeDriverManager
import io.fluentlenium.adapter.spock.FluentSpecification
import io.fluentlenium.core.hook.wait.Wait
import org.openqa.selenium.Capabilities
import org.openqa.selenium.Cookie
import org.openqa.selenium.chrome.ChromeOptions

@Wait
class DuckDuckGoSpec extends FluentSpecification {
    def SEARCH_TEXT = "FluentLenium"
    def SCREENSHOT_TEMP_PATH = "/tmp"
    def PNG_FILTER = new PngFilter()

    def setupSpec() {
        ChromeDriverManager.chromedriver().setup()
    }

    @Override
    String getWebDriver() {
        return "chrome"
    }

    @Override
    Capabilities getCapabilities() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true)
        return chromeOptions
    }

    def "Title of duck duck go"() {
        given:
        goTo("https://duckduckgo.com")

        when:
        el("#search_form_input_homepage").fill().with(SEARCH_TEXT)
        el("#search_button_homepage").submit()
        await().untilWindow(SEARCH_TEXT)

        then:
        window().title().contains(SEARCH_TEXT)
    }

    def "Should take screenshot"() {
        given:
        goTo("https://awesome-testing.com")
        setScreenshotPath(SCREENSHOT_TEMP_PATH)
        getFiles().size() == 0

        when:
        takeScreenshot()

        then:
        getFiles().size() > 0

        cleanup:
        new File("$SCREENSHOT_TEMP_PATH/${-> getFiles().first()}").delete()
    }

    def "Should set cookie"() {
        given:
        goTo("https://awesome-testing.com")

        when:
        getDriver().manage().addCookie(new Cookie("my", "cookie"))
        getDriver().navigate().refresh()

        then:
        getCookie("my").value == "cookie"
    }

    String[] getFiles() {
        File tempFolder = new File(SCREENSHOT_TEMP_PATH)
        return tempFolder.list(PNG_FILTER)
    }

}
