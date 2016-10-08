package org.fluentlenium.core.components;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Default component instantiator.
 */
public class DefaultComponentInstantiator extends AbstractComponentInstantiator {
    private final FluentControl control;
    private final ComponentInstantiator instantiator;

    /**
     * Creates a new component instantiator, using given fluent control.
     *
     * @param control control interface
     */
    public DefaultComponentInstantiator(final FluentControl control) {
        this.control = control;
        this.instantiator = this;
    }

    /**
     * Creates a new component instantiator, using given fluent control and underlying instantiator.
     *
     * @param control      control interface
     * @param instantiator component instantiator
     */
    public DefaultComponentInstantiator(final FluentControl control, final ComponentInstantiator instantiator) {
        this.control = control;
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
            return ReflectionUtils.newInstanceOptionalArgs(1, componentClass, element, control, instantiator);
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
            return ReflectionUtils.newInstanceOptionalArgs(1, listClass, componentClass, componentsList, control, instantiator);
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
