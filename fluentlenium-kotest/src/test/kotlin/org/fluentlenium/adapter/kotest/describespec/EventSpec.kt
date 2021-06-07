package org.fluentlenium.adapter.kotest.describespec

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.core.events.annotations.AfterNavigateTo

class EventSpec: FluentDescribeSpec() {

    var url: String? = null

    @AfterNavigateTo
    fun afterNavigateTo(url: String) {
        this.url = url
    }

    init {
        it("Fluentlenium listeners are executed") {
            url.shouldBeNull()

            goTo("https://duckduckgo.com")

            url shouldBe "https://duckduckgo.com"
        }
    }
}