package org.fluentlenium.core.action;

import com.google.common.base.Predicate;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import static com.google.common.collect.Iterables.getLast;
import static com.google.common.collect.Iterables.getOnlyElement;
import static com.google.common.collect.Sets.difference;

/**
 * Execute actions on active window.
 */
public class WindowAction {
    private final FluentControl fluentControl;
    private final WebDriver driver;

    /**
     * Creates a new window action.
     *
     * @param control control interface
     * @param driver  selenium driver
     */
    public WindowAction(final FluentControl control, final WebDriver driver) {
        this.driver = driver;
        this.fluentControl = control;
    }

    /**
     * Gets the page title.
     *
     * @return page title text
     */
    public String title() {
        return this.driver.getTitle();
    }

    /**
     * Maximize the current window.
     *
     * @return the WindowAction object itself
     */
    public WindowAction maximize() {
        this.driver.manage().window().maximize();
        return this;
    }

    /**
     * FullScreen the current window.
     *
     * @return the WindowAction object itself
     */
    public WindowAction fullscreen() {
        this.driver.manage().window().fullscreen();
        return this;
    }

    /**
     * Sets the current window size.
     *
     * @param size size of the window
     * @return the WindowAction object itself
     */
    public WindowAction setSize(final Dimension size) {
        this.driver.manage().window().setSize(size);
        return this;
    }

    /**
     * Gets the current window size.
     *
     * @return the current window size
     */
    public Dimension getSize() {
        return this.driver.manage().window().getSize();
    }

    /**
     * Sets the current window position.
     *
     * @param position position to set
     * @return the WindowAction object itself
     */
    public WindowAction setPosition(final Point position) {
        this.driver.manage().window().setPosition(position);
        return this;
    }

    /**
     * Gets the current window position.
     *
     * @return the WindowAction object itself
     */
    public Point getPosition() {
        return this.driver.manage().window().getPosition();
    }

    /**
     * Clicks button, which opens new window and switches to newly opened window.
     * <p>
     * This method doesn't force opening window in new window, we assume the code under test will open new window.
     *
     * @param button button to be clicked
     * @return handle of old (parent) window
     */
    public String clickAndOpenNew(final FluentWebElement button) {
        final String oldWindowHandle = this.driver.getWindowHandle();

        final Set<String> oldWindowHandles = this.driver.getWindowHandles();
        button.click();

        waitForNewWindowToOpen(oldWindowHandles);

        final Set<String> newWindowHandles = this.driver.getWindowHandles();
        final String newWindowHandle = getOnlyElement(difference(newWindowHandles, oldWindowHandles));
        switchTo(newWindowHandle);

        return oldWindowHandle;
    }

    /**
     * Opens new window.
     *
     * @return handle of old (parent) window
     */
    public String openNewAndSwitch() {
        final Set<String> oldWindowHandles = this.driver.getWindowHandles();
        final String oldWindowHandle = this.driver.getWindowHandle();

        final JavascriptExecutor jse = (JavascriptExecutor) this.driver;
        jse.executeScript("window.open('someUrl', '_blank')");
        waitForNewWindowToOpen(oldWindowHandles);

        switchToLast(oldWindowHandle);

        return oldWindowHandle;
    }

    /**
     * Clicks button, which closes current window and switches to last window (in set returned by
     * {@link WebDriver#getWindowHandles()}).
     * <p>
     * If the last window is not the target window, use {@link #switchTo(String)}
     * to focus on desired window
     *
     * @param button button to be clicked
     */
    public void clickAndCloseCurrent(final FluentWebElement button) {
        final String currentWindowHandle = this.driver.getWindowHandle();
        button.click();

        this.fluentControl.await().untilWindow(currentWindowHandle).notDisplayed();

        switchToLast();
    }

    /**
     * Close the current window.
     */
    public void close() {
        this.driver.close();
    }

    /**
     * Switches to parent frame.
     *
     * @return the WindowAction object itself
     */
    public WindowAction switchToParentFrame() {
        this.driver.switchTo().parentFrame();
        return this;
    }

    /**
     * Switches to lastly opened window.
     *
     * @return the WindowAction object itself
     */
    public WindowAction switchToLast() {
        final Set<String> windowHandles = new TreeSet<>(this.driver.getWindowHandles());

        this.driver.switchTo().window(getLast(windowHandles));
        return this;
    }

    /**
     * Switches to lastly opened window excluding the one provided as a parameter.
     *
     * @param windowHandleToExclude - if list size is greater then one it will be removed
     * @return the WindowAction object itself
     */
    public WindowAction switchToLast(final String windowHandleToExclude) {
        final Set<String> windowHandles = new TreeSet<>(this.driver.getWindowHandles());

        if (windowHandles.size() > 1) {
            windowHandles.remove(windowHandleToExclude);
        }

        this.driver.switchTo().window(getLast(windowHandles));
        return this;
    }

    /**
     * Switches to particular window by handle.
     *
     * @param windowHandle window handle reference as a String
     * @return the WindowAction object itself
     */
    public WindowAction switchTo(final String windowHandle) {
        this.driver.switchTo().window(windowHandle);
        return this;
    }

    /**
     * Gets the current window object.
     *
     * @return the WebDriver.Window object
     */
    public WebDriver.Window getWindow() {
        return this.driver.manage().window();
    }

    private class WindowHandlesCountIs implements Predicate<FluentControl> {
        private final int expectedValue;

        WindowHandlesCountIs(final int expectedValue) {
            this.expectedValue = expectedValue;
        }

        @Override
        public boolean apply(final FluentControl input) {
            return WindowAction.this.driver.getWindowHandles().size() == this.expectedValue;
        }
    }

    private void waitForNewWindowToOpen(final Set<String> oldWindowHandles) {
        this.fluentControl.await().atMost(10, TimeUnit.SECONDS).withMessage("Timed out waiting for new window to open.")
                .untilPredicate(new WindowHandlesCountIs(oldWindowHandles.size() + 1));
    }
}
