package io.fluentlenium.core.css;

/**
 * Control interface for CSS related features.
 */
public interface CssControl {
    /**
     * Features related to CSS loaded in the active page.
     *
     * @return a CssSupport instance
     */
    CssSupport css();
}
