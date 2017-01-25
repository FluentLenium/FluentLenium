package org.fluentlenium.adapter.spock.page

import org.fluentlenium.adapter.spock.FluentSpecification
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver

class FluentSpecificationPageSpec extends FluentSpecification {

    private Page1 page1
    private Page2 page2

    @Override
    WebDriver newWebDriver() {
        return new HtmlUnitDriver(true)
    }

    void setup() {
        page1 = newInstance(Page1)
        page2 = newInstance(Page2)
    }

    def "Open Page1 and click to navigate to Page2"() {
        given:
        goTo(page1)

        when:
        $("a#linkToPage2").click()

        then:
        window().title() == "Page 2"
    }
}
