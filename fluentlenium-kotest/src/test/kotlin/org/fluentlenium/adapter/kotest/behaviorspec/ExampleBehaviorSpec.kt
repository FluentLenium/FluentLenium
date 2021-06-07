package org.fluentlenium.adapter.kotest.behaviorspec

import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.FluentBehaviorSpec
import org.fluentlenium.adapter.kotest.jq

class ExampleBehaviorSpec : FluentBehaviorSpec({
    given("Behavior Spec") {
        `when`("browsing duckduck go") {
            then("title can be accessed") {
                goTo("https://duckduckgo.com")

                jq("#search_form_input_homepage").fill().with("FluentLenium")
                jq("#search_button_homepage").submit()

                window().title() shouldContain "FluentLenium"
            }
        }
    }
})
