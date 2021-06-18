package org.fluentlenium.adapter.kotest

import io.appium.java_client.MobileBy
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement
import org.fluentlenium.core.search.SearchControl
import org.fluentlenium.core.search.SearchFilter
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

fun <E : FluentWebElement> SearchControl<E>.jq(selector: String, vararg filter: SearchFilter): FluentList<E> =
    `$`(selector, *filter)

fun <E : FluentWebElement> SearchControl<E>.jq(rawElements: List<WebElement>): FluentList<E> =
    `$`(rawElements)

fun <E : FluentWebElement> SearchControl<E>.jq(vararg filters: SearchFilter): FluentList<E> =
    `$`(*filters)

fun <E : FluentWebElement> SearchControl<E>.jq(locator: By, vararg filters: SearchFilter): FluentList<E> =
    `$`(locator, *filters)

fun <E : FluentWebElement> SearchControl<E>.jq(locator: MobileBy, vararg filters: SearchFilter): FluentList<E> =
    `$`(locator, *filters)
