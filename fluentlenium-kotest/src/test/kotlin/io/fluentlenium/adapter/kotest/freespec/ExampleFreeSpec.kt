package io.fluentlenium.adapter.kotest.freespec

import io.fluentlenium.adapter.kotest.FluentFreeSpec
import io.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import io.fluentlenium.adapter.kotest.jq
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

class ExampleFreeSpec : FluentFreeSpec({
    "Free Spec" - {
        "Title should be correct" {
            goTo(DEFAULT_URL)
            jq("#name").fill().with("FluentLenium")
            el("#name").value() shouldBe "FluentLenium"
            window().title() shouldContain "Fluent"
        }
    }
})
