package org.fluentlenium.core.hook.wait;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.fluentlenium.core.wait.FluentWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Options for {@link WaitHook}.
 */
@Getter
@Setter
@Builder
@Wait
@AllArgsConstructor
public class WaitHookOptions {

    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    private Long atMost;
    private TimeUnit pollingTimeUnit = TimeUnit.MILLISECONDS;
    private Long pollingEvery;
    private java.util.Collection<Class<? extends Throwable>> ignoreAll;
    private boolean withNoDefaultsException;

    /**
     * Creates a new wait hook options, with default annotation options.
     */
    public WaitHookOptions() {
        this(WaitHookOptions.class.getAnnotation(Wait.class));
    }

    /**
     * Creates a new wait hook options, with given annotation options.
     *
     * @param annotation wait annotation
     */
    public WaitHookOptions(final Wait annotation) {
        timeUnit = annotation.timeUnit();
        pollingTimeUnit = annotation.pollingTimeUnit();
        atMost = annotation.timeout() == -1L ? null : annotation.timeout();
        pollingEvery = annotation.pollingInterval() == -1L ? null : annotation.pollingInterval();
        ignoreAll = new ArrayList<>(Arrays.asList(annotation.ignoreAll()));
        withNoDefaultsException = annotation.withNoDefaultsException();
    }

    /**
     * Configure fluent wait with this options.
     *
     * @param await fluent wait object to configure
     * @return configured fluent wait object
     */
    protected FluentWait configureAwait(final FluentWait await) {
        if (atMost != null) {
            await.atMost(atMost, timeUnit);
        }

        if (pollingEvery != null) {
            await.pollingEvery(pollingEvery, pollingTimeUnit);
        }

        if (withNoDefaultsException) {
            await.withNoDefaultsException();
        }

        if (ignoreAll != null) {
            await.ignoreAll(ignoreAll);
        }

        return await;
    }
}
