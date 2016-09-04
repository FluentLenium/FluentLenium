package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static org.fluentlenium.core.wait.FluentWaitMessages.isPageLoaded;

public class FluentWaitPageMatcher extends AbstractWaitMatcher {
    private FluentWait wait;
    private WebDriver webDriver;
    private FluentPage page;

    protected FluentWaitPageMatcher(FluentWait wait, WebDriver driver) {
        this.wait = wait;
        this.webDriver = driver;
    }


    protected FluentWaitPageMatcher(FluentWait wait, WebDriver driver, FluentPage page) {
        this.wait = wait;
        this.webDriver = driver;
        this.page = page;
    }

    /**
     * check if the page is loaded or not.
     * Be careful, it needs javascript enabled. Throw an UnsupportedOperationException if not.
     */
    public void isLoaded() {

        if (!(webDriver instanceof JavascriptExecutor)) {
            throw new UnsupportedOperationException("Driver must support javascript execution to use this feature");
        } else {
            Predicate<FluentControl> isLoaded = new com.google.common.base.Predicate<FluentControl>() {
                public boolean apply(FluentControl fluent) {
                    Object result = fluent.executeScript("if (document.readyState) return document.readyState;").getStringResult();
                    return result != null && "complete".equals(result);
                }
            };
            until(wait, isLoaded, isPageLoaded(webDriver.getCurrentUrl()));
        }
    }

    /**
     * check if ou are on the good page calling isAt.
     */
    public void isAt() {
        if (page == null) {
            throw new IllegalArgumentException("You should use a page argument when you call the untilPage method to specify the page you want to be. Example : await().untilPage(myPage).isAt();");
        }
        Predicate<FluentControl> isLoaded = new com.google.common.base.Predicate<FluentControl>() {
            public boolean apply(FluentControl fluent) {
                try {
                    page.isAt();
                } catch (Error e) {
                    return false;
                }
                return true;
            }
        };
        until(wait, isLoaded, "");

    }
}
