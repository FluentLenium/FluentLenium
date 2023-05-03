package io.fluentlenium.core.hook;

/**
 * Exception that can occur when applying a hook.
 */
public class HookException extends RuntimeException {
    /**
     * Creates a new hook exception.
     *
     * @param cause exception cause
     */
    public HookException(Throwable cause) {
        super("An error has occurred with a defined hook.", cause);
    }
}
