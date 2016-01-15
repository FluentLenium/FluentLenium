package org.fluentlenium.core.page;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentAdapter;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.AjaxElement;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Handle initialization of @AjaxElement proxies @Page objects in pages and adapters.
 */
public class PageInitializer {

    private final ConcurrentMap<Class, FluentPage> pageInstances = new ConcurrentHashMap<Class, FluentPage>();

    private final Fluent fluent;

    public PageInitializer(Fluent fluent) {
        this.fluent = fluent;
    }

    /**
     * Release FluentPages loaded.
     */
    public void release() {
        pageInstances.clear();
    }

    /**
     * Creates a new page from it's class name and constructor parameters.
     *
     * @param cls Page class
     *
     * @param params
     * @param <T>
     * @return
     */
    public <T extends FluentPage> T createPage(Class<T> cls, Object... params) {
        T container = initClass(cls, params);
        try {
            initContainer(container);
        } catch (ClassNotFoundException e) {
            throw new PageInitializerException("Class " + (cls != null ? cls.getName() : " null") + "not found", e);
        } catch (IllegalAccessException e) {
            throw new PageInitializerException("IllegalAccessException on class " + (cls != null ? cls.getName() : " null"), e);
        }
        return container;
    }

    /**
     * Initialize a page or an adapter.
     *
     * @param container FluentPage or FluentAdapter object.
     *
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public void initContainer(Fluent container) throws IllegalAccessException, ClassNotFoundException {
        injectPageIntoContainer(container);
        initFluentWebElements(container);
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
                        initContainer(page);
                    }
                }
            }
        }
    }

    private <T extends FluentPage> T initClass(Class<T> cls, Object... params) {
        try {
            T page = constructPageWithParams(cls, params);
            initClass(page, params);
            return page;
        } catch (IllegalAccessException e) {
            throw new PageInitializerException("IllegalAccessException on class " + (cls != null ? cls.getName() : " null"), e);
        } catch (NoSuchMethodException e) {
            throw new PageInitializerException("No constructor found on class " + (cls != null ? cls.getName() : " null"), e);
        } catch (InstantiationException e) {
            throw new PageInitializerException("Unable to instantiate " + (cls != null ? cls.getName() : " null"), e);
        } catch (InvocationTargetException e) {
            throw new PageInitializerException("Cannot invoke method setDriver on " + (cls != null ? cls.getName() : " null"), e);
        }
    }

    private <T extends FluentPage> T initClass(T page, Object... params) {
        try {
            Class parent = Fluent.class;
            initDriver(page, parent);
            initBaseUrl(page, parent);

            return page;
        } catch (IllegalAccessException e) {
            throw new PageInitializerException("IllegalAccessException on class " + page.getClass().getName(), e);
        } catch (NoSuchMethodException e) {
            throw new PageInitializerException("No constructor found on class " + page.getClass().getName(), e);
        } catch (InvocationTargetException e) {
            throw new PageInitializerException("Cannot invoke method setDriver on " + page.getClass().getName(), e);
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
                        proxyElement(new DefaultElementLocatorFactory(this.fluent.getDriver()), page, fieldFromPage);
                    } else {
                        proxyElement(new AjaxElementLocatorFactory(this.fluent.getDriver(), elem.timeOutInSeconds()), page, fieldFromPage);
                    }
                }
            }
        }
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

    private <T extends FluentPage> T constructPageWithParams(Class<T> cls, Object[] params) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        T page;
        Class<?>[] classTypes = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            classTypes[i] = params[i].getClass();
        }

        try {
            Constructor<T> construct = cls.getDeclaredConstructor(classTypes);

            construct.setAccessible(true);
            page = construct.newInstance(params);
            return page;
        } catch (NoSuchMethodException ex) {
            if (params.length != 0) {
                throw new PageInitializerException(
                        "You provided the wrong arguments to the createPage method, " +
                                "if you just want to use a page with a default constructor, use @Page or createPage(" + cls.getSimpleName() + ".class)", ex);
            } else {
                throw ex;
            }
        }
    }

    private static Field[] getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<Field>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields.toArray(new Field[0]);
    }

    private <T extends FluentPage> void initBaseUrl(T page, Class<?> parent) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (this.fluent.getBaseUrl() == null) {
            return;
        }

        Method m = parent.getDeclaredMethod("withDefaultUrl", String.class);
        m.setAccessible(true);
        m.invoke(page, this.fluent.getBaseUrl());
    }

    private <T extends FluentPage> void initDriver(T page, Class<?> parent) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method m = parent.getDeclaredMethod("initFluent", WebDriver.class);
        m.setAccessible(true);
        m.invoke(page, this.fluent.getDriver());
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
}
