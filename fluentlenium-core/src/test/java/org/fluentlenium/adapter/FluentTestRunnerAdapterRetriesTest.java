package org.fluentlenium.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.fluentlenium.core.FluentControl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriverException;

@RunWith(MockitoJUnitRunner.class)
public class FluentTestRunnerAdapterRetriesTest {
    @Mock
    ExecutorService executorService;
    @Mock
    Future future;
    @Mock
    FluentControl fluentControl;
    @Mock
    FluentControlContainer fluentControlContainer;
    @Mock
    SharedMutator sharedMutator;

    @Spy
    @InjectMocks
    static FluentTestRunnerAdapter adapter;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test()
    public void testFailedWhenBrowserCrashed() throws IOException, ExecutionException, InterruptedException {
        String testName = "test1";

        when(adapter.getBrowserTimeout()).thenReturn(1L);
        when(adapter.getBrowserTimeoutRetries()).thenReturn(1);

        try {
            adapter.starting("test1");
        } catch (WebDriverException ex) {
            assertThat(ex.getMessage()).contains("Browser failed to start");
        }

        verify(adapter, times(1)).starting(testName);
        verify(adapter, times(1)).starting(any(), eq(testName));
        verify(adapter, times(1)).failed(adapter.getClass(), testName);
    }


    @Test()
    public void testGetSharedWebDriverRetry() throws IOException, ExecutionException, InterruptedException {
        when(adapter.getBrowserTimeoutRetries()).thenReturn(2);
        when(adapter.getBrowserTimeout()).thenReturn(1L);

        when(future.get()).thenReturn(null);
        when(executorService.submit(any(Callable.class))).thenReturn(future);

        try {
            adapter.getSharedWebDriver(null, executorService);
        } catch (WebDriverException ex) {
            assertThat(ex.getMessage()).contains("Browser failed to start");
        }

        verify(executorService, times(2)).submit(any(Callable.class));
        verify(executorService, times(2)).shutdownNow();
    }
}
