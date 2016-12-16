package org.fluentlenium.integration;

import java.util.function.Predicate;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

public class AwaitWithStaleElementReferenceException extends IntegrationFluentTest {
    @Before
    public void before() {
        goTo(DEFAULT_URL);

    }

    @Test(expected = TimeoutException.class)
    public void givenDefaultWhenStateElementReferenceExceptionIsThrownThenItIsIgnored() {
        await().atMost(3, SECONDS).untilPredicate(fluent -> {
            find(".small").clear();
            throw new StaleElementReferenceException("test");
        });
    }

    @Test(expected = StaleElementReferenceException.class)
    public void givenNoDefaultsWhenStateElementReferenceExceptionIsThrownThenItIsNotIgnored() {
        await().atMost(3, SECONDS).withNoDefaultsException().untilPredicate(fluent -> {
            find(".small").clear();
            throw new StaleElementReferenceException("test");
        });
    }

}
