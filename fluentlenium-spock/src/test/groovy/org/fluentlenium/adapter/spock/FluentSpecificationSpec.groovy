package org.fluentlenium.adapter.spock

import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver

class FluentSpecificationSpec extends FluentSpecification {
    public static final String INPUT_VALUE = "1"
    private String page1

    @Override
    WebDriver newWebDriver() {
        return new HtmlUnitDriver(true)
    }

    void setup() {
        page1 = ClassLoader.getSystemResource("page1.html").toString()
    }

    def "Run basic test without exception"() {
        when:
        '' // Need a when to compile the specification

        then:
        true
    }

    def "Open html file and fill value into input tag"() {
        given:
        goTo(page1)

        when:
        FluentWebElement inputElement = el("input")
        inputElement.fill().with(INPUT_VALUE)
        await().until(inputElement).value(INPUT_VALUE)

        then:
        el("input").value() == INPUT_VALUE
    }

}
