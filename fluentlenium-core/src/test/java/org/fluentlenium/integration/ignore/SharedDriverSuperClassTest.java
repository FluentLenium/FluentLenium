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

@FluentConfiguration(driverLifecycle = DriverLifecycle.JVM, deleteCookies = BooleanValue.TRUE)
class SharedDriverSuperClass extends IntegrationFluentTest {
}

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SharedDriverSuperClassTest extends SharedDriverSuperClass {
    @Test
    public void firstMethod() {
        goTo(IntegrationFluentTest.DEFAULT_URL);
        this.getDriver().manage().addCookie(new Cookie("cookie", "fluent"));
        assertThat($(".small", withName("name"))).hasSize(1);
    }

    @Test
    public void secondMethod() {
        assertThat($(".small", withName("name"))).hasSize(1);
        assertThat(this.getCookie("cookie")).isNull();
    }

}
