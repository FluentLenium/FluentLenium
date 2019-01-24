package org.fluentlenium.test.capabilities;

import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Capabilities;

import static org.assertj.core.api.Assertions.assertThat;

class CapabilitiesTest extends IntegrationFluentTest {

    @Test
    void testCapabilities() {
        Capabilities capabilities = capabilities();

        assertThat(capabilities).isNotNull();
        assertThat(capabilities.getBrowserName()).isEqualTo("htmlunit");
    }
}
