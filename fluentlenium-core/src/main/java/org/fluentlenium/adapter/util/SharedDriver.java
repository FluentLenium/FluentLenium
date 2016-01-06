package org.fluentlenium.adapter.util;

import org.openqa.selenium.Beta;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SharedDriver {
    public enum SharedType {ONCE, PER_CLASS, PER_METHOD}

    /**
     * deleteCookies default : true.
     * If deleteCookies is enabled, after each test method the cookies will be deleted
     *
     * @return boolean value for deleteCookies policy
     */
    boolean deleteCookies() default true;

    SharedType type() default SharedType.ONCE;

}
