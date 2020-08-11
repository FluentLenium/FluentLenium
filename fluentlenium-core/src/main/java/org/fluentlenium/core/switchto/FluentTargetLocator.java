package org.fluentlenium.core.switchto;

import org.fluentlenium.core.alert.AlertImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Used to locate a given frame or window.
 *
 * @param <T> self type
 */
public interface FluentTargetLocator<T> {

    /**
     * Select a frame by its (zero-based) index. Selecting a frame by index is equivalent to the
     * JS expression window.frames[index] where "window" is the DOM window represented by the
     * current context. Once the frame has been selected, all subsequent calls on the WebDriver
     * interface are made to that frame.
     *
     * @param index (zero-based) index
     * @return ${@code this} reference for chain calls.
     * @throws org.openqa.selenium.NoSuchFrameException If the frame cannot be found
     */
    T frame(int index);

    /**
     * Select a frame by its name or ID. Frames located by matching name attributes are always given
     * precedence over those matched by ID.
     *
     * @param nameOrId the name of the frame window, the id of the &lt;frame&gt; or &lt;iframe&gt;
     *                 element, or the (zero-based) index
     * @return This driver focused on the given frame
     * @throws org.openqa.selenium.NoSuchFrameException If the frame cannot be found
     */
    T frame(String nameOrId);

    /**
     * Select a frame using its previously located {@link WebElement}.
     *
     * @param frameElement The frame element to switch to.
     * @return This driver focused on the given frame.
     * @throws org.openqa.selenium.NoSuchFrameException           If the given element is neither an IFRAME nor a FRAME element.
     * @throws org.openqa.selenium.StaleElementReferenceException If the WebElement has gone stale.
     */
    T frame(WebElement frameElement);

    /**
     * Select a frame using its previously located {@link WebElement}.
     *
     * @param frameElement The frame element to switch to.
     * @return This driver focused on the given frame.
     * @throws org.openqa.selenium.NoSuchFrameException           If the given element is neither an IFRAME nor a FRAME element.
     * @throws org.openqa.selenium.StaleElementReferenceException If the WebElement has gone stale.
     */
    T frame(FluentWebElement frameElement);

    /**
     * Change focus to the parent context. If the current context is the top level browsing context,
     * the context remains unchanged.
     *
     * @return This driver focused on the parent frame
     */
    T parentFrame();

    /**
     * Switch the focus of future commands for this driver to the window with the given name/handle.
     *
     * @param nameOrHandle The name of the window or the handle as returned by
     *                     {@link WebDriver#getWindowHandle()}
     * @return This driver focused on the given window
     * @throws org.openqa.selenium.NoSuchWindowException If the window cannot be found
     */
    T window(String nameOrHandle);

    /**
     * Selects either the first frame on the page, or the main document when a page contains
     * iframes.
     *
     * @return This driver focused on the top window/first frame.
     */
    T defaultContent();

    /**
     * Switches to the element that currently has focus within the document currently "switched to",
     * or the body element if this cannot be detected. This matches the semantics of calling
     * "document.activeElement" in Javascript.
     *
     * @return The WebElement with focus, or the body element if no element with focus can be
     * detected.
     */
    FluentWebElement activeElement();

    /**
     * Switches to the currently active modal dialog for this particular driver instance.
     *
     * @return A handle to the dialog.
     * @throws org.openqa.selenium.NoAlertPresentException If the dialog cannot be found
     */
    AlertImpl alert();
}
