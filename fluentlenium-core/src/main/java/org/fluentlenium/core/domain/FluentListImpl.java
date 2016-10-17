package org.fluentlenium.core.domain;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import lombok.experimental.Delegate;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.action.Fill;
import org.fluentlenium.core.action.FillSelect;
import org.fluentlenium.core.action.FluentJavascriptActionsImpl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.conditions.AtLeastOneElementConditions;
import org.fluentlenium.core.conditions.EachElementConditions;
import org.fluentlenium.core.conditions.FluentListConditions;
import org.fluentlenium.core.conditions.wait.WaitConditionProxy;
import org.fluentlenium.core.hook.HookControl;
import org.fluentlenium.core.hook.HookControlImpl;
import org.fluentlenium.core.hook.HookDefinition;
import org.fluentlenium.core.label.FluentLabel;
import org.fluentlenium.core.label.FluentLabelImpl;
import org.fluentlenium.core.proxy.LocatorHandler;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.fluentlenium.core.search.SearchFilter;
import org.fluentlenium.core.wait.FluentWaitElementList;
import org.fluentlenium.utils.SupplierOfInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Map the list to a FluentList in order to offers some events like click(), submit(), value() ...
 *
 * @param <E> type of fluent element
 */
@SuppressWarnings({"PMD.GodClass", "PMD.ExcessivePublicCount"})
public class FluentListImpl<E extends FluentWebElement> extends ComponentList<E> implements FluentList<E> {
    private final FluentLabelImpl<FluentList<E>> label;

    private final HookControlImpl<FluentList<E>> hookControl;

    private final FluentJavascriptActionsImpl<FluentList<E>> javascriptActions;

    /**
     * Creates a new fluent list.
     *
     * @param componentClass component class
     * @param list           list of fluent element
     * @param control        control interface
     * @param instantiator   component instantiator
     */
    public FluentListImpl(final Class<E> componentClass, final List<E> list, final FluentControl control,
            final ComponentInstantiator instantiator) {
        super(componentClass, list, control, instantiator);
        this.hookControl = new HookControlImpl<>(this, proxy, control, instantiator, new Supplier<FluentList<E>>() {
            @Override
            public FluentList<E> get() {
                final LocatorHandler locatorHandler = LocatorProxies.getLocatorHandler(proxy);
                final ElementLocator locator = locatorHandler.getLocator();
                final List<WebElement> webElementList = LocatorProxies.createWebElementList(locator);
                return instantiator.asComponentList(FluentListImpl.this.getClass(), componentClass, webElementList);
            }
        });
        this.label = new FluentLabelImpl<>(this, new Supplier<String>() {
            @Override
            public String get() {
                return list.toString();
            }
        });
        this.javascriptActions = new FluentJavascriptActionsImpl<>(this, this.control, new Supplier<FluentWebElement>() {
            @Override
            public FluentWebElement get() {
                return first();
            }

            @Override
            public String toString() {
                return String.valueOf(first());
            }
        });
    }

    @Delegate
    private FluentLabel<FluentList<E>> getLabel() {
        return label;
    }

    @Delegate
    private HookControl<FluentList<E>> getHookControl() { //NOPMD UnusedPrivateMethod
        return hookControl;
    }

    @Delegate
    private FluentJavascriptActionsImpl<FluentList<E>> getJavascriptActions() { //NOPMD UnusedPrivateMethod
        return javascriptActions;
    }

    @Override
    public List<WebElement> toElements() {
        final ArrayList<WebElement> elements = new ArrayList<>();

        for (final FluentWebElement fluentElement : this) {
            elements.add(fluentElement.getElement());
        }
        return elements;
    }

    @Override
    public FluentWaitElementList await() {
        return new FluentWaitElementList(control.await(), this);
    }

