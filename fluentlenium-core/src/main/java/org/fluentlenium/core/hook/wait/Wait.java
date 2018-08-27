package org.fluentlenium.core.hook.wait;

import org.fluentlenium.core.hook.Hook;
import org.fluentlenium.core.hook.HookOptions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Annotation to enable the Wait Hook.
 *
 * @see WaitHook
 * @see WaitHookOptions
 */
@Inherited
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Hook(WaitHook.class)
@HookOptions(WaitHookOptions.class)
public @interface Wait {
    /**
     * Maximum amount of time to wait before throwing a {@link org.openqa.selenium.TimeoutException}.
     *
     * @return timeout value
     */
    long timeout() default 5000L;

    /**
     * Time unit used for timeout value.
     *
     * @return time unit
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * Time interval to wait between each condition check.
     *
     * @return polling interval value
     */
    long pollingInterval() default 500L;

    /**
     * Time unit used for polling interval.
     *
     * @return time unit
     */
    TimeUnit pollingTimeUnit() default TimeUnit.MILLISECONDS;

    /**
     * Enable this option to disable default exceptions from {@link org.fluentlenium.core.wait.FluentWait}.
     *
     * @return boolean
     */
    boolean withNoDefaultsException() default false;

    /**
     * Throwables that will be ignored while waiting for a condition.
     *
     * @return array of ignored throwable
     */
    java.lang.Class<? extends Throwable>[] ignoreAll() default {};
}
