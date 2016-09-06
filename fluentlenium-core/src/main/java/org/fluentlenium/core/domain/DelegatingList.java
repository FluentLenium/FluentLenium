package org.fluentlenium.core.domain;

import lombok.experimental.Delegate;

import java.util.List;

public class DelegatingList<T> implements List<T> {
    @Delegate
    protected final List<T> list;


    public DelegatingList(List<T> list) {
        super();
        this.list = list;
    }

}
