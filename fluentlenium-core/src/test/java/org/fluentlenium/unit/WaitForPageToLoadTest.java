/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package org.fluentlenium.unit;


import com.google.common.base.Predicate;
import org.fluentlenium.core.wait.FluentWaitPageMatcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.List;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WaitForPageToLoadTest {

    @Mock
    WebDriver webDriver;

    @Mock
    FluentWait wait;

    @Mock
    WebDriverWithJavascriptExecutor webDriverWithJavascriptExecutor;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        when(wait.withMessage(anyString())).thenReturn(wait);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void when_browser_do_not_implemets_Javascript_executor_throws_exception() {
        FluentWaitPageMatcher fluentWaitPageBuilder = new FluentWaitPageMatcher(wait, webDriver);
        fluentWaitPageBuilder.isLoaded();
    }

    @Test
    public void when_browser_implemets_Javascript_executor_then_go_to_predicate() {
        FluentWaitPageMatcher fluentWaitPageBuilder = new FluentWaitPageMatcher(wait, new WebDriverWithJavascriptExecutor());
        fluentWaitPageBuilder.isLoaded();
        verify(wait).until(any(Predicate.class));
    }

    private class WebDriverWithJavascriptExecutor implements WebDriver, JavascriptExecutor {

        public Object executeScript(String s, Object... objects) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Object executeAsyncScript(String s, Object... objects) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public void get(String s) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        public String getCurrentUrl() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public String getTitle() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public List<WebElement> findElements(By by) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public WebElement findElement(By by) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public String getPageSource() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public void close() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        public void quit() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        public Set<String> getWindowHandles() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public String getWindowHandle() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public TargetLocator switchTo() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Navigation navigate() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Options manage() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
