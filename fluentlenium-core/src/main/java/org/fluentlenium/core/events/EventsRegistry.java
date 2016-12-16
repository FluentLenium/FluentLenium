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
    public EventsRegistry(FluentControl control) {
        eventDriver = (EventFiringWebDriver) control.getDriver();
        support = new EventsSupport(this);
        instantiator = new DefaultComponentInstantiator(control);
        register(support);
    }

    /**
     * Register a new event listener.
     *
     * @param eventListener event listener to register
     * @return {@code this} to chain method calls
     */
    public EventsRegistry register(WebDriverEventListener eventListener) {
        eventDriver.register(eventListener);
        return this;
    }

    /**
     * Unregister an existing event listener.
     *
     * @param eventListener existing event listener to unregister
     * @return {@code this} to chain method calls
     */
    public EventsRegistry unregister(WebDriverEventListener eventListener) {
        eventDriver.unregister(eventListener);
        return this;
    }

    /**
     * Register a new event listener.
     *
     * @param eventListener event listener to register
     * @return {@code this} to chain method calls
     */
    public EventsRegistry register(EventListener eventListener) {
        eventDriver.register(new EventAdapter(eventListener, instantiator));
        return this;
    }

    /**
     * Unregister an existing event listener.
     *
     * @param eventListener existing event listener to unregister
     * @return {@code this} to chain method calls
     */
    public EventsRegistry unregister(EventListener eventListener) {
        eventDriver.unregister(new EventAdapter(eventListener, instantiator));
        return this;
    }

    /**
     * Unregister all event listeners.
     */
    public void close() {
        unregister(support);
    }

    @Override
    public WebDriver getWrappedDriver() {
        return eventDriver.getWrappedDriver();
    }

    /**
     * Add a listener that will be invoked before navigating to an url.
     *
     * @param listener listener invoked before navigating to an url.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeNavigateTo(NavigateToListener listener) {
        beforeNavigateTo.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after navigation.
     *
     * @param listener listener invoked after navigation.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterNavigateTo(NavigateToListener listener) {
        afterNavigateTo.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked before navigating back.
     *
     * @param listener listener invoked before navigating back.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeNavigateBack(NavigateListener listener) {
        beforeNavigateBack.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after navigating back.
     *
     * @param listener listener invoked after navigating back.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterNavigateBack(NavigateListener listener) {
        afterNavigateBack.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked before navigating forward.
     *
     * @param listener listener invoked before navigating forward.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeNavigateForward(NavigateListener listener) {
        beforeNavigateForward.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after navigating forward.
     *
     * @param listener listener invoked after navigating forward.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterNavigateForward(NavigateListener listener) {
        afterNavigateForward.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked before navigating.
     *
     * @param listener listener invoked before navigating.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeNavigate(NavigateAllListener listener) {
        beforeNavigate.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after navigating.
     *
     * @param listener listener invoked after navigating.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterNavigate(NavigateAllListener listener) {
        afterNavigate.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked before refresh.
     *
     * @param listener listener invoked before refresh.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeNavigateRefresh(NavigateListener listener) {
        beforeNavigateRefresh.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after refresh.
     *
     * @param listener listener invoked after refresh.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterNavigateRefresh(NavigateListener listener) {
        afterNavigateRefresh.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked before finding an element.
     *
     * @param listener listener invoked before finding an element.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeFindBy(FindByListener listener) {
        beforeFindBy.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after finding an element.
     *
     * @param listener listener invoked after finding an element.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterFindBy(FindByListener listener) {
        afterFindBy.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked before clicking an element.
     *
     * @param listener listener invoked before clicking an element.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeClickOn(ElementListener listener) {
        beforeClickOn.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after clicking an element.
     *
     * @param listener listener invoked after clicking an element.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterClickOn(ElementListener listener) {
        afterClickOn.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked before changing value of an element.
     *
     * @param listener listener invoked before changing value of an element.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeChangeValueOf(ElementListener listener) {
        beforeChangeValueOf.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after changing value of an element.
     *
     * @param listener listener invoked after changing value of an element.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterChangeValueOf(ElementListener listener) {
        afterChangeValueOf.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked before executing a script.
     *
     * @param listener listener invoked before executing a script.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeScript(ScriptListener listener) {
        beforeScript.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after executing a script.
     *
     * @param listener listener invoked after executing a script.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterScript(ScriptListener listener) {
        afterScript.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after an exception occurred.
     *
     * @param listener listener invoked after an exception occurred.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry onException(ExceptionListener listener) {
        onException.add(listener);
        return this;
    }

    /**
     * Sort listeners based on priority.
     *
     * @see ListenerPriorityComparator
     */
    protected void sortListeners() {
        ListenerPriorityComparator comparator = new ListenerPriorityComparator();

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
    public void unregisterContainer(Object container) {
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

    private void unregisterContainer(Iterable iterable, Object container) {
        Iterator<?> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            if (next instanceof ContainerListener && next == container) { // NOPMD CompareObjectsWithEquals
                iterator.remove();
            }
        }
    }
}
