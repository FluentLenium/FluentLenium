package io.fluentlenium.adapter.kotest.featurespec

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.fluentlenium.adapter.kotest.FluentFeatureSpec
import io.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import io.fluentlenium.adapter.kotest.jq

class ExampleFeatureSpec : FluentFeatureSpec({
    feature("Free Spec") {
        scenario("Title should be correct") {
            goTo(DEFAULT_URL)
            jq("#name").fill().with("FluentLenium")
            el("#name").value() shouldBe "FluentLenium"
            window().title() shouldContain "Fluent"
        }
    }
})
