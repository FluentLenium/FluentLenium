package io.fluentlenium.adapter.cucumber.unit;

import io.fluentlenium.adapter.FluentAdapter;
import io.fluentlenium.adapter.FluentControlContainer;
import io.fluentlenium.adapter.cucumber.FluentCucumberTest;
import io.fluentlenium.adapter.cucumber.FluentTestContainer;
import org.assertj.core.api.Assertions;
import io.fluentlenium.adapter.FluentAdapter;
import io.fluentlenium.adapter.FluentControlContainer;
import io.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.junit.After;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static io.fluentlenium.adapter.cucumber.FluentTestContainer.FLUENT_TEST;

public class FluentTestContainerTest {

    @After
    public void reset() {
        FluentTestContainer.FLUENT_TEST.reset();
    }

    @Test
    public void shouldGetFluentCucumberTestInstance() {

        FluentAdapter test = FluentTestContainer.FLUENT_TEST.instance();

        assertThat(test)
                .isInstanceOf(FluentCucumberTest.class)
                .isNotNull();
    }

    @Test
    public void shouldAlwaysReturnTheSameFluentTestInstance() {

        FluentAdapter test = FluentTestContainer.FLUENT_TEST.instance();
        FluentAdapter test2 = FluentTestContainer.FLUENT_TEST.instance();

        assertThat(test)
                .isNotNull()
                .isEqualTo(test2);
    }

    @Test
    public void childClassShouldProvideSameControlContainer() {

        FluentTestContainer.FLUENT_TEST.instance();
        ExampleTest1 test1 = new ExampleTest1();
        ExampleTest2 test2 = new ExampleTest2();


        Assertions.assertThat(test1.returnContainer())
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
