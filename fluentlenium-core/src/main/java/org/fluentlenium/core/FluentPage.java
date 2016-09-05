package org.fluentlenium.core;

import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.page.ClassAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;

/**
 * Use the Page Object Pattern to have more resilient tests.
 * <p>
 * Extend this class and use @{@link PageUrl} and @{@link org.openqa.selenium.support.FindBy} annotations to provide
 * injectable Page Objects to FluentLenium.
 */
public abstract class FluentPage extends DefaultFluentContainer implements FluentPageControl {

    private ClassAnnotations classAnnotations = new ClassAnnotations(getClass());

    public FluentPage() {
    }

    public FluentPage(FluentControl control) {
        super(control);
    }

    @Override
    public String getUrl() {
        if (this.getClass().isAnnotationPresent(PageUrl.class)) {
            String url = this.getClass().getAnnotation(PageUrl.class).value();
            if (!url.isEmpty()) {
                return url;
            }
        }
        return null;
    }

    @Override
    public void isAt() {
        By by = classAnnotations.buildBy();
        if (by != null) {
            try {
                findFirst(by).now();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                throw new AssertionError("@FindBy element not found for page " + getClass().getName());
            }
        }
    }

    @Override
    public final void go() {
        goTo(getUrl());
    }
}
