package org.fluentlenium.integration;

import org.fluentlenium.core.events.ElementListener;
import org.fluentlenium.core.events.FindByListener;
import org.fluentlenium.core.events.NavigateAllListener;
import org.fluentlenium.core.events.NavigateListener;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EventsTest extends IntegrationFluentTest {

    @Test
    void clickOn() {
        ElementListener beforeListener = Mockito.mock(ElementListener.class);
        ElementListener afterListener = Mockito.mock(ElementListener.class);

        events().beforeClickOn(beforeListener);
        events().afterClickOn(afterListener);
        goTo(DEFAULT_URL);

        $("button").click();

        Mockito.verify(beforeListener).on(Mockito.any(), Mockito.any());
        Mockito.verify(afterListener).on(Mockito.any(), Mockito.any());
    }

    @Test
    void findBy() {
        FindByListener beforeListener = Mockito.mock(FindByListener.class);
        FindByListener afterListener = Mockito.mock(FindByListener.class);

        events().beforeFindBy(beforeListener);
        events().afterFindBy(afterListener);
        goTo(DEFAULT_URL);

        el("button").now();

        Mockito.verify(beforeListener)
                .on(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(afterListener)
                .on(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void navigate() {
        NavigateAllListener beforeListener = Mockito.mock(NavigateAllListener.class);
        NavigateAllListener afterListener = Mockito.mock(NavigateAllListener.class);

        events().beforeNavigate(beforeListener);
        events().afterNavigate(afterListener);
        goTo(DEFAULT_URL);

        Mockito.verify(beforeListener, Mockito.times(1))
                .on(Mockito.eq(DEFAULT_URL), Mockito.any(), Mockito.any());
        Mockito.verify(afterListener, Mockito.times(1))
                .on(Mockito.eq(DEFAULT_URL), Mockito.any(), Mockito.any());

        getDriver().navigate().refresh();

        Mockito.verify(beforeListener, Mockito.times(1))
                .on(Mockito.isNull(), Mockito.any(), Mockito.eq(NavigateAllListener.Direction.REFRESH));
        Mockito.verify(afterListener, Mockito.times(1))
                .on(Mockito.isNull(), Mockito.any(), Mockito.eq(NavigateAllListener.Direction.REFRESH));

    }

    @Test
    void refresh() {
        NavigateListener beforeListener = Mockito.mock(NavigateListener.class);
        NavigateListener afterListener = Mockito.mock(NavigateListener.class);

        events().beforeNavigateRefresh(beforeListener);
        events().afterNavigateRefresh(afterListener);
        goTo(DEFAULT_URL);

        Mockito.verify(beforeListener, Mockito.times(0)).on(Mockito.any());
        Mockito.verify(afterListener, Mockito.times(0)).on(Mockito.any());

        getDriver().navigate().refresh();
        Mockito.verify(beforeListener, Mockito.times(1)).on(Mockito.any());
        Mockito.verify(afterListener, Mockito.times(1)).on(Mockito.any());
    }

}
