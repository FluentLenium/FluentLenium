package org.fluentlenium.core.wait;

/**
 * Control interface for fluent wait.
 */
public interface AwaitControl {
    /**
     * wait for an asynchronous call
     *
     * @return FluentWait element
     */
    FluentWait await();
}
