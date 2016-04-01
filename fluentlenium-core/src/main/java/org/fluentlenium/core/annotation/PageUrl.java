package org.fluentlenium.core.annotation;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface PageUrl {
    /**
     * The page URL.
     * URLs starting with {@code "/"} (slash), are treated as relative pages
     *
     * @return page url
     */
    String value();
}
