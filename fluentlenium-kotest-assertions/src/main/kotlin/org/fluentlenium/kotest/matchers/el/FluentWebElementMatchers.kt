package org.fluentlenium.kotest.matchers.el

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.Dimension

/**
 * Checks if the element is present.
 * <p>
 * Example:
 * <pre>
 * el("button") should bePresent()
 * </pre>
 *
 * @return the matcher object.
 */
fun bePresent() = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult = MatcherResult(
        value.enabled(),
        "Element '$value' should be present",
        "Element '$value' should not be present"
    )
}

/**
 * @see bePresent
 */
fun FluentWebElement.shouldBePresent() = also {
    it should bePresent()
}

/**
 * @see bePresent
 */
fun FluentWebElement.shouldNotBePresent() = also { it shouldNot bePresent() }

/**
 * Checks if the element is visible and enabled such that you can click it.
 * <p>
 * Example:
 * <pre>
 * el("button") should beEnabled()
 * </pre>
 *
 * @return the matcher object.
 */
fun beEnabled() = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult = MatcherResult(
        value.enabled(),
        "Element $value should be enabled",
        "Element $value should not be enabled"
    )
}

/**
 * @see beEnabled
 */
fun FluentWebElement.shouldBeEnabled() = also { it should beEnabled() }

/**
 * @see beEnabled
 */
fun FluentWebElement.shouldNotBeEnabled() = also { it shouldNot beEnabled() }

/**
 * Checks if the element is present on the DOM of a page and visible.
 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
 * <p>
 * Example:
 * <pre>
 * el("button") should beDisplayed()
 * </pre>
 *
 * @return the matcher object.
 */
fun beDisplayed() = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult = MatcherResult(
        value.displayed(),
        "Element $value should be displayed",
        "Element $value should not be displayed"
    )
}

/**
 * @see beDisplayed
 */
fun FluentWebElement.shouldBeDisplayed() = also { it should beDisplayed() }

/**
 * @see beDisplayed
 */
fun FluentWebElement.shouldNotBeDisplayed() = also { it shouldNot beDisplayed() }

/**
 * Checks if the element is visible and enabled such that you can click it.
 *
 * <p>
 * Example:
 * <pre>
 * el("button") should beClickable()
 * </pre>
 *
 * @return the matcher object.
 */
fun beClickable() = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult = MatcherResult(
        value.clickable(),
        "Element $value should be clickable",
        "Element $value should not be clickable"
    )
}

/**
 * @see beClickable
 */
fun FluentWebElement.shouldBeClickable() = also { it should beClickable() }

/**
 * @see beClickable
 */
fun FluentWebElement.shouldNotBeClickable() = also { it shouldNot beClickable() }

/**
 * Checks if the element is selected.
 *
 * <p>
 * Example:
 * <pre>
 * el("button") should beSelected()
 * </pre>
 *
 * @return the matcher object.
 */
fun beSelected() = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult = MatcherResult(
        value.selected(),
        "Element $value should be selected",
        "Element $value should not be selected"
    )
}

/**
 * @see beSelected
 */
fun FluentWebElement.shouldBeSelected() = also { it should beSelected() }

/**
 * @see beSelected
 */
fun FluentWebElement.shouldNotBeSelected() = also { it shouldNot beSelected() }

/**
 * Checks if the element has text with a value as a part of it.
 *
 * <p>
 * Example:
 * <pre>
 * el("button") should containText("click me")
 * </pre>
 *
 * @return the matcher object.
 */
fun containText(expectedText: String) = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult {
        val actualText = value.text()

        return MatcherResult(
            actualText.contains(expectedText),
            "Expected element to contain text '$expectedText', actual text is '$actualText'.",
            "Expected element to not contain text '$expectedText', actual text is '$actualText'."
        )
    }
}

/**
 * @see containText
 */
fun FluentWebElement.shouldContainText(text: String) = also { it should containText(text) }

/**
 * @see containText
 */
fun FluentWebElement.shouldNotContainText(text: String) = also { it shouldNot containText(text) }

/**
 * Checks if the element has text that can be matched with the given pattern.
 *
 * <p>
 * Example:
 * <pre>
 * el("button") should matchText("click.*")
 * </pre>
 *
 * @return the matcher object.
 */
fun matchText(pattern: String) = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult {
        val actualText = value.text()

        return MatcherResult(
            actualText.matches(pattern.toRegex()),
            "Expected element text to match pattern '$pattern', actual text is '$actualText'.",
            "Expected element text to not match pattern '$pattern', actual text is '$actualText'."
        )
    }
}

