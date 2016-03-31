package org.fluentlenium.core.events;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.ArrayList;
import java.util.List;


public class EventsRegistry {

    private final EventFiringWebDriver eventDriver;

    private final EventsSupport support;

    List<NavigateToListener> beforeNavigateTo = new ArrayList<NavigateToListener>();

    List<NavigateToListener> afterNavigateTo = new ArrayList<NavigateToListener>();

    List<NavigateListener> beforeNavigateBack = new ArrayList<NavigateListener>();

    List<NavigateListener> afterNavigateBack = new ArrayList<NavigateListener>();

    List<NavigateListener> beforeNavigateForward = new ArrayList<NavigateListener>();

    List<NavigateListener> afterNavigateForward = new ArrayList<NavigateListener>();

    List<NavigateAllListener> beforeNavigate = new ArrayList<NavigateAllListener>();

    List<NavigateAllListener> afterNavigate = new ArrayList<NavigateAllListener>();

    List<NavigateListener> beforeNavigateRefresh = new ArrayList<>();

    List<NavigateListener> afterNavigateRefresh = new ArrayList<>();

    List<FindByListener> beforeFindBy = new ArrayList<FindByListener>();

    List<FindByListener> afterFindBy = new ArrayList<FindByListener>();

    List<ElementListener> beforeClickOn = new ArrayList<ElementListener>();

    List<ElementListener> afterClickOn = new ArrayList<ElementListener>();

    List<ElementListener> beforeChangeValueOf = new ArrayList<ElementListener>();

    List<ElementListener> afterChangeValueOf = new ArrayList<ElementListener>();

    List<ScriptListener> beforeScript = new ArrayList<ScriptListener>();

    List<ScriptListener> afterScript = new ArrayList<ScriptListener>();

    List<ExceptionListener> onException = new ArrayList<ExceptionListener>();

    public EventsRegistry(final EventFiringWebDriver driver) {
        this.eventDriver = driver;
        this.support = new EventsSupport(this);
        this.register(this.support);
    }

    public EventsRegistry register(final EventListener eventListener) {
        this.eventDriver.register(new EventAdapter(eventListener));
        return this;
    }

    public EventsRegistry unregister(final EventListener eventListener) {
        this.eventDriver.unregister(new EventAdapter(eventListener));
        return this;
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
}
