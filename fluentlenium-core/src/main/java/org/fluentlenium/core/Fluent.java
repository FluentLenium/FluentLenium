package org.fluentlenium.core;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.fluentlenium.core.action.FillConstructor;
import org.fluentlenium.core.action.FillSelectConstructor;
import org.fluentlenium.core.action.FluentDefaultActions;
import org.fluentlenium.core.domain.FluentJavascript;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.events.EventsRegistry;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.page.PageInitializer;
import org.fluentlenium.core.search.Search;
import org.fluentlenium.core.search.SearchActions;
import org.fluentlenium.core.wait.FluentWait;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 * Util Class which offers some shortcut to webdriver methods
 */
public abstract class Fluent implements SearchActions<FluentWebElement> {
    protected enum TriggerMode {ON_FAIL, NEVER}

    private String baseUrl;

    private ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<WebDriver>();
    private ThreadLocal<Search> searchThreadLocal = new ThreadLocal<Search>();

    private String htmlDumpPath;
    private String screenshotPath;

    protected TriggerMode screenshotMode = TriggerMode.NEVER;
    protected TriggerMode htmlDumpMode = TriggerMode.NEVER;

    private EventsRegistry events = null;

    protected PageInitializer pageInitializer = new PageInitializer(this);

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


    public void setScreenshotPath(String path) {
        this.screenshotPath = path;
    }

    public void setHtmlDumpPath(String htmlDumpPath) {
        this.htmlDumpPath = htmlDumpPath;
    }

    public void setScreenshotMode(TriggerMode mode) {
        this.screenshotMode = mode;
    }

    public void setHtmlDumpMode(TriggerMode htmlDumpMode) {
        this.htmlDumpMode = htmlDumpMode;
    }

    /**
     * Take a html dump of the browser DOM. By default the file will be a html named by the current
     * timestamp.
     *
     * @return fluent object
     */
    public Fluent takeHtmlDump() {
        return takeHtmlDump(new Date().getTime() + ".html");
    }

    /**
     * Take a html dump of the browser DOM into a file given by the fileName param.
     *
     * @param fileName
     *            file name for html dump
     * @return fluent object
     */
    public Fluent takeHtmlDump(String fileName) {
        try {
            String html;
            synchronized (Fluent.class) {
                html = this.findFirst("html").html();
            }
            File destFile;
            if (htmlDumpPath != null) {
                destFile = Paths.get(htmlDumpPath, fileName).toFile();
            } else {
                destFile = new File(fileName);
            }
            FileUtils.write(destFile, html, "UTF-8");
        } catch (Exception e) {
            File destFile = new File(fileName);
            try {
                PrintWriter printWriter = new PrintWriter(destFile, "UTF-8");
                printWriter.write("Can't dump HTML");
                printWriter.println();
                e.printStackTrace(printWriter);
                IOUtils.closeQuietly(printWriter);
            } catch (IOException e1) {
                throw new RuntimeException("error when dumping HTML", e);
            }
        }
        return this;
    }

    /**
     * Take a snapshot of the browser. By default the file will be a png named by the current timestamp.
     *
     * @return fluent object
     */
    public Fluent takeScreenShot() {
        return takeScreenShot(new Date().getTime() + ".png");
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
            File destFile;
            if (screenshotPath != null) {
                destFile = Paths.get(screenshotPath, fileName).toFile();
            } else {
                destFile = new File(fileName);
            }
            FileUtils.copyFile(scrFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error when taking the snapshot", e);
        }
        FileUtils.deleteQuietly(scrFile);
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

    public <T extends FluentPage> T createPage(Class<T> cls, Object... params) {
        return pageInitializer.createPage(cls, params);
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

    /**
     * Open the url page in a new tab
     *
     * @param url
     */
    public Fluent goToInNewTab(String url) {
        if (url == null) {
            throw new IllegalArgumentException("Url is mandatory");
        }

        if (baseUrl != null) {
            URI uri = URI.create(url);
            if (!uri.isAbsolute()) {
                url = baseUrl + url;
            }
        }

        String newTab;
        synchronized (getClass()) {
            Set<String> initialTabs = getDriver().getWindowHandles();
            executeScript("window.open('" + url + "', '_blank');");
            Set<String> tabs = getDriver().getWindowHandles();
            tabs.removeAll(initialTabs);
            newTab = tabs.iterator().next();
        }

        getDriver().switchTo().window(newTab);

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
