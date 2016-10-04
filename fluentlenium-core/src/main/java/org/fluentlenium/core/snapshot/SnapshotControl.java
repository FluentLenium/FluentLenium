package org.fluentlenium.core.snapshot;

/**
 * Control interface for Screenshot and HTML Dumps.
 */
public interface SnapshotControl {

    /**
     * Take a html dump of the browser DOM. By default the file will be a html named by the current
     * timestamp.
     */
    void takeHtmlDump();

    /**
     * Take a html dump of the browser DOM into a file given by the fileName param.
     *
     * @param fileName file name for html dump
     */
    void takeHtmlDump(String fileName);

    /**
     * Check if underlying {@link org.openqa.selenium.WebDriver} can take screenshot.
     *
     * @return true if screenshot can be taken, false otherwise
     */
    boolean canTakeScreenShot();

    /**
     * Take a snapshot of the browser. By default the file will be a png named by the current
     * timestamp.
     */
    void takeScreenShot();

    /**
     * Take a snapshot of the browser into a file given by the fileName param.
     *
     * @param fileName file name for screenshot
     */
    void takeScreenShot(String fileName);
}
