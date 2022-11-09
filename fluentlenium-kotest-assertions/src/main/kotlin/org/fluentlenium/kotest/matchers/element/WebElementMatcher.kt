package org.fluentlenium.kotest.matchers.element

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.openqa.selenium.WebElement

/**
 * Checks if the element is visible and enabled such that you can click it.
 *
 * Example:
 * `el("button") should beEnabled()`
 *
 * @return the matcher object.
 */
fun beEnabled() = object : Matcher<WebElement> {
    override fun test(value: WebElement): MatcherResult =
        MatcherResult(
            value.isEnabled,
            { "Element $value should be enabled" },
            {
                "Element $value should not be enabled"
            }
        )
}

/**
 * See [beEnabled]
 */
fun WebElement.shouldBeEnabled() = also { it should beEnabled() }

/**
 * See [beEnabled]
 */
fun WebElement.shouldNotBeEnabled() = also { it shouldNot beEnabled() }

/**
 * Checks if the element is present on the DOM of a page and visible.
 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
 *
 * Example:
 * `el("button") should beDisplayed()`
 *
 * @return the matcher object.
 */
fun beDisplayed() = object : Matcher<WebElement> {
    override fun test(value: WebElement): MatcherResult =
        MatcherResult(
            value.isDisplayed,
            { "Element $value should be displayed" },
            {
                "Element $value should not be displayed"
            }
        )
}

/**
 * See [beDisplayed]
 */
fun WebElement.shouldBeDisplayed() = also { it should beDisplayed() }

/**
 * See [beDisplayed]
 */
fun WebElement.shouldNotBeDisplayed() = also { it shouldNot beDisplayed() }

/**
 * Checks if the element is present on the DOM of a page and visible.
 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
 *
 * Example:
 * `el("button") should beDisplayed()`
 *
 * @return the matcher object.
 */
fun beSelected() = object : Matcher<WebElement> {
    override fun test(value: WebElement): MatcherResult =
        MatcherResult(
            value.isSelected,
            { "Element $value should be selected" },
            {
                "Element $value should not be selected"
            }
        )
}

/**
 * See [beDisplayed]
 */
fun WebElement.shouldBeSelected() = also { it should beSelected() }

/**
 * See [beDisplayed]
 */
fun WebElement.shouldNotBeSelected() = also { it shouldNot beSelected() }

fun haveTagName(tagName: String) = object : Matcher<WebElement> {
    override fun test(value: WebElement): MatcherResult =
        MatcherResult(
            value.tagName == tagName,
            { "Element $value should have tagName $tagName" },
            {
                "Element $value should not have tagName $tagName"
            }
        )
}

/**
 * See [haveTagName]
 */
fun WebElement.shouldHaveTagName(tagName: String) = also { it should haveTagName(tagName) }

/**
 * See [haveTagName]
 */
fun WebElement.shouldNotHaveTagName(tagName: String) = also { it shouldNot haveTagName(tagName) }

/**
 * Checks if the element has the expected attribute key.
 *
 * Example:
 * `el("button") should haveAttribute("attr")`
 *
 * @return the matcher object.
 */
fun haveAttribute(expectedAttribute: String) = object : Matcher<WebElement> {
    override fun test(value: WebElement): MatcherResult {
        val actualAttributeValue = value.getAttribute(expectedAttribute)
        return MatcherResult(
            actualAttributeValue != null,
            { "Expected element to have attribute '$expectedAttribute'." },
            {
                "Expected element to not have attribute '$expectedAttribute'. attribute exists and has value '$actualAttributeValue'."
            }
        )
    }
}

/**
 * See [haveAttribute]
 */
fun WebElement.shouldHaveAttribute(expectedAttribute: String) =
    also { it should haveAttribute(expectedAttribute) }

/**
 * See [haveAttribute]
 */
fun WebElement.shouldNotHaveAttribute(expectedAttribute: String) =
    also { it shouldNot haveAttribute(expectedAttribute) }

/**
 * Checks if the element has the expected attribute key and value
 *
 * Example:
 * `el("button") should haveAttributeValue("key", "myValue")`
 *
 * @return the matcher object.
 */
fun haveAttributeValue(attribute: String, expectedValue: String?) = object : Matcher<WebElement> {
    override fun test(value: WebElement): MatcherResult {
        val actualAttributeValue = value.getAttribute(attribute)
        return MatcherResult(
            actualAttributeValue == expectedValue,
            { "Expected element to have attribute '$attribute' with value '$expectedValue'. actual value is '$actualAttributeValue'" },
            {
                "Expected element to not have attribute '$attribute' with value '$expectedValue'."
            }
        )
    }
}

/**
 * See [haveAttributeValue]
 */
fun WebElement.shouldHaveAttributeValue(attribute: String, expectedValue: String?) =
    also { it should haveAttributeValue(attribute, expectedValue) }

/**
 * See [haveAttributeValue]
 */
fun WebElement.shouldHaveAttributeValue(pair: Pair<String, String?>) =
    also { it should haveAttributeValue(pair.first, pair.second) }

/**
 * See [haveAttributeValue]
 */
fun WebElement.shouldNotHaveAttributeValue(attribute: String, expectedValue: String?) =
    also { it shouldNot haveAttributeValue(attribute, expectedValue) }

/**
 * See [haveAttributeValue]
 */
fun WebElement.shouldNotHaveAttributeValue(pair: Pair<String, String?>) =
    also { it shouldNot haveAttributeValue(pair.first, pair.second) }
