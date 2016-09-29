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

    public ContainerFluentControl(FluentControl adapterControl) {
        this(adapterControl, null);
    }

    public ContainerFluentControl(FluentControl adapterControl, ContainerContext context) {
        this.adapterControl = adapterControl;
        this.context = context;
    }

    public void setContext(ContainerContext context) {
        this.context = context;
    }

    private <T extends HookControl<?>> T applyHooks(T hookControl) {
        if (context != null) {
            for (HookDefinition hookDefinition : context.getHookDefinitions()) {
                hookControl.withHook(hookDefinition.getHookClass(), hookDefinition.getOptions());
            }
        }
        return hookControl;
    }


    @Override
    public FluentList<FluentWebElement> find(String selector, Filter... filters) {
        return applyHooks(adapterControl.find(selector, filters));
    }

    @Override
    public FluentList<FluentWebElement> $(String selector, Filter... filters) {
        return applyHooks(adapterControl.$(selector, filters));
    }

    @Override
    public FluentWebElement el(String selector, Filter... filters) {
        return applyHooks(adapterControl.el(selector, filters));
    }

    @Override
    public FluentList<FluentWebElement> find(Filter... filters) {
        return applyHooks(adapterControl.find(filters));
    }

    @Override
    public FluentList<FluentWebElement> $(Filter... filters) {
        return applyHooks(adapterControl.$(filters));
    }

    @Override
    public FluentWebElement el(Filter... filters) {
        return applyHooks(adapterControl.el(filters));
    }

    @Override
    public FluentList<FluentWebElement> find(By locator, Filter... filters) {
        return applyHooks(adapterControl.find(locator, filters));
    }

    @Override
    public FluentList<FluentWebElement> $(By locator, Filter... filters) {
        return applyHooks(adapterControl.$(locator, filters));
    }

    @Override
    public FluentWebElement el(By locator, Filter... filters) {
        return applyHooks(adapterControl.el(locator, filters));
    }

}
