package org.fluentlenium.it

import org.fluentlenium.adapter.spock.FluentSpecification
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver

class DefaultSpec extends FluentSpecification {
    private String inputsHtmlFile

    @Override
    WebDriver newWebDriver() {
        return new HtmlUnitDriver(true)
    }

    void setup() {
        inputsHtmlFile = ClassLoader.getSystemResource("inputs.html").toString()
    }

    def "test basic spock setup"() {
        when:
        '' // Need a when to compile the specification

        then:
        true
    }

    def "test with html file"() {
        given:
        goTo(inputsHtmlFile)

        when:
        el("input").fill().with("1")
        await().explicitlyFor(5000L)

        then:
        el("input").value() == "1"
    }
}
