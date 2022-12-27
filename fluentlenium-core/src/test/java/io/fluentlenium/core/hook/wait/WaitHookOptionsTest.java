package io.fluentlenium.core.hook.wait;

import io.fluentlenium.core.wait.FluentWait;
import io.fluentlenium.core.wait.FluentWait;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

@RunWith(MockitoJUnitRunner.class)
public class WaitHookOptionsTest {
    @Mock
    private FluentWait wait;

    private WaitHookOptions waitHookOptions;

    @Before
    public void before() {
        waitHookOptions = new WaitHookOptions();

    }

    @Test
    public void testDefaultValues() {
        assertThat(waitHookOptions.getAtMost()).isEqualTo(5000L);
        assertThat(waitHookOptions.getTimeUnit()).isEqualTo(TimeUnit.MILLISECONDS);
        assertThat(waitHookOptions.getPollingEvery()).isEqualTo(500L);
        assertThat(waitHookOptions.getPollingTimeUnit()).isEqualTo(TimeUnit.MILLISECONDS);
        assertThat(waitHookOptions.getIgnoreAll()).isEmpty();
        assertThat(waitHookOptions.isWithNoDefaultsException()).isFalse();
    }

    @Test
    public void testDefaultValuesConfigureAwait() {
        waitHookOptions.configureAwait(wait);

        Mockito.verify(wait, never()).atMost(any(Integer.class));
        Mockito.verify(wait, never()).atMost(any(Integer.class), any(TimeUnit.class));
        Mockito.verify(wait, never()).pollingEvery(any(Integer.class));
        Mockito.verify(wait, never()).pollingEvery(any(Integer.class), any(TimeUnit.class));
    }

    @Test
    public void testCustomConfigureAwait() {
        waitHookOptions.setWithNoDefaultsException(true);

        waitHookOptions.configureAwait(wait);

        Mockito.verify(wait).withNoDefaultsException();
    }
}
