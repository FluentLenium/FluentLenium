package org.fluentlenium.core;

import lombok.experimental.Delegate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.core.action.KeyboardActions;
import org.fluentlenium.core.action.MouseActions;
import org.fluentlenium.core.action.WindowAction;
import org.fluentlenium.core.alert.Alert;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.components.ComponentsManager;
import org.fluentlenium.core.css.CssControl;
import org.fluentlenium.core.css.CssControlImpl;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.events.AnnotationsComponentListener;
import org.fluentlenium.core.events.EventsRegistry;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.inject.DefaultContainerInstanciator;
import org.fluentlenium.core.inject.FluentInjector;
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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
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
public class FluentDriver implements FluentControl {
    private final FluentControl adapter;

    private String baseUrl;

    private final ConfigurationProperties configuration;

    @Delegate(types = ComponentInstantiator.class)
    private final ComponentsManager componentsManager;

    private EventsRegistry events;

    private AnnotationsComponentListener eventsComponentsAnnotations;

    @Delegate
    private FluentInjector fluentInjector;

    @Delegate
    private CssControl cssControl; // NOPMD SingularField

    private Search search;

    private WebDriver driver;

    private MouseActions mouseActions;

    private KeyboardActions keyboardActions;

    private WindowAction windowAction;

    public FluentDriver(WebDriver driver, ConfigurationProperties configuration, FluentControl adapter) {
        this.configuration = configuration;
        this.adapter = adapter;
        this.componentsManager = new ComponentsManager(adapter);
        this.driver = driver;
        this.search = new Search(driver, componentsManager);
        if (driver instanceof EventFiringWebDriver) {
            this.events = new EventsRegistry(this);
            this.eventsComponentsAnnotations = new AnnotationsComponentListener(componentsManager);
            this.events.register(this.eventsComponentsAnnotations);
        } else {
            this.events = null;
        }
        this.mouseActions = new MouseActions(driver);
        this.keyboardActions = new KeyboardActions(driver);
        this.fluentInjector = new FluentInjector(adapter, events, componentsManager, new DefaultContainerInstanciator(this));
        this.cssControl = new CssControlImpl(adapter, adapter);
        this.windowAction = new WindowAction(adapter, driver);

        configureDriver();
    }

    private void configureDriver() {
        if (this.getDriver() != null) {
            if (this.getDriver().manage() != null && this.getDriver().manage().timeouts() != null) {
                if (this.configuration.getPageLoadTimeout() == null) {
                    this.getDriver().manage().timeouts().pageLoadTimeout(-1, TimeUnit.MILLISECONDS);
                } else {
                    this.getDriver().manage().timeouts()
                            .pageLoadTimeout(this.configuration.getPageLoadTimeout(), TimeUnit.MILLISECONDS);
                }

                if (this.configuration.getImplicitlyWait() == null) {
                    this.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
                } else {
                    this.getDriver().manage().timeouts()
                            .implicitlyWait(this.configuration.getImplicitlyWait(), TimeUnit.MILLISECONDS);
                }

                if (this.configuration.getScriptTimeout() == null) {
                    this.getDriver().manage().timeouts().setScriptTimeout(-1, TimeUnit.MILLISECONDS);
                } else {
                    this.getDriver().manage().timeouts()
                            .setScriptTimeout(this.configuration.getScriptTimeout(), TimeUnit.MILLISECONDS);
                }
            }

            if (this.configuration.getBaseUrl() != null) {
                String configBaseUrl = this.configuration.getBaseUrl();
                if (configBaseUrl != null) {
                    if (configBaseUrl.endsWith("/")) {
                        configBaseUrl = configBaseUrl.substring(0, configBaseUrl.length() - 1);
                    }
                    this.baseUrl = configBaseUrl;
                }
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
                throw new RuntimeException("error when dumping HTML", e);
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
        return this.driver;
    }

    private Search getSearch() {
        return this.search;
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
        return new FluentWait(this);
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
    public FluentWebElement el(String selector, final Filter... filters) {
        return find(selector, filters).first();
    }

    @Override
    public FluentList<FluentWebElement> $(final Filter... filters) {
        return find(filters);
    }

    @Override
    public FluentWebElement el(final Filter... filters) {
        return find(filters).first();
    }

    @Override
    public FluentList<FluentWebElement> $(By locator, final Filter... filters) {
        return find(locator, filters);
    }

    @Override
    public FluentWebElement el(By locator, final Filter... filters) {
        return find(locator, filters).first();
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
        this.switchTo((FluentWebElement) null);
    }

    @Override
    public void switchToDefault() {
        this.switchTo((FluentWebElement) null);
    }

    @Override
    public Alert alert() {
        return new Alert(getDriver());
    }

    public void quit() {
        if (getDriver() != null) {
            getDriver().quit();
        }
        releaseFluent();
    }

    public void releaseFluent() {
        fluentInjector.release();
        if (this.events != null) {
            this.events.unregister(this.eventsComponentsAnnotations);
        }
    }
}
