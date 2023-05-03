package io.fluentlenium.core.hook.wait;

import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.components.ComponentInstantiator;
import io.fluentlenium.core.hook.BaseFluentHook;
import io.fluentlenium.core.wait.FluentWait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

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
    public WaitHook(FluentControl control, ComponentInstantiator instantiator, Supplier<WebElement> elementSupplier,
                    Supplier<ElementLocator> locatorSupplier, Supplier<String> toStringSupplier, WaitHookOptions options) {
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
        buildAwait().until(() -> getFluentWebElement().present() && getFluentWebElement().clickable());
        super.click();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        buildAwait().until(() -> getFluentWebElement().present() && getFluentWebElement().enabled());
        super.sendKeys(keysToSend);
    }

    @Override
    public void submit() {
        buildAwait().until(() -> getFluentWebElement().displayed() && getFluentWebElement().enabled());
        super.submit();
    }

    @Override
    public void clear() {
        buildAwait().until(() -> getFluentWebElement().displayed() && getFluentWebElement().enabled());
        super.clear();
    }

    @Override
    public List<WebElement> findElements() {
        return buildAwait().until(new Function<FluentControl, List<WebElement>>() {

            @Override
            public List<WebElement> apply(FluentControl input) {
                List<WebElement> elements = WaitHook.super.findElements();
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
            public WebElement apply(FluentControl input) {
                return WaitHook.super.findElement();
            }

            @Override
            public String toString() {
                return WaitHook.super.toString();
            }
        });
    }
}
