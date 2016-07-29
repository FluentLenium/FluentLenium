package org.fluentlenium.core;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.fluentlenium.core.action.KeyboardActions;
import org.fluentlenium.core.action.MouseActions;
import org.fluentlenium.core.alert.Alert;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.events.EventsRegistry;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.page.PageInitializer;
import org.fluentlenium.core.script.FluentJavascript;
import org.fluentlenium.core.search.Search;
import org.fluentlenium.core.wait.FluentWait;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Util Class which offers some shortcut to webdriver methods
 */
public abstract class FluentDriver implements WrapsDriver, FluentControl {
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

    private MouseActions mouseActions;
    private KeyboardActions keyboardActions;

    protected FluentDriver() {
    }

    protected FluentDriver(WebDriver driver) {
        this.webDriverThreadLocal.set(driver);
        this.searchThreadLocal.set(new Search(driver));
    }

    /**
     * Define the default url that will be used in the test and in the relative pages
     *
     * @param baseUrl base URL
     * @return Fluent element
     */
    public FluentDriver withDefaultUrl(String baseUrl) {
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
    public FluentDriver withDefaultPageWait(long l, TimeUnit timeUnit) {
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
    public FluentDriver withDefaultSearchWait(long l, TimeUnit timeUnit) {
        this.getDriver().manage().timeouts().implicitlyWait(l, timeUnit);
        return this;
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

    @Override
    public void takeHtmlDump() {
        takeHtmlDump(new Date().getTime() + ".html");
    }

    @Override
    public void takeHtmlDump(String fileName) {
        File destFile = null;
        try {
            if (htmlDumpPath != null) {
                destFile = Paths.get(htmlDumpPath, fileName).toFile();
            } else {
                destFile = new File(fileName);
            }
            String html;
            synchronized (FluentDriver.class) {
                html = this.findFirst("html").html();
            }
            FileUtils.write(destFile, html, "UTF-8");
        } catch (Exception e) {
            if (destFile == null) {
                destFile = new File(fileName);
            }
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
    }

    @Override
    public void takeScreenShot() {
        takeScreenShot(new Date().getTime() + ".png");
    }

    @Override
    public void takeScreenShot(String fileName) {
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
    }

    protected FluentDriver initFluent(WebDriver driver) {
        this.webDriverThreadLocal.set(driver);
        this.searchThreadLocal.set(new Search(driver));
        if (driver instanceof EventFiringWebDriver) {
            this.events = new EventsRegistry((EventFiringWebDriver) driver);
        }
        this.mouseActions = new MouseActions(driver);
        this.keyboardActions = new KeyboardActions(driver);
        return this;
    }

    @Override
    public WebDriver getDriver() {
        return webDriverThreadLocal.get();
    }

    @Override
    public WebDriver getWrappedDriver() {
        return getDriver();
    }

    private Search getSearch() {
        return searchThreadLocal.get();
    }

    @Override
    public EventsRegistry events() {
        if (events == null) {
            throw new IllegalStateException("An EventFiringWebDriver instance is required to use events. "
                    + "Please override getDefaultDriver() to provide it.");
        }
        return events;
    }

    @Override
    public MouseActions mouse() {
        return mouseActions;
    }

    @Override
    public KeyboardActions keyboard() {
        return keyboardActions;
    }

    @Override
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

    @Override
    public FluentWait await() {
        return new FluentWait(this, getSearch());
    }

    @Override
    public String title() {
        return getDriver().getTitle();
    }

    @Override
    public Set<Cookie> getCookies() {
        return getDriver().manage().getCookies();
    }

    @Override
    public Cookie getCookie(String name) {
        return getDriver().manage().getCookieNamed(name);
    }

    @Override
    public String url() {
        String currentUrl = getDriver().getCurrentUrl();

        if (currentUrl != null && baseUrl != null && currentUrl.startsWith(baseUrl)) {
            currentUrl = currentUrl.substring(baseUrl.length());
        }

        return currentUrl;
    }

    @Override
    public String pageSource() {
        return getDriver().getPageSource();
    }

    @Override
    public <P extends FluentPage> P goTo(P page) {
        if (page == null) {
            throw new IllegalArgumentException("Page is mandatory");
        }
        page.go();
        return page;
    }

    @Override
    public void goTo(String url) {
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
    }

    @Override
    public void goToInNewTab(String url) {
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
    }

    @Override
    public FluentJavascript executeScript(String script, Object... args) {
        return new FluentJavascript((JavascriptExecutor) getDriver(), false, script, args);
    }

    @Override
    public FluentJavascript executeAsyncScript(String script, Object... args) {
        return new FluentJavascript((JavascriptExecutor) getDriver(), true, script, args);
    }

    @Override
    public FluentList<FluentWebElement> $(String selector, final Filter... filters) {
        return find(selector, filters);
    }

    @Override
    public FluentList<FluentWebElement> $(final Filter... filters) {
        return find(filters);
    }

    @Override
    public FluentList<FluentWebElement> $(By locator, final Filter... filters) {
        return find(locator, filters);
    }

    @Override
    public FluentWebElement $(String selector, Integer index, final Filter... filters) {
        return find(selector, index, filters);
    }

    @Override
    public FluentWebElement $(By locator, Integer index, final Filter... filters) {
        return find(locator, index, filters);
    }

    @Override
    public FluentWebElement $(Integer index, Filter... filters) {
        return find(index, filters);
    }

    @Override
    public FluentList<FluentWebElement> find(String selector, final Filter... filters) {
        return getSearch().find(selector, filters);
    }

    @Override
    public FluentList<FluentWebElement> find(By locator, final Filter... filters) {
        return getSearch().find(locator, filters);
    }

    @Override
    public FluentList<FluentWebElement> find(final Filter... filters) {
        return getSearch().find(filters);
    }

    @Override
    public FluentWebElement find(String selector, Integer number, final Filter... filters) {
        return getSearch().find(selector, number, filters);
    }

    @Override
    public FluentWebElement find(By locator, Integer index, final Filter... filters) {
        return getSearch().find(locator, index, filters);
    }

    @Override
    public FluentWebElement find(Integer index, final Filter... filters) {
        return getSearch().find(index, filters);
    }

    @Override
    public FluentWebElement findFirst(String selector, final Filter... filters) {
        return getSearch().findFirst(selector, filters);
    }

    @Override
    public FluentWebElement findFirst(final Filter... filters) {
        return getSearch().findFirst(filters);
    }

    @Override
    public FluentWebElement findFirst(By locator, final Filter... filters) {
        return getSearch().findFirst(locator, filters);
    }

    @Override
    public void switchTo(FluentWebElement element) {
        if (null == element || !"iframe".equals(element.getTagName())) {
            getDriver().switchTo().defaultContent();
        } else {
            getDriver().switchTo().frame(element.getElement());
        }
    }

    @Override
    public void switchTo() {
        this.switchTo(null);
    }

    @Override
    public void switchToDefault() {
        this.switchTo(null);
    }

    @Override
    public Alert alert() {
        return new Alert(getDriver());
    }

    @Override
    public void maximizeWindow() {
        getDriver().manage().window().maximize();
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
