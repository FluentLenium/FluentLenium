package org.fluentlenium.integration;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.events.ElementListener;
import org.fluentlenium.core.events.FindByListener;
import org.fluentlenium.core.events.NavigateAllListener;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class EventsTest extends LocalFluentCase {

    @Override
    public WebDriver getDefaultDriver() {
        return new EventFiringWebDriver(super.getDefaultDriver());
    }

    @Test
    public void clickOn() {
        ElementListener beforeListener = Mockito.mock(ElementListener.class);
        ElementListener afterListener = Mockito.mock(ElementListener.class);

        events().beforeClickOn(beforeListener);
        events().afterClickOn(new ElementListener() {
            @Override
            public void on(FluentWebElement element, WebDriver driver) {
                System.out.println("Element Clicked: " + element);
            }
        });
        goTo(DEFAULT_URL);

        click("button");

        Mockito.verify(beforeListener, Mockito.times(1)).on(Mockito.<FluentWebElement>anyObject(), Mockito.<WebDriver>anyObject());
        Mockito.verify(beforeListener, Mockito.times(1)).on(Mockito.<FluentWebElement>anyObject(), Mockito.<WebDriver>anyObject());
    }

    @Test
    public void findBy() {
        FindByListener beforeListener = Mockito.mock(FindByListener.class);
        FindByListener afterListener = Mockito.mock(FindByListener.class);

        events().beforeFindBy(beforeListener);
        events().afterFindBy(afterListener);
        goTo(DEFAULT_URL);

        findFirst("button");

        Mockito.verify(beforeListener, Mockito.times(1)).on(Mockito.<By>anyObject(), Mockito.<FluentWebElement>anyObject(), Mockito.<WebDriver>anyObject());
        Mockito.verify(beforeListener, Mockito.times(1)).on(Mockito.<By>anyObject(), Mockito.<FluentWebElement>anyObject(), Mockito.<WebDriver>anyObject());
    }

    @Test
    public void navigate() {
        NavigateAllListener beforeListener = Mockito.mock(NavigateAllListener.class);
        NavigateAllListener afterListener = Mockito.mock(NavigateAllListener.class);

        events().beforeNavigate(beforeListener);
        events().afterNavigate(afterListener);
        goTo(DEFAULT_URL);

        Mockito.verify(beforeListener, Mockito.times(1)).on(Mockito.eq(DEFAULT_URL), Mockito.<WebDriver>anyObject(), Mockito.<NavigateAllListener.Direction>anyObject());
        Mockito.verify(beforeListener, Mockito.times(1)).on(Mockito.eq(DEFAULT_URL), Mockito.<WebDriver>anyObject(), Mockito.<NavigateAllListener.Direction>anyObject());
    }

}
