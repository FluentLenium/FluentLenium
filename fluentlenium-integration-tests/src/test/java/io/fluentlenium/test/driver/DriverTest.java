package io.fluentlenium.test.driver;

import io.fluentlenium.core.WrongDriverException;
import io.fluentlenium.core.WrongDriverException;
import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DriverTest extends IntegrationFluentTest {

    @Test
    void shouldReturnWebDriver() {
        assertThat(getDriver()).isNotNull();
    }

    @Test
    void shouldThrowWhenAccesingAppiumDriver() {
        assertThatThrownBy(this::getAppiumDriver)
                .isInstanceOf(WrongDriverException.class);
    }

}
