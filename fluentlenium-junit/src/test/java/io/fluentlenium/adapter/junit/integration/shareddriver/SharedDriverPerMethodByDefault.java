package io.fluentlenium.adapter.junit.integration.shareddriver;

import io.fluentlenium.core.filter.FilterConstructor;import io.fluentlenium.adapter.junit.integration.IntegrationFluentTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.assertj.core.api.Assertions.assertThat;
import static io.fluentlenium.core.filter.FilterConstructor.withName;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SharedDriverPerMethodByDefault extends IntegrationFluentTest {

    @Test
    public void firstMethod() {
        goTo(IntegrationFluentTest.DEFAULT_URL);
        assertThat($(".small", FilterConstructor.withName("name"))).hasSize(1);
    }

    @Test
    public void secondMethod() {
        assertThat($(".small", FilterConstructor.withName("name"))).isEmpty();
    }

}
