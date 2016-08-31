package org.fluentlenium.core.components;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class DefaultComponentInstantiator extends AbstractComponentInstantiator {
    private final FluentControl fluentControl;
    private final ComponentInstantiator instantiator;

    public DefaultComponentInstantiator(FluentControl fluentControl) {
        this.fluentControl = fluentControl;
        this.instantiator = this;
    }

    public DefaultComponentInstantiator(FluentControl fluentControl, ComponentInstantiator instantiator) {
        this.fluentControl = fluentControl;
        this.instantiator = instantiator;
    }

    @Override
    public boolean isComponentClass(Class<?> componentClass) {
        try {
            ReflectionUtils.getConstructorOptional(1, componentClass, WebElement.class, FluentControl.class, ComponentInstantiator.class);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    @Override
    public boolean isComponentListClass(Class<? extends List<?>> componentListClass) {
        try {
            ReflectionUtils.getConstructorOptional(1, componentListClass, Class.class, List.class, FluentControl.class, ComponentInstantiator.class);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    @Override
    public <T> T newComponent(Class<T> componentClass, WebElement element) {
        try {
            return ReflectionUtils.newInstanceOptionalArgs(1, componentClass, element, fluentControl, instantiator);
        } catch (NoSuchMethodException e) {
            throw new ComponentException(componentClass.getName() + " is not a valid component class.", e);
        } catch (IllegalAccessException e) {
            throw new ComponentException(componentClass.getName() + " can't be instantiated.", e);
        } catch (InvocationTargetException e) {
            throw new ComponentException(componentClass.getName() + " can't be instantiated.", e);
        } catch (InstantiationException e) {
            throw new ComponentException(componentClass.getName() + " can't be instantiated.", e);
        }
    }

    @Override
    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, List<T> componentsList) {
        try {
            return ReflectionUtils.newInstanceOptionalArgs(1, listClass, componentClass, componentsList, fluentControl, instantiator);
        } catch (NoSuchMethodException e) {
            throw new ComponentException(listClass.getName() + " is not a valid component list class.", e);
        } catch (IllegalAccessException e) {
            throw new ComponentException(listClass.getName() + " can't be instantiated.", e);
        } catch (InvocationTargetException e) {
            throw new ComponentException(listClass.getName() + " can't be instantiated.", e);
        } catch (InstantiationException e) {
            throw new ComponentException(listClass.getName() + " can't be instantiated.", e);
        }
    }
}
