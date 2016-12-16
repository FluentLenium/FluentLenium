package org.fluentlenium.adapter;

/**
 * Shutdown hook closing the Shared WebDriver container when JVM is closing.
 */
public class SharedWebDriverContainerShutdownHook extends Thread {

    /**
     * Creates a new shutdown hook.
     *
     * @param name thread name
     */
    public SharedWebDriverContainerShutdownHook(String name) {
        super(name);
    }

    @Override
    public void start() {
        synchronized (this) {
            SharedWebDriverContainer.INSTANCE.quitAll();
        }
    }
}
