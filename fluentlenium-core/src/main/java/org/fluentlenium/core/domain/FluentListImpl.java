package org.fluentlenium.core.domain;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.action.Fill;
import org.fluentlenium.core.action.FillSelect;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.conditions.AtLeastOneElementConditions;
import org.fluentlenium.core.conditions.EachElementConditions;
import org.fluentlenium.core.conditions.FluentListConditions;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.hook.DefaultHookChainBuilder;
import org.fluentlenium.core.hook.FluentHook;
import org.fluentlenium.core.hook.HookChainBuilder;
import org.fluentlenium.core.hook.HookDefinition;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Map the list to a FluentList in order to offers some events like click(), submit(), value() ...
 */
public class FluentListImpl<E extends FluentWebElement> extends ComponentList<E> implements FluentList<E> {
    private final List<HookDefinition<?>> hookDefinitions = new ArrayList<>();
    private HookChainBuilder hookChainBuilder;

    public FluentListImpl(Class<E> componentClass, List<E> list, FluentControl fluentControl, ComponentInstantiator instantiator) {
        super(componentClass, list, fluentControl, instantiator);
        this.hookChainBuilder = new DefaultHookChainBuilder(fluentControl, instantiator);
    }


    /**
     * Creates a FluentList from array of Selenium {@link WebElement}
     *
     * @param instantiator     component instantiator
     * @param hookChainBuilder hook chain builder
     * @param elements         array of Selenium elements
     * @return list of elements
     */
    @Deprecated
    public static FluentList<FluentWebElement> fromElements(ComponentInstantiator instantiator, HookChainBuilder hookChainBuilder, WebElement... elements) {
        return fromElements(instantiator, hookChainBuilder, Arrays.asList(elements));
    }

    /**
     * Creates a FluentList from an iterable of Selenium {@link WebElement}
     *
     * @param instantiator     component instantiator
     * @param hookChainBuilder hook chain builder
     * @param elements         iterable of Selenium elements
     * @return FluentList of FluentWebElement
     */
    @Deprecated
    public static FluentList<FluentWebElement> fromElements(ComponentInstantiator instantiator, HookChainBuilder hookChainBuilder, Iterable<WebElement> elements) {
        return instantiator.asFluentList(FluentWebElement.class, elements);
    }

    @Override
    public List<WebElement> toElements() {
        ArrayList<WebElement> elements = new ArrayList<>();

        for (FluentWebElement fluentElement : this) {
            elements.add(fluentElement.getElement());
        }
        return elements;
    }

    @Override
    public E first() {
        if (!LocatorProxies.isLoaded(proxy)) {
            WebElement first = LocatorProxies.first(proxy);
            LocatorProxies.setHooks(first, hookChainBuilder, hookDefinitions);
            return instantiator.newComponent(componentClass, first);
        }
        if (this.size() == 0) {
            throw new NoSuchElementException("Element not found");
        }
        return get(0);
    }

    @Override
    public E last() {
        if (!LocatorProxies.isLoaded(proxy)) {
            WebElement last = LocatorProxies.last(proxy);
            LocatorProxies.setHooks(last, hookChainBuilder, hookDefinitions);
            return instantiator.newComponent(componentClass, last);
        }
        if (this.size() == 0) {
            throw new NoSuchElementException("Element not found");
        }
        return get(this.size() - 1);
    }

    @Override
    public E index(int index) {
        if (!LocatorProxies.isLoaded(proxy)) {
            WebElement indexElement = LocatorProxies.index(proxy, index);
            LocatorProxies.setHooks(indexElement, hookChainBuilder, hookDefinitions);
            return instantiator.newComponent(componentClass, indexElement);
        }
        if (this.size() <= index) {
            throw new NoSuchElementException("Element not found");
        }
        return get(index);
    }

    @Override
    public boolean isPresent() {
        if (LocatorProxies.getLocatorHandler(proxy) != null) {
            return LocatorProxies.isPresent(this);
        }
        return this.size() > 0;
    }

