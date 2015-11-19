package org.fluentlenium.integration;


import org.fluentlenium.integration.localtest.SauceLabsFluentCase;
import org.junit.Before;
import org.junit.Test;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class FluentLeniumWaitOnLabsTest extends SauceLabsFluentCase {

    @Before
    public void before() {
        goTo(DEFAULT_URL);

    }

    @Test
    public void checkAwaitContainsNameWithNameMatcher() {

        await().atMost(1, NANOSECONDS).until(".small").with("name").contains("name").isPresent();
    }

    @Test
    public void checkAwaitContainsIdWithIdMatcher() {

        await().atMost(1, NANOSECONDS).until(".small").with("id").contains("id2").isPresent();
    }

    @Test
    public void checkAwaitStartWith() {

        await().atMost(1, NANOSECONDS).until(".small").with("id").startsWith("id").hasSize(2);
    }

    @Test
    public void checkAwaitEndsWith() {

        await().atMost(1, NANOSECONDS).until(".small").with("id").endsWith("2").hasSize(1);
    }
}
