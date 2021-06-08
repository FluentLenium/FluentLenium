package org.fluentlenium.kotest.matchers.element

import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.fluentlenium.kotest.matchers.config.MatcherBase

class FluentWebElementMatchersSpec : MatcherBase({
    "present" {
        el("#doesNotExist") shouldNot bePresent()
        el("#doesNotExist").shouldNotBePresent()

        el("h1") should bePresent()
        el("h1").shouldBePresent()
    }

    "enabled" {
        el("#alertButton") should beEnabled()
        el("#alertButton").shouldBeEnabled()

        el("#disabledButton") shouldNot beEnabled()
        el("#disabledButton").shouldNotBeEnabled()
    }

    "displayed" {
        el("h1") should beDisplayed()
        el("h1").shouldBeDisplayed()

        el("#hiddenElement") should bePresent()
        el("#hiddenElement") shouldNot beDisplayed()
        el("#hiddenElement").shouldNotBeDisplayed()
    }

    "clickable" {
        el("#alertButton") should beClickable()
        el("#alertButton").shouldBeClickable()

        el("#disabledButton") shouldNot beClickable()
        el("#disabledButton").shouldNotBeClickable()
    }

    "selected" {
        el("#choice #second") should beSelected()
        el("#choice #second").shouldBeSelected()

        el("#choice #first") shouldNot beSelected()
        el("#choice #first").shouldNotBeSelected()
    }
})
