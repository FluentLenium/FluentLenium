package io.fluentlenium.configuration;

import java.lang.annotation.*;

/**
 * Defines the priority of the factory.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface FactoryPriority {
    /**
     * Priority of the factory
     *
     * @return factory priority
     */
    int value();
}
