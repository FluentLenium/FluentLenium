package org.fluentlenium.adapter.kotest.describespec

import io.kotest.matchers.shouldBe
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import org.fluentlenium.adapter.kotest.hooks.Example
import org.fluentlenium.adapter.kotest.hooks.ExampleHook
import org.fluentlenium.adapter.kotest.jq
import org.fluentlenium.core.hook.wait.Wait

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