package org.fluentlenium.core;

import static org.fluentlenium.core.domain.ElementUtils.getWrappedElement;
import static org.fluentlenium.utils.Preconditions.checkArgument;
import static org.fluentlenium.utils.Preconditions.checkState;

import io.appium.java_client.AppiumDriver;
import org.fluentlenium.configuration.Configuration;
import org.fluentlenium.core.action.KeyboardActions;
import org.fluentlenium.core.action.MouseActions;
import org.fluentlenium.core.action.WindowAction;
import org.fluentlenium.core.alert.Alert;
import org.fluentlenium.core.alert.AlertImpl;
import org.fluentlenium.core.components.ComponentsManager;
import org.fluentlenium.core.css.CssControl;
import org.fluentlenium.core.css.CssControlImpl;
import org.fluentlenium.core.css.CssSupport;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.events.ComponentsEventsRegistry;
import org.fluentlenium.core.events.EventsRegistry;
import org.fluentlenium.core.inject.ContainerContext;
import org.fluentlenium.core.inject.DefaultContainerInstantiator;
import org.fluentlenium.core.inject.FluentInjector;
import org.fluentlenium.core.performance.PerformanceTiming;
import org.fluentlenium.core.performance.DefaultPerformanceTiming;
import org.fluentlenium.core.script.FluentJavascript;
import org.fluentlenium.core.search.Search;
import org.fluentlenium.core.wait.FluentWait;
import org.fluentlenium.utils.UrlUtils;
import org.fluentlenium.utils.chromium.ChromiumApi;
import org.fluentlenium.utils.chromium.ChromiumControl;
import org.fluentlenium.utils.chromium.ChromiumControlImpl;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Set;

/**
 * Wrapper class for a {@link WebDriver} instance which also offers shortcut and convenience methods,
 * as well as methods to work with mouse, keyboard and windows.
 */
@SuppressWarnings("PMD.GodClass")
public class FluentDriver extends AbstractFluentDriverSearchControl { // NOPMD GodClass

    private static final Logger LOGGER =
            LoggerFactory.getLogger(FluentDriver.class);

    private final Configuration configuration;
    private final ComponentsManager componentsManager;
    private final EventsRegistry events;
    private final ComponentsEventsRegistry componentsEventsRegistry;
    private final FluentInjector fluentInjector;
    private final CssControl cssControl; // NOPMD UnusedPrivateField
    private final Search search;
    private final WebDriver driver;
    private final MouseActions mouseActions;
    private final KeyboardActions keyboardActions;
    private final WindowAction windowAction;
    private final FluentDriverScreenshotPersister screenshotPersister;
    private final FluentDriverWrappedCapabilitiesProvider capabilitiesProvider;
    private final FluentDriverHtmlDumper htmlDumper;
    private final FluentDriverWait driverWait;
    private final PerformanceTiming performanceTiming;
    private final ChromiumControl chromiumControl;

