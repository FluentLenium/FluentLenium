package org.fluentlenium.adapter.kotest

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class SampleSpec : FreeSpec({

    beforeEach {
        println("before each ${it.type}")
    }

    beforeTest {
        println("before test ${it.type}")
    }

    beforeAny {
        println("before any ${it.type}")
    }

    afterEach {
        println("after each ${it.a.type}")
    }

    afterTest {
        println("after test ${it.a.type}")
    }

    afterAny {
        println("after any ${it.a.type}")
    }

    "block" - {
        "test1" {
            println("run test")
            "foo" shouldBe "foo"
        }
    }
})
