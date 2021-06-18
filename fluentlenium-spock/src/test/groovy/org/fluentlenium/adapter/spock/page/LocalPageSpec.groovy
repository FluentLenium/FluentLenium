package org.fluentlenium.adapter.spock.page

import org.fluentlenium.adapter.spock.FluentSpecification
import org.fluentlenium.core.annotation.Page
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement

class LocalPageSpec extends FluentSpecification {
    public static final String INPUT_VALUE = "1"

    @Page
    private Page1 page1;

    def "Open html file and fill value into input tag"() {
        given:
        goTo(page1)

        when:
        FluentList<FluentWebElement> inputElements = $("input")
        inputElements.first().fill().with(INPUT_VALUE)
        await().until(inputElements).value(INPUT_VALUE)

        then:
        url().contains("page1.html")
        el("input").value() == INPUT_VALUE
    }

}
