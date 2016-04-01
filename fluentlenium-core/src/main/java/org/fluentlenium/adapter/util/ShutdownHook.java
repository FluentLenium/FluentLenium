package org.fluentlenium.adapter.util;

import org.fluentlenium.adapter.FluentAdapter;

public class ShutdownHook extends Thread {
    private final FluentAdapter test;

    public ShutdownHook(final String s, final FluentAdapter test) {
        super(s);
        this.test = test;
    }

    @Override
    public synchronized void start() {
        test.quit();
    }
}
