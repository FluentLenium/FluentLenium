package org.fluentlenium.integration;

import com.google.common.base.Supplier;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.TimeoutException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class FluentLeniumWaitIgnoringTest extends IntegrationFluentTest {

    @Before
    public void before() {
        goTo(DEFAULT_URL);
    }

    private static class CustomException extends RuntimeException {

    }

    private static class CustomException2 extends RuntimeException {

    }

    private static class CustomException3 extends RuntimeException {

    }

    @Test
    public void test_ignoreAll_positive() {

        try {
            Collection<Class<? extends Throwable>> exceptions = new ArrayList<>();
            exceptions.add(CustomException.class);
            exceptions.add(CustomException2.class);

            await().atMost(1, TimeUnit.NANOSECONDS).ignoreAll(exceptions).until(new Supplier<Boolean>() {
                @Override
                public Boolean get() {
                    throw new CustomException();
                }
            });

            throw new AssertionError();
        } catch (TimeoutException e) {
        }

    }

    @Test(expected = CustomException3.class)
    public void test_ignoreAll_negative() {

        try {
            Collection<Class<? extends Throwable>> exceptions = new ArrayList<>();
            exceptions.add(CustomException.class);
            exceptions.add(CustomException2.class);

            await().atMost(1, TimeUnit.NANOSECONDS).ignoreAll(exceptions).until(new Supplier<Boolean>() {
                @Override
                public Boolean get() {
                    throw new CustomException3();
                }
            });

            throw new AssertionError();
        } catch (TimeoutException e) {
        }

    }

    @Test
    public void test_ignoring_1_positive() {

        try {
            await().atMost(1, TimeUnit.NANOSECONDS).ignoring(CustomException.class).ignoring(CustomException2.class)
                    .until(new Supplier<Boolean>() {
                        @Override
                        public Boolean get() {
                            throw new CustomException();
                        }
                    });

            throw new AssertionError();
        } catch (TimeoutException e) {
        }

    }

    @Test(expected = CustomException3.class)
    public void test_ignoring_1_negative() {

        try {
            await().atMost(1, TimeUnit.NANOSECONDS).ignoring(CustomException.class).ignoring(CustomException2.class)
                    .until(new Supplier<Boolean>() {
                        @Override
                        public Boolean get() {
                            throw new CustomException3();
                        }
                    });

            throw new AssertionError();
        } catch (TimeoutException e) {
        }

    }

    @Test
    public void test_ignoring_2_positive() {

        try {
            await().atMost(1, TimeUnit.NANOSECONDS).ignoring(CustomException.class, CustomException2.class)
                    .until(new Supplier<Boolean>() {
                        @Override
                        public Boolean get() {
                            throw new CustomException();
                        }
                    });

            throw new AssertionError();
        } catch (TimeoutException e) {
        }

    }

    @Test(expected = CustomException3.class)
    public void test_ignoring_2_negative() {

        try {
            await().atMost(1, TimeUnit.NANOSECONDS).ignoring(CustomException.class, CustomException2.class)
                    .until(new Supplier<Boolean>() {
                        @Override
                        public Boolean get() {
                            throw new CustomException3();
                        }
                    });

            throw new AssertionError();
        } catch (TimeoutException e) {
        }

    }
}


