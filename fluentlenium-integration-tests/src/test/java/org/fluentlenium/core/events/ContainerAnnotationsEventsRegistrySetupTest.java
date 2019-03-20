package org.fluentlenium.core.events;

import static org.assertj.core.api.Assertions.assertThat;

import org.fluentlenium.core.events.annotations.AfterClickOn;
import org.fluentlenium.core.events.annotations.BeforeClickOn;
import org.fluentlenium.test.IntegrationFluentTest;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Integration test for {@link org.fluentlenium.core.events.ContainerAnnotationsEventsRegistry} to validate that
 * listeners based on method annotations are registered in {@link EventsRegistry} with their priority.
 */
class ContainerAnnotationsEventsRegistrySetupTest extends IntegrationFluentTest {

    @AfterClickOn(5)
    @BeforeClickOn
    @Test
    void shouldRegisterAnnotationBasedListeners() {
        List<List> excludedLists = ImmutableList.of(events().afterClickOn, events().beforeClickOn);
        assertThat(events().afterClickOn).hasSize(1);
        assertThat(events().afterClickOn).first().hasFieldOrPropertyWithValue("priority", 5);
        assertThat(events().beforeClickOn).hasSize(1);
        assertThat(events().beforeClickOn).first().hasFieldOrPropertyWithValue("priority", 0);

        for (List<List> events : events().eventLists) {
            if (!excludedLists.contains(events)) {
                assertThat(events).hasSize(0);
            }
        }
    }
}
