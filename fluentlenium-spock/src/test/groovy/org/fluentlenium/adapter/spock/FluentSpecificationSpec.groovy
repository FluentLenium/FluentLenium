package org.fluentlenium.adapter.spock

import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver

class FluentSpecificationSpec extends FluentSpecification {
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
        el("input").fill().with("1")
        await().explicitlyFor(5000L)

        then:
        el("input").value() == "1"
    }


}
