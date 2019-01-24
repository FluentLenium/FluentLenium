package org.fluentlenium.test.events;

import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.core.events.ElementListener;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;

@FluentConfiguration(eventsEnabled = FluentConfiguration.BooleanValue.FALSE)
class EventsInvalidDriverTest extends IntegrationFluentTest {

    @Test
    void checkInvalidDriver() {
        assertThrows(IllegalStateException.class,
                () -> {
                    ElementListener beforeListener = Mockito.mock(ElementListener.class);
                    events().beforeClickOn(beforeListener);
                });
    }
}
