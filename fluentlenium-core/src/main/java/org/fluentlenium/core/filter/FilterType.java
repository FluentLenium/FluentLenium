package org.fluentlenium.core.filter;

/**
 * Different fluentlenium.integration. actually supported by the framework.
 * PreFilter are fluentlenium.integration. than are supported by WebDriver as CSS Selector
 * PostFilter are used after the webdriver selection to fluentlenium.integration. the collection
 */
public enum FilterType {
    CUSTOM, NAME, ID, TEXT, CLASS
}
