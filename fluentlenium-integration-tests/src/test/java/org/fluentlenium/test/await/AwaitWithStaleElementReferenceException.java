package org.fluentlenium.test.await;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;

class AwaitWithStaleElementReferenceException extends IntegrationFluentTest {

    @BeforeEach
    void before() {
        goTo(DEFAULT_URL);

    }

    @Test
    void givenDefaultWhenStateElementReferenceExceptionIsThrownThenItIsIgnored() {
        assertThrows(TimeoutException.class,
                () -> await().atMost(3, SECONDS)
                        .untilPredicate(fluent -> {
                            find(".small").clear();
                            throw new StaleElementReferenceException("test");
                        }));
    }

    @Test
    void givenNoDefaultsWhenStateElementReferenceExceptionIsThrownThenItIsNotIgnored() {
        assertThrows(StaleElementReferenceException.class,
                () -> await().atMost(3, SECONDS)
                        .withNoDefaultsException()
                        .untilPredicate(fluent -> {
                            find(".small").clear();
                            throw new StaleElementReferenceException("test");
                        }));
    }

}
