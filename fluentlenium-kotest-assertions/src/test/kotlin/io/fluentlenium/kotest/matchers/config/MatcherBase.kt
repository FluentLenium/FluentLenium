package io.fluentlenium.kotest.matchers.config

import io.fluentlenium.adapter.kotest.FluentStringSpec
import io.fluentlenium.utils.UrlUtils
import io.kotest.assertions.throwables.shouldThrow

abstract class MatcherBase(
    body: FluentStringSpec.() -> Unit = {}
) : FluentStringSpec(body) {

    fun goToFile(file: String) =
        goTo(UrlUtils.getAbsoluteUrlFromFile(file))

    init {
        beforeTest {
            goToFile("index.html")
        }
    }
}

fun shouldAssert(block: () -> Any?): AssertionError =
    shouldThrow(block)