/**
 * @see matchText
 */
fun FluentWebElement.shouldMatchText(pattern: String) = also { it should matchText(pattern) }

/**
 * @see matchText
 */
fun FluentWebElement.shouldNotMatchText(pattern: String) = also { it shouldNot matchText(pattern) }

/**
 * Checks if the element has the expected id.
 *
 * <p>
 * Example:
 * <pre>
 * el("button") should haveId("id")
 * </pre>
 *
 * @return the matcher object.
 */
fun haveId(id: String) = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult {
        val actualId = value.id()

        return MatcherResult(
            actualId == id,
            "Expected element to have id '$id', actual id is '$actualId'.",
            "Expected element to not have id '$id'."
        )
    }
}

/**
 * @see haveId
 */
fun FluentWebElement.shouldHaveId(id: String) = also { it should haveId(id) }

/**
 * @see haveId
 */
fun FluentWebElement.shouldNotHaveId(id: String) = also { it shouldNot haveId(id) }

const val CLASS_ATTRIBUTE = "class"
const val CLASS_DELIMITER = " "

/**
 * Checks if the element has the expected class.
 *
 * <p>
 * Example:
 * <pre>
 * el("button") should haveClass("small")
 * </pre>
 *
 * @return the matcher object.
 */
fun haveClass(vararg expectedClasses: String) = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult {
        val actualAttribute: String? = value.attribute(CLASS_ATTRIBUTE)
        val actualClasses = actualAttribute?.split(CLASS_DELIMITER) ?: emptyList()
        val expectedList = expectedClasses.toList()

        return MatcherResult(
            actualClasses.containsAll(expectedList),
            "Expected element to have classes $expectedList, actual classes are $actualClasses.",
            "Expected element to not have classes $expectedList, actual classes are $actualClasses."
        )
    }
}

/**
 * @see haveClass
 */
fun FluentWebElement.shouldHaveClass(vararg expectedClasses: String) = also { it should haveClass(*expectedClasses) }

/**
 * @see haveClass
 */
fun FluentWebElement.shouldNotHaveClass(vararg expectedClasses: String) =
    also { it shouldNot haveClass(*expectedClasses) }

/**
 * Checks if the elements value is equal to the expected value.
 *
 * <p>
 * Example:
 * <pre>
 * el("button") should haveValue("value")
 * </pre>
 *
 * @return the matcher object.
 */
fun haveValue(expectedValue: String) = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult {
        val actualValue = value.value()
        return MatcherResult(
            actualValue == expectedValue,
            "Expected element to have value '$expectedValue', actual value is '$actualValue'.",
            "Expected element to not have value '$actualValue'."
        )
    }
}

/**
 * @see haveValue
 */
fun FluentWebElement.shouldHaveValue(expectedValue: String) = also { it should haveValue(expectedValue) }

/**
 * @see haveValue
 */
fun FluentWebElement.shouldNotHaveValue(expectedValue: String) = also { it shouldNot haveValue(expectedValue) }

/**
 * Checks if the element has the expected name.
 *
 * <p>
 * Example:
 * <pre>
 * el("button") should haveName("myButton")
 * </pre>
 *
 * @return the matcher object.
 */
fun haveName(expectedName: String) = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult {
        val actualName = value.name()
        return MatcherResult(
            actualName == expectedName,
            "Expected element to have name '$expectedName', actual name is '$actualName'.",
            "Expected element to not have name '$actualName'."
        )
    }
}

/**
 * @see haveName
 */
fun FluentWebElement.shouldHaveName(expectedName: String) = also { it should haveName(expectedName) }

/**
 * @see haveName
 */
fun FluentWebElement.shouldNotHaveName(expectedName: String) = also { it shouldNot haveName(expectedName) }

/**
 * Checks if the element has the expected tag.
 *
 * <p>
 * Example:
 * <pre>
 * el("button") should haveTagName("button")
 * </pre>
 *
 * @return the matcher object.
 */
fun haveTagName(expectedTagName: String) = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult {
        val actualTagName = value.tagName()
        return MatcherResult(
            actualTagName == expectedTagName,
            "Expected element to be a '$expectedTagName' tag, actually it is a '$actualTagName' tag.",
            "Expected element to not to be a '$actualTagName' tag."
        )
    }
}

/**
 * @see haveTagName
 */
