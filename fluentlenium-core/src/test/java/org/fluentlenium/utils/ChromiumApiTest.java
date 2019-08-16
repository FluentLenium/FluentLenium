package org.fluentlenium.utils;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.Command;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * Unit test for {@link ChromiumApi}.
 */
public class ChromiumApiTest {

    private ChromiumApi chromiumApi;
    @Mock
    private CommandExecutor executor;
    @Mock
    private SessionId sessionId;

    @Before
    public void before() throws IOException {
        MockitoAnnotations.initMocks(this);
        DesiredCapabilities cap = new DesiredCapabilities();
        when(sessionId.toString()).thenReturn("test");
        Response response = new Response(sessionId);
        response.setValue(cap.asMap());
        when(executor.execute(any(Command.class))).thenReturn(response);
        RemoteWebDriver remoteWebDriver = new RemoteWebDriver(executor, cap);
        chromiumApi = new ChromiumApi(remoteWebDriver);
    }

    @Test
    public void shouldReturnSessionIdWhenSendCommandAndGetResponseIsCalled() {
        Response response = chromiumApi.sendCommandAndGetResponse("", ImmutableMap.of());
        assertEquals(sessionId.toString(), response.getSessionId());
    }

    @Test
    public void shouldInvokeExecuteTwiceWhenDriverIsInstantiatedAndSendCommandIsCalled() throws IOException {
        chromiumApi.sendCommand("", ImmutableMap.of());
        verify(executor, times(2)).execute(any(Command.class));
    }

    @Test
    public void shouldThrowAnExceptionIfWebDriverInstanceIsNull() {
        assertThatNullPointerException()
                .isThrownBy(() -> new ChromiumApi(null))
                .withMessage("WebDriver instance must not be null");
    }
}
