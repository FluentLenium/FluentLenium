package io.fluentlenium.adapter.kotest.describespec

import io.fluentlenium.adapter.kotest.FluentDescribeSpec
import io.fluentlenium.adapter.kotest.TestConstants
import io.fluentlenium.adapter.kotest.jq
import io.fluentlenium.core.hook.wait.Wait
import io.kotest.matchers.booleans.shouldBeFalse

@Wait(timeout = 1)
class WaitHookPresentSpec : FluentDescribeSpec({
    it("can test for absence when using wait hook") {
        goTo(TestConstants.DEFAULT_URL)
        el("#doesNotExist").present().shouldBeFalse()
        jq("#doesNotExist").present().shouldBeFalse()
    }
})
