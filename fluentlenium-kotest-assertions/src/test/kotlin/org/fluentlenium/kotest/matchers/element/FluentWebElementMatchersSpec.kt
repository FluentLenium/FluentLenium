package org.fluentlenium.kotest.matchers.element

import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.fluentlenium.kotest.matchers.config.MatcherBase

class FluentWebElementMatchersSpec : MatcherBase({
    "present" {
        el("#doesNotExist") shouldNot bePresent()
        el("h1") should bePresent()
    }

    "enabled" {
        el("#alertButton") should beEnabled()
        el("#disabledButton") shouldNot beEnabled()
    }

    "displayed" {
        el("h1") should beDisplayed()
        el("#hiddenElement")should bePresent()
        el("#hiddenElement")shouldNot beDisplayed()
    }

    "clickable" {
        el("#alertButton") should beClickable()
        el("#disabledButton") shouldNot beClickable()
    }

    "selected" {
        el("#choice #second") should beSelected()
        el("#choice #first") shouldNot beSelected()
    }
})
