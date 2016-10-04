package org.fluentlenium.core.inject;

import lombok.experimental.Delegate;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.hook.HookControl;
import org.fluentlenium.core.hook.HookDefinition;
import org.fluentlenium.core.search.SearchControl;
import org.openqa.selenium.By;

public class ContainerFluentControl implements FluentControl {
    @Delegate(excludes = SearchControl.class)
    private final FluentControl adapterControl;

    private ContainerContext context;

    public FluentControl getAdapterControl() {
        return adapterControl;
    }

    public ContainerFluentControl(final FluentControl adapterControl) {
        this(adapterControl, null);
    }

    public ContainerFluentControl(final FluentControl adapterControl, final ContainerContext context) {
        this.adapterControl = adapterControl;
        this.context = context;
    }

    public void setContext(final ContainerContext context) {
        this.context = context;
    }

    private <T extends HookControl<?>> T applyHooks(final T hookControl) {
        if (context != null) {
            for (final HookDefinition hookDefinition : context.getHookDefinitions()) {
                hookControl.withHook(hookDefinition.getHookClass(), hookDefinition.getOptions());
            }
        }
        return hookControl;
    }

    @Override
    public FluentList<FluentWebElement> find(final String selector, final Filter... filters) {
        return applyHooks(adapterControl.find(selector, filters));
    }

    @Override
    public FluentList<FluentWebElement> $(final String selector, final Filter... filters) {
        return applyHooks(adapterControl.$(selector, filters));
    }

    @Override
    public FluentWebElement el(final String selector, final Filter... filters) {
        return applyHooks(adapterControl.el(selector, filters));
    }

    @Override
    public FluentList<FluentWebElement> find(final Filter... filters) {
        return applyHooks(adapterControl.find(filters));
    }

    @Override
    public FluentList<FluentWebElement> $(final Filter... filters) {
        return applyHooks(adapterControl.$(filters));
    }

    @Override
    public FluentWebElement el(final Filter... filters) {
        return applyHooks(adapterControl.el(filters));
    }

    @Override
    public FluentList<FluentWebElement> find(final By locator, final Filter... filters) {
        return applyHooks(adapterControl.find(locator, filters));
    }

    @Override
    public FluentList<FluentWebElement> $(final By locator, final Filter... filters) {
        return applyHooks(adapterControl.$(locator, filters));
    }

    @Override
    public FluentWebElement el(final By locator, final Filter... filters) {
        return applyHooks(adapterControl.el(locator, filters));
    }

}
