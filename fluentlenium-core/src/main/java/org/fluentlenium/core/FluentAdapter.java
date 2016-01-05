package org.fluentlenium.core;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.fluentlenium.core.annotation.AjaxElement;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
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
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FluentAdapter extends Fluent {

    private final ConcurrentMap<Class, FluentPage> pageInstances = new ConcurrentHashMap<Class, FluentPage>();

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

    protected void cleanUp() {
        pageInstances.clear();
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
                    FluentPage page = (FluentPage) field.get(container);
                    if (existingPage != null) {
                        // if page isn't injected
                        if (page == null) {
                            field.set(container, existingPage);
                        }
                    } else {
                        // if page isn't injected
                        if (page == null) {
                            page = initClass(clsPage);
                            field.set(container, page);
                        } else {
                            page = initClass(page);
                        }
                        pageInstances.putIfAbsent(clsPage, page);
                        injectPageIntoContainer(page);
                    }
                }
            }
        }
    }

    public <T extends FluentPage> T createPage(Class<T> cls, Object... params) {
        T container = initClass(cls, params);
        try {
            injectPageIntoContainer(container);
        } catch (ClassNotFoundException e) {
            throw new ConstructionException("Class " + (cls != null ? cls.getName() : " null") + "not found", e);
        } catch (IllegalAccessException e) {
            throw new ConstructionException("IllegalAccessException on class " + (cls != null ? cls.getName() : " null"), e);
        }
        return container;
    }

    protected <T extends FluentPage> T initClass(Class<T> cls, Object... params) {
        try {
            T page = constructPageWithParams(cls, params);
            initClass(page, params);
            return page;
        } catch (IllegalAccessException e) {
            throw new ConstructionException("IllegalAccessException on class " + (cls != null ? cls.getName() : " null"), e);
        } catch (NoSuchMethodException e) {
            throw new ConstructionException("No constructor found on class " + (cls != null ? cls.getName() : " null"), e);
        } catch (InstantiationException e) {
            throw new ConstructionException("Unable to instantiate " + (cls != null ? cls.getName() : " null"), e);
        } catch (InvocationTargetException e) {
            throw new ConstructionException("Cannot invoke method setDriver on " + (cls != null ? cls.getName() : " null"), e);
        }
    }

    protected <T extends FluentPage> T initClass(T page, Object... params) {
        try {
            Class parent = Fluent.class;
            initDriver(page, parent);
            initBaseUrl(page, parent);

            //init fields with default proxies
            initFluentWebElements(page);
            return page;
        } catch (IllegalAccessException e) {
            throw new ConstructionException("IllegalAccessException on class " + page.getClass().getName(), e);
        } catch (NoSuchMethodException e) {
            throw new ConstructionException("No constructor found on class " + page.getClass().getName(), e);
        } catch (InvocationTargetException e) {
            throw new ConstructionException("Cannot invoke method setDriver on " + page.getClass().getName(), e);
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
            return !Modifier.isFinal(field.getModifiers()) && (isFluentList(field) ||
                    field.getType().getConstructor(WebElement.class) != null);
        } catch (Exception e) {
            return false; // Constructor not found
        }
    }

    private static boolean isFluentList(Field field) {
        return FluentList.class.isAssignableFrom(field.getType());
    }

    private static void proxyElement(ElementLocatorFactory factory, Object page, Field field) {
        ElementLocator locator = factory.createLocator(field);
        if (locator == null) {
            return;
        }

        try {
            field.setAccessible(true);
            if (isFluentList(field)) {
                // LocatingElementListHandler is not a good choice, elements are FluentWebElement not WebElement.
                final InvocationHandler handler = new FluentListInvocationHandler(locator);
                FluentList<FluentWebElement> proxy = (FluentList<FluentWebElement>) Proxy.newProxyInstance(
                        page.getClass().getClassLoader(), new Class[]{FluentList.class}, handler);
                field.set(page, proxy);
            } else {
                final InvocationHandler handler = new LocatingElementHandler(locator);
                WebElement proxy = (WebElement) Proxy.newProxyInstance(
                        page.getClass().getClassLoader(), new Class[]{WebElement.class}, handler);
                field.set(page, field.getType().getConstructor(WebElement.class).newInstance(proxy));
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to find an accessible constructor with an argument of type WebElement in " + field.getType(), e);
        }
    }

    private static class FluentListInvocationHandler implements InvocationHandler {

        private final ElementLocator elementLocator;

        public FluentListInvocationHandler(ElementLocator elementLocator) {
            this.elementLocator = elementLocator;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            List<WebElement> elements = elementLocator.findElements();
            FluentListImpl list = new FluentListImpl(FluentIterable.from(elements).transform(new Function<WebElement, FluentWebElement>() {
                @Override
                public FluentWebElement apply(WebElement input) {
                    return new FluentWebElement(input);
                }
            }).toList());
            try {
                return method.invoke(list, args);
            } catch (InvocationTargetException e) {
                // Unwrap the underlying exception
                throw e.getCause();
            }
        }
    }

    /**
     * Override this method to change the driver
     *
     * @return returns WebDriver which is set to FirefoxDriver by default - can be overwritten
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
}
