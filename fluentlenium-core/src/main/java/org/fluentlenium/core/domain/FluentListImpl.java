package org.fluentlenium.core.domain;

import static java.util.stream.Collectors.toList;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
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
import org.fluentlenium.core.hook.FluentHook;
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
        javascriptActions = new FluentJavascriptActionsImpl<>(this, this.control, new Supplier<FluentWebElement>() {
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

    private FluentLabel<FluentList<E>> getLabel() {
        return label;
    }

    private HookControl<FluentList<E>> getHookControl() { //NOPMD UnusedPrivateMethod
        return hookControl;
    }

    @Override
    public FluentWaitElementList await() {
        return new FluentWaitElementList(control.await(), this);
    }

    @Override
    public E first() {
        if (!LocatorProxies.loaded(proxy)) {
            E component = instantiator.newComponent(componentClass, LocatorProxies.first(proxy));
            configureComponentWithLabel(component);
            configureComponentWithHooks(component);
            return component;
        }
        validateListIsNotEmpty();
        return get(0);
    }

    @Override
    public E single() {
        if (size() > 1) {
            throw new AssertionError(
                    String.format("%s list should contain one element only but there are [ %s ] elements instead",
                            LocatorProxies.getMessageContext(proxy), size()));
        }

        return first();
    }

    @Override
    public E last() {
        if (!LocatorProxies.loaded(proxy)) {
            E component = instantiator.newComponent(componentClass, LocatorProxies.last(proxy));
            configureComponentWithLabel(component);
            configureComponentWithHooks(component);
            return component;
        }
        validateListIsNotEmpty();
        return get(size() - 1);
    }

    @Override
    public E get(int index) {
        return index(index);
    }

    @Override
    public E index(int index) {
        if (!LocatorProxies.loaded(proxy) && !componentClass.equals(FluentWebElement.class)) {
            E component = instantiator.newComponent(componentClass, LocatorProxies.index(proxy, index));
            configureComponentWithLabel(component);
            configureComponentWithHooks(component);
            if (component instanceof FluentWebElement) {
                component.setHookRestoreStack(hookControl.getHookRestoreStack());
            }
            return component.reset().as(componentClass);
        }
        if (size() <= index) {
            throw LocatorProxies.noSuchElement(proxy);
        }
        return super.get(index);
    }

    @Override
    public int count() {
        if (proxy != null) {
            LocatorHandler locatorHandler = LocatorProxies.getLocatorHandler(proxy);
            if (locatorHandler != null) {
                return locatorHandler.getLocator().findElements().size();
            }
        }
        return super.size();
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
        validateListIsNotEmpty();
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

    @Override
    public FluentList<E> waitAndClick() {
        return waitAndClick(Duration.ofSeconds(5));
    }

    @Override
    public FluentList<E> waitAndClick(Duration duration) {
        validateListIsNotEmpty();
        await().atMost(duration).until(this).clickable();
        this.scrollToCenter();
        this.click();
        return this;
    }

    @Override
    public FluentList<E> write(String... with) {
        validateListIsNotEmpty();

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
        return clearAllInputs(FluentWebElement::clear, "clear values");
    }

    @Override
    public FluentList<E> clearAllReactInputs() {
        return clearAllInputs(FluentWebElement::clearReactInput, "clear values by using backspace");
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
        return perform(FluentWebElement::submit, FluentWebElement::enabled,
                " has no element enabled. At least one element should be enabled to perform a submit.");
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

    private void configureComponentWithHooks(E component) {
        if (component instanceof HookControl) {
            for (HookDefinition definition : hookControl.getHookDefinitions()) {
                component.withHook(definition.getHookClass(), definition.getOptions());
            }
        }
    }

    private void configureComponentWithLabel(E component) {
        if (component instanceof FluentLabel) {
            component.withLabel(label.getLabel());
            component.withLabelHint(label.getLabelHints());
        }
    }

    private FluentList<E> doClick(Consumer<E> clickAction, String clickType) {
        return perform(clickAction, fluentWebElement -> fluentWebElement.conditions().clickable(),
                " has no element clickable. At least one element should be clickable to perform a " + clickType + ".");
    }

    private FluentList<E> clearAllInputs(Consumer<E> action, String actionMessage) {
        return perform(action, FluentWebElement::enabled,
                " has no element enabled. At least one element should be enabled to " + actionMessage + ".");
    }

    private FluentList<E> perform(Consumer<E> action, Predicate<E> condition, String message) {
        validateListIsNotEmpty();

        boolean atLeastOne = false;
        for (E fluentWebElement : this) {
            if (condition.test(fluentWebElement)) {
                atLeastOne = true;
                action.accept(fluentWebElement);
            }
        }

        if (!atLeastOne) {
            throw new NoSuchElementException(LocatorProxies.getMessageContext(proxy) + message);
        }

        return this;
    }

    private void validateListIsNotEmpty() {
        if (size() == 0) {
            throw LocatorProxies.noSuchElement(proxy);
        }
    }

    private FluentList<E> findBy(Function<FluentWebElement, Collection<E>> filteredElementsFinder) {
        List<E> finds = new ArrayList<>();
        for (FluentWebElement e : this) {
            finds.addAll(filteredElementsFinder.apply(e));
        }
        return instantiator.newComponentList(getClass(), componentClass, finds);
    }

    @Override
    public FluentList<E> withLabel(String label) {
        return getLabel().withLabel(label);
    }

    @Override
    public FluentList<E> withLabelHint(String... labelHint) {
        return getLabel().withLabelHint(labelHint);
    }

    @Override
    public FluentList<E> noHookInstance() {
        return getHookControl().noHookInstance();
    }

    @Override
    public FluentList<E> noHook() {
        return getHookControl().noHook();
    }

    @Override
    public <O, H extends FluentHook<O>> FluentList<E> withHook(Class<H> hook) {
        return getHookControl().withHook(hook);
    }

    @Override
    public <R> R noHook(Class<? extends FluentHook> hook, Function<FluentList<E>, R> function) {
        return getHookControl().noHook(hook, function);
    }

    @Override
    public FluentList<E> restoreHooks() {
        return getHookControl().restoreHooks();
    }

    @Override
    public <O, H extends FluentHook<O>> FluentList<E> withHook(Class<H> hook, O options) {
        return getHookControl().withHook(hook, options);
    }

    @Override
    public FluentList<E> noHook(Class<? extends FluentHook>... hooks) {
        return getHookControl().noHook(hooks);
    }

    @Override
    public FluentList<E> noHookInstance(Class<? extends FluentHook>... hooks) {
        return getHookControl().noHookInstance(hooks);
    }

    @Override
    public <R> R noHook(Function<FluentList<E>, R> function) {
        return getHookControl().noHook(function);
    }

    /**
     * Scrolls to first element of list
     *
     * @return this object reference to chain methods calls
     */
    @Override
    public FluentList<E> scrollToCenter() {
        return javascriptActions.scrollToCenter();
    }

    /**
     * Scrolls to first element of list
     *
     * @return this object reference to chain methods calls
     */
    @Override
    public FluentList<E> scrollIntoView(boolean alignWithTop) {
        return javascriptActions.scrollIntoView(alignWithTop);
    }

    /**
     * Modifies attributes of first element only
     *
     * @return this object reference to chain methods calls
     */
    @Override
    public FluentList<E> modifyAttribute(String attributeName, String attributeValue) {
        return javascriptActions.modifyAttribute(attributeName, attributeValue);
    }

    /**
     * Scrolls to first element of list
     *
     * @return this object reference to chain methods calls
     */
    @Override
    public FluentList<E> scrollIntoView() {
        return javascriptActions.scrollIntoView();
    }
}

