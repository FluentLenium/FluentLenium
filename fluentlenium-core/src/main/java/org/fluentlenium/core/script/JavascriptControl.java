package org.fluentlenium.core.script;

/**
 * Control interface to execute script, synchronously or asynchronously.
 */
public interface JavascriptControl {

    /**
     * Execute a script, synchronously.
     *
     * @param script script source to execute
     * @param args   script arguments
     * @return an object wrapping the result
     */
    FluentJavascript executeScript(String script, Object... args);

    /**
     * Execute a script, asynchronously.
     *
     * @param script script source to execute
     * @param args   script arguments
     * @return an object wrapping the result
     */
    FluentJavascript executeAsyncScript(String script, Object... args);
}