    @Override
    public FluentList<E> now() {
        LocatorProxies.now(this);
        if (this.size() == 0) {
            throw new NoSuchElementException("Element not found");
        }
        return this;
    }

    @Override
    public FluentList<E> reset() {
        LocatorProxies.reset(this);
        return this;
    }

    @Override
    public boolean isLoaded() {
        return LocatorProxies.isLoaded(this);
    }

    @Override
    public FluentList click() {
        if (this.size() == 0) {
            throw new NoSuchElementException("Element not found");
        }

        for (E fluentWebElement : this) {
            if (fluentWebElement.isEnabled()) {
                fluentWebElement.click();
            }
        }
        return this;
    }

    @Override
    public FluentList text(String... with) {
        if (this.size() == 0) {
            throw new NoSuchElementException("Element not found");
        }

        boolean atMostOne = false;
        if (with.length > 0) {
            int id = 0;
            String value;

            for (E fluentWebElement : this) {
                if (fluentWebElement.isDisplayed()) {
                    if (with.length > id) {
                        value = with[id++];
                    } else {
                        value = with[with.length - 1];
                    }
                    if (fluentWebElement.isEnabled()) {
                        atMostOne = true;
                        fluentWebElement.text(value);
                    }
                }
            }

            if (!atMostOne) {
                throw new NoSuchElementException("No element is displayed and enabled. Can't set a new value.");
            }
        }
        return this;
    }

    @Override
    public FluentList<E> clearAll() {
        if (this.size() == 0) {
            throw new NoSuchElementException("Element no found");
        }

        for (E fluentWebElement : this) {
            if (fluentWebElement.isEnabled()) {
                fluentWebElement.clear();
            }
        }

        return this;
    }

    @Override
    public void clearList() {
        list.clear();
    }

    @Override
    public FluentListConditions each() {
        return new EachElementConditions(this);
    }

    @Override
    public FluentListConditions one() {
        return new AtLeastOneElementConditions(this);
    }

    @Override
    public FluentList<E> submit() {
        if (this.size() == 0) {
            throw new NoSuchElementException("Element no found");
        }

        for (E fluentWebElement : this) {
            if (fluentWebElement.isEnabled()) {
                fluentWebElement.submit();
            }
        }
        return this;
    }

