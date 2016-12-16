package org.fluentlenium.core.wait;

import java.util.function.Predicate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WaitForPageToLoadTest {

    @Mock
    private WebDriver webDriver;

    @Mock
    private FluentWait wait;

    @Before
    public void before() {
        when(wait.withMessage(anyString())).thenReturn(wait);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenBrowserDoNotImplementsJavascriptExecutorThrowsException() {
        final FluentWaitPageConditions fluentWaitPageBuilder = new FluentWaitPageConditions(wait, webDriver);
        fluentWaitPageBuilder.isLoaded();
    }

    @Test
    public void whenBrowserImplementsJavascriptExecutorThenGoToPredicate() {
        final FluentWaitPageConditions fluentWaitPageBuilder = new FluentWaitPageConditions(wait,
                new WebDriverWithJavascriptExecutor());
        fluentWaitPageBuilder.isLoaded();
        verify(wait).untilPredicate(any(Predicate.class));
    }

    private static class WebDriverWithJavascriptExecutor implements WebDriver, JavascriptExecutor {

        public Object executeScript(final String script, final Object... args) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Object executeAsyncScript(final String script, final Object... args) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public void get(final String url) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        public String getCurrentUrl() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public String getTitle() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public List<WebElement> findElements(final By by) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public WebElement findElement(final By by) {
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
