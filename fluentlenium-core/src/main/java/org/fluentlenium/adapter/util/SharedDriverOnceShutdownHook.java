package org.fluentlenium.adapter.util;

import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.adapter.FluentTestRunnerAdapter;

public class SharedDriverOnceShutdownHook extends Thread {
    public SharedDriverOnceShutdownHook(final String s) {
        super(s);
    }

    @Override
    public synchronized void start() {
        FluentTestRunnerAdapter.doReleaseSharedDriver(); // https://github.com/FluentLenium/FluentLenium/issues/196
    }
}
