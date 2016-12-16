package org.fluentlenium.integration;

import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.TimeoutException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

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
    public void testIgnoreAllPositive() {

        try {
            Collection<Class<? extends Throwable>> exceptions = new ArrayList<>();
            exceptions.add(CustomException.class);
            exceptions.add(CustomException2.class);

            await().atMost(1, TimeUnit.NANOSECONDS).ignoreAll(exceptions).until(() -> {
                throw new CustomException();
            });

            throw new AssertionError();
        } catch (TimeoutException e) { // NOPMD EmptyCatchBlock
        }

    }

    @Test(expected = CustomException3.class)
    public void testIgnoreAllNegative() {

        try {
            Collection<Class<? extends Throwable>> exceptions = new ArrayList<>();
            exceptions.add(CustomException.class);
            exceptions.add(CustomException2.class);

            await().atMost(1, TimeUnit.NANOSECONDS).ignoreAll(exceptions).until(() -> {
                throw new CustomException3();
            });

            throw new AssertionError();
        } catch (TimeoutException e) { // NOPMD EmptyCatchBlock
        }

    }

    @Test
    public void testIgnoring1Positive() {

        try {
            await().atMost(1, TimeUnit.NANOSECONDS).ignoring(CustomException.class).ignoring(CustomException2.class)
                    .until(new Supplier<Boolean>() {
                        @Override
                        public Boolean get() {
                            throw new CustomException();
                        }
                    });

            throw new AssertionError();
        } catch (TimeoutException e) { // NOPMD EmptyCatchBlock
        }

    }

    @Test(expected = CustomException3.class)
    public void testIgnoring1Negative() {

        try {
            await().atMost(1, TimeUnit.NANOSECONDS).ignoring(CustomException.class).ignoring(CustomException2.class).until(() -> {
                throw new CustomException3();
            });

            throw new AssertionError();
        } catch (TimeoutException e) { // NOPMD EmptyCatchBlock
        }

    }

    @Test
    public void testIgnoring2Positive() {

        try {
            await().atMost(1, TimeUnit.NANOSECONDS).ignoring(CustomException.class, CustomException2.class).until(() -> {
                throw new CustomException();
            });

            throw new AssertionError();
        } catch (TimeoutException e) { // NOPMD EmptyCatchBlock
        }

    }

    @Test(expected = CustomException3.class)
    public void testIgnoring2Negative() {

        try {
            await().atMost(1, TimeUnit.NANOSECONDS).ignoring(CustomException.class, CustomException2.class).until(() -> {
                throw new CustomException3();
            });

            throw new AssertionError();
        } catch (TimeoutException e) { // NOPMD EmptyCatchBlock
        }

    }
}


