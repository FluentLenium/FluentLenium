package org.fluentlenium.core.proxy;

import com.google.common.collect.FluentIterable;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.hook.HookChainBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;

class FluentListHandler<T extends FluentWebElement> extends AbstractListHandler<T, FluentList<T>> {
    public FluentListHandler(ElementLocator locator, Class<T> componentClass, ComponentInstantiator instantiator, HookChainBuilder hookChainBuilder) {
        super(locator, componentClass, instantiator, hookChainBuilder);
    }

    @Override
    protected FluentList<T> buildList(List<WebElement> elements) {
        FluentListImpl fluentList = new FluentListImpl(getComponentClass(), getInstantiator(), getHookChainBuilder(), FluentIterable.from(elements).transform(getTransformer()).toList());
        fluentList.setProxy(proxy);
        return fluentList;
    }

}
