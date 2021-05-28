package org.fluentlenium.adapter.kotest.describespec

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.adapter.kotest.hooks.Example
import org.fluentlenium.adapter.kotest.hooks.ExampleHook
import org.fluentlenium.adapter.kotest.jq
import org.fluentlenium.core.hook.wait.Wait
import java.util.concurrent.TimeUnit

@Wait
@Example
class HookSpec : FluentDescribeSpec({
    it("titleOfDuckDuckGoShouldContainSearchQueryName") {
        ExampleHook.submitCount = 0

        goTo("https://duckduckgo.com")
        jq("#search_form_input_homepage").fill().with("FluentLenium")
        jq("#search_button_homepage").submit()
        await().atMost(5, TimeUnit.SECONDS).until(el("#search_form_homepage")).not().present()
        window().title() shouldContain "FluentLenium"

        ExampleHook.submitCount shouldBe 1
    }
})