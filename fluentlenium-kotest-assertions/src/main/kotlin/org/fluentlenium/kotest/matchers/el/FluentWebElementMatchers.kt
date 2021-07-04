package org.fluentlenium.kotest.matchers.el

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.Dimension

fun bePresent() = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult = MatcherResult(
        value.present(),
        "Element '$value' should be present",
        "Element '$value' should not be present"
    )
}

fun FluentWebElement.shouldBePresent() = also {
    it should bePresent()
}

fun FluentWebElement.shouldNotBePresent() = also { it shouldNot bePresent() }

fun beEnabled() = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult = MatcherResult(
        value.enabled(),
        "Element $value should be enabled",
        "Element $value should not be enabled"
    )
}

fun FluentWebElement.shouldBeEnabled() = also { it should beEnabled() }
fun FluentWebElement.shouldNotBeEnabled() = also { it shouldNot beEnabled() }

fun beDisplayed() = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult = MatcherResult(
        value.displayed(),
        "Element $value should be displayed",
        "Element $value should not be displayed"
    )
}

fun FluentWebElement.shouldBeDisplayed() = also { it should beDisplayed() }
fun FluentWebElement.shouldNotBeDisplayed() = also { it shouldNot beDisplayed() }

fun beClickable() = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult = MatcherResult(
        value.clickable(),
        "Element $value should be clickable",
        "Element $value should not be clickable"
    )
}

fun FluentWebElement.shouldBeClickable() = also { it should beClickable() }
fun FluentWebElement.shouldNotBeClickable() = also { it shouldNot beClickable() }

fun beSelected() = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult = MatcherResult(
        value.selected(),
        "Element $value should be selected",
        "Element $value should not be selected"
    )
}

fun FluentWebElement.shouldBeSelected() = also { it should beSelected() }
fun FluentWebElement.shouldNotBeSelected() = also { it shouldNot beSelected() }

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

fun FluentWebElement.shouldContainText(text: String) = also { it should containText(text) }
fun FluentWebElement.shouldNotContainText(text: String) = also { it shouldNot containText(text) }

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

fun FluentWebElement.shouldMatchText(pattern: String) = also { it should matchText(pattern) }
fun FluentWebElement.shouldNotMatchText(pattern: String) = also { it shouldNot matchText(pattern) }

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

fun FluentWebElement.shouldHaveId(id: String) = also { it should haveId(id) }
fun FluentWebElement.shouldNotHaveId(id: String) = also { it shouldNot haveId(id) }

const val CLASS_ATTRIBUTE = "class"
const val CLASS_DELIMITER = " "

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

fun FluentWebElement.shouldHaveClass(vararg expectedClasses: String) = also { it should haveClass(*expectedClasses) }
fun FluentWebElement.shouldNotHaveClass(vararg expectedClasses: String) =
    also { it shouldNot haveClass(*expectedClasses) }

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

fun FluentWebElement.shouldHaveValue(expectedValue: String) = also { it should haveValue(expectedValue) }
fun FluentWebElement.shouldNotHaveValue(expectedValue: String) = also { it shouldNot haveValue(expectedValue) }

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

fun FluentWebElement.shouldHaveName(expectedName: String) = also { it should haveName(expectedName) }
fun FluentWebElement.shouldNotHaveName(expectedName: String) = also { it shouldNot haveName(expectedName) }

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

fun FluentWebElement.shouldHaveTagName(expectedTagName: String) = also { it should haveTagName(expectedTagName) }
fun FluentWebElement.shouldNotHaveTagName(expectedTagName: String) = also { it shouldNot haveTagName(expectedTagName) }

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

fun haveDimension(dimension: Pair<Int, Int>) =
    haveDimension(dimension.toDimension())

fun FluentWebElement.shouldHaveDimension(expectedDimension: Dimension) =
    also { it should haveDimension(expectedDimension) }

fun FluentWebElement.shouldHaveDimension(expectedDimension: Pair<Int, Int>) =
    shouldHaveDimension(expectedDimension.toDimension())

fun FluentWebElement.shouldNotHaveDimension(expectedDimension: Dimension) =
    also { it shouldNot haveDimension(expectedDimension) }

fun FluentWebElement.shouldNotHaveDimension(expectedDimension: Pair<Int, Int>) =
    shouldNotHaveDimension(expectedDimension.toDimension())

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

fun FluentWebElement.shouldHaveAttribute(expectedAttribute: String) =
    also { it should haveAttribute(expectedAttribute) }

fun FluentWebElement.shouldNotHaveAttribute(expectedAttribute: String) =
    also { it shouldNot haveAttribute(expectedAttribute) }

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

fun FluentWebElement.shouldHaveAttributeValue(attribute: String, expectedValue: String?) =
    also { it should haveAttributeValue(attribute, expectedValue) }

fun FluentWebElement.shouldHaveAttributeValue(pair: Pair<String, String?>) =
    also { it should haveAttributeValue(pair.first, pair.second) }

fun FluentWebElement.shouldNotHaveAttributeValue(attribute: String, expectedValue: String?) =
    also { it shouldNot haveAttributeValue(attribute, expectedValue) }

fun FluentWebElement.shouldNotHaveAttributeValue(pair: Pair<String, String?>) =
    also { it shouldNot haveAttributeValue(pair.first, pair.second) }
