package org.fluentlenium.core.script;

import org.fluentlenium.core.proxy.LocatorHandler;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.openqa.selenium.JavascriptExecutor;

import java.util.List;

/**
 * Execute a script and map it's result.
 *
 * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
 */
public class FluentJavascript {
    private final Object result;

    /**
     * Creates a new fluent javascript.
     *
     * @param executor underlying javascript executor
     * @param async    if true, scripts will be executed asynchronously, else it will be executed synchronously
     * @param script   script source to execute
     * @param args     script arguments
     */
    public FluentJavascript(JavascriptExecutor executor, boolean async, String script, Object... args) {
        loadElements(args);
        if (async) {
            result = executor.executeAsyncScript(script, args);
        } else {
            result = executor.executeScript(script, args);
        }
    }

    /**
     * Load lazy the argument elements.
     *
     * @param args the element proxies
     */
    private void loadElements(Object[] args) {
        for (Object arg : args) {
            LocatorHandler locatorHandler = LocatorProxies.getLocatorHandler(arg);
            if (locatorHandler != null) {
                locatorHandler.now();
            }
        }
    }

    /**
     * Check if the result is a {@link String}.
     *
     * @return true if the result of javascript execution can be retrieved as a {@link String}
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public boolean isStringResult() {
        return result instanceof String;
    }

    /**
     * Check if the result is a {@link Boolean}.
     *
     * @return true if the result of javascript execution can be retrieved as a {@link Boolean}
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public boolean isBooleanResult() {
        return result instanceof Boolean;
    }

    /**
     * Check if the result is a {@link Double}.
     *
     * @return true if the result of javascript execution can be retrieved as a {@link Double}
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public boolean isDoubleResult() {
        return result instanceof Double;
    }

    /**
     * Check if the result is a {@link Long}.
     *
     * @return true if the result of javascript execution can be retrieved as a {@link Long}
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public boolean isLongResult() {
        return result instanceof Long;
    }

    /**
     * Check if the result is a {@link List}.
     *
     * @return true if the result of javascript execution can be retrieved as a {@link List}
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public boolean isListResult() {
        return result instanceof List;
    }

    /**
     * Retrieve the result of the script execution.
     *
     * @return the result.
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public Object getResult() {
        return result;
    }

    /**
     * Retrieve the result as a {@link Double}.
     *
     * @return the result of script execution cast as a {@link Double}
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public Double getDoubleResult() {
        return (Double) result;
    }

    /**
     * Retrieve the result as a {@link Boolean}.
     *
     * @return the result of script execution cast as a {@link Boolean}
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public Boolean getBooleanResult() {
        return (Boolean) result;
    }

    /**
     * Retrieve the result as a {@link Long}.
     *
     * @return the result of script execution cast as a {@link Long}
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public Long getLongResult() {
        return (Long) result;
    }

    /**
     * Retrieve the result as a {@link String}.
     *
     * @return the result of script execution cast as a {@link String}
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public String getStringResult() {
        return (String) result;
    }

    /**
     * Retrieve the result as a {@link List}.
     *
     * @return result of javascript script cast as a {@link List}
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public List<?> getListResult() {
        return (List<?>) result;
    }

    /**
     * Retrieve the result as a typed {@link List}
     *
     * @param listType class of list elements
     * @param <T>      type of list elements
     * @return the result of javascript execution cast as a a typed {@link List}
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
     */
    public <T> List<T> getListResult(Class<T> listType) {
        return (List<T>) result;
    }
}
