package org.fluentlenium.core.conditions.message;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Message to generate when negation is applied.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotMessage {
    /**
     * Message to generate when negation is applied.
     *
     * @return message to generate when negation is applied
     */
    String value();
}
