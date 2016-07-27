package org.fluentlenium.cucumber.adapter.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Configure shared type of the Selenium WebDriver.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SharedDriver {
    /**
     * SharedType possible values.
     */
    enum SharedType {
        ONCE, PER_SCENARIO
    }

    /**
     * Sets the shared type of the driver.
     *
     * @return SharedType of the driver to use for this test class.
     */
    SharedType type() default SharedType.PER_SCENARIO;

}
