package io.fluentlenium.shareddriver;

import io.fluentlenium.IntegrationFluentTestNg;import io.fluentlenium.core.filter.FilterConstructor;import io.fluentlenium.IntegrationFluentTestNg;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static io.fluentlenium.core.filter.FilterConstructor.withName;

public class DriverPerMethodTest extends IntegrationFluentTestNg {

    @Test
    public void firstMethod() {
        goTo(IntegrationFluentTestNg.DEFAULT_URL);
        assertThat($(".small", FilterConstructor.withName("name"))).hasSize(1);
    }

    @Test
    public void secondMethod() {
        assertThat($(".small", FilterConstructor.withName("name"))).isEmpty();
    }

}
