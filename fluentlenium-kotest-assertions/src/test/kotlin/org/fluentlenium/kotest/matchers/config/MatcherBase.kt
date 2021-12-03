package org.fluentlenium.kotest.matchers.config

import io.kotest.assertions.throwables.shouldThrow
import org.fluentlenium.adapter.kotest.FluentStringSpec
import org.fluentlenium.utils.UrlUtils

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
