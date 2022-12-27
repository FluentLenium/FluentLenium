package io.fluentlenium.configuration;

import java.lang.annotation.*;

/**
 * Define names for a factory
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface FactoryName {
    /**
     * Name of the factory
     *
     * @return factory name
     */
    String value();
}
