package org.fluentlenium.core.events.annotations;

import org.fluentlenium.core.events.ScriptListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Add this annotation on a method to register it in {@link org.fluentlenium.core.events.EventsRegistry}.
 * <p>
 * Can be used in test adapter or injected pages only.
 * <p>
 * Parameters from {@link ScriptListener} will be injected in the method based on parameters types.
 *
 * @see org.fluentlenium.core.events.EventsRegistry#beforeScript(ScriptListener)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeScript {
    /**
     * Priority of the method. Higher priority will be executed first.
     *
     * @return priority value
     */
    int value() default 0;
}
