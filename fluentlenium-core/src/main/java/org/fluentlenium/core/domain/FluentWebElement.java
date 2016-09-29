package org.fluentlenium.core.domain;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import lombok.experimental.Delegate;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.action.Fill;
import org.fluentlenium.core.action.FillSelect;
import org.fluentlenium.core.action.FluentActions;
import org.fluentlenium.core.action.InputControl;
import org.fluentlenium.core.action.KeyboardElementActions;
import org.fluentlenium.core.action.MouseElementActions;
import org.fluentlenium.core.axes.Axes;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.conditions.FluentConditions;
import org.fluentlenium.core.conditions.WebElementConditions;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.hook.DefaultHookChainBuilder;
import org.fluentlenium.core.hook.FluentHook;
import org.fluentlenium.core.hook.HookChainBuilder;
import org.fluentlenium.core.hook.HookControl;
import org.fluentlenium.core.hook.HookDefinition;
import org.fluentlenium.core.label.FluentLabel;
import org.fluentlenium.core.label.FluentLabelImpl;
import org.fluentlenium.core.proxy.FluentProxyState;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.fluentlenium.core.search.Search;
import org.fluentlenium.core.search.SearchControl;
import org.fluentlenium.core.wait.AwaitControl;
import org.fluentlenium.core.wait.FluentWaitElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * WebElementCustom include a Selenium WebElement. It provides a lot of shortcuts to make selenium more fluent
 */
public class FluentWebElement extends Component implements WrapsElement, FluentActions<FluentWebElement, FluentWebElement>,
        FluentProxyState<FluentWebElement>, SearchControl<FluentWebElement>, HookControl<FluentWebElement>, FluentLabel<FluentWebElement> {
    private final Search search;
    private final Axes axes;
    private final MouseElementActions mouseActions;
    private final KeyboardElementActions keyboardActions;
    private final WebElementConditions conditions;

    private final List<HookDefinition<?>> hookDefinitions = new ArrayList<>();
    private final HookChainBuilder hookChainBuilder;

    @Delegate
    private FluentLabel<FluentWebElement> label;

    public FluentWebElement(WebElement webElement, FluentControl fluentControl, ComponentInstantiator instantiator) {
        super(webElement, fluentControl, instantiator);

        this.hookChainBuilder = new DefaultHookChainBuilder(this.fluentControl, this.instantiator);

        this.search = new Search(webElement, this.instantiator);
        this.axes = new Axes(webElement, this.instantiator, this.hookChainBuilder);
        this.mouseActions = new MouseElementActions(this.fluentControl.getDriver(), webElement);
        this.keyboardActions = new KeyboardElementActions(this.fluentControl.getDriver(), webElement);
        this.conditions = new WebElementConditions(this);
        this.label = new FluentLabelImpl<>(this, new Supplier<String>() {
            @Override
            public String get() {
                return getElement().toString();
            }
        });
    }

    @Delegate(excludes = {InputControl.class, AwaitControl.class, SearchControl.class})
    private FluentControl getFluentControl() {
        return fluentControl;
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
    public boolean present() {
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
    public boolean loaded() {
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

    public FluentConditions conditions() {
        return conditions;
    }

    public FluentWaitElement await() {
        return new FluentWaitElement(fluentControl.await(), this);
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
     */
    public String attribute(String name) {
        return webElement.getAttribute(name);
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
     */
    public boolean displayed() {
        return webElement.isDisplayed();
    }

    /**
     * return true if the element is enabled, other way return false
     *
     * @return boolean value of enabled check
     */
    public boolean enabled() {
        return webElement.isEnabled();
    }

    /**
     * return true if the element is selected, other way false
     *
     * @return boolean value of selected check
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
        return ExpectedConditions.elementToBeClickable(getElement()).apply(fluentControl.getDriver()) != null;
    }

    /**
     * Check that this element is no longer attached to the DOM.
     *
     * @return false is the element is still attached to the DOM, true otherwise.
     */
    public boolean stale() {
        return ExpectedConditions.stalenessOf(getElement()).apply(fluentControl.getDriver());
    }

    /**
     * return the tag name
     *
     * @return string value of tag name
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
     * return the size of the element
     *
     * @return dimension/size of element
     */
    public Dimension size() {
        return webElement.getSize();
    }

    public FluentList<FluentWebElement> asList() {
        return instantiator.asComponentList(FluentListImpl.class, FluentWebElement.class, Arrays.asList(webElement));
    }

    @Override
    public FluentList<FluentWebElement> $(String selector, Filter... filters) {
        return find(selector, filters);
    }

    @Override
    public FluentWebElement el(String selector, Filter... filters) {
        return find(selector, filters).first();
    }

    @Override
    public FluentList<FluentWebElement> $(Filter... filters) {
        return find(filters);
    }

    @Override
    public FluentWebElement el(Filter... filters) {
        return find(filters).first();
    }

    @Override
    public FluentList<FluentWebElement> $(By locator, Filter... filters) {
        return find(locator, filters);
    }

    @Override
    public FluentWebElement el(By locator, Filter... filters) {
        return find(locator, filters).first();
    }

    @Override
    public FluentList<FluentWebElement> find(By locator, Filter... filters) {
        return search.find(locator, filters);
    }

    @Override
    public FluentList<FluentWebElement> find(String selector, Filter... filters) {
        return search.find(selector, filters);
    }

    @Override
    public FluentList<FluentWebElement> find(Filter... filters) {
        return search.find(filters);
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
    public Optional<FluentWebElement> optional() {
        if (present()) {
            return Optional.of(this);
        } else {
            return Optional.absent();
        }
    }

    /**
     * This method return true if the current FluentWebElement is an input of type file
     *
     * @return boolean value for is input file type
     */
    private boolean isInputOfTypeFile() {
        return "input".equalsIgnoreCase(this.tagName()) && "file".equalsIgnoreCase(this.attribute("type"));
    }

    @Override
    public FluentWebElement noHook() {
        hookDefinitions.clear();
        LocatorProxies.setHooks(getElement(), hookChainBuilder, hookDefinitions);
        return this;
    }

    @Override
    public <O, H extends FluentHook<O>> FluentWebElement withHook(Class<H> hook) {
        hookDefinitions.add(new HookDefinition<>(hook));
        LocatorProxies.setHooks(getElement(), hookChainBuilder, hookDefinitions);
        return this;
    }

    @Override
    public <O, H extends FluentHook<O>> FluentWebElement withHook(Class<H> hook, O options) {
        hookDefinitions.add(new HookDefinition<>(hook, options));
        LocatorProxies.setHooks(getElement(), hookChainBuilder, hookDefinitions);
        return this;
    }

    @Override
    public String toString() {
        return label.toString();
    }
}
