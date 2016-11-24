package org.fluentlenium.integration;

import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.openqa.selenium.Capabilities;

import static org.assertj.core.api.Assertions.assertThat;

public class CapabilitiesTest extends IntegrationFluentTest {
    @Test
    public void testCapabilities() {
        Capabilities capabilities = capabilities();

        assertThat(capabilities).isNotNull();
        assertThat(capabilities.getBrowserName()).isEqualTo("htmlunit");
    }
}
