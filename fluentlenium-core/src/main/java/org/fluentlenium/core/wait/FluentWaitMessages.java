package org.fluentlenium.core.wait;

final class FluentWaitMessages {
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
    private static final String IS_NOT_LESS_THAN = " is not less than ";
    private static final String IS_NOT_LESS_THAN_OR_EQUAL_TO = " is not less than or equal to ";
    private static final String IS_NOT_GREATHER_THAN = " is not greather than ";
    private static final String IS_NOT_GREATHER_THAN_OR_EQUAL_TO = " is not greather than or equal to ";
    private static final String IS_NOT_LOADED = " is not loaded";
    private static final String IS_NOT_DISPLAYED = " is not displayed";
    private static final String IS_DISPLAYED = " is displayed";
    private static final String IS_NOT_ENABLED = " is not enabled";
    private static final String IS_NOT_CLICKABLE = " is not clickable";

    static final String hasSizeMessage(String selectionName, int size) {
        return selectionName + HAS_NOT_THE_SIZE + size + POINT;
    }

    static final String hasAttributeMessage(String selectionName, String attribute, String value) {
        return selectionName + WITH_ATTRIBUTE + attribute + WITH_VALUE + value + IS_NOT_PRESENT + POINT;
    }

    static final String isPageLoaded(String url) {
        return PAGE + url + IS_NOT_LOADED + POINT;
    }

    static final String isPresentMessage(String selectionName) {
        return selectionName + IS_NOT_PRESENT;
    }

    static final String isNotPresentMessage(String selectionName) {
        return selectionName + IS_PRESENT;
    }

    static final String isDisplayedMessage(String selectionName) {
        return selectionName + IS_NOT_DISPLAYED;
    }

    static final String isEnabledMessage(String selectionName) {
        return selectionName + IS_NOT_ENABLED;
    }

    static final String isClickableMessage(String selectionName) {
        return selectionName + IS_NOT_CLICKABLE;
    }

    static final String hasTextMessage(String selectionName, String value) {
        return selectionName + HAS_NOT_THE_TEXT + value + POINT;
    }

    static final String hasNameMessage(String selectionName, String value) {
        return selectionName + HAS_NOT_THE_NAME + value + POINT;
    }

    static final String hasIdMessage(String selectionName, String value) {
        return selectionName + HAS_NOT_THE_ID + value + POINT;
    }

    static final String equalToMessage(String selectionName, int size) {
        return selectionName + HAS_NOT_SIZE_EQUAL_TO + size + POINT;
    }

    static final String lessThanOrEqualToMessage(String selectionName, int size) {
        return selectionName + IS_NOT_LESS_THAN_OR_EQUAL_TO + size + POINT;
    }

    static final String notEqualToMessage(String selectionName, int size) {
        return selectionName + IS_EQUAL_TO + size + POINT;
    }

    static final String lessThanMessage(String selectionName, int size) {
        return selectionName + IS_NOT_LESS_THAN + size + POINT;
    }

    static final String greatherThanOrEqualToMessage(String selectionName, int size) {
        return selectionName + IS_NOT_GREATHER_THAN_OR_EQUAL_TO + size + POINT;
    }

    static final String greatherThanMessage(String selectionName, int size) {
        return selectionName + IS_NOT_GREATHER_THAN + size + POINT;
    }

    static final String isWindowDisplayedMessage(String windowName) {
        return WINDOW + windowName + IS_NOT_DISPLAYED + POINT;
    }

    static final String isWindowNotDisplayedMessage(String windowName) {
        return WINDOW + windowName + IS_DISPLAYED + POINT;
    }
}

