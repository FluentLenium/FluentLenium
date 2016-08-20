package org.fluentlenium.core.components;

import com.sun.jna.WeakIdentityHashMap;
import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Manage living components for a WebDriver instance.
 *
 * A component is an Object implementing no particular interface, but capable a wrapping
 * a {@link org.openqa.selenium.WebElement}.
 *
 * {@link org.fluentlenium.core.domain.FluentWebElement} is the most common component.
 */
public class ComponentsManager implements ComponentInstantiator, ComponentAccessor {

    private final WebDriver driver;
    private final ComponentInstantiator instantiator;

    //TODO: IdentityHashMap or WeakIdentityHashMap ?
    private Map<WebElement, Object> components = new WeakIdentityHashMap();

    public ComponentsManager(WebDriver driver) {
            this.driver = driver;
            this.instantiator = new DefaultComponentInstantiator(this.driver, this);
    }

    public ComponentsManager(WebDriver driver, ComponentInstantiator instantiator) {
        this.driver = driver;
        this.instantiator = instantiator;
    }

    public ComponentInstantiator getInstantiator() {
        return instantiator;
    }

    /**
     * Get the related component from the given element.
     *
     * @param element
     *
     * @return
     */
    public Object getComponent(WebElement element) {
        return components.get(unwrapElement(element));
    }

    /**
     * Get all the component related to this webDriver.
     * @return
     */
    public Collection<ComponentBean> getAllComponents() {
        List<ComponentBean> allComponents = new ArrayList<>();
        for (Map.Entry<WebElement, Object> entry : components.entrySet()) {
            allComponents.add(new ComponentBean(entry.getValue(), entry.getKey()));
        }
        return allComponents;
    }

    @Override
    public boolean isComponentClass(Class<?> componentClass) {
        return instantiator.isComponentClass(componentClass);
    }

    @Override
    public <T> T newComponent(Class<T> componentClass, WebElement element) {
        try {
            T component = instantiator.newComponent(componentClass, element);
            components.put(unwrapElement(element), component);
            return component;
        } catch (Exception e) {
            throw new ComponentException(componentClass.getName()
                    + " is not a valid component class. No valid constructor found (WebElement) or (WebElement, WebDriver)", e);
        }
    }

    private WebElement unwrapElement(WebElement element) {
        if (element instanceof WrapsElement) {
            return unwrapElement(((WrapsElement)element).getWrappedElement());
        }
        return element;
    }

    /**
     * Release this manager.
     */
    public void release() {
        components.clear();
    }
}
