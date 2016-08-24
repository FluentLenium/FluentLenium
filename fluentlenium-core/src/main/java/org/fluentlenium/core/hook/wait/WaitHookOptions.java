package org.fluentlenium.core.hook.wait;

import lombok.Getter;
import lombok.Setter;
import org.fluentlenium.core.wait.FluentWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class WaitHookOptions {

    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    private Long atMost;
    private Long pollingEvery;
    private java.util.Collection<Class<? extends Throwable>> ignoreAll;
    private boolean withNoDefaultsException;

    public WaitHookOptions() {}

    public WaitHookOptions(Wait annotation) {
        timeUnit = annotation.timeUnit();
        atMost = annotation.atMost() == -1L ? null : annotation.atMost();
        pollingEvery = annotation.pollingEvery() == -1L ? null : annotation.pollingEvery();
        ignoreAll = new ArrayList<Class<? extends Throwable>>(Arrays.asList(annotation.ignoreAll()));
        withNoDefaultsException = annotation.withNoDefaultsException();
    }

    FluentWait configureAwait(FluentWait await) {
        if (atMost != null) {
            await.atMost(atMost, timeUnit);
        }

        if (pollingEvery != null) {
            await.pollingEvery(pollingEvery, timeUnit);
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
