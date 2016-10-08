package org.fluentlenium.core.domain;

import lombok.experimental.Delegate;

import java.util.List;

/**
 * List that delegates to another list
 *
 * @param <T> the type of elements in this list
 */
public class DelegatingList<T> implements List<T> {
    @Delegate
    protected final List<T> list;

    /**
     * Creates a new delegating list
     *
     * @param list underlying list
     */
    public DelegatingList(final List<T> list) {
        super();
        this.list = list;
    }

}
