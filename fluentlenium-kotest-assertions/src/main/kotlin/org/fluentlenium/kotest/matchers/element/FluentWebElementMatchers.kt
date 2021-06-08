package org.fluentlenium.kotest.matchers.element

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.fluentlenium.core.domain.FluentWebElement

fun bePresent() = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult = MatcherResult(
        value.enabled(),
        "Element '$value' should be present",
        "Element '$value' should not be present"
    )
}

fun FluentWebElement.shouldBePresent() = this should bePresent()
fun FluentWebElement.shouldNotBePresent() = this shouldNot bePresent()

fun beEnabled() = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult = MatcherResult(
        value.enabled(),
        "Element $value should be enabled",
        "Element $value should not be enabled"
    )
}

fun FluentWebElement.shouldBeEnabled() = this should beEnabled()
fun FluentWebElement.shouldNotBeEnabled() = this shouldNot beEnabled()

fun beDisplayed() = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult = MatcherResult(
        value.displayed(),
        "Element $value should be displayed",
        "Element $value should not be displayed"
    )
}

fun FluentWebElement.shouldBeDisplayed() = this should beDisplayed()
fun FluentWebElement.shouldNotBeDisplayed() = this shouldNot beDisplayed()

fun beClickable() = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult = MatcherResult(
        value.clickable(),
        "Element $value should be clickable",
        "Element $value should not be clickable"
    )
}

fun FluentWebElement.shouldBeClickable() = this should beClickable()
fun FluentWebElement.shouldNotBeClickable() = this should beClickable()

fun beSelected() = object : Matcher<FluentWebElement> {
    override fun test(value: FluentWebElement): MatcherResult = MatcherResult(
        value.selected(),
        "Element $value should be selected",
        "Element $value should not be selected"
    )
}

fun FluentWebElement.shouldBeSelected() = this should beSelected()
fun FluentWebElement.shouldNotBeSelected() = this shouldNot beSelected()
