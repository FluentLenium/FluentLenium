package org.fluentlenium.core.hook.wait;

import org.fluentlenium.core.wait.FluentWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Options for {@link WaitHook}.
 */
@Wait
public class WaitHookOptions {

    private TimeUnit timeUnit;
    private Long atMost;
    private TimeUnit pollingTimeUnit;
    private Long pollingEvery;
    private Collection<Class<? extends Throwable>> ignoreAll;
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
    public WaitHookOptions(Wait annotation) {
        timeUnit = annotation.timeUnit();
        pollingTimeUnit = annotation.pollingTimeUnit();
        atMost = annotation.timeout();
        pollingEvery = annotation.pollingInterval();
        ignoreAll = new ArrayList<>(Arrays.asList(annotation.ignoreAll()));
        withNoDefaultsException = annotation.withNoDefaultsException();
    }

    public WaitHookOptions(TimeUnit timeUnit, Long atMost, TimeUnit pollingTimeUnit, Long pollingEvery,
                           Collection<Class<? extends Throwable>> ignoreAll, boolean withNoDefaultsException) {
        this.timeUnit = timeUnit;
        this.atMost = atMost;
        this.pollingTimeUnit = pollingTimeUnit;
        this.pollingEvery = pollingEvery;
        this.ignoreAll = ignoreAll;
        this.withNoDefaultsException = withNoDefaultsException;
    }

    public static WaitHookOptionsBuilder builder() {
        return new WaitHookOptionsBuilder();
    }

    /**
     * Configure fluent wait with this options.
     *
     * @param await fluent wait object to configure
     * @return configured fluent wait object
     */
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

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public Long getAtMost() {
        return atMost;
    }

    public TimeUnit getPollingTimeUnit() {
        return pollingTimeUnit;
    }

    public Long getPollingEvery() {
        return pollingEvery;
    }

    public Collection<Class<? extends Throwable>> getIgnoreAll() {
        return ignoreAll;
    }

    public boolean isWithNoDefaultsException() {
        return withNoDefaultsException;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public void setAtMost(Long atMost) {
        this.atMost = atMost;
    }

    public void setPollingTimeUnit(TimeUnit pollingTimeUnit) {
        this.pollingTimeUnit = pollingTimeUnit;
    }

    public void setPollingEvery(Long pollingEvery) {
        this.pollingEvery = pollingEvery;
    }

    public void setIgnoreAll(Collection<Class<? extends Throwable>> ignoreAll) {
        this.ignoreAll = ignoreAll;
    }

    public void setWithNoDefaultsException(boolean withNoDefaultsException) {
        this.withNoDefaultsException = withNoDefaultsException;
    }

    public static class WaitHookOptionsBuilder {
        private TimeUnit timeUnit;
        private Long atMost;
        private TimeUnit pollingTimeUnit;
        private Long pollingEvery;
        private Collection<Class<? extends Throwable>> ignoreAll;
        private boolean withNoDefaultsException;

        WaitHookOptionsBuilder() {
        }

        public WaitHookOptions.WaitHookOptionsBuilder timeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public WaitHookOptions.WaitHookOptionsBuilder atMost(Long atMost) {
            this.atMost = atMost;
            return this;
        }

        public WaitHookOptions.WaitHookOptionsBuilder pollingTimeUnit(TimeUnit pollingTimeUnit) {
            this.pollingTimeUnit = pollingTimeUnit;
            return this;
        }

        public WaitHookOptions.WaitHookOptionsBuilder pollingEvery(Long pollingEvery) {
            this.pollingEvery = pollingEvery;
            return this;
        }

        public WaitHookOptions.WaitHookOptionsBuilder ignoreAll(Collection<Class<? extends Throwable>> ignoreAll) {
            this.ignoreAll = ignoreAll;
            return this;
        }

        public WaitHookOptions.WaitHookOptionsBuilder withNoDefaultsException(boolean withNoDefaultsException) {
            this.withNoDefaultsException = withNoDefaultsException;
            return this;
        }

        public WaitHookOptions build() {
            return new WaitHookOptions(timeUnit, atMost, pollingTimeUnit, pollingEvery, ignoreAll, withNoDefaultsException);
        }

        public String toString() {
            return "WaitHookOptions.WaitHookOptionsBuilder("
                    + "timeUnit="
                    + this.timeUnit
                    + ", atMost="
                    + this.atMost
                    + ", pollingTimeUnit="
                    + this.pollingTimeUnit
                    + ", pollingEvery="
                    + this.pollingEvery
                    + ", ignoreAll="
                    + this.ignoreAll
                    + ", withNoDefaultsException="
                    + this.withNoDefaultsException
                    + ")";
        }
    }
}
