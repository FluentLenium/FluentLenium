package io.fluentlenium.kotest.matchers.alert

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import io.fluentlenium.core.alert.Alert
import org.openqa.selenium.NoAlertPresentException

/**
 * Check that the alert box contains the given text.
 *
 * It fails assertion when
 * - there is an alert box but the text in it doesn't contain the expected text,</li>
 *
 * @param text text to search for
 * @return the matcher object
 */
fun haveText(text: String) = object : Matcher<_root_ide_package_.io.fluentlenium.core.alert.Alert> {
    override fun test(value: _root_ide_package_.io.fluentlenium.core.alert.Alert): MatcherResult =
        MatcherResult(
            value.text.contains(text),
            { "The alert box is expected to contain the text '${value.text}', actual text is '$text'" },
            {
                "The alert box is expected to not contain the text '${value.text}'"
            }
        )
}

/**
 * See [haveText]
 */
fun _root_ide_package_.io.fluentlenium.core.alert.Alert.shouldHaveText(text: String) = also { it should haveText(text) }

/**
 * See [haveText]
 */
fun _root_ide_package_.io.fluentlenium.core.alert.Alert.shouldNotHaveText(text: String) = also { it shouldNot haveText(text) }

/**
 * Check that an alert box is present.
 *
 * @return the matcher object
 */
fun bePresent() = object : Matcher<_root_ide_package_.io.fluentlenium.core.alert.Alert> {
    override fun test(value: _root_ide_package_.io.fluentlenium.core.alert.Alert): MatcherResult {
        val isAlertPresent = try {
            value.present()
        } catch (e: NoAlertPresentException) {
            false
        }

        return MatcherResult(
            isAlertPresent,
            { "Alert should be present" },
            {
                "Alert should not be present"
            }
        )
    }
}

/**
 * See [bePresent]
 */
fun _root_ide_package_.io.fluentlenium.core.alert.Alert.shouldBePresent() = also { it should bePresent() }

/**
 * See [bePresent]
 */
fun _root_ide_package_.io.fluentlenium.core.alert.Alert.shouldNotBePresent() = also { it shouldNot bePresent() }
