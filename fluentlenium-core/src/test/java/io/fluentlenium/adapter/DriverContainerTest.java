package io.fluentlenium.adapter;

import io.fluentlenium.core.FluentDriver;import org.assertj.core.api.Assertions;import io.fluentlenium.core.FluentDriver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DriverContainerTest {
    @Mock
    private FluentDriver driver1;

    @Mock
    private FluentDriver driver2;

    private ExecutorService executor;

    private FluentControlContainer threadLocalContainer;
    private FluentControlContainer defaultContainer;

    @Before
    public void before() {
        executor = Executors.newSingleThreadExecutor();

        threadLocalContainer = new ThreadLocalFluentControlContainer();
        defaultContainer = new DefaultFluentControlContainer();
    }

    @Test
    public void testDriverContainer() throws InterruptedException {
        defaultContainer.setFluentControl(driver1);
        threadLocalContainer.setFluentControl(driver1);

        Assertions.assertThat(defaultContainer.getFluentControl()).isSameAs(driver1);
        Assertions.assertThat(threadLocalContainer.getFluentControl()).isSameAs(driver1);

        defaultContainer.setFluentControl(null);
        threadLocalContainer.setFluentControl(null);

        Assertions.assertThat(defaultContainer.getFluentControl()).isNull();
        Assertions.assertThat(threadLocalContainer.getFluentControl()).isNull();

        defaultContainer.setFluentControl(driver2);
        threadLocalContainer.setFluentControl(driver2);

        Assertions.assertThat(defaultContainer.getFluentControl()).isSameAs(driver2);
        Assertions.assertThat(threadLocalContainer.getFluentControl()).isSameAs(driver2);

        executor.execute(() -> {
            Assertions.assertThat(defaultContainer.getFluentControl()).isSameAs(driver2);
            Assertions.assertThat(threadLocalContainer.getFluentControl()).isNull();

            defaultContainer.setFluentControl(driver1);
            threadLocalContainer.setFluentControl(driver1);

            Assertions.assertThat(defaultContainer.getFluentControl()).isSameAs(driver1);
            Assertions.assertThat(threadLocalContainer.getFluentControl()).isSameAs(driver1);
        });
        executor.shutdown();
        executor.awaitTermination(1L, TimeUnit.MINUTES);

        Assertions.assertThat(defaultContainer.getFluentControl()).isSameAs(driver1);
        Assertions.assertThat(threadLocalContainer.getFluentControl()).isSameAs(driver2);
    }
}
