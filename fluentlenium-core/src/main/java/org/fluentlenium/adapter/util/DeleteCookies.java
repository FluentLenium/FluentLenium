package org.fluentlenium.adapter.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Delete cookies after each test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface DeleteCookies {
    /**
     * If deleteCookies is enabled, cookies will be deleted after each test method.
     *
     * @return boolean value for deleteCookies policy
     */
    boolean value() default true;
}
