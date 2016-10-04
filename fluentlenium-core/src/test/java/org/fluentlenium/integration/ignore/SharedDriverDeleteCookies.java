package org.fluentlenium.integration.ignore;

import org.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.configuration.FluentConfiguration.BooleanValue;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Cookie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@FluentConfiguration(driverLifecycle = DriverLifecycle.CLASS, deleteCookies = BooleanValue.TRUE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SharedDriverDeleteCookies extends IntegrationFluentTest {

    @Test
    public void cookieFirstMethod() {
        goTo(IntegrationFluentTest.DEFAULT_URL);
        assertThat($(".small", withName("name"))).hasSize(1);
        this.getDriver().manage().addCookie(new Cookie("cookie", "fluent"));
    }

    @Test
    public void cookieSecondMethod() {
        assertThat($(".small", withName("name"))).hasSize(1);
        assertThat(this.getCookie("cookie")).isNull();
    }

}
