package cucumber.runtime.java.fluentlenium;

import cucumber.api.java.ObjectFactory;
import cucumber.runtime.CucumberException;
import org.fluentlenium.configuration.FluentConfiguration;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;
import static org.fluentlenium.adapter.cucumber.FluentTestContainer.FLUENT_TEST;
import static org.fluentlenium.adapter.cucumber.FluentTestContainer.setConfigClass;

/**
 * It is an object factory for creating Cucumber steps objects in FluentLenium injection container
 */
public class FluentObjectFactory implements ObjectFactory {

    private final Map<Class<?>, Object> instances = new HashMap<>();

    private Class<?> configClass;

    @Override
    public synchronized void start() {
        if (nonNull(configClass)) {
            setConfigClass(configClass);
            FLUENT_TEST.instance();

        } else {
            setConfigClass(null);
            FLUENT_TEST.instance();
        }
    }

    @Override
    public synchronized void stop() {
        FLUENT_TEST.reset();
        this.instances.clear();
    }

    @Override
    public synchronized boolean addClass(Class<?> aClass) {
        if (configClass == null) {
            configClass = checkClassForConfiguration(aClass);
            if (nonNull(configClass)) {
                setConfigClass(configClass);
            }
        }
        return true;
    }

    @Override
    public synchronized  <T> T getInstance(Class<T> type) { // NOPMD
        try {
            T instance = type.cast(instances.get(type));
            if (instance == null) {
                instance = cacheNewInstance(type);
            }
            return instance;

        } catch (Exception e) {
            throw new CucumberException(String.format("Failed to instantiate %s", type), e);
        }
    }

    private synchronized <T> T cacheNewInstance(Class<T> type) {
        try {
            T instance = FLUENT_TEST.injector().newInstance(type);
            FLUENT_TEST.injector().inject(instance);
            instances.put(type, instance);
            return instance;

        } catch (Exception e) {
            throw new CucumberException(String.format("Failed to instantiate %s", type), e);
        }
    }

    private synchronized Class<?> checkClassForConfiguration(Class<?> cls) {
        Class superClass = cls.getSuperclass();
        if (superClass != null && superClass.isAnnotationPresent(FluentConfiguration.class)) {
            return superClass;

        } else if (cls.isAnnotationPresent(FluentConfiguration.class)) {
            return cls;

        } else {
            return null;
        }
    }
}
