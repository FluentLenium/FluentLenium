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

    private final FluentControl control;
    private final DefaultComponentInstantiator instantiator;

    private final Map<WebElement, Set<Object>> components = new IdentityHashMap<>();

    /**
     * Creates a new components manager.
     *
     * @param control control interface
     */
    public ComponentsManager(final FluentControl control) {
        this.control = control;
        this.instantiator = new DefaultComponentInstantiator(this.control, this);
    }

    /**
     * Get the component instantiator used by this components manager.
     *
     * @return component instantiator
     */
    public ComponentInstantiator getInstantiator() {
        return instantiator;
    }

    @Override
    public Set<Object> getComponents(final WebElement element) {
        return components.get(unwrapElement(element));
    }

    @Override
    public boolean isComponentClass(final Class<?> componentClass) {
        return instantiator.isComponentClass(componentClass);
    }

    @Override
    public boolean isComponentListClass(final Class<? extends List<?>> componentListClass) {
        return instantiator.isComponentListClass(componentListClass);
    }

    @Override
    public <T> T newComponent(final Class<T> componentClass, final WebElement element) {
        final T component = instantiator.newComponent(componentClass, element);
        register(element, component);
        return component;
    }

    private <T> void register(final WebElement element, final T component) {
        final WebElement webElement = unwrapElement(element);
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
    public <L extends List<T>, T> L newComponentList(final Class<L> listClass, final Class<T> componentClass,
            final List<T> componentsList) {
        return instantiator.newComponentList(listClass, componentClass, componentsList);
    }

    @Override
    public <L extends List<T>, T> L asComponentList(final Class<L> listClass, final Class<T> componentClass,
            final Iterable<WebElement> elementList) {
        final L componentList = instantiator.asComponentList(listClass, componentClass, elementList);

        int i = 0;
        for (final WebElement element : elementList) {
            register(element, componentList.get(i));
            i++;
        }

        return componentList;
    }

    @Override
    public void proxyElementSearch(final Object proxy, final ElementLocator locator) {
        // Do nothing.
    }

    @Override
    public void proxyElementFound(final Object proxy, final ElementLocator locator, final List<WebElement> elements) {
        synchronized (this) {
            for (final WebElement element : elements) {
                final Set<Object> proxyComponents = components.remove(proxy);
                if (proxyComponents != null) {
                    components.put(unwrapElement(element), proxyComponents);
                }
            }
        }
    }

    private WebElement unwrapElement(final WebElement element) {
        if (element instanceof WrapsElement) {
            final WebElement wrappedElement = ((WrapsElement) element).getWrappedElement();
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
        for (final WebElement element : components.keySet()) {
            LocatorProxies.removeProxyListener(element, this);
        }
        components.clear();
    }

}
