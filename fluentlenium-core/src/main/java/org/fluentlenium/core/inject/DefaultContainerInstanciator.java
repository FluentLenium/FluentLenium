package org.fluentlenium.core.inject;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.utils.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;

public class DefaultContainerInstanciator implements ContainerInstanciator {
    private final FluentControl fluentControl;

    public DefaultContainerInstanciator(final FluentControl fluentControl) {
        this.fluentControl = fluentControl;
    }

    @Override
    public <T> T newInstance(final Class<T> cls, final ContainerContext context) {
        try {
            return ReflectionUtils.newInstanceOptionalArgs(cls, new ContainerFluentControl(fluentControl, context));
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
