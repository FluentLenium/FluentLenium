package org.fluentlenium.adapter.util;

import org.openqa.selenium.Beta;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author : Mathilde Lemee
 */
@Retention(RetentionPolicy.RUNTIME)
@Beta
public @interface SharedBrowser {
  boolean deleteCookies() default true;
}