    /**
     * Wrap the driver into a Fluent driver.
     *
     * @param driver        underlying selenium driver
     * @param configuration configuration
     * @param adapter       adapter fluent control interface
     */
    public FluentDriver(WebDriver driver, Configuration configuration, FluentControl adapter) {
        super(adapter);
        this.configuration = configuration;
        screenshotPersister = new FluentDriverScreenshotPersister(configuration, driver);
        capabilitiesProvider = new FluentDriverWrappedCapabilitiesProvider();
        htmlDumper = new FluentDriverHtmlDumper(configuration);
        componentsManager = new ComponentsManager(adapter);
        driverWait = new FluentDriverWait(configuration);
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
        fluentInjector = new FluentInjector(adapter, events, componentsManager, new DefaultContainerInstantiator(this));
        cssControl = new CssControlImpl(adapter, adapter);
        windowAction = new WindowAction(adapter, componentsManager.getInstantiator(), driver);
        performanceTiming = new DefaultPerformanceTiming(driver);
        chromiumControl = new ChromiumControlImpl(driver);

        new FluentDriverTimeoutConfigurer(configuration, driver).configureDriver();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public void takeHtmlDump() {
        takeHtmlDump(new Date().getTime() + ".html");
    }

    @Override
    public void takeHtmlDump(String fileName) {
        htmlDumper.takeHtmlDump(fileName, () -> {
            synchronized (FluentDriver.class) {
                return $("html").first().html();
            }
        });
    }

    @Override
    public boolean canTakeScreenShot() {
        return getDriver() instanceof TakesScreenshot;
    }

    @Override
    public void takeScreenshot() {
        takeScreenshot(new Date().getTime() + ".png");
    }

    @Override
    public void takeScreenshot(String fileName) {
        if (!canTakeScreenShot()) {
            throw new WebDriverException("Current browser doesn't allow taking screenshot.");
        }
        screenshotPersister.persistScreenshot(fileName);
    }

    @Override
    public WebDriver getDriver() {
        if (driver instanceof AppiumDriver) {
            LOGGER.warn("You should use getAppiumDriver() method for mobile automation");
        }
        return driver;
    }

    @Override public AppiumDriver<?> getAppiumDriver() {
        if (!(driver instanceof AppiumDriver)) {
            throw new WrongDriverException("Use getDriver() method for web automation");
        }
        return (AppiumDriver<?>) driver;
    }

    @Override
    public EventsRegistry events() {
        return checkState(events, "An EventFiringWebDriver instance is required to use events. "
                + "You should set 'eventsEnabled' configuration property to 'true' "
                + "or override newWebDriver() to build an EventFiringWebDriver.");
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
        return driverWait.await(this);
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
        String baseUrl = buildUrl(null);

        String currentUrl = getDriver().getCurrentUrl();
        if (currentUrl != null && baseUrl != null && currentUrl.startsWith(baseUrl)) {
            currentUrl = currentUrl.substring(baseUrl.length());
        }

        return currentUrl;
    }

    private String buildUrl(String url) {
        String currentUrl = getDriver().getCurrentUrl();
        String baseUrl = UrlUtils.sanitizeBaseUrl(getBaseUrl(), currentUrl);

        return UrlUtils.concat(baseUrl, url);
    }

    @Override
    public String pageSource() {
        return getDriver().getPageSource();
    }

    @Override
    public <P extends FluentPage> P goTo(P page) {
        checkArgument(page, "It is required to specify an instance of FluentPage for navigation.");
        page.go();
        return page;
    }

    @Override
    public void goTo(String url) {
        checkArgument(url, "It is required to specify a URL to navigate to.");
        getDriver().get(buildUrl(url));
    }

    @Override
    public void goToInNewTab(String url) {
        checkArgument(url, "It is required to specify a URL to navigate to (in a new tab).");
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
        return capabilitiesProvider.getCapabilities(getDriver());
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
    public void switchTo(FluentList<? extends FluentWebElement> elements) {
        switchTo(elements.first());
    }

    @Override
    public void switchTo(FluentWebElement element) {
        if (null == element || !"iframe".equals(element.tagName())) {
            getDriver().switchTo().defaultContent();
        } else {
            WebElement target = element.getElement();
            while (target instanceof WrapsElement && target != getWrappedElement(target)) {
                target = getWrappedElement(target);
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

    @Override
    protected ComponentsManager getComponentsManager() {
        return componentsManager;
    }

    @Override
    protected Search getSearch() {
        return search;
    }

    @Override
    public ContainerContext inject(Object container) {
        return fluentInjector.inject(container);
    }

    @Override
    public <T> T newInstance(Class<T> cls) {
        return fluentInjector.newInstance(cls);
    }

    @Override
    public ContainerContext injectComponent(Object componentContainer, Object parentContainer, SearchContext searchContext) {
        return fluentInjector.injectComponent(componentContainer, parentContainer, searchContext);
    }

    @Override
    public CssSupport css() {
        return cssControl.css();
    }

    @Override
    public PerformanceTiming performanceTiming() {
        return performanceTiming;
    }

    @Override
    public ChromiumApi getChromiumApi() {
        return chromiumControl.getChromiumApi();
    }
}
