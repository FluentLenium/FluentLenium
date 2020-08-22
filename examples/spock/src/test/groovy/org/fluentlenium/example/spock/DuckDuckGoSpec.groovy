package org.fluentlenium.example.spock

import org.fluentlenium.adapter.spock.FluentSpecification
import org.fluentlenium.core.hook.wait.Wait
import org.openqa.selenium.Cookie

@Wait
class DuckDuckGoSpec extends FluentSpecification {
    private static final String SEARCH_TEXT = "FluentLenium"
    private static final String SCREENSHOT_TEMP_PATH = "/tmp"
    private static final PngFilter PNG_FILTER = new PngFilter()

    /**
     * This can't be done unfortunately in Groovy. See below link for details
     * https://stackoverflow.com/questions/15884190/is-it-possible-to-use-groovy-to-override-a-method-in-a-java-class-when-that-jav/15887199
     *
     * The method below has no effect.
     * Use fluentlenium.properties for Spock config
     */
    @Override
    String getWebDriver() {
        return null
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
