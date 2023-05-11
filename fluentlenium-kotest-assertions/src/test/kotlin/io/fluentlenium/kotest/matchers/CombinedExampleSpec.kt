package io.fluentlenium.kotest.matchers

import io.fluentlenium.adapter.kotest.jq
import io.fluentlenium.kotest.matchers.config.MatcherBase
import io.fluentlenium.kotest.matchers.el.haveTagName
import io.fluentlenium.kotest.matchers.el.shouldHaveTagName
import io.fluentlenium.kotest.matchers.jq.shouldHaveTagName
import io.kotest.matchers.should
import io.fluentlenium.kotest.matchers.jq.haveTagName as jqHaveTagName

class CombinedExampleSpec : MatcherBase({
    "infix matchers with same name need to renamed to avoid ambiguity" {
        el("h1") should haveTagName("h1")
        jq("h1") should jqHaveTagName("h1")

        // no problem when using the extension functions
        el("h1").shouldHaveTagName("h1")
        jq("h1").shouldHaveTagName("h1")
    }
})
