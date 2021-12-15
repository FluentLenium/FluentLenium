package org.fluentlenium.core.inject;

import java.util.List;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.MobileBy;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentControlImpl;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.hook.HookControl;
import org.fluentlenium.core.hook.HookDefinition;
import org.fluentlenium.core.search.SearchFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Container global FluentLenium control interface.
 */
public class ContainerFluentControl extends FluentControlImpl {
    private final FluentControl adapterControl;

    private ContainerContext context;

    /**
     * Get the underlying control from the test adapter.
     *
     * @return underlying control interface from the test adapter
     */
    public FluentControl getAdapterControl() {
        return adapterControl;
    }

    @Override
    public final WebDriver getDriver() {
        return getFluentControl() == null ? null : getFluentControl().getDriver();
    }

    /**
     * Creates a new container fluent control.
     *
     * @param adapterControl test adapter control interface
     */
    public ContainerFluentControl(FluentControl adapterControl) {
        super(adapterControl);
        this.adapterControl = adapterControl;
    }

    @Override
    public FluentControl getFluentControl() {
        return adapterControl;
    }

    /**
     * Creates a new container fluent control.
     *
     * @param adapterControl test adapter control interface
     * @param context        container context
     */
    public ContainerFluentControl(FluentControl adapterControl, ContainerContext context) {
        this.adapterControl = adapterControl;
        this.context = context;
    }

    /**
     * Define the container context of this container fluent control interface.
     *
     * @param context container context
     */
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
    public FluentList<FluentWebElement> find(String selector, SearchFilter... filters) {
        return applyHooks(adapterControl.find(selector, filters));
    }

    @Override
    public FluentList<FluentWebElement> $(String selector, SearchFilter... filters) {
        return applyHooks(adapterControl.$(selector, filters));
    }

    @Override
    public FluentWebElement el(String selector, SearchFilter... filters) {
        return applyHooks(adapterControl.el(selector, filters));
    }

    @Override
    public FluentList<FluentWebElement> find(SearchFilter... filters) {
        return applyHooks(adapterControl.find(filters));
    }

    @Override
    public FluentList<FluentWebElement> $(SearchFilter... filters) {
        return applyHooks(adapterControl.$(filters));
    }

    @Override
    public FluentWebElement el(SearchFilter... filters) {
        return applyHooks(adapterControl.el(filters));
    }

    @Override
    public FluentList<FluentWebElement> find(By locator, SearchFilter... filters) {
        return applyHooks(adapterControl.find(locator, filters));
    }

    @Override
    public FluentList<FluentWebElement> $(By locator, SearchFilter... filters) {
        return applyHooks(adapterControl.$(locator, filters));
    }

    @Override
    public FluentList<FluentWebElement> $(AppiumBy locator, SearchFilter... filters) {
        return applyHooks(adapterControl.$(locator, filters));
    }

    @Override
    public FluentList<FluentWebElement> find(List<WebElement> rawElements) {
        return applyHooks(adapterControl.find(rawElements));
    }

    @Override
    public FluentList<FluentWebElement> $(List<WebElement> rawElements) {
        return applyHooks(adapterControl.$(rawElements));
    }

    @Override
    public FluentWebElement el(WebElement rawElement) {
        return applyHooks(adapterControl.el(rawElement));
    }

    @Override
    public FluentWebElement el(By locator, SearchFilter... filters) {
        return applyHooks(adapterControl.el(locator, filters));
    }

    @Override
    public FluentWebElement el(AppiumBy locator, SearchFilter... filters) {
        return applyHooks(adapterControl.el(locator, filters));
    }
}
