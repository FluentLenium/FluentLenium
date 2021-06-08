package org.fluentlenium.kotest.matchers.page

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.By

fun haveElement(element: FluentWebElement) = object : Matcher<FluentPage> {
    override fun test(value: FluentPage): MatcherResult =
        MatcherResult(
            element.present(),
            "Page should have element '$element'",
            "Page should not have element '$element'"
        )
}

fun FluentPage.shouldHaveElement(element: FluentWebElement) = this should haveElement(element)
fun FluentPage.shouldNotHaveElement(element: FluentWebElement) = this shouldNot haveElement(element)

fun haveElements(elements: FluentList<FluentWebElement>) = object : Matcher<FluentPage> {
    override fun test(value: FluentPage): MatcherResult =
        MatcherResult(
            elements.present(),
            "Page should have elements '$elements'",
            "Page should not have elements '$elements'"
        )
}

fun FluentPage.shouldNotHaveElements(element: FluentList<FluentWebElement>) = this shouldNot haveElements(element)
fun FluentPage.shouldHaveElements(element: FluentList<FluentWebElement>) = this should haveElements(element)

fun haveElementDisplayed(element: FluentWebElement) = object : Matcher<FluentPage> {
    override fun test(value: FluentPage): MatcherResult = MatcherResult(
        element.displayed(),
        "Page should display '$element'",
        "Page should not display element '$element'"
    )
}

fun FluentPage.shouldHaveElementDisplayed(element: FluentWebElement) = this should haveElementDisplayed(element)
fun FluentPage.shouldNotHaveElementDisplayed(element: FluentWebElement) = this shouldNot haveElementDisplayed(element)

fun haveTitle(title: String) = object : Matcher<FluentPage> {
    override fun test(value: FluentPage): MatcherResult {

        val pageTitle: String = value.driver.title

        return MatcherResult(
            pageTitle == title,
            "Actual page title is '$pageTitle'. Expected title is '$title'",
            "Current page should not have title '$pageTitle'."
        )
    }
}

fun FluentPage.shouldHaveTitle(title: String) = this should haveTitle(title)
fun FluentPage.shouldNotHaveTitle(title: String) = this shouldNot haveTitle(title)

fun haveUrl(url: String) = object : Matcher<FluentPage> {
    override fun test(value: FluentPage): MatcherResult {

        val currentUrl: String = value.driver.currentUrl

        return MatcherResult(
            currentUrl == url,
            "Current page url is '$currentUrl'. Expected '$url'",
            "Current page url is '$currentUrl' Should not be '$url'."
        )
    }
}

fun haveExpectedElements() = object : Matcher<FluentPage> {
    override fun test(value: FluentPage): MatcherResult {
        val by: By = value.classAnnotations.buildBy()

        val isAt = try {
            value.isAtUsingSelector(by)
            true
        } catch (e: AssertionError) {
            false
        }

        return MatcherResult(
            isAt,
            "should have elements located by '$by'",
            "should not have elements located by '$by'"
        )
    }
}

fun FluentPage.shouldHaveExpectedElements() = this should haveExpectedElements()
fun FluentPage.shouldNotHaveExpectedElements() = this shouldNot haveExpectedElements()

fun haveExpectedUrl() = object : Matcher<FluentPage> {
    override fun test(value: FluentPage): MatcherResult {
        val url: String = value.url ?: return MatcherResult(
            false,
            "no page url available",
            "no page url available"
        )

        val isAt = try {
            value.isAtUsingUrl(url)
            true
        } catch (e: AssertionError) {
            false
        }

        return MatcherResult(
            isAt,
            "Should be at '$url', current url is ${value.driver.currentUrl}",
            "Should not be at '$url'"
        )
    }
}

fun FluentPage.shouldHaveExpectedUrl() = this should haveExpectedUrl()
fun FluentPage.shouldNotHaveExpectedUrl() = this shouldNot haveExpectedUrl()
