package org.fluentlenium.kotest.matchers

import io.kotest.matchers.should
import org.fluentlenium.adapter.kotest.jq
import org.fluentlenium.kotest.matchers.config.MatcherBase
import org.fluentlenium.kotest.matchers.el.haveTagName
import org.fluentlenium.kotest.matchers.el.shouldHaveTagName
import org.fluentlenium.kotest.matchers.jq.shouldHaveTagName
import org.fluentlenium.kotest.matchers.jq.haveTagName as jqHaveTagName

class CombinedExampleSpec : MatcherBase({
    "matchers with same name need to renamed to avoid amigbuity" {
        el("h1") should haveTagName("h1")
        jq("h1") should jqHaveTagName("h1")

        // no problem using the extenstion functions
        el("h1").shouldHaveTagName("h1")
        jq("h1").shouldHaveTagName("h1")
    }
})
