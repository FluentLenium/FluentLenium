package org.fluentlenium.core.components;

import lombok.experimental.Delegate;
import org.openqa.selenium.WebDriver;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Manage instances of {@link ComponentsManager}.
 */
public enum Components {
    INSTANCE;

    @Delegate
    private final Impl impl = new Impl();

    static class Impl {
        private Map<WebDriver, ComponentsManager> driverManagers = new IdentityHashMap<>();

        /**
         * Create a {@link ComponentsManager} for given {@link WebDriver}
         * @param driver
         * @return a new registered instance of {@link ComponentsManager}
         * @throws Components if a {@link ComponentsManager} is already registered.
         */
        public synchronized ComponentsManager create(WebDriver driver) {
            if (driverManagers.containsKey(driver)) throw new ComponentException("This WebDriver already have a Component Manager registered.");
            return createImpl(driver);
        }

        private ComponentsManager createImpl(WebDriver driver) {
            ComponentsManager driverManager = new ComponentsManager(driver);
            driverManagers.put(driver, driverManager);
            return driverManager;
        }

        public synchronized ComponentsManager getOrCreate(WebDriver driver) {
            ComponentsManager componentsManager = get(driver);
            if (componentsManager == null) {
                componentsManager = createImpl(driver);
            }
            return componentsManager;
        }

        /**
         * Get the existing {@link ComponentsManager} for given {@link WebDriver}
         * @param driver
         * @return existing registered instance of {@link ComponentsManager}, or null if not found.
         */
        public synchronized ComponentsManager get(WebDriver driver) {
            return driverManagers.get(driver);
        }

        /**
         * Release the existing {@link ComponentsManager} for given {@link WebDriver}
         * @param driver
         */
        public synchronized void release(WebDriver driver) {
            ComponentsManager remove = driverManagers.remove(driver);
            if (remove != null) {
                remove.release();
            }
        }
    }



}
