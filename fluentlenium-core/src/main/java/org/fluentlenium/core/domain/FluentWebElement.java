package org.fluentlenium.core.domain;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.action.Fill;
import org.fluentlenium.core.action.FillSelect;
import org.fluentlenium.core.action.FluentActions;
import org.fluentlenium.core.action.KeyboardElementActions;
import org.fluentlenium.core.action.MouseElementActions;
import org.fluentlenium.core.axes.Axes;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.conditions.WebElementConditions;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.hook.FluentHook;
import org.fluentlenium.core.hook.DefaultHookChainBuilder;
import org.fluentlenium.core.hook.HookChainBuilder;
import org.fluentlenium.core.hook.HookControl;
import org.fluentlenium.core.hook.HookDefinition;
import org.fluentlenium.core.proxy.FluentProxyState;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.fluentlenium.core.search.Search;
import org.fluentlenium.core.search.SearchControl;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * WebElementCustom include a Selenium WebElement. It provides a lot of shortcuts to make selenium more fluent
 */
public class FluentWebElement implements WrapsElement, FluentActions<FluentWebElement, FluentWebElement>, FluentProxyState<FluentWebElement>, SearchControl<FluentWebElement>, HookControl<FluentWebElement> {
    private WebElement webElement;
    private final FluentControl fluentControl;
    private final ComponentInstantiator instantiator;

    private final Search search;
    private final Axes axes;
    private final MouseElementActions mouseActions;
    private final KeyboardElementActions keyboardActions;
    private final WebElementConditions conditions;

    private final List<HookDefinition<?>> hookDefinitions = new ArrayList<>();
    private final HookChainBuilder hookChainBuilder;

    public FluentWebElement(WebElement webElement, FluentControl fluentControl, ComponentInstantiator instantiator) {
        this.webElement = webElement;
        this.fluentControl = fluentControl;
        this.instantiator = instantiator;

        this.hookChainBuilder = new DefaultHookChainBuilder(this.fluentControl, this.instantiator);

        this.search = new Search(webElement, this.instantiator, this.hookChainBuilder);
        this.axes = new Axes(webElement, this.instantiator, this.hookChainBuilder);
        this.mouseActions = new MouseElementActions(this.fluentControl.getDriver(), webElement);
        this.keyboardActions = new KeyboardElementActions(this.fluentControl.getDriver(), webElement);
        this.conditions = new WebElementConditions(this);


    }

    /**
     * Click on the element
     *
     * @return fluent web element
     */
    public FluentWebElement click() {
        webElement.click();
        return this;
    }

    @Override
    public boolean isPresent() {
        return LocatorProxies.isPresent(webElement);
    }

    @Override
    public FluentWebElement now() {
        LocatorProxies.now(webElement);
        return this;
    }

    @Override
    public FluentWebElement reset() {
        LocatorProxies.reset(webElement);
        return this;
    }

    @Override
    public boolean isLoaded() {
        return LocatorProxies.isLoaded(webElement);
    }

    /**
     * XPath Axes accessor (parent, ancestors, preceding, following, ...).
     *
     * @return object to perform XPath Axes transformations.
     */
    public Axes axes() {
        return axes;
    }

