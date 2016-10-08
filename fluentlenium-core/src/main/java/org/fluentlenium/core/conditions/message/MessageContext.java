package org.fluentlenium.core.conditions.message;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Message context to append.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageContext {
    /**
     * Message context to append.
     *
     * @return message context to append
     */
    String value();
}
