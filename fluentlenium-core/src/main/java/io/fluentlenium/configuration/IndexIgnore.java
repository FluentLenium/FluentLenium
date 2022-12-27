package io.fluentlenium.configuration;

import java.lang.annotation.*;

/**
 * Classes marked with this annotation will be ignored by automatic registration in {@link Factory}
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface IndexIgnore {
}
