package org.fluentlenium.adapter;

/**
 * Extend this class and implement {@link #doRun()} if you want to use FluentLenium as an automation framework only.
 * <p>
 * Fluent WebDriver is initialized before and released after {@link #run()} method invocation.
 */
public abstract class FluentStandaloneRunnable extends FluentStandalone implements Runnable {
    @Override
    public void run() {
        try {
            init();
            doRun();
        } finally {
            quit();
        }
    }

    /**
     * Implement this method using FluentLenium API.
     */
    abstract protected void doRun();
}
