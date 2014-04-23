package org.fluentlenium.integration;

import com.google.common.base.Predicate;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

public class AwaitWithStaleElementReferenceException extends LocalFluentCase {
    @Before
    public void before() {
        goTo(DEFAULT_URL);

    }

    @Test(expected = TimeoutException.class)
    public void given_default_when_state_element_reference_exception_is_thrown_then_it_is_ignored() {
        await().atMost(3, SECONDS).until(new Predicate<Fluent>() {
            @Override
            public boolean apply(Fluent fluent) {
                find(".small").clear();
                throw new StaleElementReferenceException("test");
            }
        });
    }


    @Test(expected = StaleElementReferenceException.class)
        public void given_no_defaults_when_state_element_reference_exception_is_thrown_then_it_is_not_ignored() {
            await().atMost(3, SECONDS).withNoDefaultsException().until(new Predicate<Fluent>() {
                @Override
                public boolean apply(Fluent fluent) {
                    find(".small").clear();
                    throw new StaleElementReferenceException("test");
                }
            });
        }

}
