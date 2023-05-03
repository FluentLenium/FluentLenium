package io.fluentlenium.adapter.kotest.behaviorspec

import io.fluentlenium.adapter.kotest.FluentBehaviorSpec
import io.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import io.fluentlenium.adapter.kotest.jq
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

class ExampleBehaviorSpec : FluentBehaviorSpec({
    given("Behavior Spec") {
        `when`("browsing example page") {
            then("title can be accessed") {
                goTo(DEFAULT_URL)
                jq("#name").fill().with("FluentLenium")
                el("#name").value() shouldBe "FluentLenium"
                window().title() shouldContain "Fluent"
            }
        }
    }
})
