package io.fluentlenium.core;

import io.fluentlenium.core.components.ComponentInstantiator;
import io.fluentlenium.core.components.ComponentsManager;
import io.fluentlenium.core.domain.ComponentList;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.components.ComponentsManager;
import io.fluentlenium.core.domain.ComponentList;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Abstract {@link ComponentInstantiator} implementation for {@link FluentDriver}.
 */
abstract class AbstractFluentDriverComponentInstantiator extends FluentControlImpl {

    protected AbstractFluentDriverComponentInstantiator(FluentControl adapter) {
        super(adapter);
    }

    /**
     * Returns the {@link ComponentsManager} required for this class.
     *
     * @return the components manager
     */
    protected abstract ComponentsManager getComponentsManager();

    @Override
    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass) {
        return getComponentsManager().newComponentList(listClass, componentClass);
    }

    @Override
    public <T> ComponentList asComponentList(Class<T> componentClass, Iterable<WebElement> elements) {
        return getComponentsManager().asComponentList(componentClass, elements);
    }

    @Override
    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, T... componentsList) {
        return getComponentsManager().newComponentList(listClass, componentClass, componentsList);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, Iterable<WebElement> elements) {
        return getComponentsManager().asFluentList(componentClass, elements);
    }

    @Override
    public boolean isComponentClass(Class<?> componentClass) {
        return getComponentsManager().isComponentClass(componentClass);
    }

    @Override
    public <T> ComponentList<T> asComponentList(Class<T> componentClass, List<WebElement> elements) {
        return getComponentsManager().asComponentList(componentClass, elements);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, WebElement... elements) {
        return getComponentsManager().asFluentList(componentClass, elements);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass) {
        return getComponentsManager().newFluentList(componentClass);
    }

    @Override
    public FluentWebElement newFluent(WebElement element) {
        return getComponentsManager().newFluent(element);
    }

    @Override
    public boolean isComponentListClass(Class<? extends List<?>> componentListClass) {
        return getComponentsManager().isComponentListClass(componentListClass);
    }

    @Override
    public FluentList<FluentWebElement> asFluentList(WebElement... elements) {
        return getComponentsManager().asFluentList(elements);
    }

    @Override
    public FluentList<FluentWebElement> asFluentList(Iterable<WebElement> elements) {
        return getComponentsManager().asFluentList(elements);
    }

    @Override
    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, WebElement... elements) {
        return getComponentsManager().asComponentList(listClass, componentClass, elements);
    }

    @Override
    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, Iterable<WebElement> elements) {
        return getComponentsManager().asComponentList(listClass, componentClass, elements);
    }

    @Override
    public FluentList<FluentWebElement> asFluentList(List<WebElement> elements) {
        return getComponentsManager().asFluentList(elements);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, List<WebElement> elements) {
        return getComponentsManager().asFluentList(componentClass, elements);
    }

    @Override
    public <T> ComponentList<T> asComponentList(Class<T> componentClass, WebElement... elements) {
        return getComponentsManager().asComponentList(componentClass, elements);
    }

    @Override
    public <T> T newComponent(Class<T> componentClass, WebElement element) {
        return getComponentsManager().newComponent(componentClass, element);
    }

    @Override
    public <T> ComponentList<T> newComponentList(Class<T> componentClass, T... componentsList) {
        return getComponentsManager().newComponentList(componentClass, componentsList);
    }

    @Override
    public <T> ComponentList<T> newComponentList(Class<T> componentClass, List<T> componentsList) {
        return getComponentsManager().newComponentList(componentClass, componentsList);
    }

    @Override
    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, List<T> componentsList) {
        return getComponentsManager().newComponentList(listClass, componentClass, componentsList);
    }

    @Override
    public FluentList<FluentWebElement> newFluentList() {
        return getComponentsManager().newFluentList();
    }

    @Override
    public FluentList<FluentWebElement> newFluentList(List<FluentWebElement> elements) {
        return getComponentsManager().newFluentList(elements);
    }

    @Override
    public <T> ComponentList<T> newComponentList(Class<T> componentClass) {
        return getComponentsManager().newComponentList(componentClass);
    }

    @Override
    public FluentList<FluentWebElement> newFluentList(FluentWebElement... elements) {
        return getComponentsManager().newFluentList(elements);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass, List<T> elements) {
        return getComponentsManager().newFluentList(componentClass, elements);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass, T... elements) {
        return getComponentsManager().newFluentList(componentClass, elements);
    }

    @Override
    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, List<WebElement> elements) {
        return getComponentsManager().asComponentList(listClass, componentClass, elements);
    }
}
