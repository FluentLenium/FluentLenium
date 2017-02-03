package org.fluentlenium.example.spock

import io.github.bonigarcia.wdm.FirefoxDriverManager
import org.fluentlenium.adapter.spock.FluentSpecification
import org.fluentlenium.core.hook.wait.Wait

@Wait
class DuckDuckGoSpec extends FluentSpecification {

    void setupSpec() {
        FirefoxDriverManager.getInstance().setup()
    }

    def "Title of duck duck go"() {
        given:
        goTo("https://duckduckgo.com")

        when:
        $("#search_form_input_homepage").fill().with("FluentLenium")
        $("#search_button_homepage").submit()
        await().explicitlyFor(5000L)

        then:
        window().title().contains("FluentLenium")
    }

}
