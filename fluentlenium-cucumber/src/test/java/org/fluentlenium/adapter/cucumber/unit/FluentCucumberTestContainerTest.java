package org.fluentlenium.adapter.cucumber.unit;

import org.fluentlenium.adapter.FluentControlContainer;
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.adapter.cucumber.FluentCucumberTestContainer;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FluentCucumberTestContainerTest {

    @Test
    public void shouldGetFluentCucumberTestInstance() {

        FluentCucumberTest test = FluentCucumberTestContainer.FLUENT_TEST.instance();

        assertThat(test)
                .isInstanceOf(FluentCucumberTest.class)
                .isNotNull();
    }

    @Test
    public void shouldAlwaysReturnTheSameFluentTestInstance() {

        FluentCucumberTest test = FluentCucumberTestContainer.FLUENT_TEST.instance();
        FluentCucumberTest test2 = FluentCucumberTestContainer.FLUENT_TEST.instance();

        assertThat(test)
                .isNotNull()
                .isEqualTo(test2);
    }

    @Test
    public void childClassShouldProvideSameControlContainer() {

        FluentCucumberTestContainer.FLUENT_TEST.instance();
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
