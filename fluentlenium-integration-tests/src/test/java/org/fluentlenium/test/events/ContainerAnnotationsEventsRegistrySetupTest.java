package org.fluentlenium.test.events;

import static org.assertj.core.api.Assertions.assertThat;

import net.jcip.annotations.NotThreadSafe;
import org.fluentlenium.core.events.EventsRegistry;
import org.fluentlenium.core.events.annotations.AfterClickOn;
import org.fluentlenium.core.events.annotations.BeforeClickOn;
import org.fluentlenium.test.IntegrationFluentTest;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Integration test for {@link org.fluentlenium.core.events.ContainerAnnotationsEventsRegistry} to validate that
 * listeners based on method annotations are registered in {@link EventsRegistry} with their priority.
 */
@NotThreadSafe
class ContainerAnnotationsEventsRegistrySetupTest extends IntegrationFluentTest {

    private static final Logger logger =
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
        logger.info("About to click on something...");
        executed.add(BEFORE);
    }

    @AfterClickOn(10)
    void afterClickOnListenerMethodWithLowerPriority() {
        logger.info("Clicked successfully. FluentLenium is awesome! :)");
        executed.add(FIRST_AFTER);
    }

    @AfterClickOn(5)
    void afterClickOnListenerMethod() {
        logger.info("Isn't it?");
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
        assertThat(events().beforeClickOn).hasSize(1);
        assertThat(events().beforeClickOn).first().hasFieldOrPropertyWithValue("priority", 0);

        for (List<List> events : events().eventLists) {
            if (!excludedLists.contains(events)) {
                assertThat(events).hasSize(0);
            }
        }
    }
}
