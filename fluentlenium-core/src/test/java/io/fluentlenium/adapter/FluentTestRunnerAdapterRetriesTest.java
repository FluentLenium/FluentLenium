package io.fluentlenium.adapter;

import io.fluentlenium.adapter.sharedwebdriver.SharedWebDriver;
import io.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer;
import io.fluentlenium.core.FluentControl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebDriverException;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unused")
@RunWith(MockitoJUnitRunner.class)
public class FluentTestRunnerAdapterRetriesTest {
    @Mock
    ExecutorService executorService;
    @Mock
    Future<SharedWebDriver> future;
    @Mock
    FluentControl fluentControl;
    @Mock
    FluentControlContainer fluentControlContainer;
    @Mock
    SharedMutator sharedMutator;

    @Spy
    @InjectMocks
    FluentTestRunnerAdapter adapter;

    @Test
    public void testFailedWhenBrowserCrashed() {
        String testName = "test1";

        assertThatThrownBy(() -> adapter.starting("test1"))
                .isInstanceOf(WebDriverException.class)
                .hasMessageContaining("Browser failed to start");

        verify(adapter, times(1)).starting(testName);
        verify(adapter, times(1)).starting(any(), eq(testName));
        verify(adapter, times(1)).failed(any(), any(), any());
    }

    @Test
    public void testGetSharedWebDriverRetry() throws ExecutionException, InterruptedException {
        when(future.get()).thenReturn(null);
        when(executorService.submit(any(Callable.class))).thenReturn(future);

        SharedWebDriverContainer.INSTANCE.getSharedWebDriver(
                null, executorService, adapter::newWebDriver, adapter.getConfiguration());

        verify(executorService, times(2)).submit(any(Callable.class));
        verify(executorService, times(2)).shutdownNow();
    }
}
