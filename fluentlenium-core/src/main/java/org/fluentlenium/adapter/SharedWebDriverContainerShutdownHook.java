package org.fluentlenium.adapter;

public class SharedWebDriverContainerShutdownHook extends Thread {

    public SharedWebDriverContainerShutdownHook(final String name) {
        super(name);
    }

    @Override
    public void start() {
        synchronized (this) {
            SharedWebDriverContainer.INSTANCE.quitAll();
        }
    }
}
