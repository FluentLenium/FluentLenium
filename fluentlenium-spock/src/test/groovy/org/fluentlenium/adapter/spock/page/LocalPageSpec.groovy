package org.fluentlenium.adapter.spock.page

import org.fluentlenium.adapter.spock.FluentSpecification
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement

class LocalPageSpec extends FluentSpecification {
    public static final String INPUT_VALUE = "1"
    private static String page1

    def setupSpec() {
        page1 = ClassLoader.getSystemResource("page1.html").toString()
    }

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