    public WebElementConditions conditions() {
        return conditions;
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
     * Wrap all underlying elements in a componen..
     *
     * @param componentClass component class
     * @param <T>            type of component
     * @return element as component.
     */
    public <T> T as(Class<T> componentClass) {
        WebElement webElement = getElement();
        this.webElement = new FailWebElement(); // Make sure this FluentWebElement won't be used anymore.
        return instantiator.newComponent(componentClass, webElement);
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
    public FluentWebElement text(String... text) {
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
    public String getName() {
        return webElement.getAttribute("name");
    }

    /**
     * return any value of custom attribute (generated=true will return "true" if getAttribute("generated") is called.
     *
     * @param attribute custom attribute name
     * @return name value
     */
    public String getAttribute(String attribute) {
        return webElement.getAttribute(attribute);
    }

    /**
     * return the id of the elements
     *
     * @return id of element
     */
    public String getId() {
        return webElement.getAttribute("id");
    }

    /**
     * return the visible text of the element
     *
     * @return text of element
     */
    public String getText() {
        return webElement.getText();
    }

    /**
     * return the text content of the element (even invisible through textContent attribute)
     *
     * @return text content of element
     */
    public String getTextContent() {
        return webElement.getAttribute("textContent");
    }

    /**
     * return the value of the elements
     *
     * @return value of attribute
     */
    public String getValue() {
        return webElement.getAttribute("value");
    }

    /**
     * return true if the element is displayed, other way return false
     *
     * @return boolean value of displayed check
     */
    public boolean isDisplayed() {
        return webElement.isDisplayed();
    }

    /**
     * return true if the element is enabled, other way return false
     *
     * @return boolean value of enabled check
     */
    public boolean isEnabled() {
        return webElement.isEnabled();
    }

    /**
     * return true if the element is selected, other way false
     *
     * @return boolean value of selected check
     */
    public boolean isSelected() {
        return webElement.isSelected();
    }

    /**
     * Check that this element is visible and enabled such that you can click it.
     *
     * @return true if the element can be clicked, false otherwise.
     */

    public boolean isClickable() {
        return ExpectedConditions.elementToBeClickable(getElement()).apply(fluentControl.getDriver()) != null;
    }

    /**
     * Check that this element is no longer attached to the DOM.
     *
     * @return false is the element is still attached to the DOM, true otherwise.
     */
    public boolean isStale() {
        return ExpectedConditions.stalenessOf(getElement()).apply(fluentControl.getDriver());
    }

    /**
     * return the tag name
     *
     * @return string value of tag name
     */
    public String getTagName() {
        return webElement.getTagName();
    }

    /**
     * return the webElement
     *
     * @return web element
     */
    public WebElement getElement() {
        if (webElement instanceof FailWebElement) {
            ((FailWebElement) webElement).fail();
        }
        return webElement;
    }

    @Override
    public WebElement getWrappedElement() {
        return getElement();
    }

    /**
     * return the size of the elements
     *
     * @return dimension/size of element
     */
    public Dimension getSize() {
        return webElement.getSize();
    }

    public FluentList<FluentWebElement> asList() {
        return new FluentListImpl<>(FluentWebElement.class, instantiator, hookChainBuilder, Arrays.asList(this));
    }

    @Override
    public FluentList<FluentWebElement> $(String selector, Filter... filters) {
        return find(selector, filters);
    }

    @Override
    public FluentList<FluentWebElement> $(Filter... filters) {
        return find(filters);
    }

    @Override
    public FluentList<FluentWebElement> $(By locator, Filter... filters) {
        return find(locator, filters);
    }

    /**
     * find elements into the children with the corresponding filters
     *
     * @param locator elements locator
     * @param filters filters set
     * @return list of Fluent web elements
     */
    public FluentList<FluentWebElement> find(By locator, Filter... filters) {
        return search.find(locator, filters);
    }

    /**
     * find elements into the children with the corresponding filters
     *
     * @param selector name of element
     * @param filters  filters set
     * @return list of Fluent web elements
     */
    public FluentList<FluentWebElement> find(String selector, Filter... filters) {
        return search.find(selector, filters);
    }

    /**
     * find elements in the children with the corresponding filters
     *
     * @param filters filters set
     * @return list of Fluent web elements
     */
    public FluentList<FluentWebElement> find(Filter... filters) {
        return search.find(filters);
    }

    /**
     * find elements into the children with the corresponding filters at the given position
     *
     * @param selector name of element
     * @param filters  filters set
     * @return fluent web element
     */
    public FluentWebElement find(String selector, Integer index, Filter... filters) {
        return search.find(selector, filters).index(index);
    }

    /**
     * find elements into the children with the corresponding filters at the given position
     *
     * @param locator elements locator
     * @param filters filters set
     * @return fluent web element
     */
    public FluentWebElement find(By locator, Integer index, Filter... filters) {
        return search.find(locator, filters).index(index);
    }

    @Override
    public FluentWebElement $(String selector, Integer index, Filter... filters) {
        return find(selector, filters).index(index);
    }

    @Override
    public FluentWebElement $(By locator, Integer index, Filter... filters) {
        return find(locator, filters).index(index);
    }

    /**
     * find element in the children with the corresponding filters at the given position
     *
     * @param index   index of element
     * @param filters filters set
     * @return fluent web element
     */
    @Override
    public FluentWebElement find(Integer index, Filter... filters) {
        return search.find(filters).index(index);
    }

    @Override
    public FluentWebElement $(Integer index, Filter... filters) {
        return find(filters).index(index);
    }

    /**
     * find elements into the children with the corresponding filters at the first position
     *
     * @param selector name of element
     * @param filters  filters set
     * @return fluent web element
     */
    @Override
    public FluentWebElement findFirst(String selector, Filter... filters) {
        return search.findFirst(selector, filters);
    }

    /**
     * find elements into the children with the corresponding filters at the first position
     *
     * @param locator elements locator
     * @param filters filters set
     * @return fluent web element
     */
    public FluentWebElement findFirst(By locator, Filter... filters) {
        return search.findFirst(locator, filters);
    }

    /**
     * find element in the children with the corresponding filters at the first position
     *
     * @param filters filters set
     * @return fluent web element
     */
    @Override
    public FluentWebElement findFirst(Filter... filters) {
        return search.findFirst(filters);
    }

    /**
     * Get the HTML of a the element
     *
     * @return the underlying html content
     */
    public String html() {
        return webElement.getAttribute("innerHTML");
    }

    /**
     * Construct a FillConstructor in order to allow easy fill
     * Be careful - only the visible elements are filled
     *
     * @return fill constructor
     */
    public Fill fill() {
        return new Fill(this);
    }

    /**
     * Construct a FillSelectConstructor in order to allow easy list selection
     * Be careful - only the visible elements are filled
     *
     * @return fill constructor
     */
    public FillSelect fillSelect() {
        return new FillSelect(this);
    }

    /**
     * This method return true if the current FluentWebElement is an input of type file
     *
     * @return boolean value for is input file type
     */
    private boolean isInputOfTypeFile() {
        return ("input".equalsIgnoreCase(this.getTagName()) && "file".equalsIgnoreCase(this.getAttribute("type")));
    }

    @Override
    public String toString() {
        return this.getElement().toString();
    }

    @Override
    public FluentWebElement noHook() {
        hookDefinitions.clear();
        LocatorProxies.setHooks(hookChainBuilder, getElement(), hookDefinitions);
        return this;
    }

    @Override
    public <O, H extends FluentHook<O>> FluentWebElement withHook(Class<H> hook) {
        hookDefinitions.add(new HookDefinition<>(hook));
        LocatorProxies.setHooks(hookChainBuilder, getElement(), hookDefinitions);
        return this;
    }

    @Override
    public <O, H extends FluentHook<O>> FluentWebElement withHook(Class<H> hook, O options) {
        hookDefinitions.add(new HookDefinition<>(hook, options));
        LocatorProxies.setHooks(hookChainBuilder, getElement(), hookDefinitions);
        return this;
    }
}
