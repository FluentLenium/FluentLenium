package org.fluentlenium.core.wait;

import com.google.common.base.Joiner;

import java.util.List;

final class FluentWaitMessages {
    private static final String PAGE = "Page ";
    private static final String WINDOW = "Window ";
    private static final String IS_NOT_PRESENT = " is not present";
    private static final String IS_PRESENT = " is present";
    private static final String HAS_NOT_THE_NAME = " has not the name ";
    private static final String HAS_THE_NAME = " has not the name ";
    private static final String HAS_NOT_THE_SIZE = " has not the size ";
    private static final String HAS_THE_SIZE = " has not the size ";
    private static final String POINT = ".";
    private static final String HAS_NOT_THE_TEXT = " has not the text ";
    private static final String HAS_THE_TEXT = " has the text ";
    private static final String HAS_NOT_THE_ID = " has not the id ";
    private static final String HAS_THE_ID = " has the id ";
    private static final String WITH_ATTRIBUTE = " with attribute ";
    private static final String WITH_POS_X = " with X position ";
    private static final String WITH_VALUE = " with value ";
    private static final String HAS_NOT_SIZE_EQUAL_TO = " has not size equal to ";
    private static final String IS_EQUAL_TO = " is equal to ";
    private static final String IS_NOT_LESS_THAN = " is not less than ";
    private static final String IS_LESS_THAN = " is less than ";
    private static final String IS_NOT_LESS_THAN_OR_EQUAL_TO = " is not less than or equal to ";
    private static final String IS_LESS_THAN_OR_EQUAL_TO = " is less than or equal to ";
    private static final String IS_NOT_GREATER_THAN = " is not greater than ";
    private static final String IS_GREATER_THAN = " is greater than ";
    private static final String IS_NOT_GREATER_THAN_OR_EQUAL_TO = " is not greater than or equal to ";
    private static final String IS_GREATER_THAN_OR_EQUAL_TO = " is greater than or equal to ";
    private static final String IS_NOT_LOADED = " is not loaded";
    private static final String IS_NOT_DISPLAYED = " is not displayed";
    private static final String IS_DISPLAYED = " is displayed";
    private static final String IS_NOT_STALE = " is not stale";
    private static final String IS_STALE = " is stale";
    private static final String IS_NOT_ENABLED = " is not enabled";
    private static final String IS_ENABLED = " is enabled";
    private static final String IS_NOT_CLICKABLE = " is not clickable";
    private static final String IS_CLICKABLE = " is clickable";
    private static final String IS_NOT_SELECTED = " is not selected";
    private static final String IS_SELECTED = " is selected";
    private static final String IS_NOT_ABOVE = " is not above screen top or invisible";
    private static final String PREDICATE_IS_NOT_VERIFIED = " predicated is not verified";
    private static final String PREDICATE_IS_VERIFIED = " predicated is verified";
    private static final String IS_NOT_AT_POSITION = " is not at position";
    private static final String IS_AT_POSITION = " is at position";
    private static final String HAS_NOT_THE_DIMENSION = " has not the dimension";
    private static final String HAS_THE_DIMENSION = " has the dimension";


    static final String hasSizeMessage(String selectionName, int size) {
        return selectionName + HAS_NOT_THE_SIZE + size + POINT;
    }

    static final String hasNotSizeMessage(String selectionName, int size) {
        return selectionName + HAS_THE_SIZE + size + POINT;
    }

    static final String hasAttributeMessage(String selectionName, String attribute, String value) {
        return selectionName + WITH_ATTRIBUTE + attribute + WITH_VALUE + value + IS_NOT_PRESENT + POINT;
    }

    static final String hasNotAttributeMessage(String selectionName, String attribute, String value) {
        return selectionName + WITH_ATTRIBUTE + attribute + WITH_VALUE + value + IS_PRESENT + POINT;
    }

    static final String isAboveMessage(List<String> ids) {
        return Joiner.on(",").join(ids) + IS_NOT_ABOVE + POINT;
    }

