package org.fluentlenium.kotest.matchers.jq

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.Dimension

/**
 * Checks if at least one element in a list of elements, equals the expected text.
 * <p>
 * Example:
 * <p>
 * <pre>
 * jq("h1") should haveText("text")
 * </pre>
 * <p>
 *
 * @param expectedText text to find
 * @return the matcher object
 */
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

/**
 * @see haveText
 */
fun FluentList<FluentWebElement>.shouldHaveText(text: String) = also { it should haveText(text) }

/**
 * @see haveText
 */
fun FluentList<FluentWebElement>.shouldNotHaveText(text: String) = also { it shouldNot haveText(text) }

/**
 * Checks if at least one element in a list of elements, contains the expected text as a substring.
 * <p>
 * Example:
 * <p>
 * <pre>
 * jq("h1") should haveTextContaining("substring")
 * </pre>
 * <p>
 *
 * @param expectedText substring to find
 * @return the matcher object
 */
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

/**
 * @see haveTextContaining
 */
fun FluentList<FluentWebElement>.shouldHaveTextContaining(text: String) = also { it should haveTextContaining(text) }

/**
 * @see haveTextContaining
 */
fun FluentList<FluentWebElement>.shouldNotHaveTextContaining(text: String) =
    also { it shouldNot haveTextContaining(text) }

/**
 * Checks if at least one element in a list of elements, matches the given pattern.
 * <p>
 * Example:
 * <p>
 * <pre>
 * jq("h1") should haveTextMatching("t.*")
 * </pre>
 * <p>
 *
 * @param expectedPattern pattern to match
 * @return the matcher object
 */
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

/**
 * @see haveTextMatching
 */
fun FluentList<FluentWebElement>.shouldHaveTextMatching(text: String) = also { it should haveTextMatching(text) }

/**
 * @see haveTextMatching
 */
fun FluentList<FluentWebElement>.shouldNotHaveTextMatching(text: String) = also { it shouldNot haveTextMatching(text) }

/**
 * Checks if at least one element in a list of elements, has the given id.
 * <p>
 * Example:
 * <p>
 * <pre>
 * jq("#myId") should haveId("myId")
 * </pre>
 * <p>
 *
 * @param id id to find
 * @return the matcher object
 */
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


/**
 * @see haveId
 */
fun FluentList<FluentWebElement>.shouldHaveId(id: String) = also { it should haveId(id) }

/**
 * @see haveId
 */
fun FluentList<FluentWebElement>.shouldNotHaveId(id: String) = also { it shouldNot haveId(id) }

const val CLASS_ATTRIBUTE = "class"
const val CLASS_DELIMITER = " "

/**
 * Checks if at least one element in a list of elements, has the expected class(es).
 * <p>
 * Example:
 * <p>
 * <pre>
 * jq(".myClass") should haveClass("myClass")
 * jq(".myClass") should haveClass("class1", "class2")
 * </pre>
 * <p>
 *
 * @param expectedText expected classes
 * @return the matcher object
 */
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

/**
 * @see haveClass
 */
fun FluentList<FluentWebElement>.shouldHaveClass(vararg classes: String) = also { it should haveClass(*classes) }

/**
 * @see haveClass
 */
fun FluentList<FluentWebElement>.shouldNotHaveClass(vararg classes: String) = also { it shouldNot haveClass(*classes) }

/**
 * Checks if at least one element in a list of elements, has the expected name.
 * <p>
 * Example:
 * <p>
 * <pre>
 * jq("#myName") should haveName("myName")
 * </pre>
 * <p>
 *
 * @param expectedName name to find
 * @return the matcher object
 */
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

/**
 * @see haveName
 */
fun FluentList<FluentWebElement>.shouldHaveName(name: String) = also { it should haveName(name) }

/**
 * @see haveName
 */
fun FluentList<FluentWebElement>.shouldNotHaveName(name: String) = also { it shouldNot haveName(name) }

