package org.fluentlenium.examples.hooks;

public class ExampleHookOptions {
    public ExampleHookOptions(Example annotation) {
        message = annotation.message();

    }

    public ExampleHookOptions() {
    }

    private String message = "ExampleHook";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
