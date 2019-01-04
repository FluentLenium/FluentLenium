package org.fluentlenium.core.events;

import org.fluentlenium.core.components.ComponentsAccessor;
import org.fluentlenium.core.components.ComponentsListener;
import org.openqa.selenium.WebElement;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Listen to components registration to register their events annotations couterparts.
 */
public class ComponentsEventsRegistry implements ComponentsListener {
    private final EventsRegistry registry;
    private final ComponentsAccessor componentsAccessor;
    private final Map<RegistryKey, ContainerAnnotationsEventsRegistry> allRegistries = new IdentityHashMap<>();

    /**
     * Constructor
     */
    public static class RegistryKey {
        WebElement element;
        Object component;

        public RegistryKey(WebElement element, Object component) {
            this.element = element;
            this.component = component;
        }
    }

    /**
     * Constructor
     *
     * @param registry           events registry
     * @param componentsAccessor components accessor
     */
    public ComponentsEventsRegistry(EventsRegistry registry, ComponentsAccessor componentsAccessor) {
        this.registry = registry;
        this.componentsAccessor = componentsAccessor;
        this.componentsAccessor.addComponentsListener(this);
    }

    @Override
    public void componentRegistered(WebElement element, Object component) {
        ContainerAnnotationsEventsRegistry containerRegistry = new ContainerAnnotationsEventsRegistry(registry, component,
                element);

        if (containerRegistry.getListenerCount() > 0) {
            allRegistries.put(new RegistryKey(element, component), containerRegistry);
        }
    }

    @Override
    public void componentReleased(WebElement element, Object component) {
        ContainerAnnotationsEventsRegistry remove = allRegistries.remove(new RegistryKey(element, component));
        if (remove != null) {
            remove.close();
        }
    }

    /**
     * Close all registries.
     */
    public void close() {
        Iterator<Map.Entry<RegistryKey, ContainerAnnotationsEventsRegistry>> entries = allRegistries.entrySet().iterator();

        while (entries.hasNext()) {
            Map.Entry<RegistryKey, ContainerAnnotationsEventsRegistry> entry = entries.next();
            entry.getValue().close();
            entries.remove();
        }

        componentsAccessor.removeComponentsListener(this);
    }

}
