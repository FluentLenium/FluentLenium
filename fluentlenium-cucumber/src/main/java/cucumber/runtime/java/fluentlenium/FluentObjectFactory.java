package cucumber.runtime.java.fluentlenium;

import io.cucumber.core.backend.ObjectFactory;
import io.cucumber.core.exception.CucumberException;
import org.fluentlenium.configuration.FluentConfiguration;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;
import static org.fluentlenium.adapter.cucumber.FluentTestContainer.FLUENT_TEST;
import static org.fluentlenium.adapter.cucumber.FluentTestContainer.setConfigClass;

/**
 * It is an object factory for creating Cucumber steps objects in FluentLenium injection container.
 * <p>
 * It also configures a config class which is either a subclass of {@link org.fluentlenium.adapter.cucumber.FluentCucumberTest}
 * annotated with {@code @FluentConfiguration}, or if there is no such class, then sets it as null.
 * <p>
 * Since a FluentLenium configuration can be configured not only via the {@link FluentConfiguration} annotation but in
 * other ways too, the config class can be null if there is no annotated class.
 */
public class FluentObjectFactory implements ObjectFactory {

    /**
     * Cache for Cucumber glue class instances ({@code FluentCucumberTest} subclasses in this case).
     */
    private final Map<Class<?>, Object> instances = new HashMap<>();
    private Class<?> configClass;

    @Override
    public void start() {
        if (nonNull(configClass)) {
            setConfigClass(configClass);
        } else {
            setConfigClass(null);
        }
        FLUENT_TEST.instance();
    }

    @Override
    public void stop() {
        FLUENT_TEST.reset();
        this.instances.clear();
    }

    @Override
    public boolean addClass(Class<?> aClass) {
        if (configClass == null) {
            configClass = getFluentConfigurationClass(aClass);
            if (nonNull(configClass)) {
                setConfigClass(configClass);
            }
        }
        return true;
    }

    @Override
    public <T> T getInstance(Class<T> type) { // NOPMD
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

    private <T> T cacheNewInstance(Class<T> type) {
        try {
            T instance = FLUENT_TEST.injector().newInstance(type);
            FLUENT_TEST.injector().inject(instance);
            instances.put(type, instance);
            return instance;

        } catch (Exception e) {
            throw new CucumberException(String.format("Failed to instantiate %s", type), e);
        }
    }

    /**
     * Returns either the superclass of the provided class, or the provided one depending one which one is
     * annotated as {@link FluentConfiguration}.
     *
     * @return superclass of {@code cls} if it is the annotated one, {@code cls} if it is annotated, otherwise null
     */
    private Class<?> getFluentConfigurationClass(Class<?> cls) {
        Class<?> result = null;
        Class superClass = cls.getSuperclass();
        if (superClass != null && superClass.isAnnotationPresent(FluentConfiguration.class)) {
            result = superClass;
        } else if (cls.isAnnotationPresent(FluentConfiguration.class)) {
            result = cls;
        }
        return result;
    }
}
