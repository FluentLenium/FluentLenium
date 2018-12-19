package org.fluentlenium.core.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

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

import lombok.experimental.Delegate;

import static java.util.stream.Collectors.toList;

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
    public FluentListImpl(Class<E> componentClass, List<E> list, FluentControl control, ComponentInstantiator instantiator) {
        super(componentClass, list, control, instantiator);
        hookControl = new HookControlImpl<>(this, proxy, control, instantiator, (Supplier<FluentList<E>>) () -> {
            LocatorHandler locatorHandler = LocatorProxies.getLocatorHandler(proxy);
            ElementLocator locator = locatorHandler.getLocator();
            List<WebElement> webElementList = LocatorProxies.createWebElementList(locator);
            return instantiator.asComponentList(getClass(), componentClass, webElementList);
        });
        label = new FluentLabelImpl<>(this, list::toString);
        javascriptActions = new FluentJavascriptActionsImpl<>(this, this.control, new Supplier<>() {
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
        return this.stream().map(FluentWebElement::getElement).collect(toList());
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
    public E single() {
        if (size() > 1) {
            throw new AssertionError(String.format("%s list should contain one " +
                    "element only but there are [ %s ] elements instead",
                    LocatorProxies.getMessageContext(proxy), size()));
        }

        return first();
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
    public FluentList<E> click() {
        return doClick(FluentWebElement::click, "click");
    }

    @Override
    public FluentList<E> doubleClick() {
        return doClick(FluentWebElement::doubleClick, "double click");
    }

    @Override
    public FluentList<E> contextClick() {
        return doClick(FluentWebElement::contextClick, "context click");
    }

    private FluentList<E> doClick(Consumer<FluentWebElement> clickAction, String clickType) {
        validateListIsNotEmpty();

        boolean atLeastOne = false;
        for (E fluentWebElement : this) {
            if (fluentWebElement.conditions().clickable()) {
                atLeastOne = true;
                clickAction.accept(fluentWebElement);
            }
        }

        if (!atLeastOne) {
            throw new NoSuchElementException(LocatorProxies.getMessageContext(proxy)
                    + " has no element clickable. At least one element should be clickable to perform a " + clickType + ".");
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
        return stream().map(FluentWebElement::value).collect(toList());
    }

    @Override
    public List<String> ids() {
        return stream().map(FluentWebElement::id).collect(toList());
    }

    @Override
    public List<String> attributes(String attribute) {
        return stream().map(webElement -> webElement.attribute(attribute)).collect(toList());
    }

    @Override
    public List<String> names() {
        return stream().map(FluentWebElement::name).collect(toList());
    }

    @Override
    public List<String> tagNames() {
        return stream().map(FluentWebElement::tagName).collect(toList());
    }

    @Override
    public List<String> textContents() {
        return stream().map(FluentWebElement::textContent).collect(toList());
    }

    @Override
    public List<String> texts() {
        return stream().map(FluentWebElement::text).collect(toList());
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
        return findBy(e -> (Collection<E>) e.find(selector, filters));
    }

    @Override
    public FluentList<E> find(By locator, SearchFilter... filters) {
        return findBy(e -> (Collection<E>) e.find(locator, filters));
    }

    @Override
    public FluentList<E> find(SearchFilter... filters) {
        return findBy(e -> (Collection<E>) e.find(filters));
    }

    private FluentList<E> findBy(Function<FluentWebElement, Collection<E>> filteredElementsFinder) {
        List<E> finds = new ArrayList<>();
        for (FluentWebElement e : this) {
            finds.addAll(filteredElementsFinder.apply(e));
        }
        return instantiator.newComponentList(getClass(), componentClass, finds);
    }

    @Override
    public Fill fill() {
        return new Fill(this);
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
            return Optional.of(this);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> as(Class<T> componentClass) {
        return instantiator
                .newComponentList(getClass(), componentClass, this.stream().map(e -> e.as(componentClass)).collect(toList()));
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

