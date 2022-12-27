package io.fluentlenium.core.components;

import io.fluentlenium.utils.ReflectionUtils;
import io.fluentlenium.core.FluentControl;
import io.fluentlenium.utils.ReflectionUtils;
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
    public DefaultComponentInstantiator(FluentControl control) {
        this.control = control;
        instantiator = this;
    }

    /**
     * Creates a new component instantiator, using given fluent control and underlying instantiator.
     *
     * @param control      control interface
     * @param instantiator component instantiator
     */
    public DefaultComponentInstantiator(FluentControl control, ComponentInstantiator instantiator) {
        this.control = control;
        this.instantiator = instantiator;
    }

    @Override
    public boolean isComponentClass(Class<?> componentClass) {
        try {
            ReflectionUtils.getConstructorOptional(1, componentClass, WebElement.class, FluentControl.class,
                    ComponentInstantiator.class);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    @Override
    public boolean isComponentListClass(Class<? extends List<?>> componentListClass) {
        try {
            ReflectionUtils.getConstructorOptional(1, componentListClass, Class.class, List.class, FluentControl.class,
                    ComponentInstantiator.class);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    @Override
    public <T> T newComponent(Class<T> componentClass, WebElement element) {
        try {
            return ReflectionUtils.newInstanceOptionalArgs(1, componentClass, element, control, instantiator);
        } catch (NoSuchMethodException e) {
            throw new ComponentException(componentClass.getName() + " is not a valid component class.", e);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new ComponentException(componentClass.getName() + " can't be instantiated.", e);
        }
    }

    @Override
    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, List<T> componentsList) {
        try {
            return ReflectionUtils.newInstanceOptionalArgs(1, listClass, componentClass, componentsList, control, instantiator);
        } catch (NoSuchMethodException e) {
            throw new ComponentException(listClass.getName() + " is not a valid component list class.", e);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new ComponentException(listClass.getName() + " can't be instantiated.", e);
        }
    }
}
