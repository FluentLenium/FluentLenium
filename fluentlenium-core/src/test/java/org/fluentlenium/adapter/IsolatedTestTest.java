package org.fluentlenium.adapter;

import com.google.common.base.Supplier;
import org.assertj.core.api.Assertions;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.proxy.Proxies;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;

import javax.inject.Inject;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IsolatedTestTest {

    private final Supplier<WebDriver> webDriverFactory = new Supplier<WebDriver>() {
        @Override
        public WebDriver get() {
            return mock(WebDriver.class);
        }
    };

    @Mock
    private WebElement element;

    @Mock
    private WebElement pageElement;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }


    private static class SomePage extends FluentPage {
        private FluentWebElement pageElement;
    }

    private class IsolatedTestSpy extends IsolatedTest {
        @Inject
        private SomePage page;

        private FluentWebElement element;

        @Override
        public WebDriver newWebDriver() {
            WebDriver webDriver = webDriverFactory.get();

            when(webDriver.findElement(new ByIdOrName("element"))).thenReturn(IsolatedTestTest.this.element);
            when(webDriver.findElement(new ByIdOrName("pageElement"))).thenReturn(IsolatedTestTest.this.pageElement);

            return webDriver;
        }

        public void testSomething() {
            Assertions.assertThat(Proxies.getElement(element.now().getElement())).isSameAs(IsolatedTestTest.this.element);
            Assertions.assertThat(Proxies.getElement(page.pageElement.now().getElement())).isSameAs(IsolatedTestTest.this.pageElement);
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
