package org.fluentlenium.adapter;

public class SharedWebDriverContainerShutdownHook extends Thread {

    public SharedWebDriverContainerShutdownHook(final String s) {
        super(s);
    }

    @Override
    public void start() {
        synchronized (this) {
            SharedWebDriverContainer.INSTANCE.quitAll();
        }
    }
}
