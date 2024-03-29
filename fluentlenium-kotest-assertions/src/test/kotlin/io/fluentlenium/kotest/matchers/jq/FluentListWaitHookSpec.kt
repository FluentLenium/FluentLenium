package io.fluentlenium.kotest.matchers.jq

import io.fluentlenium.adapter.kotest.jq
import io.fluentlenium.core.hook.wait.Wait
import io.fluentlenium.kotest.matchers.config.MatcherBase
import io.kotest.matchers.shouldNot

@Wait(timeout = 1)
class FluentListWaitHookSpec : MatcherBase({
    "can check for absent elements when using waithook" {
        jq("#doesNotExist") shouldNot bePresent()
    }
})
