package org.fluentlenium.kotest.matchers.element

import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.fluentlenium.kotest.matchers.config.MatcherBase
import org.fluentlenium.kotest.matchers.config.shouldAssert

class WebElementMatchersSpec : MatcherBase({
    "enabled" {
        el("#alertButton").element.shouldBeEnabled()

        el("#disabledElement").element.shouldNotBeEnabled()
    }

    "displayed" {
        el("h1").element.let {
            it should beDisplayed()
            it.shouldBeDisplayed()
        }

        el("#hiddenElement").element.let {
            it shouldNot beDisplayed()
            it.shouldNotBeDisplayed()
        }
    }

    "tagName" {
        el("h1").element.let {
            it.shouldHaveTagName("h1")
            it.shouldNotHaveTagName("h2")

            shouldAssert {
                it.shouldHaveTagName("h2")
            }

            shouldAssert {
                it.shouldNotHaveTagName("h1")
            }
        }
    }

    "haveAttribute" {
        el("#choice #first").element.let {
            it should haveAttribute("value")
            it.shouldHaveAttribute("value")

            it shouldNot haveAttribute("other")
            it.shouldNotHaveAttribute("other")

            it should haveAttributeValue("value", "first")
            it.shouldHaveAttributeValue("value", "first")
            it.shouldHaveAttributeValue("value" to "first")

            it shouldNot haveAttributeValue("value", "other")
            it.shouldNotHaveAttributeValue("value", "other")
            it.shouldNotHaveAttributeValue("value" to "other")
        }
    }

    "selected" {
        el("#choice #second").element.let {
            it should beSelected()
            it.shouldBeSelected()
        }

        el("#choice #first").element.let {
            it shouldNot beSelected()

            it.shouldNotBeSelected()
        }
    }
})
