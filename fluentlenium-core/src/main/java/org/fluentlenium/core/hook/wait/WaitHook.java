package org.fluentlenium.core.hook.wait;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.components.ComponentsManager;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.hook.BaseFluentHook;
import org.fluentlenium.core.hook.BaseHook;
import org.fluentlenium.core.wait.FluentWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;

public class WaitHook extends BaseFluentHook<WaitHookOptions> {
    public WaitHook(WebDriver webDriver, ComponentInstantiator instantiator, Supplier<WebElement> elementSupplier, Supplier<ElementLocator> locatorSupplier, WaitHookOptions options) {
        super(webDriver, instantiator, elementSupplier, locatorSupplier, options);
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
        return buildAwait().until(new Function<FluentDriver, List<WebElement>>() {
            @Override
            public List<WebElement> apply(FluentDriver input) {
                return WaitHook.super.findElements();
            }
        });
    }

    @Override
    public WebElement findElement() {
        return buildAwait().until(new Function<FluentDriver, WebElement>() {
            @Override
            public WebElement apply(FluentDriver input) {
                return WaitHook.super.findElement();
            }
        });
    }
}
