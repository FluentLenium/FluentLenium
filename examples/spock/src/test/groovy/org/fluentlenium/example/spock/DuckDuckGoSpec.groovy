package org.fluentlenium.example.spock

import org.fluentlenium.adapter.spock.FluentSpecification
import org.fluentlenium.core.hook.wait.Wait
import org.openqa.selenium.Capabilities
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.remote.DesiredCapabilities

@Wait
class DuckDuckGoSpec extends FluentSpecification {
    public static final String SEARCH_TEXT = "FluentLenium"

    @Override
    String getWebDriver() {
        return new ChromeDriver()
    }

    @Override
    Capabilities getCapabilities() {
        return DesiredCapabilities.chrome()
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

}
