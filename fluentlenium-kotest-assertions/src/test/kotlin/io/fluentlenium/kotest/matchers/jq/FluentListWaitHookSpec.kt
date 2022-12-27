package io.fluentlenium.kotest.matchers.jq

import io.kotest.matchers.shouldNot
import org.fluentlenium.adapter.kotest.jq
import io.fluentlenium.core.hook.wait.Wait
import org.fluentlenium.kotest.matchers.config.MatcherBase

@_root_ide_package_.io.fluentlenium.core.hook.wait.Wait(timeout = 1)
class FluentListWaitHookSpec : MatcherBase({
    "can check for absent elements when using waithook" {
        jq("#doesNotExist") shouldNot bePresent()
    }
})
