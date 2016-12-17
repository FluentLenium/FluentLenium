package org.fluentlenium.core;

import lombok.experimental.Delegate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.fluentlenium.configuration.Configuration;
import org.fluentlenium.core.action.KeyboardActions;
import org.fluentlenium.core.action.MouseActions;
import org.fluentlenium.core.action.WindowAction;
import org.fluentlenium.core.alert.Alert;
import org.fluentlenium.core.alert.AlertImpl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.components.ComponentsManager;
import org.fluentlenium.core.css.CssControl;
import org.fluentlenium.core.css.CssControlImpl;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.events.ComponentsEventsRegistry;
import org.fluentlenium.core.events.EventsRegistry;
import org.fluentlenium.core.inject.DefaultContainerInstanciator;
import org.fluentlenium.core.inject.FluentInjector;
import org.fluentlenium.core.script.FluentJavascript;
import org.fluentlenium.core.search.Search;
import org.fluentlenium.core.search.SearchFilter;
import org.fluentlenium.core.wait.FluentWait;
import org.fluentlenium.utils.UrlUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Util Class which offers some shortcut to webdriver methods
 */
@SuppressWarnings("PMD.GodClass")
public class FluentDriver implements FluentControl { // NOPMD GodClass
    @Delegate
    private final Configuration configuration;

    @Delegate(types = ComponentInstantiator.class)
    private final ComponentsManager componentsManager;

    private final EventsRegistry events;

    private final ComponentsEventsRegistry componentsEventsRegistry;

    @Delegate
    private final FluentInjector fluentInjector;

    @Delegate
    private final CssControl cssControl; // NOPMD UnusedPrivateField

    private final Search search;

    private final WebDriver driver;

    private final MouseActions mouseActions;

    private final KeyboardActions keyboardActions;

    private final WindowAction windowAction;

    /**
     * Wrap the driver into a Fluent driver.
     *
     * @param driver        underlying selenium driver
     * @param configuration configuration
     * @param adapter       adapter fluent control interface
     */
    public FluentDriver(WebDriver driver, Configuration configuration, FluentControl adapter) {
        this.configuration = configuration;
        componentsManager = new ComponentsManager(adapter);
        this.driver = driver;
        search = new Search(driver, this, componentsManager, adapter);
        if (driver instanceof EventFiringWebDriver) {
            events = new EventsRegistry(this);
            componentsEventsRegistry = new ComponentsEventsRegistry(events, componentsManager);
        } else {
            events = null;
            componentsEventsRegistry = null;
        }
        mouseActions = new MouseActions(driver);
        keyboardActions = new KeyboardActions(driver);
        fluentInjector = new FluentInjector(adapter, events, componentsManager, new DefaultContainerInstanciator(this));
        cssControl = new CssControlImpl(adapter, adapter);
        windowAction = new WindowAction(adapter, componentsManager.getInstantiator(), driver);

        configureDriver(); // NOPMD ConstructorCallsOverridableMethod
    }

    private void configureDriver() {
        if (getDriver() != null && getDriver().manage() != null && getDriver().manage().timeouts() != null) {
            if (configuration.getPageLoadTimeout() != null) {
                getDriver().manage().timeouts()
                        .pageLoadTimeout(configuration.getPageLoadTimeout(), TimeUnit.MILLISECONDS);
            }

            if (configuration.getImplicitlyWait() != null) {
                getDriver().manage().timeouts()
                        .implicitlyWait(configuration.getImplicitlyWait(), TimeUnit.MILLISECONDS);
            }

            if (configuration.getScriptTimeout() != null) {
                getDriver().manage().timeouts()
                        .setScriptTimeout(configuration.getScriptTimeout(), TimeUnit.MILLISECONDS);
            }
        }
    }

    @Override
    public void takeHtmlDump() {
        takeHtmlDump(new Date().getTime() + ".html");
    }

    @Override
    public void takeHtmlDump(String fileName) {
        File destFile = null;
        try {
            if (configuration.getHtmlDumpPath() == null) {
                destFile = new File(fileName);
            } else {
                destFile = Paths.get(configuration.getHtmlDumpPath(), fileName).toFile();
            }
            String html;
            synchronized (FluentDriver.class) {
                html = $("html").first().html();
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
                throw new RuntimeException("error when dumping HTML", e); //NOPMD PreserveStackTrace
            }
        }
    }

    @Override
    public boolean canTakeScreenShot() {
        return getDriver() instanceof TakesScreenshot;
    }

    @Override
    public void takeScreenShot() {
        takeScreenShot(new Date().getTime() + ".png");
    }

    @Override
    public void takeScreenShot(String fileName) {
        if (!canTakeScreenShot()) {
            throw new WebDriverException("Current browser doesn't allow taking screenshot.");
        }
        File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            File destFile;
            if (configuration.getScreenshotPath() == null) {
                destFile = new File(fileName);
            } else {
                destFile = Paths.get(configuration.getScreenshotPath(), fileName).toFile();
            }
            FileUtils.copyFile(scrFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error when taking the snapshot", e);
        }
        FileUtils.deleteQuietly(scrFile);
    }

    @Override
    public WebDriver getDriver() {
        return driver;
    }

    private Search getSearch() {
        return search;
    }

