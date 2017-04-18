package org.fluentlenium.example.spock

import io.github.bonigarcia.wdm.FirefoxDriverManager
import org.fluentlenium.adapter.spock.FluentSpecification
import org.fluentlenium.core.hook.wait.Wait

@Wait
class DuckDuckGoSpec extends FluentSpecification {
    public static final String SEARCH_TEXT = "FluentLenium"

    void setupSpec() {
        FirefoxDriverManager.getInstance().setup()
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
