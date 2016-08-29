package org.fluentlenium.core.inject;

import org.fluentlenium.core.hook.HookDefinition;

import java.util.ArrayList;
import java.util.List;

public class DefaultContainerContext implements ContainerContext {
    private final Object container;
    private final ContainerContext parentContext;
    private final List<HookDefinition<?>> hookDefinitions = new ArrayList<>();

    public DefaultContainerContext(Object container) {
        this(container, null);
    }

    public DefaultContainerContext(Object container, ContainerContext parentContext) {
        this.container = container;
        this.parentContext = parentContext;
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
    public List<HookDefinition<?>> getHookDefinitions() {
        return hookDefinitions;
    }
}
