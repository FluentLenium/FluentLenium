package org.fluentlenium.core.script;

public interface JavascriptControl {
    FluentJavascript executeScript(String script, Object... args);

    FluentJavascript executeAsyncScript(String script, Object... args);
}
