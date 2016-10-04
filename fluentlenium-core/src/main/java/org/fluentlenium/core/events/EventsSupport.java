package org.fluentlenium.core.events;

import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EventsSupport implements EventListener {

    private final EventsRegistry eventsRegistry;

    public EventsSupport(final EventsRegistry eventsRegistry) {
        this.eventsRegistry = eventsRegistry;
    }

    @Override
    public void beforeNavigateTo(final String url, final WebDriver driver) {
        for (final NavigateToListener listener : this.eventsRegistry.beforeNavigateTo) {
            listener.on(url, driver);
        }
        for (final NavigateAllListener listener : this.eventsRegistry.beforeNavigate) {
            listener.on(url, driver, null);
        }
    }

    @Override
    public void afterNavigateTo(final String url, final WebDriver driver) {
        for (final NavigateToListener listener : this.eventsRegistry.afterNavigateTo) {
            listener.on(url, driver);
        }
        for (final NavigateAllListener listener : this.eventsRegistry.afterNavigate) {
            listener.on(url, driver, null);
        }
    }

    @Override
    public void beforeNavigateBack(final WebDriver driver) {
        for (final NavigateListener listener : this.eventsRegistry.beforeNavigateBack) {
            listener.on(driver);
        }
        for (final NavigateAllListener listener : this.eventsRegistry.beforeNavigate) {
            listener.on(null, driver, NavigateAllListener.Direction.BACK);
        }
    }

    @Override
    public void afterNavigateBack(final WebDriver driver) {
        for (final NavigateListener listener : this.eventsRegistry.afterNavigateBack) {
            listener.on(driver);
        }
        for (final NavigateAllListener listener : this.eventsRegistry.afterNavigate) {
            listener.on(null, driver, NavigateAllListener.Direction.BACK);
        }
    }

    @Override
    public void beforeNavigateForward(final WebDriver driver) {
        for (final NavigateListener listener : this.eventsRegistry.beforeNavigateForward) {
            listener.on(driver);
        }
        for (final NavigateAllListener listener : this.eventsRegistry.beforeNavigate) {
            listener.on(null, driver, NavigateAllListener.Direction.FORWARD);
        }
    }

    @Override
    public void afterNavigateForward(final WebDriver driver) {
        for (final NavigateListener listener : this.eventsRegistry.afterNavigateForward) {
            listener.on(driver);
        }
        for (final NavigateAllListener listener : this.eventsRegistry.afterNavigate) {
            listener.on(null, driver, NavigateAllListener.Direction.FORWARD);
        }
    }

    @Override
    public void beforeNavigateRefresh(final WebDriver driver) {
        for (final NavigateListener listener : this.eventsRegistry.beforeNavigateRefresh) {
            listener.on(driver);
        }
        for (final NavigateAllListener listener : this.eventsRegistry.beforeNavigate) {
            listener.on(null, driver, NavigateAllListener.Direction.REFRESH);
        }
    }

    @Override
    public void afterNavigateRefresh(final WebDriver driver) {
        for (final NavigateListener listener : this.eventsRegistry.afterNavigateRefresh) {
            listener.on(driver);
        }
        for (final NavigateAllListener listener : this.eventsRegistry.afterNavigate) {
            listener.on(null, driver, NavigateAllListener.Direction.REFRESH);
        }
    }

    @Override
    public void beforeFindBy(final By by, final FluentWebElement element, final WebDriver driver) {
        for (final FindByListener listener : this.eventsRegistry.beforeFindBy) {
            listener.on(by, element, driver);
        }
    }

    @Override
    public void afterFindBy(final By by, final FluentWebElement element, final WebDriver driver) {
        for (final FindByListener listener : this.eventsRegistry.afterFindBy) {
            listener.on(by, element, driver);
        }
    }

    @Override
    public void beforeClickOn(final FluentWebElement element, final WebDriver driver) {
        for (final ElementListener listener : this.eventsRegistry.beforeClickOn) {
            listener.on(element, driver);
        }
    }

    @Override
    public void afterClickOn(final FluentWebElement element, final WebDriver driver) {
        for (final ElementListener listener : this.eventsRegistry.afterClickOn) {
            listener.on(element, driver);
        }
    }

    @Override
    public void beforeChangeValueOf(final FluentWebElement element, final WebDriver driver) {
        for (final ElementListener listener : this.eventsRegistry.beforeChangeValueOf) {
            listener.on(element, driver);
        }
    }

    @Override
    public void afterChangeValueOf(final FluentWebElement element, final WebDriver driver) {
        for (final ElementListener listener : this.eventsRegistry.afterChangeValueOf) {
            listener.on(element, driver);
        }
    }

    @Override
    public void beforeScript(final String script, final WebDriver driver) {
        for (final ScriptListener listener : this.eventsRegistry.beforeScript) {
            listener.on(script, driver);
        }
    }

    @Override
    public void afterScript(final String script, final WebDriver driver) {
        for (final ScriptListener listener : this.eventsRegistry.afterScript) {
            listener.on(script, driver);
        }
    }

    @Override
    public void onException(final Throwable throwable, final WebDriver driver) {
        for (final ExceptionListener listener : this.eventsRegistry.onException) {
            listener.on(throwable, driver);
        }
    }

}
