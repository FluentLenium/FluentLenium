package org.fluentlenium.core;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.exception.ConstructionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.*;


public class FluentAdapter extends Fluent {

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
            Method m = parent.getDeclaredMethod("setDriver", WebDriver.class);
            m.setAccessible(true);
            m.invoke(page, getDriver());

            //init fields with default proxies
            Field[] fields = cls.getDeclaredFields();
            for (Field fieldFromPage : fields) {
                if (!FluentWebElement.class.isAssignableFrom(fieldFromPage.getType())) {
                    continue;
                }
                fieldFromPage.setAccessible(true);
                proxyElement(new DefaultElementLocatorFactory(getDriver()), page, fieldFromPage);
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

       public WebDriverWait getDefaultWait() {
           return new WebDriverWait(getDefaultDriver(), 30);
       }


       public static void assertAt(FluentPage fluent) {
             fluent.isAt();
         }

}
