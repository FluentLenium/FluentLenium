package io.fluentlenium.test.events;

import io.fluentlenium.core.events.annotations.AfterClickOn;
import io.fluentlenium.core.events.annotations.BeforeClickOn;
import io.fluentlenium.test.IntegrationFluentTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A dummy super class containing an event listener method.
 *
 * @see ContainerAnnotationsEventsRegistrySuperClassSetupTest
 */
class EventBasedIntegrationFluentTest extends IntegrationFluentTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventBasedIntegrationFluentTest.class);

    @BeforeClickOn
    void beforeClickOnListenerMethod() {
        LOGGER.info("About to click on something...");
    }

    @AfterClickOn(5)
    void afterClickOnListenerMethod() {
        LOGGER.info("Clicked successfully. FluentLenium is awesome! :)");
    }
}
