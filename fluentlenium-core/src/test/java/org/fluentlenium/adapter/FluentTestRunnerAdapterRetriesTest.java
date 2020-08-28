package org.fluentlenium.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.fluentlenium.adapter.sharedwebdriver.SharedWebDriver;
import org.fluentlenium.adapter.sharedwebdriver.SharedWebDriverContainer;
import org.fluentlenium.core.FluentControl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebDriverException;

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
    static FluentTestRunnerAdapter adapter;

    @Test
    public void testFailedWhenBrowserCrashed() {
        String testName = "test1";

        try {
            adapter.starting("test1");
        } catch (WebDriverException ex) {
            assertThat(ex.getMessage()).contains("Browser failed to start");
        }

        verify(adapter, times(1)).starting(testName);
        verify(adapter, times(1)).starting(any(), eq(testName));
        verify(adapter, times(1)).failed(adapter.getClass(), testName);
    }

    @Test
    public void testGetSharedWebDriverRetry() throws ExecutionException, InterruptedException {
        when(future.get()).thenReturn(null);
        when(executorService.submit(any(Callable.class))).thenReturn(future);

        try {
            SharedWebDriverContainer.INSTANCE.getSharedWebDriver(
                    null, executorService, adapter::newWebDriver, adapter.getConfiguration());
        } catch (WebDriverException ex) {
            assertThat(ex.getMessage()).contains("Browser failed to start");
        }

        verify(executorService, times(2)).submit(any(Callable.class));
        verify(executorService, times(2)).shutdownNow();
    }
}
