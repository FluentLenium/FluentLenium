package org.fluentlenium.adapter;

import org.fluentlenium.adapter.SharedWebDriverContainer;

public class SharedWebDriverContainerShutdownHook extends Thread {

    public SharedWebDriverContainerShutdownHook(final String s) {
        super(s);
    }

    @Override
    public synchronized void start() {
        SharedWebDriverContainer.INSTANCE.quitAll();
    }
}
