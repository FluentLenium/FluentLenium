package org.fluentlenium.core.components;

import org.fluentlenium.core.domain.ComponentList;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractComponentInstantiator implements ComponentInstantiator {

    @Override
    public FluentWebElement newFluent(final WebElement element) {
        return newComponent(FluentWebElement.class, element);
    }

    @Override
    public FluentList<FluentWebElement> newFluentList() {
        return newFluentList(FluentWebElement.class);
    }

    @Override
    public FluentList<FluentWebElement> asFluentList(final WebElement... elements) {
        return asFluentList(Arrays.asList(elements));
    }

    @Override
    public FluentList<FluentWebElement> asFluentList(final Iterable<WebElement> elements) {
        return asFluentList(FluentWebElement.class, elements);
    }

    @Override
    public FluentList<FluentWebElement> asFluentList(final List<WebElement> elements) {
        return asFluentList(FluentWebElement.class, elements);
    }

    @Override
    public FluentList<FluentWebElement> newFluentList(final FluentWebElement... elements) {
        return newFluentList(new ArrayList<>(Arrays.asList(elements)));
    }

    @Override
    public FluentList<FluentWebElement> newFluentList(final List<FluentWebElement> elements) {
        return newFluentList(FluentWebElement.class, elements);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> newFluentList(final Class<T> componentClass) {
        return asComponentList(FluentListImpl.class, componentClass);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> newFluentList(final Class<T> componentClass, final T... elements) {
        return newFluentList(componentClass, new ArrayList<>(Arrays.asList(elements)));
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> newFluentList(final Class<T> componentClass, final List<T> elements) {
        return newComponentList(FluentListImpl.class, componentClass, elements);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> asFluentList(final Class<T> componentClass, final WebElement... elements) {
        return asFluentList(componentClass, Arrays.asList(elements));
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> asFluentList(final Class<T> componentClass,
            final Iterable<WebElement> elements) {
        return asComponentList(FluentListImpl.class, componentClass, elements);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> asFluentList(final Class<T> componentClass,
            final List<WebElement> elements) {
        return asComponentList(FluentListImpl.class, componentClass, elements);
    }

    @Override
    public <L extends List<T>, T> L newComponentList(final Class<L> listClass, final Class<T> componentClass) {
        return newComponentList(listClass, componentClass, new ArrayList<>());
    }

    @Override
    public <T> ComponentList<T> newComponentList(final Class<T> componentClass) {
        return asComponentList(ComponentList.class, componentClass);
    }

    @Override
    public <T> ComponentList<T> asComponentList(final Class<T> componentClass, final WebElement... elements) {
        return asComponentList(componentClass, Arrays.asList(elements));
    }

    @Override
    public <T> ComponentList<T> asComponentList(final Class<T> componentClass, final Iterable<WebElement> elements) {
        return asComponentList(ComponentList.class, componentClass, elements);
    }

    @Override
    public <T> ComponentList<T> asComponentList(final Class<T> componentClass, final List<WebElement> elements) {
        return asComponentList(ComponentList.class, componentClass, elements);
    }

    @Override
    public <T> ComponentList<T> newComponentList(final Class<T> componentClass, final T... componentsList) {
        return newComponentList(componentClass, new ArrayList<>(Arrays.asList(componentsList)));
    }

    @Override
    public <T> ComponentList<T> newComponentList(final Class<T> componentClass, final List<T> componentsList) {
        return newComponentList(ComponentList.class, componentClass, componentsList);
    }

    @Override
    public <L extends List<T>, T> L newComponentList(final Class<L> listClass, final Class<T> componentClass,
            final T... componentsList) {
        return newComponentList(listClass, componentClass, new ArrayList<>(Arrays.asList(componentsList)));
    }

    @Override
    public <L extends List<T>, T> L asComponentList(final Class<L> listClass, final Class<T> componentClass,
            final WebElement... elements) {
        return asComponentList(listClass, componentClass, Arrays.asList(elements));
    }

    @Override
    public <L extends List<T>, T> L asComponentList(final Class<L> listClass, final Class<T> componentClass,
            final Iterable<WebElement> elements) {
        final List<T> componentsList = new ArrayList<>();

        for (final WebElement element : elements) {
            componentsList.add(newComponent(componentClass, element));
        }

        return newComponentList(listClass, componentClass, componentsList);
    }

    @Override
    public <L extends List<T>, T> L asComponentList(final Class<L> listClass, final Class<T> componentClass,
            final List<WebElement> elements) {
        return newComponentList(listClass, componentClass, new LazyComponentList<>(this, componentClass, elements));
    }

}
