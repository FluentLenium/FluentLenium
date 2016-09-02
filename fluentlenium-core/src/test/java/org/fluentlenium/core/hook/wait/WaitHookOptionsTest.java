package org.fluentlenium.core.hook.wait;

import org.fluentlenium.core.wait.FluentWait;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(waitHookOptions.getAtMost()).isEqualTo(5);
        assertThat(waitHookOptions.getTimeUnit()).isEqualTo(TimeUnit.SECONDS);
        assertThat(waitHookOptions.getPollingEvery()).isEqualTo(250);
        assertThat(waitHookOptions.getPollingTimeUnit()).isEqualTo(TimeUnit.MILLISECONDS);
        assertThat(waitHookOptions.getIgnoreAll()).isEmpty();
        assertThat(waitHookOptions.isWithNoDefaultsException()).isFalse();
    }

    @Test
    public void testDefaultValuesConfigureAwait() {
        waitHookOptions.configureAwait(wait);

        Mockito.verify(wait).atMost(5, TimeUnit.SECONDS);
    }

    @Test
    public void testCustomConfigureAwait() {
        waitHookOptions.setWithNoDefaultsException(true);

        waitHookOptions.configureAwait(wait);

        Mockito.verify(wait).withNoDefaultsException();
    }
}
