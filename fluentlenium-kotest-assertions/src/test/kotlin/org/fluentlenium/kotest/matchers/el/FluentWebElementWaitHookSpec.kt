package org.fluentlenium.kotest.matchers.el

import io.kotest.matchers.shouldNot
import org.fluentlenium.core.hook.wait.Wait
import org.fluentlenium.kotest.matchers.config.MatcherBase

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
