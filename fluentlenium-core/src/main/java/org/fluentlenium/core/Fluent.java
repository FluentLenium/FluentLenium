package org.fluentlenium.core;

import org.apache.commons.io.FileUtils;
import org.fluentlenium.core.action.FillConstructor;
import org.fluentlenium.core.action.FillSelectConstructor;
import org.fluentlenium.core.action.FluentDefaultActions;
import org.fluentlenium.core.annotation.AjaxElement;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentJavascript;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.events.EventsRegistry;
import org.fluentlenium.core.exception.ConstructionException;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.search.Search;
import org.fluentlenium.core.search.SearchActions;
import org.fluentlenium.core.wait.FluentWait;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.URI;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Util Class which offers some shortcut to webdriver methods
 */
public abstract class Fluent implements SearchActions<FluentWebElement> {
    private String baseUrl;

    private ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<WebDriver>();
    private ThreadLocal<Search> searchThreadLocal = new ThreadLocal<Search>();

    private EventsRegistry events = null;

    public Fluent(WebDriver driver) {
        this.webDriverThreadLocal.set(driver);
        this.searchThreadLocal.set(new Search(driver));
        FluentThread.set(this);
    }

    /**
     * Define the default url that will be used in the test and in the relative pages
     *
     * @param baseUrl base URL
     * @return Fluent element
     */
    public Fluent withDefaultUrl(String baseUrl) {
        if (baseUrl != null) {
            if (baseUrl.endsWith("/")) {
                baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
            }
            this.baseUrl = baseUrl;
        }
        return this;
    }

    /**
     * Define an implicit time to wait for a page to be loaded
     *
     * @param l        timeout value
     * @param timeUnit time unit for wait
     * @return Fluent element
     */
    public Fluent withDefaultPageWait(long l, TimeUnit timeUnit) {
        this.getDriver().manage().timeouts().pageLoadTimeout(l, timeUnit);
        return this;
    }

    /**
     * Define an implicit time to wait when searching an element
     *
     * @param l        timeout value
     * @param timeUnit time unit for wait
     * @return Fluent element
     */
    public Fluent withDefaultSearchWait(long l, TimeUnit timeUnit) {
        this.getDriver().manage().timeouts().implicitlyWait(l, timeUnit);
        return this;
    }

    public Fluent() {
        FluentThread.set(this);
    }

    /**
     * Take a snapshot of the browser. By default the file will be a png named by the current timestamp.
     *
     * @return fluent object
     */
    public Fluent takeScreenShot() {
        takeScreenShot(new Date().getTime() + ".png");
        return this;
    }

    /**
     * Take a snapshot of the browser into a file given by the fileName param.
     *
     * @param fileName file name for screenshot
     * @return fluent object
     */
    public Fluent takeScreenShot(String fileName) {
        if (!(getDriver() instanceof TakesScreenshot)) {
            throw new WebDriverException("Current browser doesn't allow taking screenshot.");
        }
        File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            File destFile = new File(fileName);
            FileUtils.copyFile(scrFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error when taking the snapshot", e);
        }
        return this;
    }

    protected Fluent initFluent(WebDriver driver) {
        this.webDriverThreadLocal.set(driver);
        this.searchThreadLocal.set(new Search(driver));
        if (driver instanceof EventFiringWebDriver) {
            this.events = new EventsRegistry((EventFiringWebDriver) driver);
        }
        return this;
    }

    public WebDriver getDriver() {
        return webDriverThreadLocal.get();
    }

    public Search getSearch() {
        return searchThreadLocal.get();
    }

    public EventsRegistry events() {
        if (events == null) {
            throw new IllegalStateException("An EventFiringWebDriver instance is required to use events. " +
                    "Please override getDefaultDriver() to provide it.");
        }
        return events;
    }

