package org.fluentlenium.core.hook.wait;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.hook.BaseFluentHook;
import org.fluentlenium.core.wait.FluentWait;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;

public class WaitHook extends BaseFluentHook<WaitHookOptions> {
    public WaitHook(FluentControl fluentControl, ComponentInstantiator instantiator, Supplier<WebElement> elementSupplier, Supplier<ElementLocator> locatorSupplier, WaitHookOptions options) {
        super(fluentControl, instantiator, elementSupplier, locatorSupplier, options);
    }

    @Override
    protected WaitHookOptions newOptions() {
        return new WaitHookOptions();
    }

    public FluentWait buildAwait() {
        return getOptions().configureAwait(await());
    }

    @Override
    public void click() {
        buildAwait().until(getFluentWebElement()).isClickable();
        super.click();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        buildAwait().until(getFluentWebElement()).isEnabled();
        super.sendKeys(keysToSend);
    }

    @Override
    public void submit() {
        buildAwait().until(getFluentWebElement()).isEnabled();
        super.submit();
    }

    @Override
    public void clear() {
        buildAwait().until(getFluentWebElement()).isEnabled();
        super.clear();
    }

    @Override
    public List<WebElement> findElements() {
        try {
            return buildAwait().ignoring(NoSuchElementException.class).until(new Function<FluentControl, List<WebElement>>() {
                @Override
                public List<WebElement> apply(FluentControl input) {
                    List<WebElement> elements = WaitHook.super.findElements();
                    if (elements.size() == 0) throw new NoSuchElementException("No such element");
                    return elements;
                }
            });
        } catch (TimeoutException e) {
            throw new NoSuchElementException("No such element", e);
        }

    }

    @Override
    public WebElement findElement() {
        try {
            return buildAwait().ignoring(NoSuchElementException.class).until(new Function<FluentControl, WebElement>() {
                @Override
                public WebElement apply(FluentControl input) {
                    WebElement element = WaitHook.super.findElement();
                    if (element == null) throw new NoSuchElementException("No such element");
                    return element;
                }
            });
        } catch (TimeoutException e) {
            throw new NoSuchElementException("No such element", e);
        }
    }
}
