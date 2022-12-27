package io.fluentlenium.test.events;

import com.google.common.collect.ImmutableList;
import io.fluentlenium.core.events.ContainerAnnotationsEventsRegistry;
import io.fluentlenium.core.events.EventsRegistry;
import io.fluentlenium.core.events.annotations.AfterClickOn;
import io.fluentlenium.core.events.annotations.BeforeClickOn;
import io.fluentlenium.test.IntegrationFluentTest;
import net.jcip.annotations.NotThreadSafe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for {@link ContainerAnnotationsEventsRegistry} to validate that
 * listeners based on method annotations are registered in {@link EventsRegistry} with their priority.
 */
@NotThreadSafe
class ContainerAnnotationsEventsRegistrySetupTest extends IntegrationFluentTest {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ContainerAnnotationsEventsRegistrySetupTest.class);

    private static final String BEFORE = "Before";
    private static final String FIRST_AFTER = "First after";
    private static final String SECOND_AFTER = "Second after";

    private List<String> executed;

    @BeforeEach
    void setUp() {
        executed = new ArrayList<>();
    }

    @BeforeClickOn
    void beforeClickOnListenerMethod() {
        LOGGER.info("About to click on something...");
        executed.add(BEFORE);
    }

    @AfterClickOn(10)
    void afterClickOnListenerMethodWithLowerPriority() {
        LOGGER.info("Clicked successfully. FluentLenium is awesome! :)");
        executed.add(FIRST_AFTER);
    }

    @AfterClickOn(5)
    void afterClickOnListenerMethod() {
        LOGGER.info("Isn't it?");
        executed.add(SECOND_AFTER);
    }

    @Test
    void shouldRegisterAnnotationBasedListenersAndExecuteThem() {
        assertEventsInitialState();
        goTo(DEFAULT_URL);
        el("button").click();
        assertThat(executed).hasSize(3)
                .containsExactly(BEFORE, FIRST_AFTER, SECOND_AFTER);
    }

    @Test
    void shouldRegisterAnnotationBasedListenersAndDoNotExecuteThem() {
        assertEventsInitialState();
        goTo(DEFAULT_URL);
        assertThat(executed).isEmpty();
    }

    private void assertEventsInitialState() {
        List<List> excludedLists = ImmutableList.of(events().afterClickOn, events().beforeClickOn);
        assertThat(events().afterClickOn).hasSize(2);
        assertThat(events().afterClickOn).extracting("priority")
                .containsExactly(10, 5);
        assertThat(events().beforeClickOn).hasSize(1)
                .first().hasFieldOrPropertyWithValue("priority", 0);

        for (List<List> events : events().eventLists) {
            if (!excludedLists.contains(events)) {
                assertThat(events).isEmpty();
            }
        }
    }
}
