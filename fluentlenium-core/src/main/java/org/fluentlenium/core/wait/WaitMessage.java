package org.fluentlenium.core.wait;

/**
 * Created by IntelliJ IDEA.
 * User: rigabertm
 * Date: 4/22/12
 * Time: 9:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class WaitMessage {
    static final String SELECTOR = "Selector ";
    static final String PAGE = "Page ";
    static final String IS_NOT_PRESENT = " is not present.";
    static final String HAS_NOT_THE_NAME = " has not the name ";
    static final String HAS_NOT_THE_SIZE = " has not the size";
    static final String POINT = ".";
    static final String HAS_NOT_THE_TEXT = " has not the text ";
    static final String HAS_NOT_THE_ID = " has not the id ";
    static final String WITH_ATTRIBUTE = " with attribute ";
    static final String WITH_VALUE = " with value ";
    static final String HAS_NOT_SIZE_EQUAL_TO = " has not size equal to ";
    static final String IS_EQUAL_TO = " is equal to ";
    static final String IS_NOT_LESS_THAN = " is not less than ";
    static final String IS_NOT_LESS_THAN_OR_EQUAL_TO = " is not less than or equal to ";
    static final String IS_NOT_GREATHER_THAN = " is not greather than ";
    static final String IS_NOT_GREATHER_THAN_OR_EQUAL_TO = " is not greather than or equal to ";
    static final String IS_NOT_LOADED = " is not loaded";

    static final String hasSizeMessage(String selector, int size) {
        return SELECTOR + selector + HAS_NOT_THE_SIZE + size + POINT;
    }

    static final String hasAttributeMessage(String selector, String attribute, String value) {
        return SELECTOR + selector + WITH_ATTRIBUTE + attribute + WITH_VALUE + value + IS_NOT_PRESENT + POINT;
    }

    static final String isPageLoaded(String url) {
        return PAGE + url +IS_NOT_LOADED + POINT;
    }

    static final String isPresentMessage(String selector) {
        return SELECTOR + selector + IS_NOT_PRESENT;
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

