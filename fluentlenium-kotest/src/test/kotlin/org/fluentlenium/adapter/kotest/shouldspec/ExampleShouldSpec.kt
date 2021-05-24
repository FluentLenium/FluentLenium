package org.fluentlenium.adapter.kotest.shouldspec

import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.FluentShouldSpec
import org.fluentlenium.adapter.kotest.jq

class ExampleShouldSpec : FluentShouldSpec({
    context("Should Spec") {
        should("Title of duck duck go") {
            goTo("https://duckduckgo.com")

            jq("#search_form_input_homepage").fill().with("FluentLenium")
            jq("#search_button_homepage").submit()

            window().title() shouldContain "FluentLenium"
        }
    }
})