    static final String hasPositionXMessage(String selectionName, Integer value) {
        return selectionName + WITH_POS_X + value + IS_NOT_PRESENT + POINT;
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

    static final String isNotDisplayedMessage(String selectionName) {
        return selectionName + IS_DISPLAYED;
    }

    static final String isStaleMessage(String selectionName) {
        return selectionName + IS_NOT_STALE;
    }

    static final String isNotStaleMessage(String selectionName) {
        return selectionName + IS_STALE;
    }

    static final String isEnabledMessage(String selectionName) {
        return selectionName + IS_NOT_ENABLED;
    }

    static final String isNotEnabledMessage(String selectionName) {
        return selectionName + IS_ENABLED;
    }

    static final String isClickableMessage(String selectionName) {
        return selectionName + IS_NOT_CLICKABLE;
    }

    static final String isNotClickableMessage(String selectionName) {
        return selectionName + IS_CLICKABLE;
    }

    static final String isSelectedMessage(String selectionName) {
        return selectionName + IS_SELECTED;
    }

    static final String isNotSelectedMessage(String selectionName) {
        return selectionName + IS_NOT_SELECTED;
    }

    static final String hasTextMessage(CharSequence selectionName, CharSequence value) {
        return selectionName + HAS_NOT_THE_TEXT + value + POINT;
    }

    static final String hasNotTextMessage(CharSequence selectionName, CharSequence value) {
        return selectionName + HAS_THE_TEXT + value + POINT;
    }

    static final String hasNameMessage(String selectionName, String value) {
        return selectionName + HAS_NOT_THE_NAME + value + POINT;
    }

    static final String hasNotNameMessage(String selectionName, String value) {
        return selectionName + HAS_THE_NAME + value + POINT;
    }

    static final String hasIdMessage(String selectionName, String value) {
        return selectionName + HAS_NOT_THE_ID + value + POINT;
    }

    static final String hasNotIdMessage(String selectionName, String value) {
        return selectionName + HAS_THE_ID + value + POINT;
    }

    static final String equalToMessage(String selectionName, int size) {
        return selectionName + HAS_NOT_SIZE_EQUAL_TO + size + POINT;
    }

    static final String lessThanOrEqualToMessage(String selectionName, int size) {
        return selectionName + IS_NOT_LESS_THAN_OR_EQUAL_TO + size + POINT;
    }

    static final String notLessThanOrEqualToMessage(String selectionName, int size) {
        return selectionName + IS_LESS_THAN_OR_EQUAL_TO + size + POINT;
    }

    static final String notEqualToMessage(String selectionName, int size) {
        return selectionName + IS_EQUAL_TO + size + POINT;
    }

    static final String lessThanMessage(String selectionName, int size) {
        return selectionName + IS_NOT_LESS_THAN + size + POINT;
    }

    static final String notLessThanMessage(String selectionName, int size) {
        return selectionName + IS_LESS_THAN + size + POINT;
    }

    static final String greaterThanOrEqualToMessage(String selectionName, int size) {
        return selectionName + IS_NOT_GREATER_THAN_OR_EQUAL_TO + size + POINT;
    }

    static final String notGreaterThanOrEqualToMessage(String selectionName, int size) {
        return selectionName + IS_GREATER_THAN_OR_EQUAL_TO + size + POINT;
    }

    static final String greaterThanMessage(String selectionName, int size) {
        return selectionName + IS_NOT_GREATER_THAN + size + POINT;
    }

    static final String notGreaterThanMessage(String selectionName, int size) {
        return selectionName + IS_GREATER_THAN + size + POINT;
    }

    static final String isWindowDisplayedMessage(String windowName) {
        return WINDOW + windowName + IS_NOT_DISPLAYED + POINT;
    }

    static final String isWindowNotDisplayedMessage(String windowName) {
        return WINDOW + windowName + IS_DISPLAYED + POINT;
    }

    static final String isPredicateVerifiedMessage(String selectionName) {
        return selectionName + PREDICATE_IS_NOT_VERIFIED;
    }

    static final String isPredicateNotVerifiedMessage(String selectionName) {
        return selectionName + PREDICATE_IS_VERIFIED;
    }

    static final String hasPositionMessage(String selectionName, int x, int y) {
        return selectionName + IS_NOT_AT_POSITION + " (x=" + x + "; y=" + y + ")";
    }

    static final String hasNotPositionMessage(String selectionName, int x, int y) {
        return selectionName + IS_AT_POSITION + " (x=" + x + "; y=" + y + ")";
    }

    static final String hasDimensionMessage(String selectionName, int x, int y) {
        return selectionName + HAS_NOT_THE_SIZE + " (x=" + x + "; y=" + y + ")";
    }

    static final String hasNotDimensionMessage(String selectionName, int x, int y) {
        return selectionName + HAS_THE_SIZE + " (x=" + x + "; y=" + y + ")";
    }

    static final String hasPositionAndDimensionMessage(String selectionName, int x, int y, int width, int height) {
        return selectionName + IS_NOT_AT_POSITION + " and " + HAS_NOT_THE_DIMENSION + " (x=" + x + "; y=" + y + "; width=" + width + "; height=" + height + ")";
    }

    static final String hasNotPositionAndDimensionMessage(String selectionName, int x, int y, int width, int height) {
        return selectionName + IS_AT_POSITION + " and " + HAS_THE_DIMENSION + " (x=" + x + "; y=" + y + "; width=" + width + "; height=" + height + ")";
    }
}

