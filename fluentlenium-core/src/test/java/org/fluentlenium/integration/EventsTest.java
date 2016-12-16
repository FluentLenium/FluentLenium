package org.fluentlenium.integration;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.events.ElementListener;
import org.fluentlenium.core.events.FindByListener;
import org.fluentlenium.core.events.NavigateAllListener;
import org.fluentlenium.core.events.NavigateListener;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EventsTest extends IntegrationFluentTest {

    @Test
    public void clickOn() {
        ElementListener beforeListener = Mockito.mock(ElementListener.class);
        ElementListener afterListener = Mockito.mock(ElementListener.class);

        events().beforeClickOn(beforeListener);
        events().afterClickOn(afterListener);
        goTo(DEFAULT_URL);

        $("button").click();

        Mockito.verify(beforeListener).on(Mockito.<FluentWebElement>anyObject(), Mockito.<WebDriver>anyObject());
        Mockito.verify(afterListener).on(Mockito.<FluentWebElement>anyObject(), Mockito.<WebDriver>anyObject());
    }

    @Test
    public void findBy() {
        FindByListener beforeListener = Mockito.mock(FindByListener.class);
        FindByListener afterListener = Mockito.mock(FindByListener.class);

        events().beforeFindBy(beforeListener);
        events().afterFindBy(afterListener);
        goTo(DEFAULT_URL);

        el("button").now();

        Mockito.verify(beforeListener)
                .on(Mockito.<By>anyObject(), Mockito.<FluentWebElement>anyObject(), Mockito.<WebDriver>anyObject());
        Mockito.verify(afterListener)
                .on(Mockito.<By>anyObject(), Mockito.<FluentWebElement>anyObject(), Mockito.<WebDriver>anyObject());
    }

    @Test
    public void navigate() {
        NavigateAllListener beforeListener = Mockito.mock(NavigateAllListener.class);
        NavigateAllListener afterListener = Mockito.mock(NavigateAllListener.class);

        events().beforeNavigate(beforeListener);
        events().afterNavigate(afterListener);
        goTo(DEFAULT_URL);

        Mockito.verify(beforeListener, Mockito.times(1))
                .on(Mockito.eq(DEFAULT_URL), Mockito.<WebDriver>anyObject(), Mockito.<NavigateAllListener.Direction>anyObject());
        Mockito.verify(afterListener, Mockito.times(1))
                .on(Mockito.eq(DEFAULT_URL), Mockito.<WebDriver>anyObject(), Mockito.<NavigateAllListener.Direction>anyObject());

        getDriver().navigate().refresh();

        Mockito.verify(beforeListener, Mockito.times(1))
                .on((String) Mockito.isNull(), Mockito.<WebDriver>anyObject(), Mockito.eq(NavigateAllListener.Direction.REFRESH));
        Mockito.verify(afterListener, Mockito.times(1))
                .on((String) Mockito.isNull(), Mockito.<WebDriver>anyObject(), Mockito.eq(NavigateAllListener.Direction.REFRESH));

    }

    @Test
    public void refresh() {
        NavigateListener beforeListener = Mockito.mock(NavigateListener.class);
        NavigateListener afterListener = Mockito.mock(NavigateListener.class);

        events().beforeNavigateRefresh(beforeListener);
        events().afterNavigateRefresh(afterListener);
        goTo(DEFAULT_URL);

        Mockito.verify(beforeListener, Mockito.times(0)).on(Mockito.<WebDriver>anyObject());
        Mockito.verify(afterListener, Mockito.times(0)).on(Mockito.<WebDriver>anyObject());

        getDriver().navigate().refresh();
        Mockito.verify(beforeListener, Mockito.times(1)).on(Mockito.<WebDriver>anyObject());
        Mockito.verify(afterListener, Mockito.times(1)).on(Mockito.<WebDriver>anyObject());
    }

}
