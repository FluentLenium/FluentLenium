package org.fluentlenium.adapter.kotest.annotationspec

import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.FluentAnnotationSpec
import org.fluentlenium.adapter.kotest.jq

class ExampleAnnotationSpec : FluentAnnotationSpec() {
    @Test
    fun queryDuckDuckGo() {
        goTo("https://duckduckgo.com")
        jq("#search_form_input_homepage").fill().with("FluentLenium")
        jq("#search_button_homepage").submit()
        window().title() shouldContain "FluentLenium"
    }
}
