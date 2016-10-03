package org.fluentlenium.core.axes;

import com.google.common.base.Supplier;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.hook.HookChainBuilder;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Handles XPath axes for an element (http://www.w3schools.com/xsl/xpath_axes.asp)
 */
public class Axes {
    private final WebElement webElement;
    private final ComponentInstantiator instantiator;
    private final HookChainBuilder hookChainBuilder;

    public Axes(WebElement element, ComponentInstantiator instantiator, HookChainBuilder hookChainBuilder) {
        this.webElement = element;
        this.instantiator = instantiator;
        this.hookChainBuilder = hookChainBuilder;
    }

    /**
     * Find parent element.
     *
     * @return fluent web element
     */
    public FluentWebElement parent() {
        WebElement parentElement = LocatorProxies.createWebElement(new Supplier<WebElement>() {
            @Override
            public WebElement get() {
                return Axes.this.webElement.findElement(By.xpath("parent::*"));
            }
        });

        return instantiator.newComponent(FluentWebElement.class, parentElement);
    }

    protected FluentList<FluentWebElement> handleAxe(final String axe) {
        List<WebElement> webElementList = LocatorProxies.createWebElementList(new Supplier<List<WebElement>>() {
            @Override
            public List<WebElement> get() {
                return Axes.this.webElement.findElements(By.xpath(axe + "::*"));
            }
        });

        return instantiator.asComponentList(FluentListImpl.class, FluentWebElement.class, webElementList);
    }

    /**
     * Find ancestor elements.
     *
     * @return list of Fluent web elements
     */
    public FluentList<FluentWebElement> ancestors() {
        return handleAxe("ancestor");
    }

    /**
     * Find descendants elements (children, grandchildren, etc.).
     *
     * @return list of Fluent web elements
     */
    public FluentList<FluentWebElement> descendants() {
        return handleAxe("descendant");
    }

    /**
     * Find following elements.
     *
     * @return list of Fluent web elements
     */
    public FluentList<FluentWebElement> followings() {
        return handleAxe("following");
    }

    /**
     * Find following sibling elements.
     *
     * @return list of Fluent web elements
     */
    public FluentList<FluentWebElement> followingSiblings() {
        return handleAxe("following-sibling");
    }

    /**
     * Find preceding elements. (Ancestors are NOT included)
     *
     * @return list of Fluent web elements
     */
    public FluentList<FluentWebElement> precedings() {
        return handleAxe("preceding");
    }

    /**
     * Find preceding sibling elements.
     *
     * @return list of Fluent web elements
     */
    public FluentList<FluentWebElement> precedingSiblings() {
        return handleAxe("preceding-sibling");
    }

}
