package io.fluentlenium.adapter.kotest.describespec

import io.fluentlenium.adapter.kotest.FluentDescribeSpec
import io.kotest.core.listeners.TestListener
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe

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
