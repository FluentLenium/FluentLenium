package org.fluentlenium.core.css;

/**
 * Features related to CSS loaded in the active page.
 */
public interface CssSupport {

    /**
     * Inject CSS into active page.
     *
     * @param cssSource css source to inject.
     */
    void inject(String cssSource);

    /**
     * Inject CSS classpath resource into active page.
     *
     * @param cssResource css classpath resource to inject.
     */
    void injectResource(String cssResource);
}
