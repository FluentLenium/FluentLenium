package org.fluentlenium.core.events;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.awt.event.ContainerListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableList;

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

    /* default */ final List<ElementListener> beforeGetText = new ArrayList<>();

    /* default */ final List<ElementListener> afterGetText = new ArrayList<>();

    /* default */ final List<ElementListener> beforeChangeValueOf = new ArrayList<>();

    /* default */ final List<ElementListener> afterChangeValueOf = new ArrayList<>();

    /* default */ final List<ScriptListener> beforeScript = new ArrayList<>();

    /* default */ final List<ScriptListener> afterScript = new ArrayList<>();

    /* default */ final List<AlertListener> beforeAlertAccept = new ArrayList<>();

    /* default */ final List<AlertListener> afterAlertAccept = new ArrayList<>();

    /* default */ final List<AlertListener> beforeAlertDismiss = new ArrayList<>();

    /* default */ final List<AlertListener> afterAlertDismiss = new ArrayList<>();

    /* default */ final List<SwitchToWindowListener> beforeSwitchToWindow = new ArrayList<>();

    /* default */ final List<SwitchToWindowListener> afterSwitchToWindow = new ArrayList<>();

    /* default */ final List<GetScreenshotAsListener> beforeGetScreenshotAs = new ArrayList<>();

    /* default */ final List<GetScreenshotAsListener> afterGetScreenshotAs = new ArrayList<>();

    /* default */ final List<ExceptionListener> onException = new ArrayList<>();

    final List<List> eventLists = ImmutableList.of(
            beforeNavigateTo, afterNavigateTo,
            beforeNavigateBack, afterNavigateBack,
            beforeNavigateForward, afterNavigateForward,
            beforeNavigate, afterNavigate,
            beforeNavigateRefresh, afterNavigateRefresh,
            beforeFindBy, afterFindBy,
            beforeClickOn, afterClickOn,
            beforeChangeValueOf, afterChangeValueOf,
            beforeScript, afterScript,
            beforeGetText, afterGetText,
            beforeGetScreenshotAs, afterGetScreenshotAs,
            beforeSwitchToWindow, afterSwitchToWindow,
            onException
    );

    /**
     * Creates a new registry of event listeners.
     *
     * @param control control interface
     */
    public EventsRegistry(FluentControl control) {
        eventDriver = (EventFiringWebDriver) control.getDriver();
        support = new EventsSupport(this);
        instantiator = new DefaultComponentInstantiator(control);
        eventDriver.register(new EventAdapter(support, instantiator));
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
     * Add a listener that will be invoked before get text of an element.
     *
     * @param listener listener invoked before get text of an element.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeGetText(ElementListener listener) {
        beforeGetText.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after get text of an element.
     *
     * @param listener listener invoked after get text of an element.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterGetText(ElementListener listener) {
        afterGetText.add(listener);
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
     * Add a listener that will be invoked before an alert is accepted.
     *
     * @param listener listener invoked before an alert is accepted.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeAlertAccept(AlertListener listener) {
        beforeAlertAccept.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after an alert is accepted.
     *
     * @param listener listener invoked after an alert is accepted.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterAlertAccept(AlertListener listener) {
        afterAlertAccept.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked before an alert is dismissed.
     *
     * @param listener listener invoked before an alert is dismissed.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeAlertDismiss(AlertListener listener) {
        beforeAlertDismiss.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after an alert is dismissed.
     *
     * @param listener listener invoked after an alert is dismissed.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterAlertDismiss(AlertListener listener) {
        afterAlertDismiss.add(listener);
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
     * Add a listener that will be invoked after window switch.
     *
     * @param listener listener invoked after window switch.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterSwitchToWindow(SwitchToWindowListener listener) {
        afterSwitchToWindow.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked before window switch.
     *
     * @param listener listener invoked before window switch.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeSwitchToWindow(SwitchToWindowListener listener) {
        beforeSwitchToWindow.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked after getScreenshotAs.
     *
     * @param listener listener invoked after getScreenshotAs.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry afterGetScreenshotAs(GetScreenshotAsListener listener) {
        afterGetScreenshotAs.add(listener);
        return this;
    }

    /**
     * Add a listener that will be invoked before getScreenshotAs.
     *
     * @param listener listener invoked before getScreenshotAs.
     * @return {@code this} to chain method calls
     */
    public EventsRegistry beforeGetScreenshotAs(GetScreenshotAsListener listener) {
        beforeGetScreenshotAs.add(listener);
        return this;
    }

    /**
     * Sort listeners based on priority.
     *
     * @see ListenerPriorityComparator
     */
    protected void sortListeners() {
        ListenerPriorityComparator comparator = new ListenerPriorityComparator();

        for (List eventList : eventLists) {
            Collections.sort(eventList, comparator);
        }
    }

    /**
     * Unregister all listeners attached to a given container.
     *
     * @param container container
     */
    public void unregisterContainer(Object container) {
        for (List eventList : eventLists) {
            unregisterContainer(eventList, container);
        }
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
