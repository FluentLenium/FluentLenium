package io.fluentlenium.adapter.kotest.describespec

import io.kotest.matchers.shouldBe
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.adapter.kotest.TestConstants.DEFAULT_URL
import org.fluentlenium.adapter.kotest.hooks.Example
import org.fluentlenium.adapter.kotest.hooks.ExampleHook
import org.fluentlenium.adapter.kotest.jq
import io.fluentlenium.core.hook.wait.Wait

@_root_ide_package_.io.fluentlenium.core.hook.wait.Wait
@Example
class HookSpec : FluentDescribeSpec({
    it("counts clicks") {
        ExampleHook.clickCount = 0
        goTo(DEFAULT_URL)
        jq("#linkToPage2").click()
        ExampleHook.clickCount shouldBe 1
    }
})
