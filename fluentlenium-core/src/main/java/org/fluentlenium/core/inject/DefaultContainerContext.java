package org.fluentlenium.core.inject;

import org.fluentlenium.core.hook.HookDefinition;
import org.openqa.selenium.SearchContext;

import java.util.ArrayList;
import java.util.List;

public class DefaultContainerContext implements ContainerContext {
    private final Object container;
    private final ContainerContext parentContext;
    private final SearchContext searchContext;
    private final List<HookDefinition<?>> hookDefinitions = new ArrayList<>();

    public DefaultContainerContext(Object container) {
        this(container, null, null);
    }

    public DefaultContainerContext(Object container, ContainerContext parentContext, SearchContext searchContext) {
        this.container = container;
        this.parentContext = parentContext;
        this.searchContext = searchContext;
    }

    @Override
    public Object getContainer() {
        return container;
    }

    @Override
    public ContainerContext getParent() {
        return parentContext;
    }

    @Override
    public SearchContext getSearchContext() {
        return searchContext;
    }

    @Override
    public List<HookDefinition<?>> getHookDefinitions() {
        return hookDefinitions;
    }
}