/**
 * Checks if at least one element in a list of elements, has the expected attribute.
 * <p>
 * Example:
 * <p>
 * <pre>
 * jq("h1") should haveAttribute("attr")
 * </pre>
 * <p>
 *
 * @param expectedAttribute attribute to find
 * @return the matcher object
 */
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

/**
 * @see haveAttribute
 */
fun FluentList<FluentWebElement>.shouldHaveAttribute(attribute: String) = also { it should haveAttribute(attribute) }

/**
 * @see haveAttribute
 */
fun FluentList<FluentWebElement>.shouldNotHaveAttribute(attribute: String) =
    also { it shouldNot haveAttribute(attribute) }

/**
 * Checks if at least one element in a list of elements, contains the expected value in its value attribute.
 * <p>
 * Example:
 * <p>
 * <pre>
 * jq("h1") should haveValue("text")
 * </pre>
 * <p>
 *
 * @param expectedValue value to find
 * @return the matcher object
 */
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

/**
 * @see haveValue
 */
fun FluentList<FluentWebElement>.shouldHaveValue(value: String) = also { it should haveValue(value) }

/**
 * @see haveValue
 */
fun FluentList<FluentWebElement>.shouldNotHaveValue(value: String) = also { it shouldNot haveValue(value) }

/**
 * Checks if at least one element in a list of elements, contains the expected attribute key and value.
 * <p>
 * Example:
 * <p>
 * <pre>
 * jq("h1") should haveAttributeValue("key", "value")
 * </pre>
 * <p>
 *
 * @param key attribute key
 * @param expectedValue attribute value
 * @return the matcher object
 */
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

/**
 * @see haveAttributeValue
 */
fun FluentList<FluentWebElement>.shouldHaveAttributeValue(key: String, attribute: String) =
    also { it should haveAttributeValue(key, attribute) }

/**
 * @see haveAttributeValue
 */
fun FluentList<FluentWebElement>.shouldHaveAttributeValue(pair: Pair<String, String>) =
    shouldHaveAttributeValue(pair.first, pair.second)

/**
 * @see haveAttributeValue
 */
fun FluentList<FluentWebElement>.shouldNotHaveAttributeValue(pair: Pair<String, String>) =
    shouldNotHaveAttributeValue(pair.first, pair.second)

/**
 * @see haveAttributeValue
 */
fun FluentList<FluentWebElement>.shouldNotHaveAttributeValue(key: String, attribute: String) =
    also { it shouldNot haveAttributeValue(key, attribute) }

/**
 * Checks if at least one element in a list of elements, is of the expected tag.
 * <p>
 * Example:
 * <p>
 * <pre>
 * jq("h1") should haveTag("h1")
 * </pre>
 * <p>
 *
 * @param expectedTag tagname to find
 * @return the matcher object
 */
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

/**
 * @see haveTagName
 */
fun FluentList<FluentWebElement>.shouldHaveTagName(tagName: String) = also { it should haveTagName(tagName) }

/**
 * @see haveTagName
 */
fun FluentList<FluentWebElement>.shouldNotHaveTagName(tagName: String) = also { it shouldNot haveTagName(tagName) }

/**
 * Checks if at least one element in a list of elements, has the expected dimension.
 * <p>
 * Example:
 * <p>
 * <pre>
 * jq("h1") should haveDimension(Dimension(200, 200))
 * </pre>
 * <p>
 *
 * @param expectedDimension dimension to check for
 * @return the matcher object
 */
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

/**
 * @see haveDimension
 */
fun haveDimension(expectedDimension: Pair<Int, Int>) =
    haveDimension(Dimension(expectedDimension.first, expectedDimension.second))

/**
 * @see haveDimension
 */
fun FluentList<FluentWebElement>.shouldHaveDimension(dimension: Dimension) = also { it should haveDimension(dimension) }

/**
 * @see haveDimension
 */
fun FluentList<FluentWebElement>.shouldNotHaveDimension(dimension: Dimension) =
    also { it shouldNot haveDimension(dimension) }
