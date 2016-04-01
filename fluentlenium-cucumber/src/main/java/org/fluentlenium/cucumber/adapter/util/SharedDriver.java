package org.fluentlenium.cucumber.adapter.util;

import org.openqa.selenium.Beta;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SharedDriver {
    public enum SharedType {ONCE, PER_SCENARIO}

    SharedType type() default SharedType.PER_SCENARIO;

}
