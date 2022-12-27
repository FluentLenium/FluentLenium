package io.fluentlenium.adapter.kotest.annotationspec

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.fluentlenium.adapter.kotest.FluentAnnotationSpec
import io.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import io.fluentlenium.adapter.kotest.jq

class ExampleAnnotationSpec : FluentAnnotationSpec() {
    @Test
    fun queryDuckDuckGo() {
        goTo(DEFAULT_URL)
        jq("#name").fill().with("FluentLenium")
        el("#name").value() shouldBe "FluentLenium"
        window().title() shouldContain "Fluent"
    }
}
