package org.fluentlenium.core.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;

import org.fluentlenium.configuration.ConfigurationFactory;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.action.Fill;
import org.fluentlenium.core.action.FillSelect;
import org.fluentlenium.core.action.FluentActions;
import org.fluentlenium.core.action.FluentJavascriptActionsImpl;
import org.fluentlenium.core.action.KeyboardElementActions;
import org.fluentlenium.core.action.MouseElementActions;
import org.fluentlenium.core.action.WindowAction;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.conditions.FluentConditions;
import org.fluentlenium.core.conditions.WebElementConditions;
import org.fluentlenium.core.css.CssSupport;
import org.fluentlenium.core.dom.Dom;
import org.fluentlenium.core.events.EventsRegistry;
import org.fluentlenium.core.hook.FluentHook;
import org.fluentlenium.core.hook.HookControl;
import org.fluentlenium.core.hook.HookControlImpl;
import org.fluentlenium.core.hook.HookDefinition;
import org.fluentlenium.core.inject.ContainerContext;
import org.fluentlenium.core.label.FluentLabel;
import org.fluentlenium.core.label.FluentLabelImpl;
import org.fluentlenium.core.proxy.FluentProxyState;
import org.fluentlenium.core.proxy.LocatorHandler;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.fluentlenium.core.script.FluentJavascript;
import org.fluentlenium.core.search.Search;
import org.fluentlenium.core.search.SearchControl;
import org.fluentlenium.core.search.SearchFilter;
import org.fluentlenium.core.wait.FluentWaitElement;
import org.fluentlenium.utils.SupplierOfInstance;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Wraps a Selenium {@link WebElement}. It provides an enhanced API to control selenium element.
 */
