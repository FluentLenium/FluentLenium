package org.fluentlenium.core.snapshot;

public interface SnapshotControl {

    /**
     * Take a html dump of the browser DOM. By default the file will be a html named by the current
     * timestamp.
     */
    void takeHtmlDump();

    /**
     * Take a html dump of the browser DOM into a file given by the fileName param.
     *
     * @param fileName
     *            file name for html dump
     */
    void takeHtmlDump(String fileName);

    /**
     * Check if underlying {@link org.openqa.selenium.WebDriver} can take screenshot.
     * 
     * @return
     */
    boolean canTakeScreenShot();

    /**
     * Take a snapshot of the browser. By default the file will be a png named by the current
     * timestamp.
     *
     * @return fluent object
     */
    void takeScreenShot();

    /**
     * Take a snapshot of the browser into a file given by the fileName param.
     *
     * @param fileName
     *            file name for screenshot
     * @return fluent object
     */
    void takeScreenShot(String fileName);
}
