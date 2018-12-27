package org.fluentlenium.core.hook;

import org.fluentlenium.core.DefaultFluentContainer;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;
import java.util.function.Supplier;

/**
 * Base hook implementation.
 * <p>
 * You should extends this class to implement your own hook.
 *
 * @param <T> type of options for the hook
 */
public class BaseHook<T> extends DefaultFluentContainer implements FluentHook<T> {
    private final ComponentInstantiator instantiator;

    private final Supplier<ElementLocator> locatorSupplier;

    private final Supplier<String> toStringSupplier;

    private T options;

    private final Supplier<WebElement> elementSupplier;

    /**
     * Get the underlying element of the hook.
     * <p>
     * Can be another hook, or a real element.
     *
     * @return underlying element
     */
    public final WebElement getElement() {
        return elementSupplier.get();
    }

    @Override
    public WebElement getWrappedElement() {
        return getElement();
    }

    /**
     * Get the underlying element locator of the hook.
     *
     * @return underlying element locator
     */
    public final ElementLocator getElementLocator() {
        return locatorSupplier.get();
    }


    /**
     * Get coordinates of the underlying element.
     *
     * @return cooridnates of underlying element
     */
    public Coordinates getCoordinates() {
        return ((Locatable) getElement()).getCoordinates();
    }

    /**
     * Creates a new base hook.
     *
     * @param control          control interface
     * @param instantiator     component instantiator
     * @param elementSupplier  element supplier
     * @param locatorSupplier  element locator supplier
     * @param toStringSupplier element toString supplier
     * @param options          hook options
     */
    public BaseHook(FluentControl control, ComponentInstantiator instantiator, Supplier<WebElement> elementSupplier,
            Supplier<ElementLocator> locatorSupplier, Supplier<String> toStringSupplier, T options) {
        super(control);
        this.instantiator = instantiator;
        this.elementSupplier = elementSupplier;
        this.locatorSupplier = locatorSupplier;
        this.toStringSupplier = toStringSupplier;
        this.options = options;

        if (this.options == null) {
            this.options = newOptions(); // NOPMD ConstructorCallsOverridableMethod
        }
    }

    /**
     * Builds default options.
     *
     * @return default options
     */
    protected T newOptions() {
        return null;
    }

    /**
     * Get the component instantiator.
     *
     * @return component instantiator
     */
    public ComponentInstantiator getInstantiator() {
        return instantiator;
    }

    @Override
    public T getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return toStringSupplier.get();
    }

    public void sendKeys(CharSequence... charSequences) {
        getElement().sendKeys(charSequences);
    }

    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return getElement().getScreenshotAs(outputType);
    }

    public WebElement findElement(By by) {
        return getElement().findElement(by);
    }

    public boolean isSelected() {
        return getElement().isSelected();
    }

    public Rectangle getRect() {
        return getElement().getRect();
    }

    public boolean isDisplayed() {
        return getElement().isDisplayed();
    }

    public boolean isEnabled() {
        return getElement().isEnabled();
    }

    public List<WebElement> findElements(By by) {
        return getElement().findElements(by);
    }

    public void submit() {
        getElement().submit();
    }

    public String getCssValue(String s) {
        return getElement().getCssValue(s);
    }

    public String getTagName() {
        return getElement().getTagName();
    }

    public Point getLocation() {
        return getElement().getLocation();
    }

    public Dimension getSize() {
        return getElement().getSize();
    }

    public String getText() {
        return getElement().getText();
    }

    public void click() {
        getElement().click();
    }

    public String getAttribute(String s) {
        return getElement().getAttribute(s);
    }

    public void clear() {
        getElement().clear();
    }

    public WebElement findElement() {
        return this.getElementLocator().findElement();
    }

    public List<WebElement> findElements() {
        return this.getElementLocator().findElements();
    }
}
