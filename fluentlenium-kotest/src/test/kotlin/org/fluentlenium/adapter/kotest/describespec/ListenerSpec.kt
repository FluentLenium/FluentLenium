package org.fluentlenium.adapter.kotest.describespec

import io.kotest.core.listeners.TestListener
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe
import org.fluentlenium.adapter.kotest.FluentDescribeSpec

class ListenerSpec : FluentDescribeSpec() {

    override fun listeners(): List<TestListener> =
        listOf(MyListener)

    object MyListener : TestListener {
        var beforeTestCalled = 0

        override suspend fun beforeTest(testCase: TestCase) {
            beforeTestCalled++
        }
    }

    init {
        it("can use custom listener") {
            MyListener.beforeTestCalled shouldBe 1
        }
    }
}
