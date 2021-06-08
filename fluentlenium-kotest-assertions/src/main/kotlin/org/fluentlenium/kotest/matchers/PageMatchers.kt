package org.fluentlenium.kotest.matchers

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.By

fun haveElement(element: FluentWebElement) = object : Matcher<FluentPage> {
    override fun test(value: FluentPage): MatcherResult {

        return MatcherResult(
            element.present(),
            "Page should have element $element",
            "Page should not have element $element"
        )
    }
}

fun haveElements(elements: FluentList<FluentWebElement>) = object : Matcher<FluentPage> {
    override fun test(value: FluentPage): MatcherResult {
        TODO("Not yet implemented")
    }
}

fun haveElementDisplayed(fluentWebElement: FluentWebElement) = object : Matcher<FluentPage> {
    override fun test(value: FluentPage): MatcherResult {
        TODO("Not yet implemented")
    }
}

fun haveTitle(title: String) = object : Matcher<FluentPage> {
    override fun test(value: FluentPage): MatcherResult {

        val pageTitle: String = value.driver.title

        return MatcherResult(
            pageTitle == title,
            "Current page title is '$pageTitle'. Expected '$title'",
            "Current page title '$pageTitle' Should not be '$title'."
        )
    }
}

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
            "should have expected elements located by $by",
            "should not have expected elements located by $by"
        )
    }
}

fun haveExpectedUrl() = object : Matcher<FluentPage> {
    override fun test(value: FluentPage): MatcherResult {
        val url: String = value.url ?: return MatcherResult(
            false,
            "no page url available",
            "no page url available"
        )

        value.isAtUsingUrl(url)

        val isAt = try {
            value.isAtUsingUrl(url)
            true
        } catch (e: AssertionError) {
            false
        }
        return MatcherResult(
            isAt,
            "Should be at $url, currently ${value.driver.currentUrl}",
            "Should not be at $url, currently ${value.driver.currentUrl}"
        )
    }
}

