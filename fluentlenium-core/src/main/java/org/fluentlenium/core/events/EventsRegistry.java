package org.fluentlenium.core.events;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.awt.event.ContainerListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Registry of event listeners.
 */
public class EventsRegistry implements WrapsDriver { // NOPMD TooManyFields

    private final EventFiringWebDriver eventDriver;

    private final EventsSupport support;

    private final DefaultComponentInstantiator instantiator;

    /* default */ final List<NavigateToListener> beforeNavigateTo = new ArrayList<>();

    /* default */ final List<NavigateToListener> afterNavigateTo = new ArrayList<>();

    /* default */ final List<NavigateListener> beforeNavigateBack = new ArrayList<>();

    /* default */ final List<NavigateListener> afterNavigateBack = new ArrayList<>();

    /* default */ final List<NavigateListener> beforeNavigateForward = new ArrayList<>();

    /* default */ final List<NavigateListener> afterNavigateForward = new ArrayList<>();

    /* default */ final List<NavigateAllListener> beforeNavigate = new ArrayList<>();

    /* default */ final List<NavigateAllListener> afterNavigate = new ArrayList<>();

    /* default */ final List<NavigateListener> beforeNavigateRefresh = new ArrayList<>();

    /* default */ final List<NavigateListener> afterNavigateRefresh = new ArrayList<>();

    /* default */ final List<FindByListener> beforeFindBy = new ArrayList<>();

    /* default */ final List<FindByListener> afterFindBy = new ArrayList<>();

    /* default */ final List<ElementListener> beforeClickOn = new ArrayList<>();

    /* default */ final List<ElementListener> afterClickOn = new ArrayList<>();

    /* default */ final List<ElementListener> beforeChangeValueOf = new ArrayList<>();

    /* default */ final List<ElementListener> afterChangeValueOf = new ArrayList<>();

    /* default */ final List<ScriptListener> beforeScript = new ArrayList<>();

    /* default */ final List<ScriptListener> afterScript = new ArrayList<>();

    /* default */ final List<ExceptionListener> onException = new ArrayList<>();

    /**
     * Creates a new registry of event listeners.
     *
     * @param control control interface
     */
    public EventsRegistry(final FluentControl control) {
        this.eventDriver = (EventFiringWebDriver) control.getDriver();
        this.support = new EventsSupport(this);
        this.instantiator = new DefaultComponentInstantiator(control);
        this.register(this.support);
    }

    /**
     * Register a new event listener.
     *
     * @param eventListener event listener to register
     * @return {@code this} to chain method calls
     */
    public EventsRegistry register(final WebDriverEventListener eventListener) {
        this.eventDriver.register(eventListener);
        return this;
    }

    /**
     * Unregister an existing event listener.
     *
     * @param eventListener existing event listener to unregister
     * @return {@code this} to chain method calls
     */
    public EventsRegistry unregister(final WebDriverEventListener eventListener) {
        this.eventDriver.unregister(eventListener);
        return this;
    }

    /**
     * Register a new event listener.
     *
     * @param eventListener event listener to register
     * @return {@code this} to chain method calls
     */
    public EventsRegistry register(final EventListener eventListener) {
        this.eventDriver.register(new EventAdapter(eventListener, instantiator));
        return this;
    }

    /**
     * Unregister an existing event listener.
     *
     * @param eventListener existing event listener to unregister
     * @return {@code this} to chain method calls
     */
    public EventsRegistry unregister(final EventListener eventListener) {
        this.eventDriver.unregister(new EventAdapter(eventListener, instantiator));
        return this;
    }

    /**
     * Unregister all event listeners.
     */
    public void close() {
        this.unregister(this.support);
    }

    @Override
    public WebDriver getWrappedDriver() {
        return this.eventDriver.getWrappedDriver();
    }

