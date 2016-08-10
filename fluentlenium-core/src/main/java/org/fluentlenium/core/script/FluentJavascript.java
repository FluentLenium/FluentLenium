package org.fluentlenium.core.script;

import org.openqa.selenium.JavascriptExecutor;

import java.util.List;

/**
 * Map result of executed javascript.
 *
 * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
 */
public class FluentJavascript {
    private final Object result;

    public FluentJavascript(JavascriptExecutor executor, boolean async, String script, Object... args) {
        if (async) {
            this.result = executor.executeAsyncScript(script, args);
        } else {
            this.result = executor.executeScript(script, args);
        }
    }

    /**
     * @return true if the result of javascript execution can be retrieved to String.
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public boolean isStringResult() {
        return result instanceof String;
    }

    /**
     * @return true if the result of javascript execution can be retrieved to Boolean.
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public boolean isBooleanResult() {
        return result instanceof Boolean;
    }

    /**
     * @return true if the result of javascript execution can be retrieved to Double.
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public boolean isDoubleResult() {
        return result instanceof Double;
    }

    /**
     * @return true if the result of javascript execution can be retrieved to Long.
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public boolean isLongResult() {
        return result instanceof Long;
    }

    /**
     * @return true if the result of javascript execution can be retrieved to List.
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public boolean isListResult() {
        return result instanceof List;
    }

    /**
     * @return the result of javascript execution.
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public Object getResult() {
        return result;
    }

    /**
     * @return the result of javascript execution cast as Double.
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public Double getDoubleResult() {
        return (Double) result;
    }

    /**
     * @return the result of javascript execution cast as Boolean.
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public Boolean getBooleanResult() {
        return (Boolean) result;
    }

    /**
     * @return the result of javascript execution cast as Long.
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public Long getLongResult() {
        return (Long) result;
    }

    /**
     * @return the result of javascript execution cast as String.
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public String getStringResult() {
        return (String) result;
    }

    /**
     * @return result of javascript execution cast as String.
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public List<?> getListResult() {
        return (List<?>) result;
    }

    /**
     * @param <T> type of list elements
     * @return the result of javascript execution cast as List.
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public <T> List<T> getListResult(Class<T> listType) {
        return (List<T>) result;
    }
}
