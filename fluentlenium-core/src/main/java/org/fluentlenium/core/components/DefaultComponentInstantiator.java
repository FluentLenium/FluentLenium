package org.fluentlenium.core.components;

import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;

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
            ReflectionUtils.getConstructorOptional(componentClass, WebElement.class, WebDriver.class, ComponentInstantiator.class);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    @Override
    public <T> T newComponent(Class<T> componentClass, WebElement element) {
        try {
            return ReflectionUtils.newInstanceOptionalArgs(componentClass, element, driver, instantiator);
        } catch (Exception e) {
            throw new ComponentException(componentClass.getName()
                    + " is not a valid component class. No valid constructor found (WebElement) or (WebElement, WebDriver)", e);
        }
    }
}
