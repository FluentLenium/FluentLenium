package org.fluentlenium.integration.shareddriver.ignore;

import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.configuration.FluentConfiguration.BooleanValue;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@FluentConfiguration(driverLifecycle = DriverLifecycle.JVM, deleteCookies = BooleanValue.TRUE)
class SharedDriverSuperClass extends IntegrationFluentTest {
}

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SharedDriverSuperClassTest extends SharedDriverSuperClass {

    @Test
    @Order(1)
    void firstMethod() {
        goTo(IntegrationFluentTest.DEFAULT_URL);
        getDriver().manage().addCookie(new Cookie("cookie", "fluent"));
        assertThat($(".small", withName("name"))).hasSize(1);
    }

    @Test
    @Order(2)
    void secondMethod() {
        assertThat($(".small", withName("name"))).hasSize(1);
        assertThat(getCookie("cookie")).isNull();
    }

}
