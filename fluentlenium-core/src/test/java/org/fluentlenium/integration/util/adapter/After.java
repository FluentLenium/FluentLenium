package org.fluentlenium.integration.util.adapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A equivalent of JUnit @{@link org.junit.After}.
 * <p>
 * It should be used instead of JUnit @{@link org.junit.After} annotation when screenshot is configured on
 * {@link org.fluentlenium.configuration.ConfigurationProperties.TriggerMode#AUTOMATIC_ON_FAIL} value
 * to make sure screenshot is taken just after the exception occurs, and before @After methods are invoked.
 * <p>
 * When using default JUnit @{@link org.junit.After} annotation, screenshot is taken after the invocation of @After methods and
 * may cause hard to diagnose errors when After change the state of the underlying web application.
 *
 * @see <a href="https://github.com/FluentLenium/FluentLenium/issues/390">Issue #390</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface After {
}
