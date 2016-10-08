package org.fluentlenium.core.inject;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.utils.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Creates container instances
 */
public class DefaultContainerInstanciator implements ContainerInstanciator {
    private final FluentControl control;

    /**
     * Creates a new container instanciator
     *
     * @param control FluentLenium control
     */
    public DefaultContainerInstanciator(final FluentControl control) {
        this.control = control;
    }

    @Override
    public <T> T newInstance(final Class<T> cls, final ContainerContext context) {
        try {
            return ReflectionUtils.newInstanceOptionalArgs(cls, new ContainerFluentControl(control, context));
        } catch (final NoSuchMethodException e) {
            throw new FluentInjectException(cls.getName() + " is not a valid component class.", e);
        } catch (final IllegalAccessException e) {
            throw new FluentInjectException(cls.getName() + " can't be instantiated.", e);
        } catch (final InvocationTargetException e) {
            throw new FluentInjectException(cls.getName() + " can't be instantiated.", e);
        } catch (final InstantiationException e) {
            throw new FluentInjectException(cls.getName() + " can't be instantiated.", e);
        }
    }
}
