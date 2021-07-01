package org.fluentlenium.kotest.matchers.el

import io.kotest.matchers.shouldNot
import org.fluentlenium.core.hook.wait.Wait
import org.fluentlenium.kotest.matchers.config.MatcherBase

@Wait(timeout = 1)
class FluentWebElementWaitHookSpec : MatcherBase({
    "can check for absent elements when using waithook" {
        el("#doesNotExist") shouldNot bePresent()
    }
})
