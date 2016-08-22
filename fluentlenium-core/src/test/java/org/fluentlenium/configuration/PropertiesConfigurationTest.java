package org.fluentlenium.configuration;


import org.junit.Before;

import java.util.Properties;

public class PropertiesConfigurationTest extends AbstractPropertiesConfigurationTest<PropertiesConfiguration> {

    private PropertiesConfiguration configuration;
    private Properties properties;

    @Before
    public void before() {
        properties = new Properties();
        configuration = new PropertiesConfiguration(properties);
    }

    @Override
    public PropertiesConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    protected void mockProperty(String propertyName, Object propertyValue) {
        if (propertyValue != null) {
            properties.setProperty(propertyName, valueToString(propertyValue));
        } else {
            properties.remove(propertyName);
        }

    }
}
