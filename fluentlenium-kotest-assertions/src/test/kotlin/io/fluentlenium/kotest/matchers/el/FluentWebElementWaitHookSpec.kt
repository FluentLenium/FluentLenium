package io.fluentlenium.kotest.matchers.el

import io.fluentlenium.core.hook.wait.Wait
import io.fluentlenium.kotest.matchers.config.MatcherBase
import io.kotest.matchers.shouldNot

@Wait(timeout = 1)
class FluentWebElementWaitHookSpec : MatcherBase({
    "can check for absent elements when using waithook" {
        el("#doesNotExist") shouldNot bePresent()
    }

    "can check for not displayed elements when using waithook" {
        el("#non_display") shouldNot beDisplayed()
    }

    "can check for not enabled elements when using waithook" {
        el("#disabled") shouldNot beEnabled()
    }

    "can check for not selected elements when using waithook" {
        el("#not_selected") shouldNot beSelected()
    }
})
