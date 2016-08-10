package org.fluentlenium.core.wait;

public interface AwaitControl {
    /**
     * wait for an asynchronous call
     *
     * @return FluentWait element
     */
    FluentWait await();
}
