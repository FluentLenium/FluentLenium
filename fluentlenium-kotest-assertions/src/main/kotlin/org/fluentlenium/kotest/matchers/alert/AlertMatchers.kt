package org.fluentlenium.kotest.matchers.alert

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.fluentlenium.core.alert.Alert
import org.openqa.selenium.NoAlertPresentException

fun haveText(text: String) = object : Matcher<Alert> {
    override fun test(value: Alert): MatcherResult =
        MatcherResult(
            value.text.contains(text),
            "The alert box is expected to contain the text '${value.text}', actual text is '$text'",
            "The alert box is expected to not contain the text '${value.text}'"
        )
}

fun Alert.shouldHaveText(text: String) = also { it should haveText(text) }
fun Alert.shouldNotHaveText(text: String) = also { it shouldNot haveText(text) }

fun bePresent() = object : Matcher<Alert> {
    override fun test(value: Alert): MatcherResult {
        val isAlertPresent = try {
            value.present()
        } catch (e: NoAlertPresentException) {
            false
        }

        return MatcherResult(
            isAlertPresent,
            "Alert should be present",
            "Alert should not be present"
        )
    }
}

fun Alert.shouldBePresent() = also { it should bePresent() }
fun Alert.shouldNotBePresent() = also { it shouldNot bePresent() }