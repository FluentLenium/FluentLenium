package org.fluentlenium.configuration;

import com.codebox.bean.JavaBeanTester;
import org.junit.Test;

public class ProgrammaticConfigurationTest {
    @Test
    public void testDefaultConfiguration() {
        JavaBeanTester.builder(ProgrammaticConfiguration.class).loadData().testInstance(new ProgrammaticConfiguration());
    }
}
