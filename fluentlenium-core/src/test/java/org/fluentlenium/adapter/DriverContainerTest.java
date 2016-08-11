package org.fluentlenium.adapter;

import org.fluentlenium.core.FluentDriver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class DriverContainerTest {
    @Mock
    private FluentDriver driver1;

    @Mock
    private FluentDriver driver2;

    private ExecutorService executor;

    private DriverContainer threadLocalContainer;
    private DriverContainer defaultContainer;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        executor = Executors.newSingleThreadExecutor();

        threadLocalContainer = new ThreadLocalDriverContainer();
        defaultContainer = new DefaultDriverContainer();
    }

    @Test
    public void testDriverContainer() throws InterruptedException {
        defaultContainer.setFluentDriver(driver1);
        threadLocalContainer.setFluentDriver(driver1);

        assertThat(defaultContainer.getFluentDriver()).isSameAs(driver1);
        assertThat(threadLocalContainer.getFluentDriver()).isSameAs(driver1);

        defaultContainer.setFluentDriver(null);
        threadLocalContainer.setFluentDriver(null);

        assertThat(defaultContainer.getFluentDriver()).isNull();
        assertThat(threadLocalContainer.getFluentDriver()).isNull();

        defaultContainer.setFluentDriver(driver2);
        threadLocalContainer.setFluentDriver(driver2);

        assertThat(defaultContainer.getFluentDriver()).isSameAs(driver2);
        assertThat(threadLocalContainer.getFluentDriver()).isSameAs(driver2);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                assertThat(defaultContainer.getFluentDriver()).isSameAs(driver2);
                assertThat(threadLocalContainer.getFluentDriver()).isNull();

                defaultContainer.setFluentDriver(driver1);
                threadLocalContainer.setFluentDriver(driver1);

                assertThat(defaultContainer.getFluentDriver()).isSameAs(driver1);
                assertThat(threadLocalContainer.getFluentDriver()).isSameAs(driver1);
            }
        });
        executor.shutdown();
        executor.awaitTermination(1L, TimeUnit.MINUTES);

        assertThat(defaultContainer.getFluentDriver()).isSameAs(driver1);
        assertThat(threadLocalContainer.getFluentDriver()).isSameAs(driver2);
    }
}
