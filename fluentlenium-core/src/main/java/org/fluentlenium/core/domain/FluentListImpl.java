package org.fluentlenium.core.domain;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import lombok.experimental.Delegate;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.action.Fill;
import org.fluentlenium.core.action.FillSelect;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.conditions.AtLeastOneElementConditions;
import org.fluentlenium.core.conditions.EachElementConditions;
import org.fluentlenium.core.conditions.FluentListConditions;
import org.fluentlenium.core.conditions.wait.WaitConditionProxy;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.hook.DefaultHookChainBuilder;
import org.fluentlenium.core.hook.FluentHook;
import org.fluentlenium.core.hook.HookChainBuilder;
import org.fluentlenium.core.hook.HookControl;
import org.fluentlenium.core.hook.HookDefinition;
import org.fluentlenium.core.label.FluentLabel;
import org.fluentlenium.core.label.FluentLabelImpl;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.fluentlenium.core.wait.FluentWaitElementList;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Map the list to a FluentList in order to offers some events like click(), submit(), value() ...
 */
public class FluentListImpl<E extends FluentWebElement> extends ComponentList<E> implements FluentList<E> {
    private final List<HookDefinition<?>> hookDefinitions = new ArrayList<>();
    private final HookChainBuilder hookChainBuilder;

    private List<HookDefinition<?>> hookDefinitionsBackup;

    private final FluentLabelImpl<FluentListImpl<E>> label;

    public FluentListImpl(final Class<E> componentClass, final List<E> list, final FluentControl fluentControl,
            final ComponentInstantiator instantiator) {
        super(componentClass, list, fluentControl, instantiator);
        this.hookChainBuilder = new DefaultHookChainBuilder(fluentControl, instantiator);
        this.label = new FluentLabelImpl<>(this, new Supplier<String>() {
            @Override
            public String get() {
                return list.toString();
            }
        });
    }

    @Delegate
    private FluentLabel<FluentListImpl<E>> getLabel() {
        return label;
    }

    @Override
    public List<WebElement> toElements() {
        final ArrayList<WebElement> elements = new ArrayList<>();

        for (final FluentWebElement fluentElement : this) {
            elements.add(fluentElement.getElement());
        }
        return elements;
    }

    public FluentWaitElementList await() {
        return new FluentWaitElementList(fluentControl.await(), this);
    }

    @Override
    public E first() {
        if (!LocatorProxies.isLoaded(proxy)) {
            final E component = instantiator.newComponent(componentClass, LocatorProxies.first(proxy));
            if (component instanceof FluentLabel) {
                component.withLabel(label.getLabel());
                component.withLabelHint(label.getLabelHints());
            }
            if (component instanceof HookControl) {
                for (HookDefinition definition : hookDefinitions) {
                    component.withHook(definition.getHookClass(), definition.getOptions());
                }
            }
            return component;
        }
        if (this.size() == 0) {
            throw new NoSuchElementException("Element not found");
        }
        return get(0);
    }

    @Override
    public E last() {
        if (!LocatorProxies.isLoaded(proxy)) {
            final E component = instantiator.newComponent(componentClass, LocatorProxies.last(proxy));
            if (component instanceof FluentLabel) {
                component.withLabel(label.getLabel());
                component.withLabelHint(label.getLabelHints());
            }
            if (component instanceof HookControl) {
                for (HookDefinition definition : hookDefinitions) {
                    component.withHook(definition.getHookClass(), definition.getOptions());
                }
            }
            return component;
        }
        if (this.size() == 0) {
            throw new NoSuchElementException("Element not found");
        }
        return get(this.size() - 1);
    }

    @Override
    public E index(final int index) {
        if (!LocatorProxies.isLoaded(proxy)) {
            final E component = instantiator.newComponent(componentClass, LocatorProxies.index(proxy, index));
            if (component instanceof FluentLabel) {
                component.withLabel(label.getLabel());
                component.withLabelHint(label.getLabelHints());
            }
            if (component instanceof HookControl) {
                for (HookDefinition definition : hookDefinitions) {
                    component.withHook(definition.getHookClass(), definition.getOptions());
                }
            }
            if (component instanceof FluentWebElement) {
                component.setHookDefinitionsBackup(hookDefinitionsBackup);
            }
            return component;
        }
        if (this.size() <= index) {
            throw new NoSuchElementException("Element not found");
        }
        return get(index);
    }

    @Override
    public boolean present() {
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
    public boolean loaded() {
        return LocatorProxies.isLoaded(this);
    }

    @Override
    public FluentList click() {
        if (this.size() == 0) {
            throw new NoSuchElementException("Element not found");
        }

        for (final E fluentWebElement : this) {
            if (fluentWebElement.enabled()) {
                fluentWebElement.click();
            }
        }
        return this;
    }

    @Override
    public FluentList write(final String... with) {
        if (this.size() == 0) {
            throw new NoSuchElementException("Element not found");
        }

        boolean atMostOne = false;
        if (with.length > 0) {
            int id = 0;
            String value;

            for (final E fluentWebElement : this) {
                if (fluentWebElement.displayed()) {
                    if (with.length > id) {
                        value = with[id++];
                    } else {
                        value = with[with.length - 1];
                    }
                    if (fluentWebElement.enabled()) {
                        atMostOne = true;
                        fluentWebElement.write(value);
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

        for (final E fluentWebElement : this) {
            if (fluentWebElement.enabled()) {
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

    public FluentListConditions awaitUntilEach() {
        return WaitConditionProxy.each(fluentControl.await(), this.toString(), Suppliers.ofInstance(this));
    }

    @Override
    public FluentListConditions one() {
        return new AtLeastOneElementConditions(this);
    }

    public FluentListConditions awaitUntilOne() {
        return WaitConditionProxy.one(fluentControl.await(), this.toString(), Suppliers.ofInstance(this));
    }

    @Override
    public FluentList<E> submit() {
        if (this.size() == 0) {
            throw new NoSuchElementException("Element no found");
        }

        for (final E fluentWebElement : this) {
            if (fluentWebElement.enabled()) {
                fluentWebElement.submit();
            }
        }
        return this;
    }

    @Override
    public List<String> values() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(final E webElement) {
                return webElement.value();
            }
        });
    }

    @Override
    public List<String> ids() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(final E webElement) {
                return webElement.id();
            }
        });
    }

    @Override
    public List<String> attributes(final String attribute) {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(final E webElement) {
                return webElement.attribute(attribute);
            }
        });
    }

