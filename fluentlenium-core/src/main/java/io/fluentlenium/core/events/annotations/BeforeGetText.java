package io.fluentlenium.core.events.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.fluentlenium.core.events.EventsRegistry;
import io.fluentlenium.core.events.ElementListener;

/**
 * Add this annotation on a method to register it in {@link EventsRegistry}.
 * <p>
 * Can be used in test adapter, injected pages and components.
 * <p>
 * Parameters from {@link ElementListener} will be injected in the method based on parameters types.
 *
 * @see EventsRegistry#beforeGetText(ElementListener)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeGetText {
    /**
     * Priority of the method. Higher priority will be executed first.
     *
     * @return priority value
     */
    int value() default 0;
}
