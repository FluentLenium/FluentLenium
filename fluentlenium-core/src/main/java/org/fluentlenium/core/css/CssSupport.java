package org.fluentlenium.core.css;

/**
 * Features related to CSS loaded in the active page.
 *
 * @see CssControl
 */
public interface CssSupport {

    /**
     * Inject CSS into active page.
     * <p>
     * Additional escaping might be necessary depending on in what form the source if provided.
     * <p>
     * Example:
     * <pre>
     * &#064;Test
     * public void testMethod() {
     *      css().inject("#location {display: none}");
     *      css().inject("#location {\ndisplay: none\n}");
     * }
     * </pre>
     *
     * @param cssSource css source to inject
     */
    void inject(String cssSource);

    /**
     * Inject CSS classpath resource into active page.
     * <p>
     * Example:
     * <pre>
     * &#064;Test
     * public void testMethod() {
     *      css().injectResource("/path/to/css/resource.css");
     * }
     * </pre>
     *
     * @param cssResource css classpath resource to inject
     */
    void injectResource(String cssResource);
}