    @Override
    public List<String> names() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(final E webElement) {
                return webElement.name();
            }
        });
    }

    @Override
    public List<String> tagNames() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(final E webElement) {
                return webElement.tagName();
            }
        });
    }

    @Override
    public List<String> textContents() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(final E webElement) {
                return webElement.textContent();
            }
        });
    }

    @Override
    public List<String> texts() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(final E webElement) {
                return webElement.text();
            }
        });
    }

    @Override
    public String value() {
        if (this.size() > 0) {
            return this.get(0).value();
        }
        return null;
    }

    @Override
    public String id() {
        if (this.size() > 0) {
            return this.get(0).id();
        }
        return null;
    }

    @Override
    public String attribute(final String attribute) {
        if (this.size() > 0) {
            return this.get(0).attribute(attribute);
        }
        return null;
    }

    @Override
    public String name() {
        if (this.size() > 0) {
            return this.get(0).name();
        }
        return null;
    }

    @Override
    public String tagName() {
        if (this.size() > 0) {
            return this.get(0).tagName();
        }
        return null;
    }

    @Override
    public String text() {
        if (this.size() > 0) {
            return this.get(0).text();
        }
        return null;
    }

    @Override
    public String textContent() {
        if (this.size() > 0) {
            return this.get(0).textContent();
        }
        return null;
    }

    @Override
    public FluentList<E> $(final String selector, final Filter... filters) {
        return find(selector, filters);
    }

    @Override
    public E el(final String selector, final Filter... filters) {
        return find(selector, filters).first();
    }

    @Override
    public FluentList<E> $(final Filter... filters) {
        return find(filters);
    }

    @Override
    public E el(final Filter... filters) {
        return find(filters).first();
    }

    @Override
    public FluentList<E> $(final By locator, final Filter... filters) {
        return find(locator, filters);
    }

    @Override
    public E el(final By locator, final Filter... filters) {
        return find(locator, filters).first();
    }

    @Override
    public FluentList<E> find(final String selector, final Filter... filters) {
        final List<E> finds = new ArrayList<>();
        for (final FluentWebElement e : this) {
            finds.addAll((Collection<E>) e.find(selector, filters));
        }
        return instantiator.newComponentList(getClass(), componentClass, finds);
    }

    @Override
    public FluentList<E> find(final By locator, final Filter... filters) {
        final List<E> finds = new ArrayList<>();
        for (final FluentWebElement e : this) {
            finds.addAll((Collection<E>) e.find(locator, filters));
        }
        return instantiator.newComponentList(getClass(), componentClass, finds);
    }

    @Override
    public FluentList<E> find(final Filter... filters) {
        final List<E> finds = new ArrayList<>();
        for (final FluentWebElement e : this) {
            finds.addAll((Collection<E>) e.find(filters));
        }
        return instantiator.newComponentList(getClass(), componentClass, finds);
    }

    @Override
    public Fill fill() {
        return new Fill((FluentList<E>) this);
    }

    @Override
    public FillSelect fillSelect() {
        return new FillSelect(this);
    }

    @Override
    public Optional<FluentList<E>> optional() {
        if (present()) {
            return Optional.of((FluentList<E>) this);
        } else {
            return Optional.absent();
        }
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> as(final Class<T> componentClass) {
        final List<T> elements = new ArrayList<>();

        for (final E e : this) {
            elements.add(e.as(componentClass));
        }

        return instantiator.newComponentList(getClass(), componentClass, elements);
    }

    @Override
    public <R> R noHook(final Function<FluentList<E>, R> function) {
        noHook();
        R functionReturn = function.apply(this);
        restoreHooks();
        return functionReturn;
    }

    @Override
    public FluentList<E> noHook() {
        hookDefinitionsBackup = new ArrayList<>(hookDefinitions);
        hookDefinitions.clear();
        LocatorProxies.setHooks(proxy, hookChainBuilder, hookDefinitions);
        return this;
    }

    @Override
    public FluentList<E> restoreHooks() {
        if (hookDefinitionsBackup != null) {
            hookDefinitions.clear();
            hookDefinitions.addAll(hookDefinitionsBackup);
            hookDefinitionsBackup = null;
        }
        LocatorProxies.setHooks(proxy, hookChainBuilder, hookDefinitions);
        return this;
    }

    @Override
    public <O, H extends FluentHook<O>> FluentList<E> withHook(final Class<H> hook) {
        hookDefinitions.add(new HookDefinition<>(hook));
        LocatorProxies.setHooks(proxy, hookChainBuilder, hookDefinitions);
        return this;
    }

    @Override
    public <O, H extends FluentHook<O>> FluentList<E> withHook(final Class<H> hook, final O options) {
        hookDefinitions.add(new HookDefinition<>(hook, options));
        LocatorProxies.setHooks(proxy, hookChainBuilder, hookDefinitions);
        return this;
    }

    @Override
    public void clear() {
        clearAll();
    }

    @Override
    public String toString() {
        return label.toString();
    }
}

