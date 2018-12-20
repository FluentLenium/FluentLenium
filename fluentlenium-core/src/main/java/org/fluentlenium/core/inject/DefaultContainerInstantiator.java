package org.fluentlenium.core.inject;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.utils.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Creates container instances
 */
public class DefaultContainerInstantiator implements ContainerInstantiator {
    private final FluentControl control;

    /**
     * Creates a new container instantiator
     *
     * @param control FluentLenium control
     */
    public DefaultContainerInstantiator(FluentControl control) {
        this.control = control;
    }

    @Override
    public <T> T newInstance(Class<T> cls, ContainerContext context) {
        try {
            return ReflectionUtils.newInstanceOptionalArgs(cls, new ContainerFluentControl(control, context));
        } catch (NoSuchMethodException e) {
            throw new FluentInjectException(cls.getName() + " is not a valid component class.", e);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new FluentInjectException(cls.getName() + " can't be instantiated.", e);
        }
    }
}
