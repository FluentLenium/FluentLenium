package io.fluentlenium.adapter.spock.page


import io.github.bonigarcia.wdm.managers.ChromeDriverManager
import io.fluentlenium.adapter.spock.FluentSpecification
import io.fluentlenium.core.annotation.Page

class PageSpec extends FluentSpecification {

    @io.fluentlenium.core.annotation.Page
    private Page1 page1

    def setupSpec() {
        ChromeDriverManager.chromedriver().setup()
    }

    def "Open Page1 and click to navigate to Page2"() {
        given:
        goTo(page1)

        when:
        page1.clickLink()

        then:
        window().title() == "Page 2"
    }
}
