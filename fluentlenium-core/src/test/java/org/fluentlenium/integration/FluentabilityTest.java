package org.fluentlenium.integration;

import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.assertj.core.api.Assertions.assertThat;

public class FluentabilityTest extends LocalFluentCase {

    @Test
    public void checkIsEnabled() {
        assertThat(
                goTo(DEFAULT_URL).
                        await().
                        atMost(1, NANOSECONDS).
                        until(".small").
                        with("name").
                        equalTo("name").
                        isPresent().
                        find("input").first().isEnabled()).isTrue();
    }

}