fun FluentWebElement.shouldHaveTagName(expectedTagName: String) = also { it should haveTagName(expectedTagName) }

/**
 * @see haveTagName
 */
fun FluentWebElement.shouldNotHaveTagName(expectedTagName: String) = also { it shouldNot haveTagName(expectedTagName) }

/**
 * Checks if the element has the expected Dimension.
 *
 * <p>
 * Example:
 * <pre>
 * el("button") should haveDimension(Dimension(200, 400))
 * </pre>
 *
 * @return the matcher object.
 */
fun haveDimension(expectedDimension: Dimension) = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult {
        val actualDimension = value.size()
        return MatcherResult(
            actualDimension == expectedDimension,
            "Expected element to have dimension '$expectedDimension', actual dimension is '$actualDimension'.",
            "Expected element to not have dimension '$actualDimension'."
        )
    }
}

private fun Pair<Int, Int>.toDimension() = Dimension(first, second)

/**
 * Checks if the element has the expected dimension.
 *
 * <p>
 * Example:
 * <pre>
 * el("button") should haveDimension(200 t0 400)
 * </pre>
 *
 * @return the matcher object.
 */
fun haveDimension(dimension: Pair<Int, Int>) =
    haveDimension(dimension.toDimension())

/**
 * @see haveDimension
 */
fun FluentWebElement.shouldHaveDimension(expectedDimension: Dimension) =
    also { it should haveDimension(expectedDimension) }

/**
 * @see haveDimension
 */
fun FluentWebElement.shouldHaveDimension(expectedDimension: Pair<Int, Int>) =
    shouldHaveDimension(expectedDimension.toDimension())

/**
 * @see haveDimension
 */
fun FluentWebElement.shouldNotHaveDimension(expectedDimension: Dimension) =
    also { it shouldNot haveDimension(expectedDimension) }

/**
 * @see haveDimension
 */
fun FluentWebElement.shouldNotHaveDimension(expectedDimension: Pair<Int, Int>) =
    shouldNotHaveDimension(expectedDimension.toDimension())

/**
 * Checks if the element has the expected attribute key.
 *
 * <p>
 * Example:
 * <pre>
 * el("button") should haveAttribute("attr")
 * </pre>
 *
 * @return the matcher object.
 */
fun haveAttribute(expectedAttribute: String) = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult {
        val actualAttributeValue = value.attribute(expectedAttribute)
        return MatcherResult(
            actualAttributeValue != null,
            "Expected element to have attribute '$expectedAttribute'.",
            "Expected element to not have attribute '$expectedAttribute'. attribute exists and has value '$actualAttributeValue'."
        )
    }
}

/**
 * @see haveAttribute
 */
fun FluentWebElement.shouldHaveAttribute(expectedAttribute: String) =
    also { it should haveAttribute(expectedAttribute) }

/**
 * @see haveAttribute
 */
fun FluentWebElement.shouldNotHaveAttribute(expectedAttribute: String) =
    also { it shouldNot haveAttribute(expectedAttribute) }

/**
 * Checks if the element has the expected attribute key and value
 *
 * <p>
 * Example:
 * <pre>
 * el("button") should haveAttributeValue("key", "myValue")
 * </pre>
 *
 * @return the matcher object.
 */
fun haveAttributeValue(attribute: String, expectedValue: String?) = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult {
        val actualAttributeValue = value.attribute(attribute)
        return MatcherResult(
            actualAttributeValue == expectedValue,
            "Expected element to have attribute '$attribute' with value '$expectedValue'. actual value is '$actualAttributeValue'",
            "Expected element to not have attribute '$attribute' with value '$expectedValue'."
        )
    }
}

/**
 * @see haveAttributeValue
 */
fun FluentWebElement.shouldHaveAttributeValue(attribute: String, expectedValue: String?) =
    also { it should haveAttributeValue(attribute, expectedValue) }

/**
 * @see haveAttributeValue
 */
fun FluentWebElement.shouldHaveAttributeValue(pair: Pair<String, String?>) =
    also { it should haveAttributeValue(pair.first, pair.second) }

/**
 * @see haveAttributeValue
 */
fun FluentWebElement.shouldNotHaveAttributeValue(attribute: String, expectedValue: String?) =
    also { it shouldNot haveAttributeValue(attribute, expectedValue) }

/**
 * @see haveAttributeValue
 */
fun FluentWebElement.shouldNotHaveAttributeValue(pair: Pair<String, String?>) =
    also { it shouldNot haveAttributeValue(pair.first, pair.second) }
