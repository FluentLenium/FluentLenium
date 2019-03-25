package org.fluentlenium.core.events;

import org.fluentlenium.core.events.annotations.BeforeClickOn;
import org.fluentlenium.test.IntegrationFluentTest;

/**
 * A dummy super class containing an event listener method.
 *
 * @see ContainerAnnotationsEventsRegistrySuperClassSetupTest
 */
class EventBasedIntegrationFluentTest extends IntegrationFluentTest {

    @BeforeClickOn
    void beforeClickOnListenerMethod() {
    }
}
