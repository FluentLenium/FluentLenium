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
    public FluentListImpl(Class<E> componentClass, List<E> list, FluentControl control,
            ComponentInstantiator instantiator) {
        super(componentClass, list, control, instantiator);
        hookControl = new HookControlImpl<>(this, proxy, control, instantiator, (Supplier<FluentList<E>>) () -> {
            LocatorHandler locatorHandler = LocatorProxies.getLocatorHandler(proxy);
            ElementLocator locator = locatorHandler.getLocator();
            List<WebElement> webElementList = LocatorProxies.createWebElementList(locator);
            return instantiator.asComponentList(this.getClass(), componentClass, webElementList);
        });
        label = new FluentLabelImpl<>(this, list::toString);
        javascriptActions = new FluentJavascriptActionsImpl<>(this, this.control, new Supplier<FluentWebElement>() {
            @Override
            public FluentList<E> get() {
                LocatorHandler locatorHandler = LocatorProxies.getLocatorHandler(proxy);
                ElementLocator locator = locatorHandler.getLocator();
                List<WebElement> webElementList = LocatorProxies.createWebElementList(locator);
                return instantiator.asComponentList(FluentListImpl.this.getClass(), componentClass, webElementList);
            }
        });
        label = new FluentLabelImpl<FluentList<E>>(this, new Supplier<String>() {
            @Override
            public String get() {
                return list.toString();
            }
        });
        javascriptActions = new FluentJavascriptActionsImpl<FluentList<E>>(this, this.control,
                new Supplier<FluentWebElement>() {
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
        ArrayList<WebElement> elements = new ArrayList<>();

        for (FluentWebElement fluentElement : this) {
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
            E component = instantiator.newComponent(componentClass, LocatorProxies.first(proxy));
            if (component instanceof FluentLabel) {
                component.withLabel(label.getLabel());
                component.withLabelHint(label.getLabelHints());
            }
            if (component instanceof HookControl) {
                for (HookDefinition definition : hookControl.getHookDefinitions()) {
                    component.withHook(definition.getHookClass(), definition.getOptions());
                }
            }
            return component;
        }
        if (size() == 0) {
            throw LocatorProxies.noSuchElement(proxy);
        }
        return get(0);
    }

    @Override
    public E last() {
        if (!LocatorProxies.loaded(proxy)) {
            E component = instantiator.newComponent(componentClass, LocatorProxies.last(proxy));
            if (component instanceof FluentLabel) {
                component.withLabel(label.getLabel());
                component.withLabelHint(label.getLabelHints());
            }
            if (component instanceof HookControl) {
                for (HookDefinition definition : hookControl.getHookDefinitions()) {
                    component.withHook(definition.getHookClass(), definition.getOptions());
                }
            }
            return component;
        }
        if (size() == 0) {
            throw LocatorProxies.noSuchElement(proxy);
        }
        return get(size() - 1);
    }

    @Override
    public E index(int index) {
        if (!LocatorProxies.loaded(proxy)) {
            E component = instantiator.newComponent(componentClass, LocatorProxies.index(proxy, index));
            if (component instanceof FluentLabel) {
                component.withLabel(label.getLabel());
                component.withLabelHint(label.getLabelHints());
            }
            if (component instanceof HookControl) {
                for (HookDefinition definition : hookControl.getHookDefinitions()) {
                    component.withHook(definition.getHookClass(), definition.getOptions());
                }
            }
            if (component instanceof FluentWebElement) {
                component.setHookRestoreStack(hookControl.getHookRestoreStack());
            }
            return component;
        }
        if (size() <= index) {
            throw LocatorProxies.noSuchElement(proxy);
        }
        return get(index);
    }

    @Override
    public int count() {
        if (loaded()) {
            return super.size();
        } else {
            return LocatorProxies.getLocatorHandler(proxy).getLocator().findElements().size();
        }
    }

    @Override
    public boolean present() {
        if (LocatorProxies.getLocatorHandler(proxy) != null) {
            return LocatorProxies.present(this);
        }
        return size() > 0;
    }

    @Override
    public FluentList<E> now() {
        LocatorProxies.now(this);
        if (size() == 0) {
            throw LocatorProxies.noSuchElement(proxy);
        }
        return this;
    }

    @Override
    public FluentList<E> now(boolean force) {
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
        if (size() == 0) {
            throw LocatorProxies.noSuchElement(proxy);
        }

        boolean atLeastOne = false;
        for (E fluentWebElement : this) {
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
        if (size() == 0) {
            throw LocatorProxies.noSuchElement(proxy);
        }

        boolean atLeastOne = false;
        for (E fluentWebElement : this) {
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
        if (size() == 0) {
            throw LocatorProxies.noSuchElement(proxy);
        }

        boolean atLeastOne = false;
        for (E fluentWebElement : this) {
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
    public FluentList write(String... with) {
        if (size() == 0) {
            throw LocatorProxies.noSuchElement(proxy);
        }

        boolean atLeastOne = false;
        if (with.length > 0) {
            int id = 0;
            String value;

            for (E fluentWebElement : this) {
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
        if (size() == 0) {
            throw LocatorProxies.noSuchElement(proxy);
        }

        boolean atLeastOne = false;
        for (E fluentWebElement : this) {
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
                .each(control.await(), toString(), new SupplierOfInstance<List<? extends FluentWebElement>>(this));
    }

    @Override
    public FluentListConditions awaitUntilOne() {
        return WaitConditionProxy
                .one(control.await(), toString(), new SupplierOfInstance<List<? extends FluentWebElement>>(this));
    }

    @Override
    public FluentList<E> submit() {
        if (size() == 0) {
            throw LocatorProxies.noSuchElement(proxy);
        }

        boolean atLeastOne = false;
        for (E fluentWebElement : this) {
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
        return stream().map(FluentWebElement::value).collect(Collectors.toList());
    }

    @Override
    public List<String> ids() {
        return stream().map(FluentWebElement::id).collect(Collectors.toList());
    }

    @Override
    public List<String> attributes(final String attribute) {
        return stream().map(webElement -> webElement.attribute(attribute)).collect(Collectors.toList());
    }

    @Override
    public List<String> names() {
        return stream().map(FluentWebElement::name).collect(Collectors.toList());
    }

    @Override
    public List<String> tagNames() {
        return stream().map(FluentWebElement::tagName).collect(Collectors.toList());
    }

    @Override
    public List<String> textContents() {
        return stream().map(FluentWebElement::textContent).collect(Collectors.toList());
    }

    @Override
    public List<String> texts() {
        return stream().map(FluentWebElement::text).collect(Collectors.toList());
    }

    @Override
    public String value() {
        if (size() > 0) {
            return get(0).value();
        }
        return null;
    }

    @Override
    public String id() {
        if (size() > 0) {
            return get(0).id();
        }
        return null;
    }

    @Override
    public String attribute(String attribute) {
        if (size() > 0) {
            return get(0).attribute(attribute);
        }
        return null;
    }

    @Override
    public String name() {
        if (size() > 0) {
            return get(0).name();
        }
        return null;
    }

    @Override
    public String tagName() {
        if (size() > 0) {
            return get(0).tagName();
        }
        return null;
    }

    @Override
    public String text() {
        if (size() > 0) {
            return get(0).text();
        }
        return null;
    }

    @Override
    public String textContent() {
        if (size() > 0) {
            return get(0).textContent();
        }
        return null;
    }

    @Override
    public FluentList<E> $(String selector, SearchFilter... filters) {
        return find(selector, filters);
    }

    @Override
    public E el(String selector, SearchFilter... filters) {
        return find(selector, filters).first();
    }

    @Override
    public FluentList<E> $(SearchFilter... filters) {
        return find(filters);
    }

    @Override
    public E el(SearchFilter... filters) {
        return find(filters).first();
    }

    @Override
    public FluentList<E> $(By locator, SearchFilter... filters) {
        return find(locator, filters);
    }

    @Override
    public E el(By locator, SearchFilter... filters) {
        return find(locator, filters).first();
    }

    @Override
    public FluentList<E> find(List<WebElement> rawElements) {
        return (FluentList<E>) control.find(rawElements);
    }

    @Override
    public FluentList<E> $(List<WebElement> rawElements) {
        return (FluentList<E>) control.$(rawElements);
    }

    @Override
    public E el(WebElement rawElement) {
        return (E) control.el(rawElement);
    }

    @Override
    public FluentList<E> find(String selector, SearchFilter... filters) {
        List<E> finds = new ArrayList<>();
        for (FluentWebElement e : this) {
            finds.addAll((Collection<E>) e.find(selector, filters));
        }
        return instantiator.newComponentList(getClass(), componentClass, finds);
    }

    @Override
    public FluentList<E> find(By locator, SearchFilter... filters) {
        List<E> finds = new ArrayList<>();
        for (FluentWebElement e : this) {
            finds.addAll((Collection<E>) e.find(locator, filters));
        }
        return instantiator.newComponentList(getClass(), componentClass, finds);
    }

    @Override
    public FluentList<E> find(SearchFilter... filters) {
        List<E> finds = new ArrayList<>();
        for (FluentWebElement e : this) {
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
    public <T extends FluentWebElement> FluentList<T> as(Class<T> componentClass) {
        List<T> elements = new ArrayList<>();

        for (E e : this) {
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

