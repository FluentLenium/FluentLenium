package org.fluentlenium.kotest.matchers.alert

import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.fluentlenium.adapter.kotest.jq
import org.fluentlenium.kotest.matchers.config.MatcherBase

class AlertMatchersSpec : MatcherBase(
    {
        "present" {
            jq("#alertButton").click()
            alert() should bePresent()
            alert().shouldBePresent()
            alert().accept()

            // we cannot really test absence using the matcher
            // alert() will already throw when no alert is present
        }

        "haveText" {
            jq("#alertButton").click()

            alert() should haveText("Hello! I am an alert box")
            alert().shouldHaveText("Hello! I am an alert box")
            alert() shouldNot haveText("Something else")
            alert().shouldNotHaveText("Something else")
        }
    })