    protected void initTest() {
        Class cls = null;
        try {
            for (cls = Class.forName(this.getClass().getName()); FluentAdapter.class.isAssignableFrom(cls); cls = cls.getSuperclass()) {
                for (Field field : cls.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Page.class)) {
                        field.setAccessible(true);
                        Class clsField = field.getType();
                        Class clsPage = Class.forName(clsField.getName());
                        Object page = initClass(clsPage);
                        field.set(this, page);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            throw new ConstructionException("Class " + (cls != null ? cls.getName() : " null") + "not found", e);
        } catch (IllegalAccessException e) {
            throw new ConstructionException("IllegalAccessException on class " + (cls != null ? cls.getName() : " null"), e);
        }
    }

    public <T extends FluentPage> T createPage(Class<T> classOfPage, Object... params) {
        return initClass(classOfPage, params);
    }


    protected <T extends FluentPage> T initClass(Class<T> cls, Object... params) {
        T page = null;
        try {
            page = constructPageWithParams(cls, params);
            Class parent = Class.forName(Fluent.class.getName());
            initDriver(page, parent);
            initBaseUrl(page, parent);

            //init fields (including superclass fields) with default proxies
            Field[] fields = getAllFields(cls);
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

    protected <T extends FluentPage> T constructPageWithParams(Class<T> cls, Object[] params) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
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
                throw new ConstructionException(
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
        WebElement proxy = (WebElement) java.lang.reflect.Proxy.newProxyInstance(
                page.getClass().getClassLoader(), new Class[]{WebElement.class}, handler);
        try {
            field.setAccessible(true);
            field.set(page, new FluentWebElement(proxy));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the base URL to use when visiting relative URLs, if one is configured
     *
     * @return The base URL, or null if none configured
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * wait for an asynchronous call
     *
     * @return FluentWait element
     */
    public FluentWait await() {
        return new FluentWait(this, getSearch());
    }


    /**
     * Return the title of the page
     *
     * @return browser window title
     */
    public String title() {
        return getDriver().getTitle();
    }

    /**
     * return the cookies as a set
     *
     * @return set of cookies
     */
    public Set<Cookie> getCookies() {
        return getDriver().manage().getCookies();
    }

    /**
     * return the corresponding cookie given a name
     *
     * @param name cookie name
     * @return cookie selected by name
     */
    public Cookie getCookie(String name) {
        return getDriver().manage().getCookieNamed(name);
    }

    /**
     * Return the url of the page. If a base url is provided, the current url will be relative to that base url.
     *
     * @return current URL
     */
    public String url() {
        String currentUrl = getDriver().getCurrentUrl();

        if (currentUrl != null && baseUrl != null && currentUrl.startsWith(baseUrl)) {
            currentUrl = currentUrl.substring(baseUrl.length());
        }

        return currentUrl;
    }

    /**
     * Return the source of the page
     *
     * @return source of the page under test
     */
    public String pageSource() {
        return getDriver().getPageSource();
    }


    public <P extends FluentPage> P goTo(P page) {
        if (page == null) {
            throw new IllegalArgumentException("Page is mandatory");
        }
        page.go();
        return page;
    }

    /**
     * Open the url page
     *
     * @param url page URL to visit
     * @return fluent object
     */
    public Fluent goTo(String url) {
        if (url == null) {
            throw new IllegalArgumentException("Url is mandatory");
        }
        if (baseUrl != null) {
            URI uri = URI.create(url);
            if (!uri.isAbsolute()) {
                url = baseUrl + url;
            }
        }
        getDriver().get(url);
        return this;
    }


    public FluentJavascript executeScript(String script, Object... args) {
        return new FluentJavascript(this.getDriver(), false, script, args);
    }

    public FluentJavascript executeAsyncScript(String script, Object... args) {
        return new FluentJavascript(this.getDriver(), true, script, args);
    }


    /**
     * Central methods to find elements on the page. Can provide some filters. Able to use css1, css2, css3, see WebDriver  restrictions
     *
     * @param name    item selector
     * @param filters set of filters
     * @return list of fluent elements
     */
    public FluentList<FluentWebElement> $(String name, final Filter... filters) {
        return getSearch().find(name, filters);
    }

    /**
     * Central methods to find elements on the page with filters.
     *
     * @param filters set of filters in the current context
     * @return list of fluent elements
     */
    public FluentList<FluentWebElement> $(final Filter... filters) {
        return getSearch().find(filters);
    }

    /**
     * Central methods a find element on the page, the number indicate the index of the desired element on the list.
     * Can provide some filters. Able to use css1, css2, css3, see WebDriver  restrictions
     *
     * @param name    item selector
     * @param number  index of the desired element
     * @param filters set of filters in the current context
     * @return fluent web element
     */
    public FluentWebElement $(String name, Integer number, final Filter... filters) {
        return getSearch().find(name, number, filters);
    }

    /**
     * Central method to find an element on the page with filters.
     * The number indicates the index of the desired element on the list.
     *
     * @param number  index of element from obtained list
     * @param filters set of filters in the current context
     * @return fluent web element
     */
    public FluentWebElement $(Integer number, final Filter... filters) {
        return getSearch().find(number, filters);
    }

    /**
     * return the lists corresponding to the cssSelector with it filters
     *
     * @param name    cssSelector
     * @param filters set of filters in current context
     * @return list of fluent web elements
     */
    public FluentList<FluentWebElement> find(String name, final Filter... filters) {
        return getSearch().find(name, filters);
    }

    /**
     * Return the list filtered by the specified filters.
     *
     * @param filters set of filters in the current context
     * @return list of fluent web elements
     */
    public FluentList<FluentWebElement> find(final Filter... filters) {
        return getSearch().find(filters);
    }

    /**
     * Return the elements at the number position into the lists corresponding to the cssSelector with it filters
     *
     * @param name    cssSelector
     * @param number  index in the retrieved items list
     * @param filters set of filters in the current context
     * @return fluent web element
     */
    public FluentWebElement find(String name, Integer number, final Filter... filters) {
        return getSearch().find(name, number, filters);
    }

    /**
     * Return the element at the number position in the list filtered by the specified filters.
     *
     * @param number  index in the retrieved items list
     * @param filters set of filters in the current context
     * @return fluent web element
     */
    public FluentWebElement find(Integer number, final Filter... filters) {
        return getSearch().find(number, filters);
    }

    /**
     * Return the first element corresponding to the name and the filters
     *
     * @param name    cssSelector
     * @param filters set of filters in the current context
     * @return fluent web element
     */
    public FluentWebElement findFirst(String name, final Filter... filters) {
        return getSearch().findFirst(name, filters);
    }

    /**
     * Return the first element corresponding to the filters.
     *
     * @param filters set of filters in the current context
     * @return fluent web element
     */
    public FluentWebElement findFirst(final Filter... filters) {
        return getSearch().findFirst(filters);
    }

    /**
     * Construct a FillConstructor in order to allow easy fill
     * Be careful - only the visible elements are filled
     *
     * @param cssSelector cssSelector
     * @param filters     set of filters in the current context
     * @return fill constructor
     */
    public FillConstructor fill(String cssSelector, Filter... filters) {
        return new FillConstructor(cssSelector, getDriver(), filters);
    }

    /**
     * Construct a FillConstructor with filters in order to allow easy fill.
     * Be careful - only the visible elements are filled
     *
     * @param filters set of filters in the current context
     * @return fill constructor
     */
    public FillConstructor fill(Filter... filters) {
        return new FillConstructor(getDriver(), filters);
    }

    /**
     * Construct a FillConstructor in order to allow easy fill
     * Be careful - only the visible elements are filled
     *
     * @param list    FluentDefaultActions list
     * @param filters set of filters in the current context
     * @return fill constructor
     */
    public FillConstructor fill(FluentDefaultActions list, Filter... filters) {
        return new FillConstructor(list, getDriver(), filters);
    }

    /**
     * Construct a FillSelectConstructor in order to allow easy fill
     * Be careful - only the visible elements are filled
     *
     * @param cssSelector cssSelector
     * @param filters     set of filters in the current context
     * @return fill constructor
     */
    public FillSelectConstructor fillSelect(String cssSelector, Filter... filters) {
        return new FillSelectConstructor(cssSelector, getDriver(), filters);
    }

    /**
     * Construct a FillSelectConstructor with filters in order to allow easy fill.
     * Be careful - only the visible elements are filled
     *
     * @param filters set of filters in the current context
     * @return fill constructor
     */
    public FillSelectConstructor fillSelect(Filter... filters) {
        return new FillSelectConstructor(getDriver(), filters);
    }

    /**
     * click all elements that are in cssSelector with its filters
     * Be careful - only the visible elements are clicked
     *
     * @param cssSelector cssSelector
     * @param filters     set of filters in the current context
     * @return fluent object
     */
    public Fluent click(String cssSelector, Filter... filters) {
        $(cssSelector, filters).click();
        return this;
    }

    /**
     * Click all elements filtered by the specified filters.
     * Be careful - only the visible elements are clicked
     *
     * @param filters set of filters in the current context
     * @return fluent object
     */
    public Fluent click(Filter... filters) {
        $(filters).click();
        return this;
    }

    /**
     * Submit all elements that are in cssSelector with its filters
     * Be careful - only the visible elements are cleared
     *
     * @param cssSelector cssSelector
     * @param filters     set of filters in the current context
     * @return fluent object
     */
    public Fluent clear(String cssSelector, Filter... filters) {
        $(cssSelector, filters).clear();
        return this;
    }

    /**
     * Clear texts of the all elements filtered by the specified filters.
     * Be careful - only the visible elements are cleared
     *
     * @param filters set of filters in the current context
     * @return fluent object
     */
    public Fluent clear(Filter... filters) {
        $(filters).clear();
        return this;
    }

    /**
     * Submit all elements that are in cssSelector with its filters
     * Be careful - only the visible elements are submitted
     *
     * @param cssSelector cssSelector
     * @param filters     set of filters in the current context
     * @return fluent object
     */
    public Fluent submit(String cssSelector, Filter... filters) {
        $(cssSelector, filters).submit();
        return this;
    }

    /**
     * Submit all elements filtered by the specified filters.
     * Be careful - only the visible elements are submitted
     *
     * @param filters set of filters in the current context
     * @return fluent object
     */
    public Fluent submit(Filter... filters) {
        $(filters).submit();
        return this;
    }

    /**
     * get a list all elements that are in cssSelector with its filters
     * Be careful - only the visible elements are submitted
     * //TODO UTILITY ? Deprecated ?
     *
     * @param cssSelector cssSelector
     * @param filters     set of filters in the current context
     * @return list of strings
     */
    public List<String> text(String cssSelector, Filter... filters) {
        return $(cssSelector, filters).getTexts();
    }

    /**
     * Get all texts of the elements filtered by the specified filters.
     * Be careful - only the visible elements are submitted
     * //TODO UTILITY ? Deprecated ?
     *
     * @param filters set of filters in the current context
     * @return list of strings
     */
    public List<String> text(Filter... filters) {
        return $(filters).getTexts();
    }

    /**
     * Value all elements that are in cssSelector with its filters
     * Be careful - only the visible elements are returned
     * //TODO UTILITY ? Deprecated ?
     *
     * @param cssSelector cssSelector
     * @param filters     set of filters in the current context
     * @return list of strings
     */
    public List<String> value(String cssSelector, Filter... filters) {
        return $(cssSelector, filters).getValues();
    }

    /**
     * Get all values of the elements filtered by the specified filters.
     * Be careful - only the visible elements are submitted
     * //TODO UTILITY ? Deprecated ?
     *
     * @param filters set of filters in the current context
     * @return list of strings
     */
    public List<String> value(Filter... filters) {
        return $(filters).getValues();
    }

    /**
     * click all elements that are in the list
     * Be careful - only the visible elements are clicked
     *
     * @param fluentObject fluent object to click
     * @return fluent object
     */
    public Fluent click(FluentDefaultActions fluentObject) {
        fluentObject.click();
        return this;
    }

    /**
     * Clear all elements that are in the list
     * Be careful - only the visible elements are cleared
     *
     * @param fluentObject list of fluent web elements to clear
     * @return fluent object
     */
    public Fluent clear(FluentList<FluentWebElement> fluentObject) {
        fluentObject.clear();
        return this;
    }

    /**
     * Clear the given parameters elements that are in the list
     * Be careful - only the visible elements are cleared
     *
     * @param fluentObject fluent web element to clear
     * @return fluent object
     */
    public Fluent clear(FluentWebElement fluentObject) {
        fluentObject.clear();
        return this;
    }

    /**
     * Submit all elements that are in the list
     * Be careful - only the visible elements are submitted
     *
     * @param fluentObject fluent default actions
     * @return fluent object
     */
    public Fluent submit(FluentDefaultActions fluentObject) {
        fluentObject.submit();
        return this;
    }

    /**
     * Switch to the selected Element (if element is null or not an iframe, or haven't an id then
     * switch to the default)
     *
     * @param element fluent web element
     * @return fluent object
     */
    public Fluent switchTo(FluentWebElement element) {
        if (null == element || !"iframe".equals(element.getTagName())) {
            getDriver().switchTo().defaultContent();
        } else {
            getDriver().switchTo().frame(element.getElement());
        }
        return this;
    }

    /**
     * Switch to the default element
     *
     * @return fluent object
     */
    public Fluent switchTo() {
        this.switchTo(null);
        return this;
    }


    /**
     * Switch to the default element
     *
     * @return fluent object
     */
    public Fluent switchToDefault() {
        this.switchTo(null);
        return this;
    }

    public Alert alert() {
        return new Alert(getDriver());
    }

    /**
     * Maximize browser window using webdriver
     *
     * @return fluent object
     */
    public Fluent maximizeWindow() {
        getDriver().manage().window().maximize();
        return this;
    }

    public void quit() {
        if (getDriver() != null) {
            getDriver().quit();
        }
        cleanupDriver();
    }

    public void cleanupDriver() {
        webDriverThreadLocal.remove();
        searchThreadLocal.remove();
    }
}