    @Override
    public E first() {
        if (!LocatorProxies.loaded(proxy)) {
            final E component = instantiator.newComponent(componentClass, LocatorProxies.first(proxy));
            if (component instanceof FluentLabel) {
                component.withLabel(label.getLabel());
                component.withLabelHint(label.getLabelHints());
            }
            if (component instanceof HookControl) {
                for (final HookDefinition definition : hookControl.getHookDefinitions()) {
                    component.withHook(definition.getHookClass(), definition.getOptions());
                }
            }
            return component;
        }
        if (this.size() == 0) {
            throw LocatorProxies.noSuchElement(proxy);
        }
        return get(0);
    }

    @Override
    public E last() {
        if (!LocatorProxies.loaded(proxy)) {
            final E component = instantiator.newComponent(componentClass, LocatorProxies.last(proxy));
            if (component instanceof FluentLabel) {
                component.withLabel(label.getLabel());
                component.withLabelHint(label.getLabelHints());
            }
            if (component instanceof HookControl) {
                for (final HookDefinition definition : hookControl.getHookDefinitions()) {
                    component.withHook(definition.getHookClass(), definition.getOptions());
                }
            }
            return component;
        }
        if (this.size() == 0) {
            throw LocatorProxies.noSuchElement(proxy);
        }
        return get(this.size() - 1);
    }

    @Override
    public E index(final int index) {
        if (!LocatorProxies.loaded(proxy)) {
            final E component = instantiator.newComponent(componentClass, LocatorProxies.index(proxy, index));
            if (component instanceof FluentLabel) {
                component.withLabel(label.getLabel());
                component.withLabelHint(label.getLabelHints());
            }
            if (component instanceof HookControl) {
                for (final HookDefinition definition : hookControl.getHookDefinitions()) {
                    component.withHook(definition.getHookClass(), definition.getOptions());
                }
            }
            if (component instanceof FluentWebElement) {
                component.setHookRestoreStack(hookControl.getHookRestoreStack());
            }
            return component;
        }
        if (this.size() <= index) {
            throw LocatorProxies.noSuchElement(proxy);
        }
        return get(index);
    }

    @Override
    public boolean present() {
        if (LocatorProxies.getLocatorHandler(proxy) != null) {
            return LocatorProxies.present(this);
        }
        return this.size() > 0;
    }

    @Override
    public FluentList<E> now() {
        LocatorProxies.now(this);
        if (this.size() == 0) {
            throw LocatorProxies.noSuchElement(proxy);
        }
        return this;
    }

    @Override
    public FluentList<E> now(final boolean force) {
        if (force) {
            reset();
        }
        return now();
    }

    @Override
    public FluentList<E> reset() {
        LocatorProxies.reset(this);
        return this;
    }

    @Override
    public boolean loaded() {
        return LocatorProxies.loaded(this);
    }

    @Override
    public FluentList click() {
        if (this.size() == 0) {
            throw LocatorProxies.noSuchElement(proxy);
        }

        boolean atLeastOne = false;
        for (final E fluentWebElement : this) {
            if (fluentWebElement.conditions().clickable()) {
                atLeastOne = true;
                fluentWebElement.click();
            }
        }

        if (!atLeastOne) {
            throw new NoSuchElementException(LocatorProxies.getMessageContext(proxy) + " has no element clickable."
                    + " At least one element should be clickable to perform a click.");
        }

        return this;
    }

    @Override
    public FluentList doubleClick() {
        if (this.size() == 0) {
            throw LocatorProxies.noSuchElement(proxy);
        }

        boolean atLeastOne = false;
        for (final E fluentWebElement : this) {
            if (fluentWebElement.conditions().clickable()) {
                atLeastOne = true;
                fluentWebElement.doubleClick();
            }
        }

        if (!atLeastOne) {
            throw new NoSuchElementException(LocatorProxies.getMessageContext(proxy) + " has no element clickable."
                    + " At least one element should be clickable to perform a double click.");
        }

        return this;
    }

