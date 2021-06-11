package org.fluentlenium.kotest.matchers.jq

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.Dimension

fun haveText(expectedText: String) = object : Matcher<FluentList<FluentWebElement>> {
    override fun test(value: FluentList<FluentWebElement>): MatcherResult {
        val actualTexts = value.texts()

        return MatcherResult(
            actualTexts.contains(expectedText),
            "No selected elements has text '$expectedText', found texts $actualTexts",
            "No selected elements should have '$expectedText',"
        )
    }
}

fun FluentList<FluentWebElement>.shouldHaveText(text: String) = also { it should haveText(text) }
fun FluentList<FluentWebElement>.shouldNotHaveText(text: String) = also { it shouldNot haveText(text) }

fun haveTextContaining(expectedText: String) = object : Matcher<FluentList<FluentWebElement>> {
    override fun test(value: FluentList<FluentWebElement>): MatcherResult {
        val actualTexts = value.texts()

        return MatcherResult(
            actualTexts.any { it.contains(expectedText) },
            "No selected elements text contains '$expectedText', found texts $actualTexts",
            "No selected elements should contain '$expectedText',"
        )
    }
}

fun FluentList<FluentWebElement>.shouldHaveTextContaining(text: String) = also { it should haveTextContaining(text) }
fun FluentList<FluentWebElement>.shouldNotHaveTextContaining(text: String) =
    also { it shouldNot haveTextContaining(text) }

fun haveTextMatching(expectedPattern: String) = object : Matcher<FluentList<FluentWebElement>> {
    override fun test(value: FluentList<FluentWebElement>): MatcherResult {
        val actualTexts = value.texts()

        return MatcherResult(
            actualTexts.any { it.matches(expectedPattern.toRegex()) },
            "No selected elements text matches '$expectedPattern', found texts $actualTexts",
            "No selected elements should match '$expectedPattern',"
        )
    }
}

fun FluentList<FluentWebElement>.shouldHaveTextMatching(text: String) = also { it should haveTextMatching(text) }
fun FluentList<FluentWebElement>.shouldNotHaveTextMatching(text: String) = also { it shouldNot haveTextMatching(text) }

fun haveId(id: String) = object : Matcher<FluentList<FluentWebElement>> {
    override fun test(value: FluentList<FluentWebElement>): MatcherResult {
        val actualIds = value.ids()

        return MatcherResult(
            actualIds.contains(id),
            "No selected elements has id '$id', found ids $actualIds",
            "No selected elements should have id '$id',"
        )
    }
}

fun FluentList<FluentWebElement>.shouldHaveId(id: String) = also { it should haveId(id) }
fun FluentList<FluentWebElement>.shouldNotHaveId(id: String) = also { it shouldNot haveId(id) }

const val CLASS_ATTRIBUTE = "class"
const val CLASS_DELIMITER = " "

fun haveClass(vararg expectedClasses: String) = object : Matcher<FluentList<FluentWebElement>> {
    override fun test(value: FluentList<FluentWebElement>): MatcherResult {
        val expectedList = expectedClasses.toList()
        val actualClasses = value.attributes(CLASS_ATTRIBUTE)
            .map { it.split(CLASS_DELIMITER) }

        val anyMatch = actualClasses.any {
            it.containsAll(expectedClasses.toList())
        }

        return MatcherResult(
            anyMatch,
            "No selected elements has expected classes $expectedList, found $actualClasses",
            "No selected elements should have classes $expectedList"
        )
    }
}

fun FluentList<FluentWebElement>.shouldHaveClass(vararg classes: String) = also { it should haveClass(*classes) }
fun FluentList<FluentWebElement>.shouldNotHaveClass(vararg classes: String) = also { it shouldNot haveClass(*classes) }

fun haveName(expectedName: String) = object : Matcher<FluentList<FluentWebElement>> {
    override fun test(value: FluentList<FluentWebElement>): MatcherResult {
        val actualNames = value.names()

        return MatcherResult(
            actualNames.contains(expectedName),
            "No selected elements with name '$expectedName', found $actualNames.",
            "No selected elements should have name '$expectedName'."
        )
    }
}

fun FluentList<FluentWebElement>.shouldHaveName(name: String) = also { it should haveName(name) }
fun FluentList<FluentWebElement>.shouldNotHaveName(name: String) = also { it shouldNot haveName(name) }

