package io.fluentlenium.adapter.kotest

import io.appium.java_client.AppiumBy
import io.fluentlenium.core.domain.FluentList
import io.fluentlenium.core.domain.FluentWebElement
import io.fluentlenium.core.search.SearchControl
import io.fluentlenium.core.search.SearchFilter
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

/**
 * Find list of elements with CSS selector and filters.
 *
 * @param selector CSS selector
 * @param filters  set of filters
 * @return list of element
 */
fun <E : FluentWebElement> SearchControl<E>.jq(selector: String, vararg filters: SearchFilter): FluentList<E> =
    `$`(selector, *filters)

/**
 * Wrap raw selenium elements into a list of elements.
 *
 * @param rawElements raw selenium elements
 * @return list of element
 */
fun <E : FluentWebElement> SearchControl<E>.jq(rawElements: List<WebElement>): FluentList<E> =
    `$`(rawElements)

/**
 * Find list of elements with filters.
 *
 * @param filters set of filters in the current context
 * @return list of elements
 */
fun <E : FluentWebElement> SearchControl<E>.jq(vararg filters: SearchFilter): FluentList<E> =
    `$`(*filters)

/**
 * Find list of elements with Selenium locator and filters.
 *
 * @param locator elements locator
 * @param filters filters set
 * @return list of elements
 */
fun <E : FluentWebElement> SearchControl<E>.jq(locator: By, vararg filters: SearchFilter): FluentList<E> =
    `$`(locator, *filters)

/**
 * Find list of elements with Appium locator and filters.
 *
 * @param locator mobile elements locator
 * @param filters filters set
 * @return list of elements
 */
fun <E : FluentWebElement> SearchControl<E>.jq(locator: AppiumBy, vararg filters: SearchFilter): FluentList<E> =
    `$`(locator, *filters)
