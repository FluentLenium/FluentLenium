package org.fluentlenium.core.action;

import org.assertj.core.api.AbstractAssert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Sequence;

import java.util.List;
import java.util.Map;

class SequenceAssert extends AbstractAssert<SequenceAssert, Sequence> {

    static SequenceAssert assertThatSequence(Sequence sequence) {
        return new SequenceAssert(sequence);
    }

    private SequenceAssert(Sequence sequence) {
        super(sequence, SequenceAssert.class);
    }

    private void assertType(String expectedType) {
        Map<String, Object> json = actual.toJson();

        String actualType = (String) json.get("type");

        if (actualType == null) {
            failWithMessage("Expected type of %s not to be null", json);
        }

        if (!actualType.equals(expectedType)) {
            failWithMessage("Expected type of %s to be '%s' but was '%s'", json, expectedType, actualType);
        }
    }

    public SequenceAssert isKey() {
        isNotNull();

        assertType("key");

        return this;
    }

    public SequenceAssert isPointer() {
        isNotNull();

        assertType("pointer");

        return this;
    }

    private List<Map<String, Object>> actions() {
        Map<String, Object> json = actual.toJson();
        List<Map<String, Object>> actions = (List<Map<String, Object>>) json.get("actions");

        if (actions == null) {
            failWithMessage("Expected actions");
        }

        return actions;
    }

    public SequenceAssert hasKeyAction(String keyAction, Keys key) {

        isKey();

        List<Map<String, Object>> allActions = actions();

        Map<String, Object> action = allActions.get(0);

        assertActionType(keyAction, action);
        assertActionValue(key, action);

        return this;
    }

    private void assertActionValue(Keys key, Map<String, Object> action) {
        Object actionValue = action.get("value");

        if (!key.toString().equals(actionValue)) {
            failWithMessage("Expected action %s to have value '%s' actual was '%s'", action, key, actionValue);
        }
    }

    private void assertActionType(String keyAction, Map<String, Object> firstAction) {
        Object actionType = firstAction.get("type");
        if (!keyAction.equals(actionType)) {
            failWithMessage("Expected action %s to be of type '%s' actual was '%s'", firstAction, keyAction, actionType);
        }
    }

    public SequenceAssert isKeyDown(Keys key) {
        return hasKeyAction("keyDown", key);
    }

    public SequenceAssert isKeyUp(Keys key) {
        return hasKeyAction("keyUp", key);
    }

    public SequenceAssert isKeySequence(Keys... keys) {
        isKey();

        List<Map<String, Object>> actions = actions();

        for (int i = 0; i < keys.length; i++) {
            Map<String, Object> down = actions.get(2 * i);
            assertActionType("keyDown", down);
            assertActionValue(keys[i], down);
            Map<String, Object> up = actions.get(2 * i + 1);
            assertActionType("keyUp", up);
            assertActionValue(keys[i], up);
        }

        return this;
    }
}
