package org.fluentlenium.cucumber.adapter.util;


import org.fluentlenium.cucumber.adapter.FluentCucumberTest;

public class ShutdownHook extends Thread {
    private final FluentCucumberTest adapter;

    public ShutdownHook(final String s, final FluentCucumberTest adapter) {
        super(s);
        this.adapter = adapter;
    }

    @Override
    public synchronized void start() {
        adapter.forceQuit();
    }
}
