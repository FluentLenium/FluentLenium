package org.fluentlenium.core.inject;

import org.fluentlenium.core.hook.HookDefinition;
import org.openqa.selenium.SearchContext;

import java.util.List;

/**
 * Context for a container
 */
public interface ContainerContext {
    /**
     * Get the container related to this context.
     *
     * @return container
     */
    Object getContainer();

    /**
     * Get the parent context.
     *
     * @return parent context
     */
    ContainerContext getParent();

    /**
     * Get the search context of this container context.
     *
     * @return search context
     */
    SearchContext getSearchContext();

    /**
     * Get the list of hook definitions attached to this context.
     *
     * @return hook definitions list
     */
    List<HookDefinition<?>> getHookDefinitions();
}
