package org.fluentlenium.core;

import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.page.PageAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

/**
 * Use the Page Object Pattern to have more resilient tests.
 * <p>
 * Extend this class and use @{@link PageUrl} and @{@link org.openqa.selenium.support.FindBy} annotations to provide
 * injectable Page Objects to FluentLenium.
 */
public abstract class FluentPage extends DefaultFluentContainer implements FluentPageControl {

    private PageAnnotations pageAnnotations = new PageAnnotations(getClass());

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
        By by = pageAnnotations.buildBy();
        if (by != null) {
            try {
                findFirst(by);
            } catch (NoSuchElementException e) {
                throw new AssertionError("@FindBy element not found for page " + getClass().getName());
            }
        }
    }

    @Override
    public final void go() {
        goTo(getUrl());
    }
}
