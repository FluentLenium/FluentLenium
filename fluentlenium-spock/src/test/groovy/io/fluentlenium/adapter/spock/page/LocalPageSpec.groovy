package io.fluentlenium.adapter.spock.page


import io.github.bonigarcia.wdm.managers.ChromeDriverManager
import io.fluentlenium.adapter.spock.FluentSpecification
import io.fluentlenium.core.annotation.Page
import io.fluentlenium.core.domain.FluentList
import io.fluentlenium.core.domain.FluentWebElement

class LocalPageSpec extends FluentSpecification {
    public static final String INPUT_VALUE = "1"

    def setupSpec() {
        ChromeDriverManager.chromedriver().setup()
    }

    @io.fluentlenium.core.annotation.Page
    private Page1 page1

    def "Open html file and fill value into input tag"() {
        given:
        goTo(page1)

        when:
        io.fluentlenium.core.domain.FluentList<io.fluentlenium.core.domain.FluentWebElement> inputElements = $("input")
        inputElements.first().fill().with(INPUT_VALUE)
        await().until(inputElements).value(INPUT_VALUE)

        then:
        url().contains("page1.html")
        el("input").value() == INPUT_VALUE
    }

}
