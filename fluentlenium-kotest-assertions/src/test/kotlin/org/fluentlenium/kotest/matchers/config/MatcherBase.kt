package org.fluentlenium.kotest.matchers.config

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.listeners.BeforeTestListener
import io.kotest.core.test.TestCase
import org.fluentlenium.adapter.kotest.FluentStringSpec
import org.fluentlenium.utils.UrlUtils

open class MatcherBase(
    body: FluentStringSpec.() -> Unit = {}
) : FluentStringSpec(body) {

    fun goToFile(file: String) =
        goTo(UrlUtils.getAbsoluteUrlFromFile(file))

    init {
        register(object : BeforeTestListener {
            override suspend fun beforeTest(testCase: TestCase) {
                goToFile("index.html")
            }
        })
    }
}

fun shouldAssert(block: () -> Any?): AssertionError =
    shouldThrow(block)
