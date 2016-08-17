package org.fluentlenium.configuration;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class DefaultConfigurationTest {


    @Test
    public void testConfiguration() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        PropertyDescriptor[] props = Introspector.getBeanInfo(defaultConfiguration.getClass()).getPropertyDescriptors();

        for (PropertyDescriptor prop : props) {
            Method readMethod = prop.getReadMethod();
            if (readMethod.getDeclaringClass() == Object.class) continue;
            switch (prop.getName()) {
                case "webDriver":
                    Assertions.assertThat(readMethod.invoke(defaultConfiguration)).isEqualTo("firefox");
                    break;
                case "configurationFactory":
                    Assertions.assertThat(readMethod.invoke(defaultConfiguration)).isSameAs(DefaultConfigurationFactory.class);
                    break;
                default:
                    Assertions.assertThat(readMethod.invoke(defaultConfiguration)).isNull();
            }
        }
    }
}
