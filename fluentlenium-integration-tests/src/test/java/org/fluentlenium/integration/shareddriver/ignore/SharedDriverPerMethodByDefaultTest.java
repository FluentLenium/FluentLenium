package org.fluentlenium.integration.shareddriver.ignore;

import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SharedDriverPerMethodByDefaultTest extends IntegrationFluentTest {

    @Order(1)
    @Test
    void firstMethod() {
        goTo(IntegrationFluentTest.DEFAULT_URL);
        assertThat($(".small", withName("name"))).hasSize(1);
    }

    @Order(2)
    @Test
    void secondMethod() {
        assertThat($(".small", withName("name"))).hasSize(0);
    }

}
