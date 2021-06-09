package org.fluentlenium.kotest.matchers.elements

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement

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
