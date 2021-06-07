package org.fluentlenium.adapter.kotest.wordspec

import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.FluentWordSpec
import org.fluentlenium.adapter.kotest.jq

class ExampleWordSpec : FluentWordSpec({
    "Word Spec" should {
        "Title of duck duck go" {
            goTo("https://duckduckgo.com")

            jq("#search_form_input_homepage").fill().with("FluentLenium")
            jq("#search_button_homepage").submit()

            window().title() shouldContain "FluentLenium"
        }
    }
})
