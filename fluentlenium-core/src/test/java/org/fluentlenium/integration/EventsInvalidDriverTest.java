package org.fluentlenium.integration;

import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.core.events.ElementListener;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.mockito.Mockito;

@FluentConfiguration(eventsEnabled = FluentConfiguration.BooleanValue.FALSE)
public class EventsInvalidDriverTest extends IntegrationFluentTest {
    @Test(expected = IllegalStateException.class)
    public void checkInvalidDriver() {
        final ElementListener beforeListener = Mockito.mock(ElementListener.class);
        events().beforeClickOn(beforeListener);
    }
}
