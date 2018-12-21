package org.fluentlenium.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
public class CapabilitiesJsonParsingTest {

    private static final String CAPABILITIES = "{\"chromeOptions\": {\"args\": [\"headless\",\"disable-gpu\"]}}";

    private static AnnotationConfiguration configuration;

    @FluentConfiguration(webDriver = "chrome", capabilities = CAPABILITIES)
    private static class ConfiguredClass {
    }

    @BeforeClass
    public static void beforeClass() {
        configuration = new AnnotationConfiguration(ConfiguredClass.class);
    }

    @Test
    public void capabilities() {
        Capabilities capabilities = configuration.getCapabilities();
        LinkedHashMap<String, ArrayList<String>> chromeOptions =
                (LinkedHashMap<String, ArrayList<String>>) capabilities.getCapability("chromeOptions");
        assertThat(chromeOptions.get("args")).contains("headless", "disable-gpu");
    }

}