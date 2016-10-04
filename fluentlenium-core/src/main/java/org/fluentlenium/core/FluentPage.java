package org.fluentlenium.core;

import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.page.ClassAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;

/**
 * Use the Page Object Pattern to have more resilient tests.
 * <p>
 * Extend this class and use @{@link PageUrl} and @{@link org.openqa.selenium.support.FindBy} annotations to provide
 * injectable Page Objects to FluentLenium.
 */
public class FluentPage extends DefaultFluentContainer implements FluentPageControl {

    private final ClassAnnotations classAnnotations = new ClassAnnotations(getClass());

    /**
     * Creates a new fluent page.
     */
    public FluentPage() {
        // Default constructor
    }

    /**
     * Creates a new fluent page, using given fluent control.
     *
     * @param control fluent control
     */
    public FluentPage(final FluentControl control) {
        super(control);
    }

    @Override
    public String getUrl() {
        if (this.getClass().isAnnotationPresent(PageUrl.class)) {
            final String url = this.getClass().getAnnotation(PageUrl.class).value();
            if (!url.isEmpty()) {
                return url;
            }
        }
        return null;
    }

    @Override
    public void isAt() {
        final By by = classAnnotations.buildBy();
        if (by != null) {
            try {
                $(by).first().now();
            } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
                throw new AssertionError("@FindBy element not found for page " + getClass().getName(), e);
            }
        }
    }

    @Override
    public final void go() {
        goTo(getUrl());
    }
}
