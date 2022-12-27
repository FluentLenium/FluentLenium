package io.fluentlenium.utils;

import com.google.common.collect.ImmutableMap;
import io.fluentlenium.utils.chromium.ChromiumApi;
import io.fluentlenium.utils.chromium.ChromiumApiNotSupportedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.remote.*;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link ChromiumApi}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ChromiumApiTest {

    private ChromiumApi chromiumApi;
    private RemoteWebDriver remoteWebDriver;
    @Mock
    private CommandExecutor executor;
    @Mock
    private SessionId sessionId;

    @Before
    public void before() throws IOException {
        when(sessionId.toString()).thenReturn("test");
        remoteWebDriver = makeDriver("msedge");
        chromiumApi = new ChromiumApi(remoteWebDriver);
    }

    @Test
    public void shouldReturnSessionIdWhenSendCommandAndGetResponseIsCalled() {
        Response response = chromiumApi.sendCommandAndGetResponse("", ImmutableMap.of());
        assertThat(sessionId).hasToString(response.getSessionId());
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

    @Test
    public void shouldThrowAnExceptionIfBrowserOtherThanSupported() throws IOException {
        remoteWebDriver = makeDriver("firefox");
        assertThatExceptionOfType(ChromiumApiNotSupportedException.class)
                .isThrownBy(() -> new ChromiumApi(remoteWebDriver))
                .withMessage("API supported only by Chrome and Edge");
    }

    private RemoteWebDriver makeDriver(String browserName) throws IOException {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setBrowserName(browserName);
        Response response = new Response(sessionId);
        response.setValue(cap.asMap());
        when(executor.execute(any(Command.class))).thenReturn(response);
        return new RemoteWebDriver(executor, cap);
    }
}
