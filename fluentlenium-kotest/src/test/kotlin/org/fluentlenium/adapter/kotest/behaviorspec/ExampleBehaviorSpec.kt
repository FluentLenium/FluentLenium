package org.fluentlenium.adapter.kotest.behaviorspec

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.FluentBehaviorSpec
import org.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import org.fluentlenium.adapter.kotest.jq

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
