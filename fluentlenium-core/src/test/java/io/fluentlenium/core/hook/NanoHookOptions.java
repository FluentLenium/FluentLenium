package io.fluentlenium.core.hook;

public class NanoHookOptions {
    private String value; // NOPMD ImmutableField

    public NanoHookOptions(NanoHookAnnotation annotation) {
        value = annotation.value();
    }

    public NanoHookOptions(String value) {
        this.value = value;
    }

    public NanoHookOptions() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