    @Override
    public EventsRegistry events() {
        if (events == null) {
            throw new IllegalStateException("An EventFiringWebDriver instance is required to use events. "
                    + "You should set 'eventsEnabled' configuration property to 'true' "
                    + "or override newWebDriver() to build an EventFiringWebDriver.");
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
    public WindowAction window() {
        return windowAction;
    }

    @Override
    public FluentWait await() {
        FluentWait fluentWait = new FluentWait(this);
        Long atMost = configuration.getAwaitAtMost();
        if (atMost != null) {
            fluentWait.atMost(atMost);
        }
        Long pollingEvery = configuration.getAwaitPollingEvery();
        if (pollingEvery != null) {
            fluentWait.pollingEvery(pollingEvery);
        }
        return fluentWait;
    }

    @Override
    public Set<Cookie> getCookies() {
        return getDriver().manage().getCookies();
    }

    @Override
    public Cookie getCookie(String name) {
        return getDriver().manage().getCookieNamed(name);
    }

    private String buildUrl(String url) {
        String currentUrl = getDriver().getCurrentUrl();
        String baseUrl = UrlUtils.sanitizeBaseUrl(getBaseUrl(), currentUrl);

        return UrlUtils.concat(baseUrl, url);
    }

    @Override
    public String url() {
        String baseUrl = buildUrl(null);

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

        getDriver().get(buildUrl(url));
    }

    @Override
    public void goToInNewTab(String url) {
        if (url == null) {
            throw new IllegalArgumentException("Url is mandatory");
        }

        String newTab;
        synchronized (getClass()) {
            Set<String> initialTabs = getDriver().getWindowHandles();
            executeScript("window.open('" + buildUrl(url) + "', '_blank');");
            Set<String> tabs = getDriver().getWindowHandles();
            tabs.removeAll(initialTabs);
            newTab = tabs.iterator().next();
        }

        getDriver().switchTo().window(newTab);
    }

    @Override
    public Capabilities capabilities() {
        WebDriver currentDriver = getDriver();
        Capabilities capabilities = currentDriver instanceof HasCapabilities
                ? ((HasCapabilities) currentDriver).getCapabilities()
                : null;
        while (currentDriver instanceof WrapsDriver && capabilities == null) {
            currentDriver = ((WrapsDriver) currentDriver).getWrappedDriver();
            capabilities = currentDriver instanceof HasCapabilities ? ((HasCapabilities) currentDriver).getCapabilities() : null;
        }
        return capabilities;
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
    public FluentList<FluentWebElement> $(String selector, SearchFilter... filters) {
        return find(selector, filters);
    }

    @Override
    public FluentWebElement el(String selector, SearchFilter... filters) {
        return find(selector, filters).first();
    }

    @Override
    public FluentList<FluentWebElement> $(SearchFilter... filters) {
        return find(filters);
    }

    @Override
    public FluentWebElement el(SearchFilter... filters) {
        return find(filters).first();
    }

    @Override
    public FluentList<FluentWebElement> $(By locator, SearchFilter... filters) {
        return find(locator, filters);
    }

    @Override
    public FluentWebElement el(By locator, SearchFilter... filters) {
        return find(locator, filters).first();
    }

    @Override
    public FluentList<FluentWebElement> find(String selector, SearchFilter... filters) {
        return getSearch().find(selector, filters);
    }

    @Override
    public FluentList<FluentWebElement> find(By locator, SearchFilter... filters) {
        return getSearch().find(locator, filters);
    }

    @Override
    public FluentList<FluentWebElement> find(SearchFilter... filters) {
        return getSearch().find(filters);
    }

    @Override
    public FluentList<FluentWebElement> find(List<WebElement> rawElements) {
        return getSearch().find(rawElements);
    }

    @Override
    public FluentList<FluentWebElement> $(List<WebElement> rawElements) {
        return getSearch().$(rawElements);
    }

    @Override
    public FluentWebElement el(WebElement rawElement) {
        return getSearch().el(rawElement);
    }

    @Override
    public void switchTo(FluentList<? extends FluentWebElement> elements) {
        switchTo(elements.first());
    }

    @Override
    public void switchTo(FluentWebElement element) {
        if (null == element || !"iframe".equals(element.tagName())) {
            getDriver().switchTo().defaultContent();
        } else {
            WebElement target = element.getElement();
            while (target instanceof WrapsElement && target != ((WrapsElement) target).getWrappedElement()) {
                target = ((WrapsElement) target).getWrappedElement();
            }
            getDriver().switchTo().frame(target);
        }
    }

    @Override
    public void switchTo() {
        switchTo((FluentWebElement) null);
    }

    @Override
    public void switchToDefault() {
        switchTo((FluentWebElement) null);
    }

    @Override
    public Alert alert() {
        return new AlertImpl(getDriver());
    }

    /**
     * Quit the underlying web driver and release fluent driver resources.
     */
    public void quit() {
        if (getDriver() != null) {
            getDriver().quit();
        }
        releaseFluent();
    }

    /**
     * Release fluent driver resources.
     */
    public void releaseFluent() {
        fluentInjector.release();
        if (componentsEventsRegistry != null) {
            componentsEventsRegistry.close();
        }
    }
}