    @Override
    public List<String> getValues() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getValue();
            }
        });
    }

    @Override
    public List<String> getIds() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getId();
            }
        });
    }

    @Override
    public List<String> getAttributes(final String attribute) {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getAttribute(attribute);
            }
        });
    }

    @Override
    public List<String> getNames() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getName();
            }
        });
    }

    @Override
    public List<String> getTagNames() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getTagName();
            }
        });
    }

    @Override
    public List<String> getTextContents() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getTextContent();
            }
        });
    }

    @Override
    public List<String> getTexts() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getText();
            }
        });
    }

    @Override
    public String getValue() {
        if (this.size() > 0) {
            return this.get(0).getValue();
        }
        return null;
    }

    @Override
    public String getId() {
        if (this.size() > 0) {
            return this.get(0).getId();
        }
        return null;
    }

    @Override
    public String getAttribute(final String attribute) {
        if (this.size() > 0) {
            return this.get(0).getAttribute(attribute);
        }
        return null;
    }

    @Override
    public String getName() {
        if (this.size() > 0) {
            return this.get(0).getName();
        }
        return null;
    }

    @Override
    public String getTagName() {
        if (this.size() > 0) {
            return this.get(0).getTagName();
        }
        return null;
    }

    @Override
    public String getText() {
        if (this.size() > 0) {
            return this.get(0).getText();
        }
        return null;
    }

    @Override
    public String getTextContent() {
        if (this.size() > 0) {
            return this.get(0).getTextContent();
        }
        return null;
    }

    @Override
    public FluentList<E> $(String selector, Filter... filters) {
        return find(selector, filters);
    }

    @Override
    public FluentList<E> $(Filter... filters) {
        return find(filters);
    }

    @Override
    public FluentList<E> $(By locator, Filter... filters) {
        return find(locator, filters);
    }

    @Override
    public E $(Integer index, Filter... filters) {
        return find(index, filters);
    }

    @Override
    public FluentList<E> find(String selector, Filter... filters) {
        List<E> finds = new ArrayList<>();
        for (FluentWebElement e : this) {
            finds.addAll((Collection<E>) e.find(selector, filters));
        }
        return instantiator.newComponentList(getClass(), componentClass, finds);
    }

    @Override
    public FluentList<E> find(By locator, Filter... filters) {
        List<E> finds = new ArrayList<E>();
        for (FluentWebElement e : this) {
            finds.addAll((Collection<E>) e.find(locator, filters));
        }
        return instantiator.newComponentList(getClass(), componentClass, finds);
    }

    @Override
    public FluentList<E> find(Filter... filters) {
        List<E> finds = new ArrayList<E>();
        for (FluentWebElement e : this) {
            finds.addAll((Collection<E>) e.find(filters));
        }
        return instantiator.newComponentList(getClass(), componentClass, finds);
    }

    @Override
    public E find(String selector, Integer number, Filter... filters) {
        FluentList<E> fluentList = find(selector, filters);
        if (number >= fluentList.size()) {
            throw new NoSuchElementException(
                    "No such element with position: " + number + ". Number of elements available: " + fluentList.size()
                            + ". Selector: " + selector + ".");
        }
        return fluentList.get(number);
    }

    @Override
    public E find(By locator, Integer index, Filter... filters) {
        FluentList<E> fluentList = find(locator, filters);
        if (index >= fluentList.size()) {
            throw new NoSuchElementException(
                    "No such element with position: " + index + ". Number of elements available: " + fluentList
                            .size());
        }
        return fluentList.get(index);
    }

    @Override
    public E $(String selector, Integer index, Filter... filters) {
        return find(selector, index, filters);
    }

    @Override
    public E $(By locator, Integer index, Filter... filters) {
        return find(locator, index, filters);
    }

    @Override
    public E find(Integer index, Filter... filters) {
        FluentList<E> fluentList = find(filters);
        if (index >= fluentList.size()) {
            throw new NoSuchElementException(
                    "No such element with position: " + index + ". Number of elements available: " + fluentList.size()
                            + ".");
        }
        return fluentList.get(index);
    }

    @Override
    public E findFirst(By locator, Filter... filters) {
        return find(locator, 0, filters);
    }

    @Override
    public E findFirst(String selector, Filter... filters) {
        return find(selector, 0, filters);
    }

    @Override
    public E findFirst(Filter... filters) {
        return find(0, filters);
    }

    /**
     * Construct a FillConstructor in order to allow easy fill
     * Be careful - only the visible elements are filled
     *
     * @return fill constructor
     */
    public Fill fill() {
        return new Fill((FluentList<E>) this);
    }

    /**
     * Construct a FillSelectConstructor in order to allow easy list selection
     * Be careful - only the visible elements are filled
     *
     * @return fill constructor
     */
    public FillSelect fillSelect() {
        return new FillSelect(this);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> as(Class<T> componentClass) {
        List<T> elements = new ArrayList<>();

        for (E e : this) {
            elements.add(e.as(componentClass));
        }

        return instantiator.newComponentList(getClass(), componentClass, elements);
    }

    @Override
    public FluentList<E> noHook() {
        hookDefinitions.clear();
        LocatorProxies.setHooks(proxy, hookChainBuilder, hookDefinitions);
        return this;
    }

    @Override
    public <O, H extends FluentHook<O>> FluentList<E> withHook(Class<H> hook) {
        hookDefinitions.add(new HookDefinition<>(hook));
        LocatorProxies.setHooks(proxy, hookChainBuilder, hookDefinitions);
        return this;
    }

    @Override
    public <O, H extends FluentHook<O>> FluentList<E> withHook(Class<H> hook, O options) {
        hookDefinitions.add(new HookDefinition<>(hook, options));
        LocatorProxies.setHooks(proxy, hookChainBuilder, hookDefinitions);
        return this;
    }


    @Override
    public void clear() {
        clearAll();
    }
}