fun haveAttribute(expectedAttribute: String) = object : Matcher<FluentList<FluentWebElement>> {
    override fun test(value: FluentList<FluentWebElement>): MatcherResult {
        val actualAttributes = value.attributes(expectedAttribute)

        val anyPresent = actualAttributes.filterNotNull()

        return MatcherResult(
            anyPresent.isNotEmpty(),
            "No selected elements with name attribute '$expectedAttribute'.",
            "No selected elements should have name '$expectedAttribute'. found $anyPresent"
        )
    }
}

fun FluentList<FluentWebElement>.shouldHaveAttribute(attribute: String) = also { it should haveAttribute(attribute) }
fun FluentList<FluentWebElement>.shouldNotHaveAttribute(attribute: String) =
    also { it shouldNot haveAttribute(attribute) }

fun haveValue(expectedValue: String) = object : Matcher<FluentList<FluentWebElement>> {
    override fun test(value: FluentList<FluentWebElement>): MatcherResult {
        val actualAttributes = value.values()

        return MatcherResult(
            actualAttributes.contains(expectedValue),
            "No selected elements with value '$expectedValue'.",
            "No selected elements should have value '$expectedValue'."
        )
    }
}

fun FluentList<FluentWebElement>.shouldHaveValue(value: String) = also { it should haveValue(value) }
fun FluentList<FluentWebElement>.shouldNotHaveValue(value: String) = also { it shouldNot haveValue(value) }

fun haveAttributeValue(key: String, expectedValue: String) = object : Matcher<FluentList<FluentWebElement>> {
    override fun test(value: FluentList<FluentWebElement>): MatcherResult {
        val actualValues = value.attributes(key)

        return MatcherResult(
            actualValues.contains(expectedValue),
            "No selected elements with attribute value '$key' = '$expectedValue'. found values $actualValues",
            "No selected elements should have attribute value '$key' = '$expectedValue'."
        )
    }
}

fun FluentList<FluentWebElement>.shouldHaveAttributeValue(key: String, attribute: String) =
    also { it should haveAttributeValue(key, attribute) }

fun FluentList<FluentWebElement>.shouldHaveAttributeValue(pair: Pair<String, String>) =
    shouldHaveAttributeValue(pair.first, pair.second)

fun FluentList<FluentWebElement>.shouldNotHaveAttributeValue(pair: Pair<String, String>) =
    shouldNotHaveAttributeValue(pair.first, pair.second)

fun FluentList<FluentWebElement>.shouldNotHaveAttributeValue(key: String, attribute: String) =
    also { it shouldNot haveAttributeValue(key, attribute) }

fun haveTagName(expectedTag: String) = object : Matcher<FluentList<FluentWebElement>> {
    override fun test(value: FluentList<FluentWebElement>): MatcherResult {
        val actualTags = value.tagNames()

        return MatcherResult(
            actualTags.contains(expectedTag),
            "No selected elements with tag '$expectedTag'. found tags $actualTags",
            "No selected elements should be a '$expectedTag'."
        )
    }
}

fun FluentList<FluentWebElement>.shouldHaveTagName(tagName: String) = also { it should haveTagName(tagName) }
fun FluentList<FluentWebElement>.shouldNotHaveTagName(tagName: String) = also { it shouldNot haveTagName(tagName) }

fun haveDimension(expectedDimension: Dimension) = object : Matcher<FluentList<FluentWebElement>> {
    override fun test(value: FluentList<FluentWebElement>): MatcherResult {
        val actualDimensions = value.dimensions()

        return MatcherResult(
            actualDimensions.any { it == expectedDimension },
            "No selected elements with dimension '$expectedDimension'. found $actualDimensions",
            "No selected elements should have dimension '$expectedDimension'."
        )
    }
}

fun haveDimension(expectedDimension: Pair<Int, Int>) =
    haveDimension(Dimension(expectedDimension.first, expectedDimension.second))

fun FluentList<FluentWebElement>.shouldHaveDimension(dimension: Dimension) = also { it should haveDimension(dimension) }
fun FluentList<FluentWebElement>.shouldNotHaveDimension(dimension: Dimension) =
    also { it shouldNot haveDimension(dimension) }
