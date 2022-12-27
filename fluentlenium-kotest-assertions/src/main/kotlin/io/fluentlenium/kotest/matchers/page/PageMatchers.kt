package io.fluentlenium.kotest.matchers.page

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import io.fluentlenium.core.FluentPage
import io.fluentlenium.core.domain.FluentList
import io.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.By

fun haveElement(element: _root_ide_package_.io.fluentlenium.core.domain.FluentWebElement) = object : Matcher<_root_ide_package_.io.fluentlenium.core.FluentPage> {
    override fun test(value: _root_ide_package_.io.fluentlenium.core.FluentPage): MatcherResult =
        MatcherResult(
            element.present(),
            { "Page should have element '$element'" },
            {
                "Page should not have element '$element'"
            }
        )
}

fun _root_ide_package_.io.fluentlenium.core.FluentPage.shouldHaveElement(element: _root_ide_package_.io.fluentlenium.core.domain.FluentWebElement) = also { it should haveElement(element) }
fun _root_ide_package_.io.fluentlenium.core.FluentPage.shouldNotHaveElement(element: _root_ide_package_.io.fluentlenium.core.domain.FluentWebElement) = also { it shouldNot haveElement(element) }

fun haveElements(elements: _root_ide_package_.io.fluentlenium.core.domain.FluentList<_root_ide_package_.io.fluentlenium.core.domain.FluentWebElement>) = object : Matcher<_root_ide_package_.io.fluentlenium.core.FluentPage> {
    override fun test(value: _root_ide_package_.io.fluentlenium.core.FluentPage): MatcherResult =
        MatcherResult(
            elements.present(),
            { "Page should have elements '$elements'" },
            {
                "Page should not have elements '$elements'"
            }
        )
}

fun _root_ide_package_.io.fluentlenium.core.FluentPage.shouldNotHaveElements(element: _root_ide_package_.io.fluentlenium.core.domain.FluentList<_root_ide_package_.io.fluentlenium.core.domain.FluentWebElement>) =
    also { it shouldNot haveElements(element) }

fun _root_ide_package_.io.fluentlenium.core.FluentPage.shouldHaveElements(element: _root_ide_package_.io.fluentlenium.core.domain.FluentList<_root_ide_package_.io.fluentlenium.core.domain.FluentWebElement>) = also { it should haveElements(element) }

fun haveElementDisplayed(element: _root_ide_package_.io.fluentlenium.core.domain.FluentWebElement) = object : Matcher<_root_ide_package_.io.fluentlenium.core.FluentPage> {
    override fun test(value: _root_ide_package_.io.fluentlenium.core.FluentPage): MatcherResult =
        MatcherResult(
            element.displayed(),
            { "Page should display '$element'" },
            {
                "Page should not display element '$element'"
            }
        )
}

fun _root_ide_package_.io.fluentlenium.core.FluentPage.shouldHaveElementDisplayed(element: _root_ide_package_.io.fluentlenium.core.domain.FluentWebElement) = also { it should haveElementDisplayed(element) }
fun _root_ide_package_.io.fluentlenium.core.FluentPage.shouldNotHaveElementDisplayed(element: _root_ide_package_.io.fluentlenium.core.domain.FluentWebElement) =
    also { it shouldNot haveElementDisplayed(element) }

fun haveTitle(title: String) = object : Matcher<_root_ide_package_.io.fluentlenium.core.FluentPage> {
    override fun test(value: _root_ide_package_.io.fluentlenium.core.FluentPage): MatcherResult {
        val pageTitle: String = value.driver.title

        return MatcherResult(
            pageTitle == title,
            { "Actual page title is '$pageTitle'. Expected title is '$title'" },
            {
                "Current page should not have title '$pageTitle'."
            }
        )
    }
}

fun _root_ide_package_.io.fluentlenium.core.FluentPage.shouldHaveTitle(title: String) = also { this should haveTitle(title) }
fun _root_ide_package_.io.fluentlenium.core.FluentPage.shouldNotHaveTitle(title: String) = also { this shouldNot haveTitle(title) }

fun haveUrl(url: String) = object : Matcher<_root_ide_package_.io.fluentlenium.core.FluentPage> {
    override fun test(value: _root_ide_package_.io.fluentlenium.core.FluentPage): MatcherResult {
        val currentUrl: String = value.driver.currentUrl

        return MatcherResult(
            currentUrl == url,
            { "Current page url is '$currentUrl'. Expected '$url'" },
            {
                "Current page url is '$currentUrl' Should not be '$url'."
            }
        )
    }
}

fun haveExpectedElements() = object : Matcher<_root_ide_package_.io.fluentlenium.core.FluentPage> {
    override fun test(value: _root_ide_package_.io.fluentlenium.core.FluentPage): MatcherResult {
        val by: By = value.classAnnotations.buildBy()

        val isAt = try {
            value.isAtUsingSelector(by)
            true
        } catch (e: AssertionError) {
            false
        }

        return MatcherResult(
            isAt,
            { "should have elements located by '$by'" },
            {
                "should not have elements located by '$by'"
            }
        )
    }
}

fun _root_ide_package_.io.fluentlenium.core.FluentPage.shouldHaveExpectedElements() = also { this should haveExpectedElements() }
fun _root_ide_package_.io.fluentlenium.core.FluentPage.shouldNotHaveExpectedElements() = also { this shouldNot haveExpectedElements() }

fun haveExpectedUrl() = object : Matcher<_root_ide_package_.io.fluentlenium.core.FluentPage> {
    override fun test(value: _root_ide_package_.io.fluentlenium.core.FluentPage): MatcherResult {
        val url: String = value.url ?: return MatcherResult(
            false,
            { "no page url available" },
            {
                "no page url available"
            }
        )

        val isAt = try {
            value.isAtUsingUrl(url)
            true
        } catch (e: AssertionError) {
            false
        }

        return MatcherResult(
            isAt,
            { "Should be at '$url', current url is ${value.driver.currentUrl}" },
            {
                "Should not be at '$url'"
            }
        )
    }
}

fun _root_ide_package_.io.fluentlenium.core.FluentPage.shouldHaveExpectedUrl() = also { this should haveExpectedUrl() }
fun _root_ide_package_.io.fluentlenium.core.FluentPage.shouldNotHaveExpectedUrl() = also { this shouldNot haveExpectedUrl() }
