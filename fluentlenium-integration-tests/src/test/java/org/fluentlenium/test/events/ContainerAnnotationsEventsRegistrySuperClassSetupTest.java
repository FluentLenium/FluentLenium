package org.fluentlenium.test.events;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableList;
import org.fluentlenium.core.events.EventsRegistry;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Integration test for {@link org.fluentlenium.core.events.ContainerAnnotationsEventsRegistry} to validate that
 * listeners based on method annotations are registered in {@link EventsRegistry} from a super class.
 */
class ContainerAnnotationsEventsRegistrySuperClassSetupTest extends EventBasedIntegrationFluentTest {

    @Test
    void shouldRegisterAnnotationBasedListenersFromSuperClass() {
        assertInitialEventsState();
        goTo(DEFAULT_URL);
        el("button").click();
    }

    private void assertInitialEventsState() {
        List<List> excludedLists = ImmutableList.of(events().beforeClickOn, events().afterClickOn);
        assertThat(events().beforeClickOn).hasSize(1);
        assertThat(events().afterClickOn).hasSize(1);

        for (List<List> events : events().eventLists) {
            if (!excludedLists.contains(events)) {
                assertThat(events).hasSize(0);
            }
        }
    }
}
