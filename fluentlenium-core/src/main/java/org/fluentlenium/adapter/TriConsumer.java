package org.fluentlenium.adapter;

@FunctionalInterface
public interface TriConsumer<U, V, W> {

    void accept(U arg1, V arg2, W arg3);
}