@SuppressWarnings({"PMD.GodClass", "PMD.ExcessivePublicCount"})
public class FluentWebElement extends Component
        implements WrapsElement, FluentActions<FluentWebElement, FluentWebElement>, FluentProxyState<FluentWebElement>,
        SearchControl<FluentWebElement>, HookControl<FluentWebElement>, FluentLabel<FluentWebElement> {
    private final Search search;
    private final Dom dom;
    private final MouseElementActions mouseActions;
    private final KeyboardElementActions keyboardActions;
    private final WebElementConditions conditions;

    private final HookControlImpl<FluentWebElement> hookControl;

    private final FluentLabel<FluentWebElement> label;

    private final FluentJavascriptActionsImpl<FluentWebElement> javascriptActions;

    /**
     * Creates a new fluent web element.
     *
     * @param element      underlying element
     * @param control      control interface
     * @param instantiator component instantiator
     */
    public FluentWebElement(WebElement element, FluentControl control, ComponentInstantiator instantiator) {
        super(element, control, instantiator);

        hookControl = new HookControlImpl<>(this, webElement, this.control, this.instantiator,
                /*do not change it to lambda - change will affect w/ PMD warning
                Overridable method 'getElement' called during object construction*/

                () -> {
                    LocatorHandler locatorHandler = LocatorProxies.getLocatorHandler(getElement());
                    ElementLocator locator = locatorHandler.getLocator();
                    WebElement noHookElement = LocatorProxies.createWebElement(locator);
                    return control.newComponent(this.getClass(), noHookElement);
                });

        search = new Search(element, this, this.instantiator, this.control);
        dom = new Dom(element, this.instantiator);
        mouseActions = new MouseElementActions(this.control.getDriver(), element);
        keyboardActions = new KeyboardElementActions(this.control.getDriver(), element);
        conditions = new WebElementConditions(this);
        label = new FluentLabelImpl<>(this, () -> getElement().toString());
        javascriptActions = new FluentJavascriptActionsImpl<>(this, this.control, new SupplierOfInstance<>(this));
    }

    private HookControl<FluentWebElement> getHookControl() { // NOPMD UnusedPrivateMethod
        return hookControl;
    }

    private FluentJavascriptActionsImpl<FluentWebElement> getJavascriptActions() { //NOPMD UnusedPrivateMethod
        return javascriptActions;
    }

    public FluentLabel<FluentWebElement> getLabel() {
        return label;
    }


    public FluentJavascript executeScript(String script, Object... args) {
        return control.executeScript(script, args);
    }

    public FluentJavascript executeAsyncScript(String script, Object... args) {
        return control.executeAsyncScript(script, args);
    }

    public Alert alert() {
        return control.alert();
    }

    public void takeHtmlDump() {
        control.takeHtmlDump();
    }

    public void takeHtmlDump(String fileName) {
        control.takeHtmlDump(fileName);
    }

    public boolean canTakeScreenShot() {
        return control.canTakeScreenShot();
    }

    public void takeScreenshot() {
        control.takeScreenshot();
    }

    public void takeScreenshot(String fileName) {
        control.takeScreenshot(fileName);
    }

    public EventsRegistry events() {
        return control.events();
    }

    public <P extends FluentPage> P goTo(P page) {
        return control.goTo(page);
    }

    public void goTo(String url) {
        control.goTo(url);
    }

    public void goToInNewTab(String url) {
        control.goToInNewTab(url);
    }

    public void switchTo(FluentList<? extends FluentWebElement> elements) {
        control.switchTo(elements);
    }

    public void switchTo(FluentWebElement element) {
        control.switchTo(element);
    }

    public void switchTo() {
        control.switchTo();
    }

    public void switchToDefault() {
        control.switchToDefault();
    }

    public String pageSource() {
        return control.pageSource();
    }

    public WindowAction window() {
        return control.window();
    }

    public Set<Cookie> getCookies() {
        return control.getCookies();
    }

    public Cookie getCookie(String name) {
        return control.getCookie(name);
    }

    public String url() {
        return control.url();
    }

    public WebDriver getDriver() {
        return control.getDriver();
    }

    public CssSupport css() {
        return control.css();
    }

    public ContainerContext inject(Object container) {
        return control.inject(container);
    }

    public ContainerContext injectComponent(Object componentContainer, Object parentContainer, SearchContext context) {
        return control.injectComponent(componentContainer, parentContainer, context);
    }

    public <T> T newInstance(Class<T> cls) {
        return control.newInstance(cls);
    }


    public FluentWebElement newFluent(WebElement element) {
        return control.newFluent(element);
    }

    public <T> T newComponent(Class<T> componentClass, WebElement element) {
        return control.newComponent(componentClass, element);
    }

    public FluentList<FluentWebElement> newFluentList() {
        return control.newFluentList();
    }

    public FluentList<FluentWebElement> newFluentList(FluentWebElement... elements) {
        return control.newFluentList(elements);
    }

    public FluentList<FluentWebElement> newFluentList(List<FluentWebElement> elements) {
        return control.newFluentList(elements);
    }

    public FluentList<FluentWebElement> asFluentList(WebElement... elements) {
        return control.asFluentList(elements);
    }

    public FluentList<FluentWebElement> asFluentList(Iterable<WebElement> elements) {
        return control.asFluentList(elements);
    }

    public FluentList<FluentWebElement> asFluentList(List<WebElement> elements) {
        return control.asFluentList(elements);
    }

    public <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass) {
        return control.newFluentList(componentClass);
    }

    public <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass, T... elements) {
        return control.newFluentList(componentClass, elements);
    }

    public <T extends FluentWebElement> FluentList<T> newFluentList(Class<T> componentClass, List<T> elements) {
        return control.newFluentList(componentClass, elements);
    }

    public <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, WebElement... elements) {
        return control.asFluentList(componentClass, elements);
    }

    public <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, Iterable<WebElement> elements) {
        return control.asFluentList(componentClass, elements);
    }

    public <T extends FluentWebElement> FluentList<T> asFluentList(Class<T> componentClass, List<WebElement> elements) {
        return control.asFluentList(componentClass, elements);
    }

    public <T> ComponentList<T> newComponentList(Class<T> componentClass) {
        return control.newComponentList(componentClass);
    }

    public <T> ComponentList<T> asComponentList(Class<T> componentClass, WebElement... elements) {
        return control.asComponentList(componentClass, elements);
    }

    public <T> ComponentList asComponentList(Class<T> componentClass, Iterable<WebElement> elements) {
        return control.asComponentList(componentClass, elements);
    }

    public <T> ComponentList<T> asComponentList(Class<T> componentClass, List<WebElement> elements) {
        return control.asComponentList(componentClass, elements);
    }

    public <T> ComponentList<T> newComponentList(Class<T> componentClass, T... componentsList) {
        return control.newComponentList(componentClass, componentsList);
    }

    public <T> ComponentList<T> newComponentList(Class<T> componentClass, List<T> componentsList) {
        return control.newComponentList(componentClass, componentsList);
    }

    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass) {
        return control.newComponentList(listClass, componentClass);
    }

    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, WebElement... elements) {
        return control.asComponentList(listClass, componentClass, elements);
    }

    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, Iterable<WebElement> elements) {
        return control.asComponentList(listClass, componentClass, elements);
    }

    public <L extends List<T>, T> L asComponentList(Class<L> listClass, Class<T> componentClass, List<WebElement> elements) {
        return control.asComponentList(listClass, componentClass, elements);
    }

    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, T... componentsList) {
        return control.newComponentList(listClass, componentClass, componentsList);
    }

    public <L extends List<T>, T> L newComponentList(Class<L> listClass, Class<T> componentClass, List<T> componentsList) {
        return control.newComponentList(listClass, componentClass, componentsList);
    }

    public boolean isComponentClass(Class<?> componentClass) {
        return control.isComponentClass(componentClass);
    }

    public Capabilities capabilities() {
        return control.capabilities();
    }

    public boolean isComponentListClass(Class<? extends List<?>> componentListClass) {
        return control.isComponentClass(componentListClass);
    }

    public String getWebDriver() {
        return control.getWebDriver();
    }

    public String getRemoteUrl() {
        return control.getRemoteUrl();
    }

    public Capabilities getCapabilities() {
        return control.getCapabilities();
    }

    public String getBaseUrl() {
        return control.getBaseUrl();
    }

    public ConfigurationProperties.DriverLifecycle getDriverLifecycle() {
        return control.getDriverLifecycle();
    }

    public Long getBrowserTimeout() {
        return control.getBrowserTimeout();
    }

    public Integer getBrowserTimeoutRetries() {
        return control.getBrowserTimeoutRetries();
    }

    public Boolean getDeleteCookies() {
        return control.getDeleteCookies();
    }

    public Long getPageLoadTimeout() {
        return control.getPageLoadTimeout();
    }

    public Long getImplicitlyWait() {
        return control.getImplicitlyWait();
    }

    public Long getScriptTimeout() {
        return control.getScriptTimeout();
    }

    public Long getAwaitAtMost() {
        return control.getAwaitAtMost();
    }

    public Long getAwaitPollingEvery() {
        return control.getAwaitPollingEvery();
    }

    public Boolean getEventsEnabled() {
        return control.getEventsEnabled();
    }

    public String getScreenshotPath() {
        return control.getScreenshotPath();
    }

    public ConfigurationProperties.TriggerMode getScreenshotMode() {
        return control.getScreenshotMode();
    }

    public String getHtmlDumpPath() {
        return control.getHtmlDumpPath();
    }

    public ConfigurationProperties.TriggerMode getHtmlDumpMode() {
        return control.getHtmlDumpMode();
    }

    public Class<? extends ConfigurationProperties> getConfigurationDefaults() {
        return control.getConfigurationDefaults();
    }

    public Class<? extends ConfigurationFactory> getConfigurationFactory() {
        return control.getConfigurationFactory();
    }

    public String getCustomProperty(String propertyName) {
        return control.getCustomProperty(propertyName);
    }

    public void setWebDriver(String webDriver) {
        control.setWebDriver(webDriver);
    }

    public void setBrowserTimeout(Long timeout) {
        control.setBrowserTimeout(timeout);
    }

    public void setBrowserTimeoutRetries(Integer retriesNumber) {
        control.setBrowserTimeoutRetries(retriesNumber);
    }

    public void setRemoteUrl(String remoteUrl) {
        control.setRemoteUrl(remoteUrl);
    }

    public void setCapabilities(Capabilities capabilities) {
        control.setCapabilities(capabilities);
    }

    public void setConfigurationFactory(Class<? extends ConfigurationFactory> configurationFactory) {
        control.setConfigurationFactory(configurationFactory);
    }

    public void setDriverLifecycle(ConfigurationProperties.DriverLifecycle driverLifecycle) {
        control.setDriverLifecycle(driverLifecycle);
    }

    public void setDeleteCookies(Boolean deleteCookies) {
        control.setDeleteCookies(deleteCookies);
    }

    public void setBaseUrl(String baseUrl) {
        control.setBaseUrl(baseUrl);
    }

    public void setPageLoadTimeout(Long pageLoadTimeout) {
        control.setPageLoadTimeout(pageLoadTimeout);
    }

    public void setImplicitlyWait(Long implicitlyWait) {
        control.setImplicitlyWait(implicitlyWait);
    }

    public void setAwaitAtMost(Long awaitAtMost) {
        control.setAwaitAtMost(awaitAtMost);
    }

    public void setAwaitPollingEvery(Long awaitPollingEvery) {
        control.setAwaitPollingEvery(awaitPollingEvery);
    }

    public void setScriptTimeout(Long scriptTimeout) {
        control.setScriptTimeout(scriptTimeout);
    }

    public void setEventsEnabled(Boolean eventsEnabled) {
        control.setEventsEnabled(eventsEnabled);
    }

    public void setScreenshotPath(String screenshotPath) {
        control.setScreenshotPath(screenshotPath);
    }

    public void setScreenshotMode(ConfigurationProperties.TriggerMode screenshotMode) {
        control.setScreenshotMode(screenshotMode);
    }

    public void setHtmlDumpPath(String htmlDumpPath) {
        control.setHtmlDumpPath(htmlDumpPath);
    }

    public void setHtmlDumpMode(ConfigurationProperties.TriggerMode htmlDumpMode) {
        control.setHtmlDumpMode(htmlDumpMode);
    }

    public void setCustomProperty(String key, String value) {
        control.setCustomProperty(key, value);
    }

    @Override
    public FluentWebElement click() {
        webElement.click();
        return this;
    }

    @Override
    public FluentWebElement doubleClick() {
        mouse().doubleClick();
        return this;
    }

    @Override
    public FluentWebElement contextClick() {
        mouse().contextClick();
        return this;
    }

    @Override
    public boolean present() {
        return LocatorProxies.present(webElement);
    }

    @Override
    public FluentWebElement now() {
        LocatorProxies.now(webElement);
        return this;
    }

    @Override
    public FluentWebElement now(boolean force) {
        if (force) {
            reset();
        }
        return now();
    }

    @Override
    public FluentWebElement reset() {
        LocatorProxies.reset(webElement);
        return this;
    }

    @Override
    public boolean loaded() {
        return LocatorProxies.loaded(webElement);
    }

    /**
     * XPath Axes accessor (parent, ancestors, preceding, following, ...).
     *
     * @return object to perform XPath Axes transformations.
     * @deprecated Use {@link #dom()} instead.
     */
    @Deprecated
    public Dom axes() {
        return dom;
    }

    /**
     * XPath Axes accessor (parent, ancestors, preceding, following, ...).
     *
     * @return object to perform XPath Axes transformations.
     */
    public Dom dom() {
        return dom;
    }

    /**
     * Get a conditions object used to verify condition on this element.
     *
     * @return conditions object
     */
    public FluentConditions conditions() {
        return conditions;
    }

    /**
     * Build a wait object to wait for a condition of this element.
     *
     * @return a wait object
     */
    public FluentWaitElement await() {
        return new FluentWaitElement(control.await(), this);
    }

    /**
     * Execute mouse actions on the element
     *
     * @return mouse actions object
     */
    public MouseElementActions mouse() {
        return mouseActions;
    }

    /**
     * Execute keyboard actions on the element
     *
     * @return keyboard actions object
     */
    public KeyboardElementActions keyboard() {
        return keyboardActions;
    }

    /**
     * Wrap all underlying elements in a component.
     *
     * @param componentClass component class
     * @param <T>            type of component
     * @return element as component.
     */
    public <T> T as(Class<T> componentClass) {
        T component = instantiator.newComponent(componentClass, getElement());
        control.injectComponent(component, this, getElement());
        return component;
    }

    /**
     * Clear the element
     *
     * @return fluent web element
     */
    public FluentWebElement clear() {
        if (!isInputOfTypeFile()) {
            webElement.clear();
        }
        return this;
    }

    /**
     * Submit the element
     *
     * @return fluent web element
     */
    public FluentWebElement submit() {
        webElement.submit();
        return this;
    }

    /**
     * Set the text element
     *
     * @param text value to set
     * @return fluent web element
     */
    public FluentWebElement write(String... text) {
        clear();
        if (text.length != 0) {
            webElement.sendKeys(text[0]);
        }
        return this;
    }

    /**
     * return the name of the element
     *
     * @return name of the element
     */
    public String name() {
        return webElement.getAttribute("name");
    }

    /**
     * return any value of custom attribute (generated=true will return "true" if attribute("generated") is called.
     *
     * @param name custom attribute name
     * @return name value
     * @see WebElement#getAttribute(String)
     */
    public String attribute(String name) {
        return webElement.getAttribute(name);
    }

    /**
     * Get the value of a given CSS property.
     *
     * @param propertyName the css property name of the element
     * @return The current, computed value of the property.
     * @see WebElement#getCssValue(String)
     */
    public String cssValue(String propertyName) {
        return webElement.getCssValue(propertyName);
    }

    /**
     * return the id of the elements
     *
     * @return id of element
     */
    public String id() {
        return webElement.getAttribute("id");
    }

    /**
     * return the visible text of the element
     *
     * @return text of element
     * @see WebElement#getText()
     */
    public String text() {
        return webElement.getText();
    }

    /**
     * return the text content of the element (even invisible through textContent attribute)
     *
     * @return text content of element
     */
    public String textContent() {
        return webElement.getAttribute("textContent");
    }

    /**
     * return the value of the elements
     *
     * @return value of attribute
     */
    public String value() {
        return webElement.getAttribute("value");
    }

    /**
     * return true if the element is displayed, other way return false
     *
     * @return boolean value of displayed check
     * @see WebElement#isDisplayed()
     */
    public boolean displayed() {
        return webElement.isDisplayed();
    }

    /**
     * return true if the element is enabled, other way return false
     *
     * @return boolean value of enabled check
     * @see WebElement#isEnabled()
     */
    public boolean enabled() {
        return webElement.isEnabled();
    }

    /**
     * return true if the element is selected, other way false
     *
     * @return boolean value of selected check
     * @see WebElement#isSelected()
     */
    public boolean selected() {
        return webElement.isSelected();
    }

    /**
     * Check that this element is visible and enabled such that you can click it.
     *
     * @return true if the element can be clicked, false otherwise.
     */

    public boolean clickable() {
        return ExpectedConditions.elementToBeClickable(getElement()).apply(control.getDriver()) != null;
    }

    /**
     * Check that this element is no longer attached to the DOM.
     *
     * @return false is the element is still attached to the DOM, true otherwise.
     */
    public boolean stale() {
        return ExpectedConditions.stalenessOf(getElement()).apply(control.getDriver());
    }

    /**
     * return the tag name
     *
     * @return string value of tag name
     * @see WebElement#getTagName()
     */
    public String tagName() {
        return webElement.getTagName();
    }

    /**
     * return the webElement
     *
     * @return web element
     */
    public WebElement getElement() {
        return webElement;
    }

    @Override
    public WebElement getWrappedElement() {
        return getElement();
    }

    /**
     * return the size of the element
     *
     * @return dimension/size of element
     * @see WebElement#getSize()
     */
    public Dimension size() {
        return webElement.getSize();
    }

    /**
     * Converts this element as a single element list.
     *
     * @return list of element
     */
    public FluentList<FluentWebElement> asList() {
        return instantiator.asComponentList(FluentListImpl.class, FluentWebElement.class, Arrays.asList(webElement));
    }

    @Override
    public FluentList<FluentWebElement> find(By locator, SearchFilter... filters) {
        return search.find(locator, filters);
    }

    @Override
    public FluentList<FluentWebElement> find(String selector, SearchFilter... filters) {
        return search.find(selector, filters);
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

    /**
     * Get the HTML of a the element
     *
     * @return the underlying html content
     */
    public String html() {
        return webElement.getAttribute("innerHTML");
    }

    @Override
    public Fill fill() {
        return new Fill(this);
    }

    @Override
    public FillSelect fillSelect() {
        return new FillSelect(this);
    }

    @Override
    public FluentWebElement frame() {
        control.window().switchTo().frame(this);
        return this;
    }

    @Override
    public Optional<FluentWebElement> optional() {
        if (present()) {
            return Optional.of(this);
        } else {
            return Optional.empty();
        }
    }

    /**
     * This method return true if the current FluentWebElement is an input of type file
     *
     * @return boolean value for is input file type
     */
    private boolean isInputOfTypeFile() {
        return "input".equalsIgnoreCase(tagName()) && "file".equalsIgnoreCase(attribute("type"));
    }

    /**
     * Save actual hook definitions to backup.
     *
     * @param hookRestoreStack restore stack
     */
    /* default */ void setHookRestoreStack(Stack<List<HookDefinition<?>>> hookRestoreStack) {
        hookControl.setHookRestoreStack(hookRestoreStack);
    }

    @Override
    public String toString() {
        return label.toString();
    }

    @Override
    public <R> R noHook(Class<? extends FluentHook> hook, Function<FluentWebElement, R> function) {
        return getHookControl().noHook(hook, function);
    }

    @Override
    public <O, H extends FluentHook<O>> FluentWebElement withHook(Class<H> hook, O options) {
        return getHookControl().withHook(hook, options);
    }

    @Override
    public <O, H extends FluentHook<O>> FluentWebElement withHook(Class<H> hook) {
        return getHookControl().withHook(hook);
    }

    @Override
    public FluentWebElement noHook(Class<? extends FluentHook>... hooks) {
        return getHookControl().noHook(hooks);
    }

    @Override
    public <R> R noHook(Function<FluentWebElement, R> function) {
        return getHookControl().noHook(function);
    }

    @Override
    public FluentWebElement noHookInstance(Class<? extends FluentHook>... hooks) {
        return getHookControl().noHookInstance(hooks);
    }

    @Override
    public FluentWebElement restoreHooks() {
        return getHookControl().restoreHooks();
    }

    @Override
    public FluentWebElement noHookInstance() {
        return getHookControl().noHookInstance();
    }

    @Override
    public FluentWebElement noHook() {
        return getHookControl().noHook();
    }

    @Override
    public FluentWebElement scrollToCenter() {
        return getJavascriptActions().scrollToCenter();
    }

    @Override
    public FluentWebElement scrollIntoView() {
        return getJavascriptActions().scrollIntoView();
    }

    @Override
    public FluentWebElement scrollIntoView(boolean alignWithTop) {
        return getJavascriptActions().scrollIntoView(alignWithTop);
    }

    @Override
    public FluentWebElement withLabelHint(String... labelHint) {
        return getLabel().withLabelHint(labelHint);
    }

    @Override
    public FluentWebElement withLabel(String label) {
        return getLabel().withLabel(label);
    }
}
