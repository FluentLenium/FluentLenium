package org.fluentlenium.core.wait;

/**
 * Created by IntelliJ IDEA.
 * User: rigabertm
 * Date: 4/22/12
 * Time: 9:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class WaitMessage {
    private static final String SELECTOR = "Selector ";
    private static final String PAGE = "Page ";
    private static final String IS_NOT_PRESENT = " is not present.";
    private static final String HAS_NOT_THE_NAME = " has not the name ";
    private static final String HAS_NOT_THE_SIZE = " has not the size";
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
    private static final String IS_NOT_DISPLAY = " is not displayed";

    static final String hasSizeMessage(String selector, int size) {
        return SELECTOR + selector + HAS_NOT_THE_SIZE + size + POINT;
    }

    static final String hasAttributeMessage(String selector, String attribute, String value) {
        return SELECTOR + selector + WITH_ATTRIBUTE + attribute + WITH_VALUE + value + IS_NOT_PRESENT + POINT;
    }

    static final String isPageLoaded(String url) {
        return PAGE + url + IS_NOT_LOADED + POINT;
    }

    static final String isPresentMessage(String selector) {
        return SELECTOR + selector + IS_NOT_PRESENT;
    }

    static final String isDisplayedMessage(String selector) {
        return SELECTOR + selector + IS_NOT_DISPLAY;
    }

    static final String hasTextMessage(String selector, String value) {
        return SELECTOR + selector + HAS_NOT_THE_TEXT + value + POINT;
    }

    static final String hasNameMessage(String selector, String value) {
        return SELECTOR + selector + HAS_NOT_THE_NAME + value + POINT;
    }

    static final String hasIdMessage(String selector, String value) {
        return SELECTOR + selector + HAS_NOT_THE_ID + value + POINT;
    }

    static final String equalToMessage(String selector, int size) {
        return SELECTOR + selector + HAS_NOT_SIZE_EQUAL_TO + size + POINT;
    }

    static final String lessThanOrEqualToMessage(String selector, int size) {
        return SELECTOR + selector + IS_NOT_LESS_THAN_OR_EQUAL_TO + size + POINT;
    }

    static final String notEqualToMessage(String selector, int size) {
        return SELECTOR + selector + IS_EQUAL_TO + size + POINT;
    }

    static final String lessThanMessage(String selector, int size) {
        return SELECTOR + selector + IS_NOT_LESS_THAN + size + POINT;
    }

    static final String greatherThanOrEqualToMessage(String selector, int size) {
        return SELECTOR + selector + IS_NOT_GREATHER_THAN_OR_EQUAL_TO + size + POINT;
    }

    static final String greatherThanMessage(String selector, int size) {
        return SELECTOR + selector + IS_NOT_GREATHER_THAN + size + POINT;
    }

}

