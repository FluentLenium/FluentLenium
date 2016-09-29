package org.fluentlenium.examples.hooks;

/**
 * Options for {@link ExampleHook}
 */
public class ExampleHookOptions {
    /**
     * The message.
     */
    private String message = "ExampleHook";

    /**
     * Creates new example hook options
     */
    public ExampleHookOptions() {
        // Default constructor
    }

    /**
     * Creates new example hook options, using configuration provided by annotation.
     *
     * @param annotation example annotation
     */
    public ExampleHookOptions(final Example annotation) {
        message = annotation.message();

    }

    /**
     * Get the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message.
     *
     * @param message message to set
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
