/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package org.fluentlenium.core;

import org.fluentlenium.core.annotation.AjaxElement;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.exception.ConstructionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

public class FluentAdapter extends Fluent {

    private final Map<Class, FluentPage> pageInstances = new HashMap<Class, FluentPage>();

    public FluentAdapter(WebDriver webDriver) {
        super(webDriver);
    }

    public FluentAdapter() {
        super();
    }

    protected void initTest() {
        try {
            injectPageIntoContainer(this);
            initFluentWebElements(this);
        } catch (ClassNotFoundException e) {
            throw new ConstructionException("Class not found", e);
        } catch (IllegalAccessException e) {
            throw new ConstructionException("IllegalAccessException", e);
        }
    }

    private void injectPageIntoContainer(Fluent container)
            throws ClassNotFoundException, IllegalAccessException {
        for (Class cls = container.getClass();
             FluentAdapter.class.isAssignableFrom(cls) || FluentPage.class.isAssignableFrom(cls);
             cls = cls.getSuperclass()) {
            for (Field field : cls.getDeclaredFields()) {
                if (field.isAnnotationPresent(Page.class)) {
                    field.setAccessible(true);
                    Class clsField = field.getType();
                    Class clsPage = Class.forName(clsField.getName());
                    Fluent existingPage = pageInstances.get(clsPage);
                    if (existingPage != null) {
                        field.set(container, existingPage);
                    } else {
                        FluentPage page = initClass(clsPage);
                        field.set(container, page);
                        pageInstances.put(clsPage, page);
                        injectPageIntoContainer(page);
                    }
                }
            }
        }
    }

    public <T extends FluentPage> T createPage(Class<T> cls) {
        T container = initClass(cls);
        try {
            injectPageIntoContainer(container);
        } catch (ClassNotFoundException e) {
            throw new ConstructionException("Class " + (cls != null ? cls.getName() : " null") + "not found", e);
        } catch (IllegalAccessException e) {
            throw new ConstructionException("IllegalAccessException on class " + (cls != null ? cls.getName() : " null"), e);
        }
        return container;
    }


    protected <T extends FluentPage> T initClass(Class<T> cls) {
        try {
            Constructor<T> construct = cls.getDeclaredConstructor();
            construct.setAccessible(true);
            T page = construct.newInstance();
            Class parent = Class.forName(Fluent.class.getName());
            initDriver(page, parent);
            initBaseUrl(page, parent);

            //init fields with default proxies
            initFluentWebElements(page);

            return page;
        } catch (ClassNotFoundException e) {
            throw new ConstructionException("Class " + cls.getName() + "not found", e);
        } catch (IllegalAccessException e) {
            throw new ConstructionException("IllegalAccessException on class " + cls.getName(), e);
        } catch (NoSuchMethodException e) {
            throw new ConstructionException("No constructor found on class " + cls.getName(), e);
        } catch (InstantiationException e) {
            throw new ConstructionException("Unable to instantiate " + cls.getName(), e);
        } catch (InvocationTargetException e) {
            throw new ConstructionException("Cannot invoke method setDriver on " + cls.getName(), e);
        }
    }

    private <T extends Fluent> void initFluentWebElements(T page) {
        for (Class classz = page.getClass();
             FluentAdapter.class.isAssignableFrom(classz) || FluentPage.class.isAssignableFrom(classz);
             classz = classz.getSuperclass()) {
            for (Field fieldFromPage : classz.getDeclaredFields()) {
                if (isFluentWebElementField(fieldFromPage)) {
                    fieldFromPage.setAccessible(true);
                    AjaxElement elem = fieldFromPage.getAnnotation(AjaxElement.class);
                    if (elem == null) {
                        proxyElement(new DefaultElementLocatorFactory(getDriver()), page, fieldFromPage);
                    } else {
                        proxyElement(new AjaxElementLocatorFactory(getDriver(), elem.timeOutInSeconds()), page, fieldFromPage);
                    }
                }
            }
        }
    }


    private <T extends FluentPage> void initBaseUrl(T page, Class<?> parent) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (getBaseUrl() == null) {
            return;
        }

        Method m = parent.getDeclaredMethod("withDefaultUrl", String.class);
        m.setAccessible(true);
        m.invoke(page, getBaseUrl());
    }

    private <T extends FluentPage> void initDriver(T page, Class<?> parent) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method m = parent.getDeclaredMethod("initFluent", WebDriver.class);
        m.setAccessible(true);
        m.invoke(page, getDriver());
    }

    private boolean isFluentWebElementField(Field field) {
        try {
            return !Modifier.isFinal(field.getModifiers()) &&
                    field.getType().getConstructor(WebElement.class) != null;
        } catch (Exception e) {
            return false; // Constructor not found
        }
    }

    private static void proxyElement(ElementLocatorFactory factory, Object page, Field field) {
        ElementLocator locator = factory.createLocator(field);
        if (locator == null) {
            return;
        }

        InvocationHandler handler = new LocatingElementHandler(locator);
        WebElement proxy = (WebElement) Proxy.newProxyInstance(
                page.getClass().getClassLoader(), new Class[]{WebElement.class}, handler);
        try {
            field.setAccessible(true);
            field.set(page, field.getType().getConstructor(WebElement.class).newInstance(proxy));
        } catch (Exception e) {
            throw new RuntimeException("Unable to find an accessible constructor with an argument of type WebElement in " + field.getType(), e);
        }
    }

    /**
     * Override this method to change the driver
     *
     * @return
     */
    public WebDriver getDefaultDriver() {
        return new FirefoxDriver();
    }

    /**
     * Override this method to set the base URL to use when using relative URLs
     *
     * @return The base URL, or null if relative URLs should be passed to the driver untouched
     */
    public String getDefaultBaseUrl() {
        return null;
    }


    /**
     * Override this method to set some config options on the driver. For example withDefaultSearchWait and withDefaultPageWait
     * Remember that you can access to the WebDriver object using this.getDriver().
     */
    public void getDefaultConfig() {
    }

    public static void assertAt(FluentPage fluent) {
        fluent.isAt();
    }

    public void quit() {
        if (getDriver() != null) {
            getDriver().quit();
        }
    }


}
