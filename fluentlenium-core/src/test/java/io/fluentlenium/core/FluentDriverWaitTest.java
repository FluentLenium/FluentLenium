package io.fluentlenium.core;

import io.fluentlenium.configuration.Configuration;
import io.fluentlenium.core.wait.FluentWait;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link FluentDriverWait}.
 */
@RunWith(MockitoJUnitRunner.class)
public class FluentDriverWaitTest {

    @Mock
    private Configuration configuration;
    @Mock
    private FluentControl fluentControl;
    private FluentDriverWait fluentDriverWait;

    @Before
    public void setup() {
        fluentDriverWait = new FluentDriverWait(configuration);
    }

    @Test
    public void shouldConfigureAtMost() {
        when(configuration.getAwaitAtMost()).thenReturn(2L);
        when(configuration.getAwaitPollingEvery()).thenReturn(null);
        Duration defaultSeleniumInterval = Duration.ofMillis(500L);

        FluentWait fluentWait = fluentDriverWait.await(fluentControl);

        assertThat(fluentWait.getWait()).hasFieldOrPropertyWithValue("timeout", Duration.ofMillis(2L));
        assertThat(fluentWait.getWait()).hasFieldOrPropertyWithValue("interval", defaultSeleniumInterval);
    }

    @Test
    public void shouldConfigurePollingEvery() {
        when(configuration.getAwaitAtMost()).thenReturn(null);
        when(configuration.getAwaitPollingEvery()).thenReturn(2L);
        Duration defaultSeleniumTimeout = Duration.ofSeconds(5L);

        FluentWait fluentWait = fluentDriverWait.await(fluentControl);

        assertThat(fluentWait.getWait()).hasFieldOrPropertyWithValue("timeout", defaultSeleniumTimeout);
        assertThat(fluentWait.getWait()).hasFieldOrPropertyWithValue("interval", Duration.ofMillis(2L));
    }
}
