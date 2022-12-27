package io.fluentlenium.kotest.matchers.alert

import io.fluentlenium.adapter.kotest.jq
import io.fluentlenium.kotest.matchers.config.MatcherBase
import io.fluentlenium.kotest.matchers.config.shouldAssert
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot

class AlertMatchersSpec : MatcherBase({
    "present" {
        jq("#alertButton").click()
        alert() should bePresent()
        alert().shouldBePresent()

        alert().accept()

        // we cannot really test absence using the matcher
        // alert() will already throw when no alert is present
    }

    "presentNegative" {
        jq("#alertButton").click()

        shouldAssert {
            alert().shouldNotBePresent()
        }

        shouldAssert {
            alert() shouldNot bePresent()
        }
    }

    "haveText" {
        jq("#alertButton").click()

        alert() should haveText("Hello! I am an alert box")
        alert().shouldHaveText("Hello! I am an alert box")
        alert() shouldNot haveText("Something else")
        alert().shouldNotHaveText("Something else")
    }

    "haveTextNegative" {
        jq("#alertButton").click()

        shouldAssert {
            alert() should haveText("should fail")
        }

        shouldAssert {
            alert().shouldHaveText("should fail")
        }

        shouldAssert {
            alert() shouldNot haveText("Hello! I am an alert box")
        }
        shouldAssert {
            alert().shouldNotHaveText("Hello! I am an alert box")
        }
    }
})
