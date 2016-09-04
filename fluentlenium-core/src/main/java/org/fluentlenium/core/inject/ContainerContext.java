package org.fluentlenium.core.inject;

import org.fluentlenium.core.hook.HookDefinition;
import org.openqa.selenium.SearchContext;

import java.util.List;

public interface ContainerContext {
    Object getContainer();

    ContainerContext getParent();

    SearchContext getSearchContext();

    List<HookDefinition<?>> getHookDefinitions();
}
