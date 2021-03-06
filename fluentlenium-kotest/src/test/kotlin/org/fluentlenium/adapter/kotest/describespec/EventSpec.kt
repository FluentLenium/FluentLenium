package org.fluentlenium.adapter.kotest.describespec

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import org.fluentlenium.core.events.annotations.AfterNavigateTo

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
