package org.fluentlenium.test.events;

import static org.assertj.core.api.Assertions.assertThat;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.google.common.collect.ImmutableList;
import org.fluentlenium.core.events.EventsRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Integration test for {@link org.fluentlenium.core.events.ContainerAnnotationsEventsRegistry} to validate that
 * listeners based on method annotations are registered in {@link EventsRegistry} from a super class.
 */
class ContainerAnnotationsEventsRegistrySuperClassSetupTest extends EventBasedIntegrationFluentTest {

    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUp() {
        Logger fooLogger = (Logger) LoggerFactory.getLogger(EventBasedIntegrationFluentTest.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        fooLogger.addAppender(listAppender);
    }

    @Test
    void shouldRegisterAnnotationBasedListenersFromSuperClass() {
        assertInitialEventsState();
        goTo(DEFAULT_URL);
        el("button").click();
        assertEventAnnotationExecution();
    }

    private void assertEventAnnotationExecution() {
        assertThat(listAppender.list)
                .extracting(ILoggingEvent::getLevel)
                .containsOnly(Level.INFO)
                .size().isEqualTo(2);
    }

    private void assertInitialEventsState() {
        List<List> excludedLists = ImmutableList.of(events().beforeClickOn, events().afterClickOn);
        assertThat(events().beforeClickOn).hasSize(1);
        assertThat(events().afterClickOn).hasSize(1);

        for (List<List> events : events().eventLists) {
            if (!excludedLists.contains(events)) {
                assertThat(events).isEmpty();
            }
        }
    }
}
