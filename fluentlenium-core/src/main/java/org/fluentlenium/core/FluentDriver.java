package org.fluentlenium.core;

import static org.fluentlenium.core.domain.ElementUtils.getWrappedElement;
import static org.fluentlenium.utils.Preconditions.checkArgument;
import static org.fluentlenium.utils.Preconditions.checkState;

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
import org.fluentlenium.core.domain.ComponentList;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.events.ComponentsEventsRegistry;
import org.fluentlenium.core.events.EventsRegistry;
import org.fluentlenium.core.inject.ContainerContext;
import org.fluentlenium.core.inject.DefaultContainerInstantiator;
import org.fluentlenium.core.inject.FluentInjector;
import org.fluentlenium.core.script.FluentJavascript;
import org.fluentlenium.core.search.Search;
import org.fluentlenium.core.search.SearchFilter;
import org.fluentlenium.core.wait.FluentWait;
import org.fluentlenium.utils.UrlUtils;
import org.openqa.selenium.By;
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

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Offers some shortcut to WebDriver methods using a wrapped {@link WebDriver} instance.
 *
 * It provides methods to work with mouse, keyboard and windows.
 */
@SuppressWarnings("PMD.GodClass")
public class FluentDriver extends FluentControlImpl { // NOPMD GodClass
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
    private final FluentDriverCapabilitiesProvider capabilitiesProvider;
    private final FluentDriverHtmlDumper htmlDumper;
    private final FluentDriverWait driverWait;

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
        capabilitiesProvider = new FluentDriverCapabilitiesProvider();
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

        configureDriver();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    private void configureDriver() {
        if (getDriver() != null && getDriver().manage() != null && getDriver().manage().timeouts() != null) {
            if (configuration.getPageLoadTimeout() != null) {
                getDriver().manage().timeouts().pageLoadTimeout(configuration.getPageLoadTimeout(), TimeUnit.MILLISECONDS);
            }

            if (configuration.getImplicitlyWait() != null) {
                getDriver().manage().timeouts().implicitlyWait(configuration.getImplicitlyWait(), TimeUnit.MILLISECONDS);
            }

            if (configuration.getScriptTimeout() != null) {
                getDriver().manage().timeouts().setScriptTimeout(configuration.getScriptTimeout(), TimeUnit.MILLISECONDS);
            }
        }
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
        return driver;
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
    public FluentList<FluentWebElement> find(String selector, SearchFilter... filters) {
        return search.find(selector, filters);
    }

    @Override
    public FluentList<FluentWebElement> find(By locator, SearchFilter... filters) {
        return search.find(locator, filters);
    }

    @Override
    public FluentList<FluentWebElement> find(SearchFilter... filters) {
        return search.find(filters);
    }

    @Override
    public FluentList<FluentWebElement> find(List<WebElement> rawElements) {
        return search.find(rawElements);
    }

    @Override
    public FluentList<FluentWebElement> $(List<WebElement> rawElements) {
        return search.$(rawElements);
    }

    @Override
    public FluentWebElement el(WebElement rawElement) {
        return search.el(rawElement);
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
    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass) {
        return componentsManager.newComponentList(listClass, componentClass);
    }

    @Override
    public <T> ComponentList asComponentList(Class<T> componentClass, Iterable<WebElement> elements) {
        return componentsManager.asComponentList(componentClass, elements);
    }

    @Override
    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, T... componentsList) {
        return componentsManager.newComponentList(listClass, componentClass, componentsList);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, Iterable<WebElement> elements) {
        return componentsManager.asFluentList(componentClass, elements);
    }

    @Override
    public boolean isComponentClass(Class<?> componentClass) {
        return componentsManager.isComponentClass(componentClass);
    }

    @Override
    public <T> ComponentList<T> asComponentList(Class<T> componentClass, List<WebElement> elements) {
        return componentsManager.asComponentList(componentClass, elements);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, WebElement... elements) {
        return componentsManager.asFluentList(componentClass, elements);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass) {
        return componentsManager.newFluentList(componentClass);
    }

    @Override
    public FluentWebElement newFluent(WebElement element) {
        return componentsManager.newFluent(element);
    }

    @Override
    public boolean isComponentListClass(Class<? extends List<?>> componentListClass) {
        return componentsManager.isComponentListClass(componentListClass);
    }

    @Override
    public FluentList<FluentWebElement> asFluentList(WebElement... elements) {
        return componentsManager.asFluentList(elements);
    }

    @Override
    public FluentList<FluentWebElement> asFluentList(Iterable<WebElement> elements) {
        return componentsManager.asFluentList(elements);
    }

    @Override
    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, WebElement... elements) {
        return componentsManager.asComponentList(listClass, componentClass, elements);
    }

    @Override
    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, Iterable<WebElement> elements) {
        return componentsManager.asComponentList(listClass, componentClass, elements);
    }

    @Override
    public FluentList<FluentWebElement> asFluentList(List<WebElement> elements) {
        return componentsManager.asFluentList(elements);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, List<WebElement> elements) {
        return componentsManager.asFluentList(componentClass, elements);
    }

    @Override
    public <T> ComponentList<T> asComponentList(Class<T> componentClass, WebElement... elements) {
        return componentsManager.asComponentList(componentClass, elements);
    }

    @Override
    public <T> T newComponent(Class<T> componentClass, WebElement element) {
        return componentsManager.newComponent(componentClass, element);
    }

    @Override
    public <T> ComponentList<T> newComponentList(Class<T> componentClass, T... componentsList) {
        return componentsManager.newComponentList(componentClass, componentsList);
    }

    @Override
    public <T> ComponentList<T> newComponentList(Class<T> componentClass, List<T> componentsList) {
        return componentsManager.newComponentList(componentClass, componentsList);
    }

    @Override
    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, List<T> componentsList) {
        return componentsManager.newComponentList(listClass, componentClass, componentsList);
    }

    @Override
    public FluentList<FluentWebElement> newFluentList() {
        return componentsManager.newFluentList();
    }

    @Override
    public FluentList<FluentWebElement> newFluentList(List<FluentWebElement> elements) {
        return componentsManager.newFluentList(elements);
    }

    @Override
    public <T> ComponentList<T> newComponentList(Class<T> componentClass) {
        return componentsManager.newComponentList(componentClass);
    }

    @Override
    public FluentList<FluentWebElement> newFluentList(FluentWebElement... elements) {
        return componentsManager.newFluentList(elements);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass, List<T> elements) {
        return componentsManager.newFluentList(componentClass, elements);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass, T... elements) {
        return componentsManager.newFluentList(componentClass, elements);
    }

    @Override
    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, List<WebElement> elements) {
        return componentsManager.asComponentList(listClass, componentClass, elements);
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
}
