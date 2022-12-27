package io.fluentlenium.adapter.kotest.wordspec

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.fluentlenium.adapter.kotest.FluentWordSpec
import io.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import io.fluentlenium.adapter.kotest.jq

class ExampleWordSpec : FluentWordSpec({
    "Word Spec" should {
        "Title shoule be correct" {
            goTo(DEFAULT_URL)
            jq("#name").fill().with("FluentLenium")
            el("#name").value() shouldBe "FluentLenium"
            window().title() shouldContain "Fluent"
        }
    }
})
