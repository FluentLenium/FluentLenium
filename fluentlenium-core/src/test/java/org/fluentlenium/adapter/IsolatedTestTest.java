package org.fluentlenium.adapter;

import org.assertj.core.api.Assertions;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.proxy.LocatorProxies;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;

import java.util.function.Supplier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IsolatedTestTest {
    private final Supplier<WebDriver> webDriverFactory = () -> mock(WebDriver.class);

    @Mock
    private WebElement element;

    @Mock
    private WebElement pageElement;

    private static class SomePage extends FluentPage {
        private FluentWebElement pageElement;
    }

    private class IsolatedTestSpy extends IsolatedTest {
        @Page
        private SomePage page;

        private FluentWebElement element;

        @Override
        public WebDriver newWebDriver() {
            WebDriver webDriver = webDriverFactory.get();

            when(webDriver.findElement(new ByIdOrName("element"))).thenReturn(IsolatedTestTest.this.element);
            when(webDriver.findElement(new ByIdOrName("pageElement"))).thenReturn(pageElement);

            return webDriver;
        }

        public void testSomething() {
            Assertions.assertThat(LocatorProxies.getLocatorResult(element.now().getElement()))
                    .isSameAs(IsolatedTestTest.this.element);
            Assertions.assertThat(LocatorProxies.getLocatorResult(page.pageElement.now().getElement())).isSameAs(pageElement);
        }
    }

    @Test
    public void testIsolated() {
        IsolatedTestSpy spy = spy(new IsolatedTestSpy());
        spy.testSomething();

        verify(spy.getDriver(), never()).quit();
        spy.quit();

        //verify(spy.getDriver()).quit();
    }
}
