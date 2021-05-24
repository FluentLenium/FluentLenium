package org.fluentlenium.adapter.kotest.featurespec

import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.*

class ExampleFeatureSpec : FluentFeatureSpec({
    feature("Free Spec") {
        scenario("Title of duck duck go") {
            goTo("https://duckduckgo.com")

            jq("#search_form_input_homepage").fill().with("FluentLenium")
            jq("#search_button_homepage").submit()

            window().title() shouldContain "FluentLenium"
        }
    }
})
