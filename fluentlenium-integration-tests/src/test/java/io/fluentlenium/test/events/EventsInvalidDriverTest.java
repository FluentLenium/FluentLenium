package io.fluentlenium.test.events;

import io.fluentlenium.configuration.FluentConfiguration;
import io.fluentlenium.core.events.ElementListener;
import io.fluentlenium.configuration.FluentConfiguration;
import io.fluentlenium.core.events.ElementListener;
import io.fluentlenium.test.IntegrationFluentTest;
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
