package org.fluentlenium;

import org.fluentlenium.adapter.testng.FluentTestNgSpringTest;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

@ContextConfiguration(locations = {"classpath:spring-test-config.xml"})
public class TestNGWithSpringTest extends FluentTestNgSpringTest {

    @Override
    public String getWebDriver() {
        return "htmlunit";
    }

    @Test
    public void smokeTest() {

    }

}
