package org.fluentlenium.test.events;

import org.fluentlenium.core.events.annotations.AfterClickOn;
import org.fluentlenium.core.events.annotations.BeforeClickOn;
import org.fluentlenium.test.IntegrationFluentTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A dummy super class containing an event listener method.
 *
 * @see ContainerAnnotationsEventsRegistrySuperClassSetupTest
 */
class EventBasedIntegrationFluentTest extends IntegrationFluentTest {

    private static final Logger logger = LoggerFactory.getLogger(EventBasedIntegrationFluentTest.class);

    @BeforeClickOn
    void beforeClickOnListenerMethod() {
        logger.info("About to click on something...");
    }

    @AfterClickOn(5)
    void afterClickOnListenerMethod() {
        logger.info("Clicked successfully. FluentLenium is awesome! :)");
    }
}
