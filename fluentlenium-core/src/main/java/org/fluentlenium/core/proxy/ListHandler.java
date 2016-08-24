package org.fluentlenium.core.proxy;

import com.google.common.collect.FluentIterable;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.hook.HookChainBuilder;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.ArrayList;
import java.util.List;

public class ListHandler<T> extends AbstractListHandler<T, List<T>> {
    public ListHandler(ElementLocator locator, Class<T> componentClass, ComponentInstantiator instantiator, HookChainBuilder hookChainBuilder) {
        super(locator, componentClass, instantiator, hookChainBuilder);
    }

    @Override
    protected List<T> buildList(List<WebElement> elements) {
        return new ArrayList<T>(FluentIterable.from(elements).transform(getTransformer()).toList());
    }
}