    @Override
    public FluentList<E> contextClick() {
        if (this.size() == 0) {
            throw LocatorProxies.noSuchElement(proxy);
        }

        boolean atLeastOne = false;
        for (final E fluentWebElement : this) {
            if (fluentWebElement.conditions().clickable()) {
                atLeastOne = true;
                fluentWebElement.contextClick();
            }
        }

        if (!atLeastOne) {
            throw new NoSuchElementException(LocatorProxies.getMessageContext(proxy) + " has no element clickable."
                    + " At least one element should be clickable to perform a context click.");
        }

        return this;
    }

    @Override
    public FluentList write(final String... with) {
        if (this.size() == 0) {
            throw LocatorProxies.noSuchElement(proxy);
        }

        boolean atLeastOne = false;
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
                        atLeastOne = true;
                        fluentWebElement.write(value);
                    }
                }
            }

            if (!atLeastOne) {
                throw new NoSuchElementException(
                        LocatorProxies.getMessageContext(proxy) + " has no element displayed and enabled."
                                + " At least one element should be displayed and enabled to define values.");
            }
        }
        return this;
    }

    @Override
    public FluentList<E> clearAll() {
        if (this.size() == 0) {
            throw LocatorProxies.noSuchElement(proxy);
        }

        boolean atLeastOne = false;
        for (final E fluentWebElement : this) {
            if (fluentWebElement.enabled()) {
                atLeastOne = true;
                fluentWebElement.clear();
            }
        }

        if (!atLeastOne) {
            throw new NoSuchElementException(LocatorProxies.getMessageContext(proxy) + " has no element enabled."
                    + " At least one element should be enabled to clear values.");
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
    public FluentListConditions awaitUntilEach() {
        return WaitConditionProxy
                .each(control.await(), this.toString(), new SupplierOfInstance<List<? extends FluentWebElement>>(this));
    }

    @Override
    public FluentListConditions awaitUntilOne() {
        return WaitConditionProxy
                .one(control.await(), this.toString(), new SupplierOfInstance<List<? extends FluentWebElement>>(this));
    }

    @Override
    public FluentList<E> submit() {
        if (this.size() == 0) {
            throw LocatorProxies.noSuchElement(proxy);
        }

        boolean atLeastOne = false;
        for (final E fluentWebElement : this) {
            if (fluentWebElement.enabled()) {
                atLeastOne = true;
                fluentWebElement.submit();
            }
        }

        if (!atLeastOne) {
            throw new NoSuchElementException(LocatorProxies.getMessageContext(proxy) + " has no element enabled."
                    + " At least one element should be enabled to perform a submit.");
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
    public FluentList<E> $(final String selector, final SearchFilter... filters) {
        return find(selector, filters);
    }

    @Override
    public E el(final String selector, final SearchFilter... filters) {
        return find(selector, filters).first();
    }

    @Override
    public FluentList<E> $(final SearchFilter... filters) {
        return find(filters);
    }

    @Override
    public E el(final SearchFilter... filters) {
        return find(filters).first();
    }

    @Override
    public FluentList<E> $(final By locator, final SearchFilter... filters) {
        return find(locator, filters);
    }

    @Override
    public E el(final By locator, final SearchFilter... filters) {
        return find(locator, filters).first();
    }

    @Override
    public FluentList<E> find(final String selector, final SearchFilter... filters) {
        final List<E> finds = new ArrayList<>();
        for (final FluentWebElement e : this) {
            finds.addAll((Collection<E>) e.find(selector, filters));
        }
        return instantiator.newComponentList(getClass(), componentClass, finds);
    }

    @Override
    public FluentList<E> find(final By locator, final SearchFilter... filters) {
        final List<E> finds = new ArrayList<>();
        for (final FluentWebElement e : this) {
            finds.addAll((Collection<E>) e.find(locator, filters));
        }
        return instantiator.newComponentList(getClass(), componentClass, finds);
    }

    @Override
    public FluentList<E> find(final SearchFilter... filters) {
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
    public FluentList<E> frame() {
        control.window().switchTo().frame(first());
        return this;
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
    public void clear() {
        clearAll();
    }

    @Override
    public String toString() {
        return label.toString();
    }
}

