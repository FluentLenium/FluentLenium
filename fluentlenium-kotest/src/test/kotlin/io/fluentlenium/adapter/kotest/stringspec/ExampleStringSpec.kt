package io.fluentlenium.adapter.kotest.stringspec

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.fluentlenium.adapter.kotest.FluentStringSpec
import io.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import io.fluentlenium.adapter.kotest.jq

class ExampleStringSpec : FluentStringSpec({
    "Title should be correct" {
        goTo(DEFAULT_URL)
        jq("#name").fill().with("FluentLenium")
        el("#name").value() shouldBe "FluentLenium"
        window().title() shouldContain "Fluent"
    }
})
