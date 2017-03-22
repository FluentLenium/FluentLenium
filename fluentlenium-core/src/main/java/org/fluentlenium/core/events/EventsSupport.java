package org.fluentlenium.core.events;

import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * EventsSupport can be registered in Selenium {@link org.openqa.selenium.support.events.EventFiringWebDriver} to provide an
 * easier to use events interface.
 */
public class EventsSupport implements EventListener {

    private final EventsRegistry eventsRegistry;

    /**
     * Creates a new events support
     *
     * @param eventsRegistry events registry
     */
    public EventsSupport(EventsRegistry eventsRegistry) {
        this.eventsRegistry = eventsRegistry;
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        for (NavigateToListener listener : eventsRegistry.beforeNavigateTo) {
            listener.on(url, driver);
        }
        for (NavigateAllListener listener : eventsRegistry.beforeNavigate) {
            listener.on(url, driver, null);
        }
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        for (NavigateToListener listener : eventsRegistry.afterNavigateTo) {
            listener.on(url, driver);
        }
        for (NavigateAllListener listener : eventsRegistry.afterNavigate) {
            listener.on(url, driver, null);
        }
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {
        for (NavigateListener listener : eventsRegistry.beforeNavigateBack) {
            listener.on(driver);
        }
        for (NavigateAllListener listener : eventsRegistry.beforeNavigate) {
            listener.on(null, driver, NavigateAllListener.Direction.BACK);
        }
    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
        for (NavigateListener listener : eventsRegistry.afterNavigateBack) {
            listener.on(driver);
        }
        for (NavigateAllListener listener : eventsRegistry.afterNavigate) {
            listener.on(null, driver, NavigateAllListener.Direction.BACK);
        }
    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {
        for (NavigateListener listener : eventsRegistry.beforeNavigateForward) {
            listener.on(driver);
        }
        for (NavigateAllListener listener : eventsRegistry.beforeNavigate) {
            listener.on(null, driver, NavigateAllListener.Direction.FORWARD);
        }
    }

    @Override
    public void afterNavigateForward(WebDriver driver) {
        for (NavigateListener listener : eventsRegistry.afterNavigateForward) {
            listener.on(driver);
        }
        for (NavigateAllListener listener : eventsRegistry.afterNavigate) {
            listener.on(null, driver, NavigateAllListener.Direction.FORWARD);
        }
    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {
        for (NavigateListener listener : eventsRegistry.beforeNavigateRefresh) {
            listener.on(driver);
        }
        for (NavigateAllListener listener : eventsRegistry.beforeNavigate) {
            listener.on(null, driver, NavigateAllListener.Direction.REFRESH);
        }
    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {
        for (NavigateListener listener : eventsRegistry.afterNavigateRefresh) {
            listener.on(driver);
        }
        for (NavigateAllListener listener : eventsRegistry.afterNavigate) {
            listener.on(null, driver, NavigateAllListener.Direction.REFRESH);
        }
    }

    @Override
    public void beforeFindBy(By by, FluentWebElement element, WebDriver driver) {
        for (FindByListener listener : eventsRegistry.beforeFindBy) {
            listener.on(by, element, driver);
        }
    }

    @Override
    public void afterFindBy(By by, FluentWebElement element, WebDriver driver) {
        for (FindByListener listener : eventsRegistry.afterFindBy) {
            listener.on(by, element, driver);
        }
    }

    @Override
    public void beforeClickOn(FluentWebElement element, WebDriver driver) {
        for (ElementListener listener : eventsRegistry.beforeClickOn) {
            listener.on(element, driver);
        }
    }

    @Override
    public void afterClickOn(FluentWebElement element, WebDriver driver) {
        for (ElementListener listener : eventsRegistry.afterClickOn) {
            listener.on(element, driver);
        }
    }

    @Override
    public void beforeChangeValueOf(FluentWebElement element, WebDriver driver, CharSequence[] charSequence) {
        for (ElementListener listener : eventsRegistry.beforeChangeValueOf) {
            listener.on(element, driver);
        }
    }

    @Override
    public void afterChangeValueOf(FluentWebElement element, WebDriver driver, CharSequence[] charSequence) {
        for (ElementListener listener : eventsRegistry.afterChangeValueOf) {
            listener.on(element, driver);
        }
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {
        for (ScriptListener listener : eventsRegistry.beforeScript) {
            listener.on(script, driver);
        }
    }

    @Override
    public void afterScript(String script, WebDriver driver) {
        for (ScriptListener listener : eventsRegistry.afterScript) {
            listener.on(script, driver);
        }
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
        for (ExceptionListener listener : eventsRegistry.onException) {
            listener.on(throwable, driver);
        }
    }

    @Override
    public void beforeAlertAccept(WebDriver driver) {
        for (AlertListener listener : eventsRegistry.beforeAlertAccept) {
            listener.on(driver);
        }
    }

    @Override
    public void afterAlertAccept(WebDriver driver) {
        for (AlertListener listener : eventsRegistry.afterAlertAccept) {
            listener.on(driver);
        }
    }

    @Override
    public void beforeAlertDismiss(WebDriver driver) {
        for (AlertListener listener : eventsRegistry.beforeAlertDismiss) {
            listener.on(driver);
        }
    }

    @Override
    public void afterAlertDismiss(WebDriver driver) {
        for (AlertListener listener : eventsRegistry.afterAlertDismiss) {
            listener.on(driver);
        }
    }
}
