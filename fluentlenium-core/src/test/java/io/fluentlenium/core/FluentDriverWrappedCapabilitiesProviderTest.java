package io.fluentlenium.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WrapsDriver;

/**
 * Unit test for {@link FluentDriverWrappedCapabilitiesProvider}.
 */
@RunWith(MockitoJUnitRunner.class)
public class FluentDriverWrappedCapabilitiesProviderTest {

    private final FluentDriverWrappedCapabilitiesProvider capabilitiesProvider = new FluentDriverWrappedCapabilitiesProvider();

    @Mock(extraInterfaces = WrapsDriver.class)
    private WebDriver webDriverNotHaveCapabilities; //initial
    @Mock(extraInterfaces = {WrapsDriver.class, HasCapabilities.class})
    private WebDriver webDriverHasCapabilities;
    @Mock
    private Capabilities wrappedCapabilities;

    @Test
    public void shouldReturnInnerMostWrappedDriverCapabilities() {
        when(((WrapsDriver) webDriverNotHaveCapabilities).getWrappedDriver()).thenReturn(webDriverHasCapabilities);
        when(((HasCapabilities) webDriverHasCapabilities).getCapabilities()).thenReturn(wrappedCapabilities);

        Capabilities capabilities = capabilitiesProvider.getCapabilities(webDriverNotHaveCapabilities);

        assertThat(capabilities).isSameAs(wrappedCapabilities);
    }
}
