package org.fluentlenium.core.annotation;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>PageUrl</b> is a class annotation used instead of <b>getUrl</b> method of <b>FluentPage</b> object.
 * If <b>PageUrl</b> annotation is used the page class may not override the <b>getUrl</b> method.
 */
@Retention(RUNTIME)
public @interface PageUrl {
    /**
     * The page URL can be relative or absolute, if the URL is not recognized
     * as absolute will be treated as relative.
     * <p>
     * For example :
     * <code>@PageUrl("/index.html")</code>        should redirect to baseUrl + "/index.html"
     * <code>@PageUrl("http://example.com")</code> should redirect to "http://example.com"
     * <code>@PageUrl(file = "index.html", value = "?param1={param1}&param2={param2}",
     * isLocalFile = true)</code> should redirect to local file in current workspace
     *
     * @return page url
     */
    String value();
    String file() default "";
    boolean isLocalFile() default false;
}
