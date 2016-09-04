package org.fluentlenium.adapter;

import org.fluentlenium.core.FluentDriver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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

        assertThat(defaultContainer.getFluentControl()).isSameAs(driver1);
        assertThat(threadLocalContainer.getFluentControl()).isSameAs(driver1);

        defaultContainer.setFluentControl(null);
        threadLocalContainer.setFluentControl(null);

        assertThat(defaultContainer.getFluentControl()).isNull();
        assertThat(threadLocalContainer.getFluentControl()).isNull();

        defaultContainer.setFluentControl(driver2);
        threadLocalContainer.setFluentControl(driver2);

        assertThat(defaultContainer.getFluentControl()).isSameAs(driver2);
        assertThat(threadLocalContainer.getFluentControl()).isSameAs(driver2);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                assertThat(defaultContainer.getFluentControl()).isSameAs(driver2);
                assertThat(threadLocalContainer.getFluentControl()).isNull();

                defaultContainer.setFluentControl(driver1);
                threadLocalContainer.setFluentControl(driver1);

                assertThat(defaultContainer.getFluentControl()).isSameAs(driver1);
                assertThat(threadLocalContainer.getFluentControl()).isSameAs(driver1);
            }
        });
        executor.shutdown();
        executor.awaitTermination(1L, TimeUnit.MINUTES);

        assertThat(defaultContainer.getFluentControl()).isSameAs(driver1);
        assertThat(threadLocalContainer.getFluentControl()).isSameAs(driver2);
    }
}
