package org.fluentlenium.configuration;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Unit test for {@link CapabilitiesConfigurationPropertyRetriever}.
 */
public class CapabilitiesConfigurationPropertyRetrieverTest {

    private CapabilitiesConfigurationPropertyRetriever retriever;

    @Before
    public void setup() {
        retriever = new CapabilitiesConfigurationPropertyRetriever();
    }

    @Test
    public void capabilities() {
        DesiredCapabilities capabilitiesJSEnabled = new DesiredCapabilities();
        capabilitiesJSEnabled.setJavascriptEnabled(true);

        Capabilities capabilities = retriever.getCapabilitiesProperty("{\"javascriptEnabled\": true}", null);

        assertThat(capabilities).isEqualTo(capabilitiesJSEnabled);
    }

    @Test
    public void shouldFailExceptionWhenJSONStringCannotBeConverted() {
        assertThatExceptionOfType(ConfigurationException.class)
                .isThrownBy(() -> retriever.getCapabilitiesProperty("{\"javascriptEnabled\": true", null))
                .withMessage("Can't convert JSON Capabilities to Object.");
    }

    @Test
    public void desiredCapabilities() {
        DesiredCapabilities capabilitiesFirefox = PredefinedDesiredCapabilities.firefox();

        Capabilities capabilities = retriever.getCapabilitiesProperty("firefox", null);

        assertThat(capabilities).isEqualTo(capabilitiesFirefox);
    }

    @Test
    public void capabilitiesClassName() {
        Capabilities capabilities = retriever.getCapabilitiesProperty(TestCapabilities.class.getName(), null);

        assertThat(capabilities).isExactlyInstanceOf(TestCapabilities.class);
    }

    @Test
    public void capabilitiesFactory() {
        Capabilities capabilities = retriever.getCapabilitiesProperty("test-capabilities-factory", null);

        assertThat(capabilities).isExactlyInstanceOf(TestCapabilities.class);
    }

    @Test
    public void capabilitiesURL() {
        URL capabilitiesURLJSEnabled = getClass().getResource("/org/fluentlenium/configuration/capabilities.json");
        DesiredCapabilities capabilitiesJSEnabled = new DesiredCapabilities();
        capabilitiesJSEnabled.setJavascriptEnabled(true);

        Capabilities capabilities = retriever.getCapabilitiesProperty(capabilitiesURLJSEnabled.toString(), null);

        assertThat(capabilities).isEqualTo(capabilitiesJSEnabled);
    }

    @Test
    public void shouldFailWithExceptionWhenURLCannotBeRead() {
        assertThatExceptionOfType(ConfigurationException.class)
                .isThrownBy(() -> retriever.getCapabilitiesProperty("https://www.fluentlenium.io/", null))
                .withMessage("Can't read Capabilities defined at https://www.fluentlenium.io/");
    }
}
