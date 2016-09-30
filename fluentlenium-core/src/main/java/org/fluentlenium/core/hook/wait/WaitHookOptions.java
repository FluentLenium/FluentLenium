package org.fluentlenium.core.hook.wait;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.fluentlenium.core.wait.FluentWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

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

    public WaitHookOptions() {
        this(WaitHookOptions.class.getAnnotation(Wait.class));
    }

    public WaitHookOptions(Wait annotation) {
        timeUnit = annotation.timeUnit();
        pollingTimeUnit = annotation.pollingTimeUnit();
        atMost = annotation.timeout() == -1L ? null : annotation.timeout();
        pollingEvery = annotation.pollingInterval() == -1L ? null : annotation.pollingInterval();
        ignoreAll = new ArrayList<>(Arrays.asList(annotation.ignoreAll()));
        withNoDefaultsException = annotation.withNoDefaultsException();
    }

    protected FluentWait configureAwait(FluentWait await) {
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
