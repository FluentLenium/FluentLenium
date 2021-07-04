package org.fluentlenium.kotest.matchers.jq

import io.kotest.matchers.shouldNot
import org.fluentlenium.adapter.kotest.jq
import org.fluentlenium.core.hook.wait.Wait
import org.fluentlenium.kotest.matchers.config.MatcherBase

@Wait(timeout = 1)
class FluentListWaitHookSpec : MatcherBase({
    "can check for absent elements when using waithook" {
        jq("#doesNotExist") shouldNot bePresent()
    }
})
