package org.fluentlenium.adapter.kotest.describespec

import io.kotest.matchers.booleans.shouldBeFalse
import org.fluentlenium.adapter.kotest.FluentDescribeSpec
import org.fluentlenium.adapter.kotest.TestConstants
import org.fluentlenium.adapter.kotest.jq
import org.fluentlenium.core.hook.wait.Wait

@Wait(timeout = 1)
class WaitHookPresentSpec : FluentDescribeSpec({
    it("can test for absence when using wait hook") {
        goTo(TestConstants.DEFAULT_URL)
        el("#doesNotExist").present().shouldBeFalse()
        jq("#doesNotExist").present().shouldBeFalse()
    }
})
