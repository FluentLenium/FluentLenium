package org.fluentlenium.core;

import org.fluentlenium.core.annotation.AjaxElement;
import org.fluentlenium.core.annotation.Page;
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


public class FluentAdapter extends Fluent {

    public FluentAdapter(WebDriver webDriver) {
        super(webDriver);
    }

    public FluentAdapter() {
        super();
    }

    protected void initTest() {
        Class cls = null;
        try {
            cls = Class.forName(this.getClass().getName());
            for (Field field : cls.getDeclaredFields()) {
                if (field.isAnnotationPresent(Page.class)) {
                    field.setAccessible(true);
                    Class clsField = field.getType();
                    cls = Class.forName(clsField.getName());
                    Object page = initClass(cls);
                    field.set(this, page);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new ConstructionException("Class " + (cls != null ? cls.getName() : " null") + "not found", e);
        } catch (IllegalAccessException e) {
            throw new ConstructionException("IllegalAccessException on class " + (cls != null ? cls.getName() : " null"), e);
        }
    }

    public <T extends FluentPage> T createPage(Class<T> classOfPage) {
        return initClass(classOfPage);
    }


    protected <T extends FluentPage> T initClass(Class<T> cls) {
        T page = null;
        try {
            Constructor construct = cls.getDeclaredConstructor();
            construct.setAccessible(true);
            page = (T) construct.newInstance();
            Class parent = Class.forName(Fluent.class.getName());
            initDriver(page, parent);
            initBaseUrl(page, parent);

            //init fields with default proxies
            Field[] fields = cls.getDeclaredFields();
            for (Field fieldFromPage : fields) {
                if (!FluentWebElement.class.isAssignableFrom(fieldFromPage.getType())) {
                    continue;
                }
                fieldFromPage.setAccessible(true);
                AjaxElement elem = fieldFromPage.getAnnotation(AjaxElement.class);
                if (elem == null) {
                    proxyElement(new DefaultElementLocatorFactory(getDriver()), page, fieldFromPage);
                } else {
                    proxyElement(new AjaxElementLocatorFactory(getDriver(), elem.timeOutInSeconds()), page, fieldFromPage);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new ConstructionException("Class " + (cls != null ? cls.getName() : " null") + "not found", e);
        } catch (IllegalAccessException e) {
            throw new ConstructionException("IllegalAccessException on class " + (cls != null ? cls.getName() : " null"), e);
        } catch (NoSuchMethodException e) {
            throw new ConstructionException("No constructor found on class " + (cls != null ? cls.getName() : " null"), e);
        } catch (InstantiationException e) {
            throw new ConstructionException("Unable to instantiate " + (cls != null ? cls.getName() : " null"), e);
        } catch (InvocationTargetException e) {
            throw new ConstructionException("Cannot invoke method setDriver on " + (cls != null ? cls.getName() : " null"), e);
        }
        return page;
    }

    private <T extends FluentPage> void initBaseUrl(T page, Class parent) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method m;
        if (getBaseUrl() != null) {
            m = parent.getDeclaredMethod("withDefaultUrl", String.class);
            m.setAccessible(true);
            m.invoke(page, getBaseUrl());
        }
    }

    private <T extends FluentPage> void initDriver(T page, Class parent) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method m = parent.getDeclaredMethod("initFluent", WebDriver.class);
        m.setAccessible(true);
        m.invoke(page, getDriver());
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
            field.set(page, new FluentWebElement(proxy));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
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

    public static void assertAt(FluentPage fluent) {
        fluent.isAt();
    }

    public void quit() {
        getDriver().quit();
    }

}
