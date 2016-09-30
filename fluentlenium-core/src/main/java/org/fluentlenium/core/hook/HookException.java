package org.fluentlenium.core.hook;

public class HookException extends RuntimeException {
    public HookException(final Throwable cause) {
        super("An error has occurred with a defined hook.", cause);
    }
}
