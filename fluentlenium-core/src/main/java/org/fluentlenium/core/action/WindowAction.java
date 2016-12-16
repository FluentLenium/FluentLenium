package org.fluentlenium.core.action;

import com.google.common.base.Predicate;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.switchto.FluentTargetLocator;
import org.fluentlenium.core.switchto.FluentTargetLocatorImpl;
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
    private final ComponentInstantiator instantiator;
    private final WebDriver driver;

    /**
     * Creates a new window action.
     *
     * @param control      control interface
     * @param instantiator component instantiator
     * @param driver       selenium driver
     */
    public WindowAction(FluentControl control, ComponentInstantiator instantiator, WebDriver driver) {
        this.driver = driver;
        this.instantiator = instantiator;
        fluentControl = control;
    }

    /**
     * Gets the page title.
     *
     * @return page title text
     */
    public String title() {
        return driver.getTitle();
    }

    /**
     * Maximize the current window.
     *
     * @return the WindowAction object itself
     */
    public WindowAction maximize() {
        driver.manage().window().maximize();
        return this;
    }

    /**
     * FullScreen the current window.
     *
     * @return the WindowAction object itself
     */
    public WindowAction fullscreen() {
        driver.manage().window().fullscreen();
        return this;
    }

    /**
     * Sets the current window size.
     *
     * @param size size of the window
     * @return the WindowAction object itself
     */
    public WindowAction setSize(Dimension size) {
        driver.manage().window().setSize(size);
        return this;
    }

    /**
     * Gets the current window size.
     *
     * @return the current window size
     */
    public Dimension getSize() {
        return driver.manage().window().getSize();
    }

    /**
     * Sets the current window position.
     *
     * @param position position to set
     * @return the WindowAction object itself
     */
    public WindowAction setPosition(Point position) {
        driver.manage().window().setPosition(position);
        return this;
    }

    /**
     * Gets the current window position.
     *
     * @return the WindowAction object itself
     */
    public Point getPosition() {
        return driver.manage().window().getPosition();
    }

    /**
     * Clicks button, which opens new window and switches to newly opened window.
     * <p>
     * This method doesn't force opening window in new window, we assume the code under test will open new window.
     *
     * @param button button to be clicked
     * @return handle of old (parent) window
     */
    public String clickAndOpenNew(FluentWebElement button) {
        String oldWindowHandle = driver.getWindowHandle();

        Set<String> oldWindowHandles = driver.getWindowHandles();
        button.click();

        waitForNewWindowToOpen(oldWindowHandles);

        Set<String> newWindowHandles = driver.getWindowHandles();
        String newWindowHandle = getOnlyElement(difference(newWindowHandles, oldWindowHandles));
        switchTo(newWindowHandle);

        return oldWindowHandle;
    }

    /**
     * Opens new window.
     *
     * @return handle of old (parent) window
     */
    public String openNewAndSwitch() {
        Set<String> oldWindowHandles = driver.getWindowHandles();
        String oldWindowHandle = driver.getWindowHandle();

        JavascriptExecutor jse = (JavascriptExecutor) driver;
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
    public void clickAndCloseCurrent(FluentWebElement button) {
        String currentWindowHandle = driver.getWindowHandle();
        button.click();

        fluentControl.await().untilWindow(currentWindowHandle).notDisplayed();

        switchToLast();
    }

    /**
     * Close the current window.
     */
    public void close() {
        driver.close();
    }

    /**
     * Create a switch target locator.
     *
     * @return an object to perform switch on various target.
     */
    public FluentTargetLocator<WindowAction> switchTo() {
        return new FluentTargetLocatorImpl<>(this, instantiator, driver.switchTo());
    }

    /**
     * Switches to lastly opened window.
     *
     * @return the WindowAction object itself
     */
    public WindowAction switchToLast() {
        Set<String> windowHandles = new TreeSet<>(driver.getWindowHandles());
        driver.switchTo().window(getLast(windowHandles));
        return this;
    }

    /**
     * Switches to lastly opened window excluding the one provided as a parameter.
     *
     * @param nameOrHandleToExclude if list size is greater than one it will be removed
     * @return the WindowAction object itself
     */
    public WindowAction switchToLast(String nameOrHandleToExclude) {
        Set<String> windowHandles = new TreeSet<>(driver.getWindowHandles());

        if (windowHandles.size() > 1) {
            windowHandles.remove(nameOrHandleToExclude);
        }

        driver.switchTo().window(getLast(windowHandles));
        return this;
    }

    /**
     * Switches to particular window by handle.
     *
     * @param nameOrHandle window name or handle
     * @return the WindowAction object itself
     */
    public WindowAction switchTo(String nameOrHandle) {
        return switchTo().window(nameOrHandle);
    }

    /**
     * Gets the current window object.
     *
     * @return the WebDriver.Window object
     */
    public WebDriver.Window getWindow() {
        return driver.manage().window();
    }

    private class WindowHandlesCountIs implements Predicate<FluentControl> {
        private final int expectedValue;

        WindowHandlesCountIs(int expectedValue) {
            this.expectedValue = expectedValue;
        }

        @Override
        public boolean apply(FluentControl input) {
            return driver.getWindowHandles().size() == expectedValue;
        }
    }

    private void waitForNewWindowToOpen(Set<String> oldWindowHandles) {
        fluentControl.await().atMost(10, TimeUnit.SECONDS).withMessage("Timed out waiting for new window to open.")
                .untilPredicate(new WindowHandlesCountIs(oldWindowHandles.size() + 1));
    }
}
