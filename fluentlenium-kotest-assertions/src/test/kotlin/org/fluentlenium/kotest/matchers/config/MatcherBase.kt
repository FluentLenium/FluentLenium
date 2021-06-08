package org.fluentlenium.kotest.matchers.config

import io.kotest.core.test.TestCase
import org.fluentlenium.adapter.kotest.FluentStringSpec
import org.fluentlenium.utils.UrlUtils

open class MatcherBase(
    body: FluentStringSpec.() -> Unit = {}
) : FluentStringSpec(body) {

    fun goToFile(file: String) =
        goTo(UrlUtils.getAbsoluteUrlFromFile(file))

    override fun beforeEach(testCase: TestCase) {
        goToFile("index.html")
    }
}