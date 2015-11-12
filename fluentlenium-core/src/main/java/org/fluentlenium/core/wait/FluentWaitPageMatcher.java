/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package org.fluentlenium.core.wait;

import static org.fluentlenium.core.wait.WaitMessage.isPageLoaded;

import java.util.ArrayList;
import java.util.List;

import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.FluentThread;
import org.fluentlenium.core.filter.Filter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.google.common.base.Predicate;

//import org.openqa.selenium.support.ui.FluentWait;

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
     *
     * @return fluent
     */
    public Fluent isLoaded() {

        if (!(webDriver instanceof JavascriptExecutor)) {
            throw new UnsupportedOperationException("Driver must support javascript execution to use this feature");
        } else {
            Predicate<Fluent> isLoaded = new com.google.common.base.Predicate<Fluent>() {
                public boolean apply(Fluent fluent) {
                    JavascriptExecutor javascriptExecutor = (JavascriptExecutor) fluent.getDriver();
                    Object result = javascriptExecutor.executeScript("if (document.readyState) return document.readyState;");
                    return result != null && "complete".equals(result);
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
        if (page == null) {
            throw new IllegalArgumentException("You should use a page argument when you call the untilPage method to specify the page you want to be. Example : await().untilPage(myPage).isAt();");
        }
        Predicate<Fluent> isLoaded = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
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
