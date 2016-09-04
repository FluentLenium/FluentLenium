package org.fluentlenium.core.inject;

import org.fluentlenium.core.hook.HookDefinition;

import java.util.List;

public interface ContainerContext {
    Object getContainer();
    ContainerContext getParent();
    List<HookDefinition<?>> getHookDefinitions();
}
