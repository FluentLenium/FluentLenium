package org.fluentlenium.core.components;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.fluentlenium.core.proxy.ProxyElementListener;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Manage living components for a WebDriver instance.
 * <p>
 * A component is an Object implementing no particular interface, but capable a wrapping
 * a {@link org.openqa.selenium.WebElement}.
 * <p>
 * {@link org.fluentlenium.core.domain.FluentWebElement} is the most common component.
 */
public class ComponentsManager extends AbstractComponentInstantiator
        implements ComponentInstantiator, ComponentsAccessor, ProxyElementListener {

    private final FluentControl fluentControl;
    private final DefaultComponentInstantiator instantiator;

    private final Map<WebElement, Set<Object>> components = new IdentityHashMap<>();

    public ComponentsManager(FluentControl fluentControl) {
        this.fluentControl = fluentControl;
        this.instantiator = new DefaultComponentInstantiator(this.fluentControl, this);
    }

    public ComponentInstantiator getInstantiator() {
        return instantiator;
    }

    @Override
    public Set<Object> getComponents(WebElement element) {
        return components.get(unwrapElement(element));
    }

    @Override
    public boolean isComponentClass(Class<?> componentClass) {
        return instantiator.isComponentClass(componentClass);
    }

    @Override
    public boolean isComponentListClass(Class<? extends List<?>> componentListClass) {
        return instantiator.isComponentListClass(componentListClass);
    }

    @Override
    public <T> T newComponent(Class<T> componentClass, WebElement element) {
        T component = instantiator.newComponent(componentClass, element);
        register(element, component);
        return component;
    }

    private <T> void register(WebElement element, T component) {
        WebElement webElement = unwrapElement(element);
        LocatorProxies.addProxyListener(webElement, this);
        synchronized (this) {
            Set<Object> elementComponents = components.get(webElement);
            if (elementComponents == null) {
                elementComponents = new HashSet<>();
                components.put(webElement, elementComponents);
            }
            elementComponents.add(component);
        }
    }

    @Override
    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, List<T> componentsList) {
        return instantiator.newComponentList(listClass, componentClass, componentsList);
    }

    @Override
    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass,
                                                    Iterable<WebElement> elementList) {
        L componentList = instantiator.asComponentList(listClass, componentClass, elementList);

        int i = 0;
        for (WebElement element : elementList) {
            register(element, componentList.get(i));
            i++;
        }

        return componentList;
    }

    @Override
    public void proxyElementSearch(Object proxy, ElementLocator locator) {
        // Do nothing.
    }

    @Override
    public void proxyElementFound(Object proxy, ElementLocator locator, List<WebElement> elements) {
        synchronized (this) {
            for (WebElement element : elements) {
                Set<Object> proxyComponents = components.remove(proxy);
                if (proxyComponents != null) {
                    components.put(unwrapElement(element), proxyComponents);
                }
            }
        }
    }

    private WebElement unwrapElement(WebElement element) {
        if (element instanceof WrapsElement) {
            WebElement wrappedElement = ((WrapsElement) element).getWrappedElement();
            if (wrappedElement != element && wrappedElement != null) { // NOPMD CompareObjectsWithEquals
                return unwrapElement(wrappedElement);
            }
        }
        return element;
    }

    /**
     * Release this manager.
     */
    public void release() {
        for (WebElement element : components.keySet()) {
            LocatorProxies.removeProxyListener(element, this);
        }
        components.clear();
    }

}
