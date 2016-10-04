package org.fluentlenium.core.events;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.awt.event.ContainerListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class EventsRegistry {

    private final EventFiringWebDriver eventDriver;

    private final EventsSupport support;

    private final DefaultComponentInstantiator instantiator;

    List<NavigateToListener> beforeNavigateTo = new ArrayList<>();

    List<NavigateToListener> afterNavigateTo = new ArrayList<>();

    List<NavigateListener> beforeNavigateBack = new ArrayList<>();

    List<NavigateListener> afterNavigateBack = new ArrayList<>();

    List<NavigateListener> beforeNavigateForward = new ArrayList<>();

    List<NavigateListener> afterNavigateForward = new ArrayList<>();

    List<NavigateAllListener> beforeNavigate = new ArrayList<>();

    List<NavigateAllListener> afterNavigate = new ArrayList<>();

    List<NavigateListener> beforeNavigateRefresh = new ArrayList<>();

    List<NavigateListener> afterNavigateRefresh = new ArrayList<>();

    List<FindByListener> beforeFindBy = new ArrayList<>();

    List<FindByListener> afterFindBy = new ArrayList<>();

    List<ElementListener> beforeClickOn = new ArrayList<>();

    List<ElementListener> afterClickOn = new ArrayList<>();

    List<ElementListener> beforeChangeValueOf = new ArrayList<>();

    List<ElementListener> afterChangeValueOf = new ArrayList<>();

    List<ScriptListener> beforeScript = new ArrayList<>();

    List<ScriptListener> afterScript = new ArrayList<>();

    List<ExceptionListener> onException = new ArrayList<>();

    public EventsRegistry(final FluentControl fluentControl) {
        this.eventDriver = (EventFiringWebDriver) fluentControl.getDriver();
        this.support = new EventsSupport(this);
        this.instantiator = new DefaultComponentInstantiator(fluentControl);
        this.register(this.support);
    }

    public EventsRegistry register(final WebDriverEventListener eventListener) {
        this.eventDriver.register(eventListener);
        return this;
    }

    public EventsRegistry unregister(final WebDriverEventListener eventListener) {
        this.eventDriver.unregister(eventListener);
        return this;
    }

    public EventsRegistry register(final EventListener eventListener) {
        this.eventDriver.register(new EventAdapter(eventListener, instantiator));
        return this;
    }

    public EventsRegistry unregister(final EventListener eventListener) {
        this.eventDriver.unregister(new EventAdapter(eventListener, instantiator));
        return this;
    }

    public void close() {
        this.unregister(this.support);
    }

    public WebDriver getWrappedDriver() {
        return this.eventDriver.getWrappedDriver();
    }

    public EventsRegistry beforeNavigateTo(final NavigateToListener listener) {
        this.beforeNavigateTo.add(listener);
        return this;
    }

    public EventsRegistry afterNavigateTo(final NavigateToListener listener) {
        this.afterNavigateTo.add(listener);
        return this;
    }

    public EventsRegistry beforeNavigateBack(final NavigateListener listener) {
        this.beforeNavigateBack.add(listener);
        return this;
    }

    public EventsRegistry afterNavigateBack(final NavigateListener listener) {
        this.afterNavigateBack.add(listener);
        return this;
    }

    public EventsRegistry beforeNavigateForward(final NavigateListener listener) {
        this.beforeNavigateForward.add(listener);
        return this;
    }

    public EventsRegistry afterNavigateForward(final NavigateListener listener) {
        this.afterNavigateForward.add(listener);
        return this;
    }

    public EventsRegistry beforeNavigate(final NavigateAllListener listener) {
        this.beforeNavigate.add(listener);
        return this;
    }

    public EventsRegistry afterNavigate(final NavigateAllListener listener) {
        this.afterNavigate.add(listener);
        return this;
    }

    public EventsRegistry beforeNavigateRefresh(final NavigateListener listener) {
        this.beforeNavigateRefresh.add(listener);
        return this;
    }

    public EventsRegistry afterNavigateRefresh(final NavigateListener listener) {
        this.afterNavigateRefresh.add(listener);
        return this;
    }

    public EventsRegistry beforeFindBy(final FindByListener listener) {
        this.beforeFindBy.add(listener);
        return this;
    }

    public EventsRegistry afterFindBy(final FindByListener listener) {
        this.afterFindBy.add(listener);
        return this;
    }

    public EventsRegistry beforeClickOn(final ElementListener listener) {
        this.beforeClickOn.add(listener);
        return this;
    }

    public EventsRegistry afterClickOn(final ElementListener listener) {
        this.afterClickOn.add(listener);
        return this;
    }

    public EventsRegistry beforeChangeValueOf(final ElementListener listener) {
        this.beforeChangeValueOf.add(listener);
        return this;
    }

    public EventsRegistry afterChangeValueOf(final ElementListener listener) {
        this.afterChangeValueOf.add(listener);
        return this;
    }

    public EventsRegistry beforeScript(final ScriptListener listener) {
        this.beforeScript.add(listener);
        return this;
    }

    public EventsRegistry afterScript(final ScriptListener listener) {
        this.afterScript.add(listener);
        return this;
    }

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
            if (next instanceof ContainerListener && next == container) {
                iterator.remove();
            }
        }
    }
}
