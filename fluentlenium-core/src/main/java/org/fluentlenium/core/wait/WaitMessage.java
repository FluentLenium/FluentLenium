package org.fluentlenium.core.wait;

import org.openqa.selenium.By;

public class WaitMessage {
    private static final String SELECTOR = "Selector ";
    private static final String PAGE = "Page ";
    private static final String WINDOW = "Window ";
    private static final String IS_NOT_PRESENT = " is not present.";
    private static final String IS_PRESENT = " is present.";
    private static final String HAS_NOT_THE_NAME = " has not the name ";
    private static final String HAS_NOT_THE_SIZE = " has not the size ";
    private static final String POINT = ".";
    private static final String HAS_NOT_THE_TEXT = " has not the text ";
    private static final String HAS_NOT_THE_ID = " has not the id ";
    private static final String WITH_ATTRIBUTE = " with attribute ";
    private static final String WITH_VALUE = " with value ";
    private static final String HAS_NOT_SIZE_EQUAL_TO = " has not size equal to ";
    private static final String IS_EQUAL_TO = " is equal to ";
    private static final String IS_NOT_EQUAL_TO = " is not equal to ";
    private static final String IS_NOT_LESS_THAN = " is not less than ";
    private static final String IS_NOT_LESS_THAN_OR_EQUAL_TO = " is not less than or equal to ";
    private static final String IS_NOT_GREATHER_THAN = " is not greather than ";
    private static final String IS_NOT_GREATHER_THAN_OR_EQUAL_TO = " is not greather than or equal to ";
    private static final String IS_NOT_LOADED = " is not loaded";
    private static final String IS_NOT_DISPLAYED = " is not displayed";
    private static final String IS_DISPLAYED = " is displayed";
    private static final String IS_NOT_ENABLED = " is not enabled";
    private static final String IS_NOT_CLICKABLE = " is not clickable";
    private static final String IS_NOT_ABOVE = " is not above screen top or invisible";

    static final String hasSizeMessage(By locator, int size) {
        return SELECTOR + locator + HAS_NOT_THE_SIZE + size + POINT;
    }

    static final String hasAttributeMessage(By locator, String attribute, String value) {
        return SELECTOR + locator + WITH_ATTRIBUTE + attribute + WITH_VALUE + value + IS_NOT_PRESENT + POINT;
    }

    static final String isPageLoaded(String url) {
        return PAGE + url + IS_NOT_LOADED + POINT;
    }

    static final String isPresentMessage(By locator) {
        return SELECTOR + locator + IS_NOT_PRESENT;
    }

    static final String isNotPresentMessage(By locator) {
        return SELECTOR + locator + IS_PRESENT;
    }

    static final String isDisplayedMessage(By locator) {
        return SELECTOR + locator + IS_NOT_DISPLAYED;
    }

    static final String isEnabledMessage(By locator) {
        return SELECTOR + locator + IS_NOT_ENABLED;
    }

    static final String isClickableMessage(By locator) {
        return SELECTOR + locator + IS_NOT_CLICKABLE;
    }

    static final String hasTextMessage(By locator, String value) {
        return SELECTOR + locator + HAS_NOT_THE_TEXT + value + POINT;
    }

    static final String hasNameMessage(By locator, String value) {
        return SELECTOR + locator + HAS_NOT_THE_NAME + value + POINT;
    }

    static final String hasIdMessage(By locator, String value) {
        return SELECTOR + locator + HAS_NOT_THE_ID + value + POINT;
    }

    static final String equalToMessage(By locator, int size) {
        return SELECTOR + locator + HAS_NOT_SIZE_EQUAL_TO + size + POINT;
    }

    static final String isAboveMessage(By locator) {
        return SELECTOR + locator + IS_NOT_ABOVE + POINT;
    }

    static final <T> String isAttributeEqualToMessage(String attributeName, T expectedValue) {
        return attributeName + IS_NOT_EQUAL_TO + expectedValue + POINT;
    }

    static final String lessThanOrEqualToMessage(By locator, int size) {
        return SELECTOR + locator + IS_NOT_LESS_THAN_OR_EQUAL_TO + size + POINT;
    }

    static final String notEqualToMessage(By locator, int size) {
        return SELECTOR + locator + IS_EQUAL_TO + size + POINT;
    }

    static final String lessThanMessage(By locator, int size) {
        return SELECTOR + locator + IS_NOT_LESS_THAN + size + POINT;
    }

    static final String greatherThanOrEqualToMessage(By locator, int size) {
        return SELECTOR + locator + IS_NOT_GREATHER_THAN_OR_EQUAL_TO + size + POINT;
    }

    static final String greatherThanMessage(By locator, int size) {
        return SELECTOR + locator + IS_NOT_GREATHER_THAN + size + POINT;
    }

    static final String isWindowDisplayedMessage(String windowName) {
        return WINDOW + windowName + IS_NOT_DISPLAYED + POINT;
    }

    static final String isWindowNotDisplayedMessage(String windowName) {
        return WINDOW + windowName + IS_DISPLAYED + POINT;
    }
}

