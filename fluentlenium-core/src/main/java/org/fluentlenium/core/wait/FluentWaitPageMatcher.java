package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import com.sun.istack.internal.Nullable;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.FluentThread;
import org.fluentlenium.core.filter.Filter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.ArrayList;
import java.util.List;

import static org.fluentlenium.core.wait.WaitMessage.isPageLoaded;

public class FluentWaitPageMatcher {
    private List<Filter> filters = new ArrayList<Filter>();
    private FluentWait wait;
    private WebDriver webDriver;
    private FluentPage page;

    public FluentWaitPageMatcher(FluentWait wait, WebDriver driver) {
        this.wait = wait;
        this.webDriver = driver;
    }


    public FluentWaitPageMatcher(FluentWait wait, WebDriver driver, FluentPage page) {
        this.wait = wait;
        this.webDriver = driver;
        this.page = page;
    }

    /**
     * check if the page is loaded or not.
     * Be careful, it needs javascript enabled. Throw an UnsupportedOperationException if not.
     */
    public Fluent isLoaded() {

        if (!(webDriver instanceof JavascriptExecutor)) {
            throw new UnsupportedOperationException("Driver must support javascript execution to use this feature");
        } else {
            Predicate isLoaded = new com.google.common.base.Predicate<WebDriver>() {
                public boolean apply(@Nullable WebDriver webDriver) {
                    JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
                    Object result = javascriptExecutor.executeScript("if (document.readyState) return document.readyState;");
                    if (result != null) {
                        return "complete".equals((String) result);

                    }
                    return false;
                }
            };
            FluentWaitMatcher.until(wait, isLoaded, filters, isPageLoaded(webDriver.getCurrentUrl()));
        }

        return FluentThread.get();

    }

    /**
     * check if ou are on the good page calling isAt.
     */
    public void isAt() {
        if (page == null){
            throw new IllegalArgumentException("You should use a page argument when you call the untilPage method to specify the page you want to be. Example : await().untilPage(myPage).isAt();");
        }
        Predicate isLoaded = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                try {
                    page.isAt();
                } catch (Error e) {
                    return false;
                }
                return true;
            }
        };
        FluentWaitMatcher.until(wait, isLoaded, filters, "");

    }
}
