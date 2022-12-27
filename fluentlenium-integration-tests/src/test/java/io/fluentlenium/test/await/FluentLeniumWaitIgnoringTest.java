package io.fluentlenium.test.await;

import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.TimeoutException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FluentLeniumWaitIgnoringTest extends IntegrationFluentTest {

    @BeforeEach
    void before() {
        goTo(DEFAULT_URL);
    }

    private static class CustomException extends RuntimeException {

    }

    private static class CustomException2 extends RuntimeException {

    }

    private static class CustomException3 extends RuntimeException {

    }

    @Test
    void testIgnoreAllPositive() {

        try {
            Collection<Class<? extends Throwable>> exceptions = new ArrayList<>();
            exceptions.add(CustomException.class);
            exceptions.add(CustomException2.class);

            await().atMost(1, TimeUnit.NANOSECONDS).ignoreAll(exceptions).until(() -> {
                throw new CustomException();
            });

            throw new AssertionError();
        } catch (TimeoutException ignored) {
        }

    }

    @Test
    void testIgnoreAllNegative() {
        assertThrows(CustomException3.class,
                () -> {
                    try {
                        Collection<Class<? extends Throwable>> exceptions = new ArrayList<>();
                        exceptions.add(CustomException.class);
                        exceptions.add(CustomException2.class);

                        await().atMost(1, TimeUnit.NANOSECONDS)
                                .ignoreAll(exceptions).until(() -> {
                                            throw new CustomException3();
                                        }
                                );

                        throw new AssertionError();
                    } catch (TimeoutException ignored) {
                    }
                });
    }

    @Test
    void testIgnoring1Positive() {

        try {
            await().atMost(1, TimeUnit.NANOSECONDS).ignoring(CustomException.class)
                    .ignoring(CustomException2.class)
                    .until(() -> {
                        throw new CustomException();
                    });

            throw new AssertionError();
        } catch (TimeoutException ignored) {
        }

    }

    @Test
    void testIgnoring1Negative() {

        assertThrows(CustomException3.class,
                () -> {
                    try {
                        await().atMost(1, TimeUnit.NANOSECONDS)
                                .ignoring(CustomException.class).ignoring(CustomException2.class)
                                .until(() -> {
                                    throw new CustomException3();
                                });

                        throw new AssertionError();
                    } catch (TimeoutException ignored) {
                    }
                });
    }

    @Test
    void testIgnoring2Positive() {

        try {
            await().atMost(1, TimeUnit.NANOSECONDS)
                    .ignoring(CustomException.class, CustomException2.class).until(() -> {
                                throw new CustomException();
                            }
                    );

            throw new AssertionError();
        } catch (TimeoutException ignored) {
        }

    }

    @Test
    void testIgnoring2Negative() {
        assertThrows(CustomException3.class,
                () -> {
                    try {
                        await().atMost(1, TimeUnit.NANOSECONDS)
                                .ignoring(CustomException.class, CustomException2.class)
                                .until(() -> {
                                    throw new CustomException3();
                                });

                        throw new AssertionError();
                    } catch (TimeoutException ignored) {
                    }
                });
    }
}


