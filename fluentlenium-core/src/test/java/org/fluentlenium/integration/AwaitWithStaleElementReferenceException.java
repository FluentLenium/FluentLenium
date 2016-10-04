package org.fluentlenium.integration;

import com.google.common.base.Predicate;
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
        await().atMost(3, SECONDS).untilPredicate(new Predicate<FluentControl>() {
            @Override
            public boolean apply(final FluentControl fluent) {
                find(".small").clear();
                throw new StaleElementReferenceException("test");
            }
        });
    }

    @Test(expected = StaleElementReferenceException.class)
    public void givenNoDefaultsWhenStateElementReferenceExceptionIsThrownThenItIsNotIgnored() {
        await().atMost(3, SECONDS).withNoDefaultsException().untilPredicate(new Predicate<FluentControl>() {
            @Override
            public boolean apply(final FluentControl fluent) {
                find(".small").clear();
                throw new StaleElementReferenceException("test");
            }
        });
    }

}
