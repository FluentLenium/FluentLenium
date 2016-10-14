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
import org.fluentlenium.core.hook.HookControl;
import org.fluentlenium.core.hook.HookControlImpl;
import org.fluentlenium.core.hook.HookDefinition;
import org.fluentlenium.core.label.FluentLabel;
import org.fluentlenium.core.label.FluentLabelImpl;
import org.fluentlenium.core.proxy.FluentProxyState;
import org.fluentlenium.core.proxy.LocatorHandler;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.fluentlenium.core.search.Search;
import org.fluentlenium.core.search.SearchControl;
import org.fluentlenium.core.search.SearchFilter;
import org.fluentlenium.core.wait.AwaitControl;
import org.fluentlenium.core.wait.FluentWaitElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Wraps a Selenium {@link WebElement}. It provides an enhanced API to control selenium element.
 */
@SuppressWarnings({"PMD.GodClass", "PMD.ExcessivePublicCount"})
public class FluentWebElement extends Component
        implements WrapsElement, FluentActions<FluentWebElement, FluentWebElement>, FluentProxyState<FluentWebElement>,
        SearchControl<FluentWebElement>, HookControl<FluentWebElement>, FluentLabel<FluentWebElement> {
    private final Search search;
    private final Axes axes;
    private final MouseElementActions mouseActions;
    private final KeyboardElementActions keyboardActions;
    private final WebElementConditions conditions;

    private final HookControlImpl<FluentWebElement> hookControl;

    @Delegate
    private final FluentLabel<FluentWebElement> label;

    /**
     * Creates a new fluent web element.
     *
     * @param element      underlying element
     * @param control      controle interface
     * @param instantiator component instantiator
     */
    public FluentWebElement(final WebElement element, final FluentControl control, final ComponentInstantiator instantiator) {
        super(element, control, instantiator);

        this.hookControl = new HookControlImpl<>(this, this.webElement, this.control, this.instantiator,
                new Supplier<FluentWebElement>() {
                    @Override
                    public FluentWebElement get() {
                        final LocatorHandler locatorHandler = LocatorProxies.getLocatorHandler(getElement());
                        final ElementLocator locator = locatorHandler.getLocator();
                        final WebElement noHookElement = LocatorProxies.createWebElement(locator);
                        return newComponent(FluentWebElement.this.getClass(), noHookElement);
                    }
                });
        this.search = new Search(element, this.instantiator);
        this.axes = new Axes(element, this.instantiator);
        this.mouseActions = new MouseElementActions(this.control.getDriver(), element);
        this.keyboardActions = new KeyboardElementActions(this.control.getDriver(), element);
        this.conditions = new WebElementConditions(this);
        this.label = new FluentLabelImpl<>(this, new Supplier<String>() {
            @Override
            public String get() {
                return getElement().toString();
            }
        });
    }

    @Delegate(excludes = {InputControl.class, AwaitControl.class, SearchControl.class})
    private FluentControl getFluentControl() { // NOPMD UnusedPrivateMethod
        return control;
    }

    @Delegate
    private HookControl<FluentWebElement> getHookControl() { // NOPMD UnusedPrivateMethod
        return hookControl;
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
        mouseActions.contextClick();
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
    public FluentWebElement now(final boolean force) {
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
     */
    public Axes axes() {
        return axes;
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
     * Wrap all underlying elements in a componen..
     *
     * @param componentClass component class
     * @param <T>            type of component
     * @return element as component.
     */
    public <T> T as(final Class<T> componentClass) {
        return instantiator.newComponent(componentClass, getElement());
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
    public FluentWebElement write(final String... text) {
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
    public String attribute(final String name) {
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
    public FluentList<FluentWebElement> $(final String selector, final SearchFilter... filters) {
        return find(selector, filters);
    }

    @Override
    public FluentWebElement el(final String selector, final SearchFilter... filters) {
        return find(selector, filters).first();
    }

    @Override
    public FluentList<FluentWebElement> $(final SearchFilter... filters) {
        return find(filters);
    }

    @Override
    public FluentWebElement el(final SearchFilter... filters) {
        return find(filters).first();
    }

    @Override
    public FluentList<FluentWebElement> $(final By locator, final SearchFilter... filters) {
        return find(locator, filters);
    }

    @Override
    public FluentWebElement el(final By locator, final SearchFilter... filters) {
        return find(locator, filters).first();
    }

    @Override
    public FluentList<FluentWebElement> find(final By locator, final SearchFilter... filters) {
        return search.find(locator, filters);
    }

    @Override
    public FluentList<FluentWebElement> find(final String selector, final SearchFilter... filters) {
        return search.find(selector, filters);
    }

    @Override
    public FluentList<FluentWebElement> find(final SearchFilter... filters) {
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
    public FluentWebElement frame() {
        window().switchTo().frame(this);
        return this;
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

    /**
     * Save actual hook definitions to backup.
     *
     * @param hookRestoreStack restore stack
     */
    /* default */ void setHookRestoreStack(final Stack<List<HookDefinition<?>>> hookRestoreStack) {
        this.hookControl.setHookRestoreStack(hookRestoreStack);
    }

    @Override
    public String toString() {
        return label.toString();
    }
}
