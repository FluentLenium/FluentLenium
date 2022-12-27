package io.fluentlenium.test.fluentability;

import io.fluentlenium.core.filter.FilterConstructor;import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static io.fluentlenium.core.filter.FilterConstructor.with;

class FluentabilityTest extends IntegrationFluentTest {

    @Test
    void checkIsEnabled() {
        goTo(DEFAULT_URL);
        await().atMost(1, NANOSECONDS).until($(".small", FilterConstructor.with("name")
                .equalTo("name"))).present();

        assertThat(find("input").first().enabled()).isTrue();
    }

}
