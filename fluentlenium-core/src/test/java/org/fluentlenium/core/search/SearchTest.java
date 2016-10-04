package org.fluentlenium.core.search;

import com.google.common.collect.Lists;
import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.filter.matcher.AbstractMacher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchTest {
    @Mock
    private WebDriver driver;

    @Mock
    private SearchContext searchContext;

    @Mock
    private Filter filter1;

    @Mock
    private AbstractMacher matcher1;

    @Mock
    private Filter filter2;

    private Search search;

    @Before
    public void before() {
        FluentAdapter fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(driver);

        DefaultComponentInstantiator instantiator = new DefaultComponentInstantiator(fluentAdapter);
        search = new Search(searchContext, instantiator);
    }

    @After
    public void after() {
        reset(searchContext);
    }

    @Test
    public void findCheckCssIsWellFormed() {
        WebElement webElement = mock(WebElement.class);
        WebElement webElement2 = mock(WebElement.class);
        List<WebElement> webElements = Lists.newArrayList(webElement, webElement2);

        when(searchContext.findElements(By.cssSelector("cssStyle[generated=true][checked=ok]"))).thenReturn(webElements);

        String name = "cssStyle";
        Filter[] filters = new Filter[]{filter1, filter2};
        when(filter1.isPreFilter()).thenReturn(true);
        when(filter1.toString()).thenReturn("[generated=true]");
        when(filter2.isPreFilter()).thenReturn(true);
        when(filter2.toString()).thenReturn("[checked=ok]");

        search.find(name, filters).now();
        verify(searchContext).findElements(By.cssSelector("cssStyle[generated=true][checked=ok]"));
    }

    @Test
    public void canLoopIntoFluentWebElementAfterASearch() {
        WebElement webElement = mock(WebElement.class);
        WebElement webElement2 = mock(WebElement.class);
        List<WebElement> webElements = Lists.newArrayList(webElement, webElement2);

        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(webElements);
        for (FluentWebElement fluentWebElement : search.find("cssStyle")) {
            //just to check the cast
            assertThat(fluentWebElement).isInstanceOf(FluentWebElement.class);
        }

    }

    @Test
    public void findCheckCssIsWellFormedWithPostSelector() {
        WebElement webElement = mock(WebElement.class);
        WebElement webElement2 = mock(WebElement.class);
        List<WebElement> webElements = Lists.newArrayList(webElement, webElement2);

        when(searchContext.findElements(By.cssSelector("cssStyle[generated=true]"))).thenReturn(webElements);

        String name = "cssStyle";
        Filter[] filters = new Filter[]{filter1, filter2};
        when(filter1.isPreFilter()).thenReturn(true);
        when(filter1.toString()).thenReturn("[generated=true]");
        when(filter2.isPreFilter()).thenReturn(false);
        when(filter2.toString()).thenReturn("[checked=ok]");
        AbstractMacher matcher = mock(AbstractMacher.class);
        when(matcher.isSatisfiedBy(any(String.class))).thenReturn(true);
        when(filter2.getMatcher()).thenReturn(matcher);

        search.find(name, filters).present();
        verify(searchContext).findElements(By.cssSelector("cssStyle[generated=true]"));
    }

    @Test
    public void findCheckCssIsWellFormedWithPostSelectorAndByLocator() {
        WebElement webElement = mock(WebElement.class);
        WebElement webElement2 = mock(WebElement.class);
        List<WebElement> webElements = Lists.newArrayList(webElement, webElement2);

        when(searchContext.findElements(By.cssSelector("cssStyle[generated=true]"))).thenReturn(webElements);

        By locator = By.cssSelector("cssStyle");
        Filter[] filters = new Filter[]{filter1, filter2};
        when(filter1.isPreFilter()).thenReturn(true);
        when(filter1.toString()).thenReturn("[generated=true]");
        when(filter2.isPreFilter()).thenReturn(false);
        when(filter2.toString()).thenReturn("[checked=ok]");
        AbstractMacher matcher = mock(AbstractMacher.class);
        when(matcher.isSatisfiedBy(any(String.class))).thenReturn(true);
        when(filter2.getMatcher()).thenReturn(matcher);

        search.find(locator, filters).present();
        verify(searchContext).findElements(By.cssSelector("cssStyle"));
    }

    @Test
    public void findPostSelectorFilterWithElementThatMatch() {
        String name = "cssStyle";
        Filter[] filters = new Filter[]{filter1};
        when(filter1.isPreFilter()).thenReturn(false);
        WebElement webElement = mock(WebElement.class);
        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(Collections.singletonList(webElement));
        when(filter1.getMatcher()).thenReturn(matcher1);
        when(matcher1.isSatisfiedBy(Matchers.<String>anyObject())).thenReturn(true);

        FluentList fluentList = search.find(name, filters);
        assertThat(fluentList).hasSize(1);
    }

    @Test
    public void findPostSelectorFilterWithElementThatDontMatch() {
        final String name = "cssStyle";
        final Filter[] filters = new Filter[]{filter1};
        when(filter1.isPreFilter()).thenReturn(false);
        WebElement webElement = mock(WebElement.class);
        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(Collections.singletonList(webElement));
        when(filter1.getMatcher()).thenReturn(matcher1);
        when(matcher1.isSatisfiedBy(Matchers.<String>anyObject())).thenReturn(false);

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                search.find(name, filters).now();
            }
        }).isExactlyInstanceOf(NoSuchElementException.class);

        assertThat(search.find(name, filters).present()).isFalse();
        assertThat(search.find(name, filters).optional().isPresent()).isFalse();
    }

    @Test
    public void findPostSelectorFilterWithFilterOnText() {
        String name = "cssStyle";
        Filter[] filters = new Filter[]{filter1};
        when(filter1.isPreFilter()).thenReturn(false);
        WebElement webElement = mock(WebElement.class);
        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(Collections.singletonList(webElement));
        when(filter1.getMatcher()).thenReturn(matcher1);
        when(matcher1.isSatisfiedBy(Matchers.<String>anyObject())).thenReturn(false);
        when(filter1.getAttribut()).thenReturn("text");
        when(webElement.getText()).thenReturn("Ok");

        assertThat(search.find(name, filters).present()).isFalse();
        assertThat(search.find(name, filters).optional().isPresent()).isFalse();

        verify(matcher1, times(2)).isSatisfiedBy("Ok");
    }

    @Test
    public void findByPosition() {
        String name = "cssStyle";
        WebElement webElement1 = mock(WebElement.class);
        when(webElement1.getTagName()).thenReturn("span");
        WebElement webElement2 = mock(WebElement.class);
        when(webElement2.getTagName()).thenReturn("a");
        List<WebElement> webElements = new ArrayList<>();
        webElements.add(webElement1);
        webElements.add(webElement2);
        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(webElements);

        FluentWebElement fluentWebElement = search.find(name).index(1);
        assertThat(fluentWebElement.tagName()).isEqualTo("a");
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowErrorWhenPositionNotFound() {
        String name = "cssStyle";
        search.find(name).index(0).now();
    }

    @Test
    public void findFirstShouldReturnFirst() {
        String name = "cssStyle";
        WebElement webElement1 = mock(WebElement.class);
        when(webElement1.getTagName()).thenReturn("span");
        WebElement webElement2 = mock(WebElement.class);
        when(webElement2.getTagName()).thenReturn("a");
        List<WebElement> webElements = new ArrayList<>();
        webElements.add(webElement1);
        webElements.add(webElement2);
        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(webElements);

        FluentWebElement fluentWebElement = search.el(name);
        assertThat(fluentWebElement.tagName()).isEqualTo("span");
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowErrorWhenFirstNotFound() {
        String name = "cssStyle";
        FluentWebElement fluentWebElement = search.el(name);
        assertThat(fluentWebElement.tagName()).isEqualTo("span");
    }

}
