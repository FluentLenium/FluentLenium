package org.fluentlenium.kotest

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.fluentlenium.core.alert.Alert

fun haveText(text: String) = object : Matcher<Alert> {
    override fun test(value: Alert): MatcherResult {

        val actualText = value.text
        val contains = actualText.contains(text)

        return MatcherResult(
            contains,
            "Alert with text $actualText should contain text $text",
            "Alert with text $actualText should not contain text $text"
        )
    }
}

fun Alert.shouldHaveText(text: String) = this should haveText(text)
fun Alert.shouldNotHaveText(text: String) = this shouldNot haveText(text)

fun bePresent() = object : Matcher<Alert> {
    override fun test(value: Alert): MatcherResult =
        MatcherResult(
            value.present(),
            "Alert should be present",
            "Alert should not be present"
        )
}

fun Alert.shouldBePresent() = this should bePresent()
fun Alert.shouldNotBePresent() = this shouldNot bePresent()