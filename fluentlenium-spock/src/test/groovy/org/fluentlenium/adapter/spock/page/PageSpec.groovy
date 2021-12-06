package org.fluentlenium.adapter.spock.page

import io.github.bonigarcia.wdm.managers.ChromeDriverManager
import org.fluentlenium.adapter.spock.FluentSpecification
import org.fluentlenium.core.annotation.Page

class PageSpec extends FluentSpecification {

    @Page
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
