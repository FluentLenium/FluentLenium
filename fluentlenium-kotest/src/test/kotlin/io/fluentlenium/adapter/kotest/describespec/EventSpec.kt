package io.fluentlenium.adapter.kotest.describespec

import io.fluentlenium.adapter.kotest.FluentDescribeSpec
import io.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import io.fluentlenium.core.events.annotations.AfterNavigateTo
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class EventSpec : FluentDescribeSpec() {

    var url: String? = null

    @AfterNavigateTo
    fun afterNavigateTo(url: String) {
        this.url = url
    }

    init {
        it("Fluentlenium listeners are executed") {
            url.shouldBeNull()
            goTo(DEFAULT_URL)
            url shouldBe DEFAULT_URL
        }
    }
}
