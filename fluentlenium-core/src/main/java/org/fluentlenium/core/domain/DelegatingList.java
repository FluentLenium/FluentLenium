package org.fluentlenium.core.domain;

import java.util.List;

/**
 * List that delegates to another list
 *
 * @param <T> the type of elements in this list
 */
public class DelegatingList<T> extends ListImpl<T> implements List<T> {
    protected final List<T> list;

    /**
     * Creates a new delegating list
     *
     * @param list underlying list
     */
    public DelegatingList(List<T> list) {
        super();
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

}
