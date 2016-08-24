package org.fluentlenium.configuration;

import org.assertj.core.api.Assertions;
import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ConfigurationDefaultsTest {


    @Test
    public void testConfiguration() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        ConfigurationDefaults configurationDefaults = new ConfigurationDefaults();
        PropertyDescriptor[] props = Introspector.getBeanInfo(configurationDefaults.getClass()).getPropertyDescriptors();

        for (PropertyDescriptor prop : props) {
            Method readMethod = prop.getReadMethod();
            if (readMethod.getDeclaringClass() == Object.class) continue;
            switch (prop.getName()) {
                case "webDriver":
                    Assertions.assertThat(readMethod.invoke(configurationDefaults)).isEqualTo("firefox");
                    break;
                case "driverLifecycle":
                    Assertions.assertThat(readMethod.invoke(configurationDefaults)).isEqualTo(DriverLifecycle.METHOD);
                    break;
                case "configurationDefaults":
                    Assertions.assertThat(readMethod.invoke(configurationDefaults)).isSameAs(ConfigurationDefaults.class);
                    break;
                case "eventsEnabled":
                    Assertions.assertThat(readMethod.invoke(configurationDefaults)).isEqualTo(true);
                    break;
                default:
                    Assertions.assertThat(readMethod.invoke(configurationDefaults)).isNull();
            }
        }
    }
}
