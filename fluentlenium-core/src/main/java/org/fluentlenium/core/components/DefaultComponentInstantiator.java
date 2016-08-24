package org.fluentlenium.core.components;

import org.fluentlenium.core.inject.FluentInjectException;
import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;

public class DefaultComponentInstantiator implements ComponentInstantiator {
    private final WebDriver driver;
    private final ComponentInstantiator instantiator;

    public DefaultComponentInstantiator(WebDriver driver) {
        this.driver = driver;
        this.instantiator = this;
    }

    public DefaultComponentInstantiator(WebDriver driver, ComponentInstantiator instantiator) {
        this.driver = driver;
        this.instantiator = instantiator;
    }


    @Override
    public boolean isComponentClass(Class<?> componentClass) {
        try {
            ReflectionUtils.getConstructorOptional(1, componentClass, WebElement.class, WebDriver.class, ComponentInstantiator.class);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    @Override
    public <T> T newComponent(Class<T> componentClass, WebElement element) {
        try {
            return ReflectionUtils.newInstanceOptionalArgs(1, componentClass, element, driver, instantiator);
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
}
