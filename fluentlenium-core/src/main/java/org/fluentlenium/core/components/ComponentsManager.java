package org.fluentlenium.core.components;

import com.sun.jna.WeakIdentityHashMap;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.fluentlenium.core.proxy.ProxyElementListener;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;
import java.util.Map;

/**
 * Manage living components for a WebDriver instance.
 * <p>
 * A component is an Object implementing no particular interface, but capable a wrapping
 * a {@link org.openqa.selenium.WebElement}.
 * <p>
 * {@link org.fluentlenium.core.domain.FluentWebElement} is the most common component.
 */
public class ComponentsManager extends AbstractComponentInstantiator implements ComponentInstantiator, ComponentAccessor, ProxyElementListener {

    private final FluentControl fluentControl;
    private final DefaultComponentInstantiator instantiator;

    //TODO: IdentityHashMap or WeakIdentityHashMap ?
    private Map<WebElement, Object> components = new WeakIdentityHashMap();

    public ComponentsManager(FluentControl fluentControl) {
        this.fluentControl = fluentControl;
        this.instantiator = new DefaultComponentInstantiator(this.fluentControl, this);
    }

    public ComponentInstantiator getInstantiator() {
        return instantiator;
    }

    @Override
    public Object getComponent(WebElement element) {
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
        components.put(webElement, component);
    }

    @Override
    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, List<T> componentsList) {
        return instantiator.newComponentList(listClass, componentClass, componentsList);
    }

    @Override
    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, Iterable<WebElement> elementList) {
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
    }

    @Override
    public void proxyElementFound(Object proxy, ElementLocator locator, List<WebElement> elements) {
        for (WebElement element : elements) {
            Object component = components.remove(proxy);
            if (component != null) {
                components.put(unwrapElement(element), component);
            }
        }
    }

    private WebElement unwrapElement(WebElement element) {
        if (element instanceof WrapsElement) {
            WebElement wrappedElement = ((WrapsElement) element).getWrappedElement();
            if (wrappedElement != element && wrappedElement != null) {
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
