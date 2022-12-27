package io.fluentlenium.adapter.kotest.describespec

import io.kotest.matchers.shouldBe
import io.fluentlenium.adapter.kotest.FluentDescribeSpec
import io.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import io.fluentlenium.adapter.kotest.hooks.Example
import io.fluentlenium.adapter.kotest.hooks.ExampleHook
import io.fluentlenium.adapter.kotest.jq
import io.fluentlenium.core.hook.wait.Wait

@Wait
@Example
class HookSpec : FluentDescribeSpec({
    it("counts clicks") {
        ExampleHook.clickCount = 0
        goTo(DEFAULT_URL)
        jq("#linkToPage2").click()
        ExampleHook.clickCount shouldBe 1
    }
})
