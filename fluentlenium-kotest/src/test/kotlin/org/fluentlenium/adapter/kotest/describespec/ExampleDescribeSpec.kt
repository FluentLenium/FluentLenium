package org.fluentlenium.adapter.kotest.describespec

import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.adapter.kotest.jq

class ExampleDescribeSpec : FluentDescribeSpec({
    it("Title of duck duck go") {
        goTo("https://duckduckgo.com")

        jq("#search_form_input_homepage").fill().with("FluentLenium")
        jq("#search_button_homepage").submit()

        window().title() shouldContain "FluentLenium"
    }
})
