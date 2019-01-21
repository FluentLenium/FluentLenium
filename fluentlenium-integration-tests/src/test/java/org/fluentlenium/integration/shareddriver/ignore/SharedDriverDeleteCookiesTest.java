package org.fluentlenium.integration.shareddriver.ignore;

import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.configuration.FluentConfiguration.BooleanValue;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Cookie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@FluentConfiguration(driverLifecycle = DriverLifecycle.CLASS, deleteCookies = BooleanValue.TRUE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SharedDriverDeleteCookiesTest extends IntegrationFluentTest {

    @Order(1)
    @Test
    void cookieFirstMethod() {
        goTo(IntegrationFluentTest.DEFAULT_URL);
        assertThat($(".small", withName("name"))).hasSize(1);
        getDriver().manage().addCookie(new Cookie("cookie", "fluent"));
    }

    @Order(2)
    @Test
    void cookieSecondMethod() {
        assertThat($(".small", withName("name"))).hasSize(1);
        assertThat(getCookie("cookie")).isNull();
    }

}
