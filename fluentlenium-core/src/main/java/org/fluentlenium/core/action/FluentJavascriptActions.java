package org.fluentlenium.core.action;

/**
 * Javascript actions that can be used on the list or on a web element.
 * <p>
 * Underlying webDriver must support javascript to support those actions.
 *
 * @param <T> {@code this} object type to chain method calls
 */
public interface FluentJavascriptActions<T> {
    /**
     * Scrolls the current element into the visible area of the browser window.
     *
     * @return this object reference to chain methods calls
     * @see <a href="https://developer.mozilla.org/en/docs/Web/API/Element/scrollIntoView">element.scrollIntoView</a>
     */
    T scrollIntoView();

    /**
     * Scrolls the current element into the visible area of the browser window.
     *
     * @param alignWithTop If true, the top of the element will be aligned to the top of the visible area of the scrollable
     *                     ancestor. If false, the bottom of the element will be aligned to the bottom of the visible area of the
     *                     scrollable ancestor.
     * @return this object reference to chain methods calls
     * @see <a href="https://developer.mozilla.org/en/docs/Web/API/Element/scrollIntoView">element.scrollIntoView</a>
     */
    T scrollIntoView(boolean alignWithTop);

    /**
     * Scrolls center of the current element into the visible area of the browser window, respecting window size.
     *
     * @return this object reference to chain methods calls
     */
    T scrollToCenter();
}

