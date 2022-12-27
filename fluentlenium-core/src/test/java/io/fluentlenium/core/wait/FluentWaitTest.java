package io.fluentlenium.core.wait;

import io.fluentlenium.core.FluentControl;import io.fluentlenium.core.FluentControl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(MockitoJUnitRunner.class)
public class FluentWaitTest {

    @Mock
    FluentControl fluent;

    FluentWait wait;

    @Before
    public void setUp() throws Exception {
        wait = new FluentWait(fluent);
        wait.atMost(1L, TimeUnit.MILLISECONDS);
        wait.pollingEvery(1L, TimeUnit.MILLISECONDS);
    }

    @Test
    public void untilAssertedBlockIsCalled() {
        AtomicBoolean called = new AtomicBoolean(false);
        wait.untilAsserted(() -> called.set(true));
        assertThat(called).isTrue();
    }

    @Test
    public void untilAssertedBlockIsRetried() {
        Runnable block = Mockito.mock(Runnable.class);
        Mockito.doThrow(new AssertionError()).doNothing().when(block).run();

        wait.atMost(10, TimeUnit.MILLISECONDS)
                .untilAsserted(block);
    }

    @Test
    public void untilAssertedFailsOnOtherException() {
        assertThatThrownBy(() ->
                wait.untilAsserted(() -> {
                    throw new RuntimeException("my error");
                }))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("my error");
    }

    @Test
    public void untilAssertedDoesNotFailOnAssertionError() {
        assertThatThrownBy(() ->
                wait.untilAsserted(() -> {
                    throw new AssertionError("my assertion");
                }))
                .isInstanceOf(TimeoutException.class)
                .hasCauseInstanceOf(AssertionError.class)
                .hasRootCauseMessage("my assertion");
    }

    @Test
    public void untilAssertedDoesNotFailOnStaleElement() {
        assertThatThrownBy(() ->
                wait.untilAsserted(() -> {
                    throw new StaleElementReferenceException("stale");
                }))
                .isInstanceOf(TimeoutException.class)
                .hasCauseInstanceOf(StaleElementReferenceException.class);
    }

    @Test
    public void untilAssertedDoesNotFailOnNoSuchElement() {
        assertThatThrownBy(() ->
                wait.untilAsserted(() -> {
                    throw new NoSuchElementException("no");
                }))
                .isInstanceOf(TimeoutException.class)
                .hasCauseInstanceOf(NoSuchElementException.class);
    }
}