    /**
     * Add a listener that will be invoked before navigating to an url.
     *
     * @param listener listener invoked before navigating to an url.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeNavigateTo(final NavigateToListener listener) {
        this.beforeNavigateTo.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after navigation.
     *
     * @param listener listener invoked after navigation.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterNavigateTo(final NavigateToListener listener) {
        this.afterNavigateTo.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked before navigating back.
     *
     * @param listener listener invoked before navigating back.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeNavigateBack(final NavigateListener listener) {
        this.beforeNavigateBack.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after navigating back.
     *
     * @param listener listener invoked after navigating back.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterNavigateBack(final NavigateListener listener) {
        this.afterNavigateBack.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked before navigating forward.
     *
     * @param listener listener invoked before navigating forward.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeNavigateForward(final NavigateListener listener) {
        this.beforeNavigateForward.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after navigating forward.
     *
     * @param listener listener invoked after navigating forward.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterNavigateForward(final NavigateListener listener) {
        this.afterNavigateForward.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked before navigating.
     *
     * @param listener listener invoked before navigating.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeNavigate(final NavigateAllListener listener) {
        this.beforeNavigate.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after navigating.
     *
     * @param listener listener invoked after navigating.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterNavigate(final NavigateAllListener listener) {
        this.afterNavigate.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked before refresh.
     *
     * @param listener listener invoked before refresh.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeNavigateRefresh(final NavigateListener listener) {
        this.beforeNavigateRefresh.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after refresh.
     *
     * @param listener listener invoked after refresh.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterNavigateRefresh(final NavigateListener listener) {
        this.afterNavigateRefresh.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked before finding an element.
     *
     * @param listener listener invoked before finding an element.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeFindBy(final FindByListener listener) {
        this.beforeFindBy.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after finding an element.
     *
     * @param listener listener invoked after finding an element.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterFindBy(final FindByListener listener) {
        this.afterFindBy.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked before clicking an element.
     *
     * @param listener listener invoked before clicking an element.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeClickOn(final ElementListener listener) {
        this.beforeClickOn.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after clicking an element.
     *
     * @param listener listener invoked after clicking an element.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterClickOn(final ElementListener listener) {
        this.afterClickOn.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked before changing value of an element.
     *
     * @param listener listener invoked before changing value of an element.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeChangeValueOf(final ElementListener listener) {
        this.beforeChangeValueOf.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after changing value of an element.
     *
     * @param listener listener invoked after changing value of an element.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterChangeValueOf(final ElementListener listener) {
        this.afterChangeValueOf.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked before executing a script.
     *
     * @param listener listener invoked before executing a script.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeScript(final ScriptListener listener) {
        this.beforeScript.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after executing a script.
     *
     * @param listener listener invoked after executing a script.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterScript(final ScriptListener listener) {
        this.afterScript.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after an exception occurred.
     *
     * @param listener listener invoked after an exception occurred.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry onException(final ExceptionListener listener) {
        this.onException.add(listener);
        return this;
    }

    /**
     * Sort listeners based on priority.
     *
     * @see ListenerPriorityComparator
     */
    protected void sortListeners() {
        final ListenerPriorityComparator comparator = new ListenerPriorityComparator();

        Collections.sort(beforeNavigateTo, comparator);
        Collections.sort(afterNavigateTo, comparator);

        Collections.sort(beforeNavigateBack, comparator);
        Collections.sort(afterNavigateBack, comparator);

        Collections.sort(beforeNavigateForward, comparator);
        Collections.sort(afterNavigateForward, comparator);

        Collections.sort(beforeNavigate, comparator);
        Collections.sort(afterNavigate, comparator);

        Collections.sort(beforeNavigateRefresh, comparator);
        Collections.sort(afterNavigateRefresh, comparator);

        Collections.sort(beforeFindBy, comparator);
        Collections.sort(afterFindBy, comparator);

        Collections.sort(beforeClickOn, comparator);
        Collections.sort(afterClickOn, comparator);

        Collections.sort(beforeChangeValueOf, comparator);
        Collections.sort(afterChangeValueOf, comparator);

        Collections.sort(beforeScript, comparator);
        Collections.sort(afterScript, comparator);

        Collections.sort(onException, comparator);
    }

    /**
     * Unregister all listeners attached to a given container.
     *
     * @param container container
     */
    public void unregisterContainer(final Object container) {
        unregisterContainer(beforeNavigateTo, container);
        unregisterContainer(afterNavigateTo, container);

        unregisterContainer(beforeNavigateBack, container);
        unregisterContainer(afterNavigateBack, container);

        unregisterContainer(beforeNavigateForward, container);
        unregisterContainer(afterNavigateForward, container);

        unregisterContainer(beforeNavigate, container);
        unregisterContainer(afterNavigate, container);

        unregisterContainer(beforeNavigateRefresh, container);
        unregisterContainer(afterNavigateRefresh, container);

        unregisterContainer(beforeFindBy, container);
        unregisterContainer(afterFindBy, container);

        unregisterContainer(beforeClickOn, container);
        unregisterContainer(afterClickOn, container);

        unregisterContainer(beforeChangeValueOf, container);
        unregisterContainer(afterChangeValueOf, container);

        unregisterContainer(beforeScript, container);
        unregisterContainer(afterScript, container);

        unregisterContainer(onException, container);
    }

    private void unregisterContainer(final Iterable iterable, final Object container) {
        final Iterator<?> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            final Object next = iterator.next();
            if (next instanceof ContainerListener && next == container) { // NOPMD CompareObjectsWithEquals
                iterator.remove();
            }
        }
    }
}
