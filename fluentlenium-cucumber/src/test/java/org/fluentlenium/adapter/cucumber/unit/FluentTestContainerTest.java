package org.fluentlenium.adapter.cucumber.unit;

import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.adapter.FluentControlContainer;
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.adapter.cucumber.FluentTestContainer.FLUENT_TEST;

public class FluentTestContainerTest {

    @Test
    public void shouldGetFluentCucumberTestInstance() {

        FluentAdapter test = FLUENT_TEST.instance();

        assertThat(test)
                .isInstanceOf(FluentCucumberTest.class)
                .isNotNull();
    }

    @Test
    public void shouldAlwaysReturnTheSameFluentTestInstance() {

        FluentAdapter test = FLUENT_TEST.instance();
        FluentAdapter test2 = FLUENT_TEST.instance();

        assertThat(test)
                .isNotNull()
                .isEqualTo(test2);
    }

    @Test
    public void childClassShouldProvideSameControlContainer() {

        FLUENT_TEST.instance();
        ExampleTest1 test1 = new ExampleTest1();
        ExampleTest2 test2 = new ExampleTest2();


        assertThat(test1.returnContainer())
                .isNotNull()
                .isEqualTo(test2.returnContainer());
    }

    private class ExampleTest1 extends FluentCucumberTest {
        public FluentControlContainer returnContainer() {
            return this.getControlContainer();
        }
    }

    private class ExampleTest2 extends FluentCucumberTest {
        public FluentControlContainer returnContainer() {
            return this.getControlContainer();
        }
    }
}
