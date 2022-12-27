package io.fluentlenium.adapter.testng.integration.shareddriver;

import io.fluentlenium.adapter.testng.integration.localtest.IntegrationFluentTestNg;
import io.fluentlenium.configuration.ConfigurationProperties.DriverLifecycle;
import io.fluentlenium.configuration.FluentConfiguration;
import io.fluentlenium.core.filter.FilterConstructor;
import org.openqa.selenium.Cookie;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@FluentConfiguration(driverLifecycle = DriverLifecycle.CLASS)
class SharedDriverSuperClass extends IntegrationFluentTestNg {
}

public class SharedDriverSuperClassTest extends SharedDriverSuperClass {
    @Test
    public void firstMethod() {
        goTo(IntegrationFluentTestNg.DEFAULT_URL);
        getDriver().manage().addCookie(new Cookie("cookie", "fluent"));
        assertThat($(".small", FilterConstructor.withName("name"))).hasSize(1);
    }

    @Test
    public void secondMethod() {
        assertThat($(".small", FilterConstructor.withName("name"))).hasSize(1);
        assertThat(getCookie("cookie")).isNull();
    }

}
