package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.search.Search;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static org.fluentlenium.core.wait.WaitMessage.isPageLoaded;

public class FluentWaitPageMatcher {
    private List<Filter> filters = new ArrayList<Filter>();
    private Search search;
    private FluentWait wait;
    private WebDriver webDriver;


    public FluentWaitPageMatcher(Search search, FluentWait wait, WebDriver driver) {
        this.wait = wait;
        this.search = search;
        this.webDriver = driver;
    }


    /**
     * check if the page is loaded or not.
     * Be careful, it needs javascript enabled. Throw an UnsupportedOperationException if not.
     */
    public void isLoaded() {

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


    }


}
