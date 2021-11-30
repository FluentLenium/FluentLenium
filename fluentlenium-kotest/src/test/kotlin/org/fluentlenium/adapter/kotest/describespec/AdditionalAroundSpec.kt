package org.fluentlenium.adapter.kotest.describespec

import io.kotest.core.Tuple2
import io.kotest.core.spec.AroundTestFn
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import org.fluentlenium.adapter.kotest.FluentDescribeSpec

class AdditionalAroundSpec : FluentDescribeSpec() {

    override fun decorateAround(aroundFn: AroundTestFn): AroundTestFn = { (test, run) ->
        println("outer around")

        driver.shouldBeNull()
        aroundFn(Tuple2(test, run))
    }

    init {
        it("additional around behavior") {
            driver.shouldNotBeNull()

            println("test")
        }
    }
}
