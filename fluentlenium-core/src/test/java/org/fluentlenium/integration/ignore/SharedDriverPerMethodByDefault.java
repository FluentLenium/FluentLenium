package org.fluentlenium.integration.ignore;

import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SharedDriverPerMethodByDefault extends IntegrationFluentTest {


    @Test
    public void firstMethod() {
        goTo(IntegrationFluentTest.DEFAULT_URL);
        assertThat($(".small", withName("name"))).hasSize(1);
    }


    @Test
    public void secondMethod() {
        assertThat($(".small", withName("name"))).hasSize(0);
    }


}
