package org.fluentlenium.core.css;

/**
 * Control interface for css related features.
 */
public interface CssControl {
    /**
     * Features related to CSS loaded in the active page.
     *
     * @return Css support
     */
    CssSupport css();
}
