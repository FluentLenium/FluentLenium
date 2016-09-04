package org.fluentlenium.core.components;

import lombok.Getter;
import lombok.experimental.Delegate;
import org.fluentlenium.core.domain.WrapsElements;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of component that lazy initialize from it's related list of elements.
 *
 * @param <T> type of component.
 */
public class LazyComponentList<T> implements List<T>, WrapsElements {
    private final ComponentInstantiator instantiator;
    private final Class<T> componentClass;

    private final List<WebElement> elements;

    @Delegate
    @Getter(lazy = true)
    private final List<T> list = transformList();

    public LazyComponentList(ComponentInstantiator instantiator, Class<T> componentClass, List<WebElement> elements) {
        this.componentClass = componentClass;
        this.instantiator = instantiator;
        this.elements = elements;
    }

    private List<T> transformList() {
        ArrayList<T> components = new ArrayList<>();
        for (WebElement element : this.elements) {
            components.add(this.instantiator.newComponent(componentClass, element));
        }
        return components;
    }

    @Override
    public List<WebElement> getWrappedElements() {
        return this.elements;
    }
}
