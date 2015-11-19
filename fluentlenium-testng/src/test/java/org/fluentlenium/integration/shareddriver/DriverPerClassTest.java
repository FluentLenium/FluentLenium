package org.fluentlenium.integration.shareddriver;

import org.fluentlenium.adapter.util.SharedDriver;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@SharedDriver(type = SharedDriver.SharedType.PER_CLASS)
@Test(groups = "DriverPerClass1", suiteName = "PerClass")
public class DriverPerClassTest extends LocalFluentCase {

    @Test
    public void firstMethod() {
        goTo(LocalFluentCase.DEFAULT_URL);
        assertThat($(".small", withName("name"))).hasSize(1);
    }


    @Test
    public void secondMethod() {
        assertThat($(".small", withName("name"))).hasSize(1);
    }


}
