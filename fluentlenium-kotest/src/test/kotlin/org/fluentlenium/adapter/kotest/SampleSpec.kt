package org.fluentlenium.adapter.kotest

import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.test.TestType
import io.kotest.matchers.shouldBe

class SampleSpec : FreeSpec({

    aroundTest { (testcase, run) ->
        when (testcase.type) {
            TestType.Test -> {
                println("start F")
                run(testcase).also {
                    println("stop F")
                }
            }
            TestType.Container -> run(testcase)
            TestType.Dynamic -> run(testcase)
        }
    }

    beforeEach {
        println("before each")
    }

    beforeTest {
        println("before test")
    }

    beforeAny {
        println("before any")
    }

    afterEach {
        println("after each")
    }

    afterTest {
        println("after test")
    }

    afterAny {
        println("after any")
    }

    "block" - {
        "test1" {
            "foo" shouldBe "foo"
        }
    }
})
