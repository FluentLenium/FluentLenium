package org.fluentlenium.core.hook.wait;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.hook.BaseFluentHook;
import org.fluentlenium.core.wait.FluentWait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;

/**
 * Hook that automatically wait for actions beeing available on the underlying element.
 */
public class WaitHook extends BaseFluentHook<WaitHookOptions> {
    /**
     * Creates a new wait hook
     *
     * @param control          FluentLenium control interface
     * @param instantiator     FluentLenium instantiator
     * @param elementSupplier  element supplier
     * @param locatorSupplier  element locator supplier
     * @param toStringSupplier element toString supplier
     * @param options          hook options
     */
    public WaitHook(final FluentControl control, final ComponentInstantiator instantiator,
            final Supplier<WebElement> elementSupplier, final Supplier<ElementLocator> locatorSupplier,
            final Supplier<String> toStringSupplier, final WaitHookOptions options) {
        super(control, instantiator, elementSupplier, locatorSupplier, toStringSupplier, options);
    }

    @Override
    protected WaitHookOptions newOptions() {
        return new WaitHookOptions();
    }

    private FluentWait buildAwait() {
        return getOptions().configureAwait(await());
    }

    @Override
    public void click() {
        buildAwait().until(getFluentWebElement()).clickable();
        super.click();
    }

    @Override
    public void sendKeys(final CharSequence... keysToSend) {
        buildAwait().until(getFluentWebElement()).enabled();
        super.sendKeys(keysToSend);
    }

    @Override
    public void submit() {
        buildAwait().until(getFluentWebElement()).enabled();
        super.submit();
    }

    @Override
    public void clear() {
        buildAwait().until(getFluentWebElement()).enabled();
        super.clear();
    }

    @Override
    public List<WebElement> findElements() {
        return buildAwait().until(new Function<FluentControl, List<WebElement>>() {

            @Override
            public List<WebElement> apply(final FluentControl input) {
                final List<WebElement> elements = WaitHook.super.findElements();
                if (elements.size() == 0) {
                    return null;
                }
                return elements;
            }

            @Override
            public String toString() {
                return WaitHook.super.toString();
            }
        });
    }

    @Override
    public WebElement findElement() {
        return buildAwait().until(new Function<FluentControl, WebElement>() {

            @Override
            public WebElement apply(final FluentControl input) {
                return WaitHook.super.findElement();
            }

            @Override
            public String toString() {
                return WaitHook.super.toString();
            }
        });
    }
}
