package org.fluentlenium.core.components;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class DefaultComponentInstantiator extends AbstractComponentInstantiator {
    private final FluentControl fluentControl;
    private final ComponentInstantiator instantiator;

    public DefaultComponentInstantiator(final FluentControl fluentControl) {
        this.fluentControl = fluentControl;
        this.instantiator = this;
    }

    public DefaultComponentInstantiator(final FluentControl fluentControl, final ComponentInstantiator instantiator) {
        this.fluentControl = fluentControl;
        this.instantiator = instantiator;
    }

    @Override
    public boolean isComponentClass(final Class<?> componentClass) {
        try {
            ReflectionUtils.getConstructorOptional(1, componentClass, WebElement.class, FluentControl.class,
                    ComponentInstantiator.class);
            return true;
        } catch (final NoSuchMethodException e) {
            return false;
        }
    }

    @Override
    public boolean isComponentListClass(final Class<? extends List<?>> componentListClass) {
        try {
            ReflectionUtils.getConstructorOptional(1, componentListClass, Class.class, List.class, FluentControl.class,
                    ComponentInstantiator.class);
            return true;
        } catch (final NoSuchMethodException e) {
            return false;
        }
    }

    @Override
    public <T> T newComponent(final Class<T> componentClass, final WebElement element) {
        try {
            return ReflectionUtils.newInstanceOptionalArgs(1, componentClass, element, fluentControl, instantiator);
        } catch (final NoSuchMethodException e) {
            throw new ComponentException(componentClass.getName() + " is not a valid component class.", e);
        } catch (final IllegalAccessException e) {
            throw new ComponentException(componentClass.getName() + " can't be instantiated.", e);
        } catch (final InvocationTargetException e) {
            throw new ComponentException(componentClass.getName() + " can't be instantiated.", e);
        } catch (final InstantiationException e) {
            throw new ComponentException(componentClass.getName() + " can't be instantiated.", e);
        }
    }

    @Override
    public <L extends List<T>, T> L newComponentList(final Class<L> listClass, final Class<T> componentClass,
            final List<T> componentsList) {
        try {
            return ReflectionUtils
                    .newInstanceOptionalArgs(1, listClass, componentClass, componentsList, fluentControl, instantiator);
        } catch (final NoSuchMethodException e) {
            throw new ComponentException(listClass.getName() + " is not a valid component list class.", e);
        } catch (final IllegalAccessException e) {
            throw new ComponentException(listClass.getName() + " can't be instantiated.", e);
        } catch (final InvocationTargetException e) {
            throw new ComponentException(listClass.getName() + " can't be instantiated.", e);
        } catch (final InstantiationException e) {
            throw new ComponentException(listClass.getName() + " can't be instantiated.", e);
        }
    }
}
