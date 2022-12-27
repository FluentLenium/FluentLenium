package io.fluentlenium.adapter.kotest.freespec

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.FluentFreeSpec
import org.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import org.fluentlenium.adapter.kotest.jq

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
