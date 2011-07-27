package fr.java.freelance.fluentlenium.filter;

/**
 * Different integration actually supported by the framework.
 * PreFilter are integration than are supported by WebDriver as CSS Selector
 * PostFilter are used after the webdriver selection to integration the collection
 */
public enum FilterType {
    CUSTOM, NAME, ID, TEXT;
}