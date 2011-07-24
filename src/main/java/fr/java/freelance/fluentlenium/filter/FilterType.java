package fr.java.freelance.fluentlenium.filter;

/**
 * Different filter actually supported by the framework.
 * PreFilter are filter than are supported by WebDriver as CSS Selector
 * PostFilter are used after the webdriver selection to filter the collection
 */
public enum FilterType {
    CUSTOM, NAME, ID, TEXT;


}