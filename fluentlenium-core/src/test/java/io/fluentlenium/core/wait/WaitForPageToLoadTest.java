package io.fluentlenium.core.wait;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
        FluentWaitPageConditions fluentWaitPageBuilder = new FluentWaitPageConditions(wait, webDriver);
        fluentWaitPageBuilder.isLoaded();
    }

    @Test
    public void whenBrowserImplementsJavascriptExecutorThenGoToPredicate() {
        FluentWaitPageConditions fluentWaitPageBuilder = new FluentWaitPageConditions(wait,
                new WebDriverWithJavascriptExecutor());
        fluentWaitPageBuilder.isLoaded();
        verify(wait).untilPredicate(any(Predicate.class));
    }

    private static class WebDriverWithJavascriptExecutor implements WebDriver, JavascriptExecutor {

        public Object executeScript(String script, Object... args) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Object executeAsyncScript(String script, Object... args) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public void get(String url) {
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
