package org.fluentlenium.adapter.spock.page

import org.fluentlenium.adapter.spock.FluentSpecification

class PageSpec extends FluentSpecification {

    private Page1 page1
    private Page2 page2

    def setup() {
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
