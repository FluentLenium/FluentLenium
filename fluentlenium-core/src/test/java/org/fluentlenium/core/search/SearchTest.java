package org.fluentlenium.core.search;

import com.google.common.collect.Lists;
import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.AttributeFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollection;
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
    private AttributeFilter filter1;

    @Mock
    private AttributeFilter filter2;

    private Search search;

    @Before
    public void before() {
        final FluentAdapter fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(driver);

        final DefaultComponentInstantiator instantiator = new DefaultComponentInstantiator(fluentAdapter);
        search = new Search(searchContext, instantiator);
    }

    @After
    public void after() {
        reset(searchContext);
    }

    @Test
    public void findCheckCssIsWellFormed() {
        final WebElement webElement = mock(WebElement.class);
        final WebElement webElement2 = mock(WebElement.class);
        final List<WebElement> webElements = Lists.newArrayList(webElement, webElement2);

        when(searchContext.findElements(By.cssSelector("cssStyle[generated=true][checked=ok]"))).thenReturn(webElements);

        final String name = "cssStyle";
        final AttributeFilter[] filters = new AttributeFilter[] {filter1, filter2};
        when(filter1.isCssFilterSupported()).thenReturn(true);
        when(filter1.getCssFilter()).thenReturn("[generated=true]");
        when(filter2.isCssFilterSupported()).thenReturn(true);
        when(filter2.getCssFilter()).thenReturn("[checked=ok]");

        search.find(name, filters).now();
        verify(searchContext).findElements(By.cssSelector("cssStyle[generated=true][checked=ok]"));
    }

    @Test
    public void canLoopIntoFluentWebElementAfterASearch() {
        final WebElement webElement = mock(WebElement.class);
        final WebElement webElement2 = mock(WebElement.class);
        final List<WebElement> webElements = Lists.newArrayList(webElement, webElement2);

        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(webElements);
        for (final FluentWebElement fluentWebElement : search.find("cssStyle")) {
            //just to check the cast
            assertThat(fluentWebElement).isInstanceOf(FluentWebElement.class);
        }

    }

    @Test
    public void findCheckCssIsWellFormedWithPostSelector() {
        final WebElement webElement = mock(WebElement.class);
        final WebElement webElement2 = mock(WebElement.class);
        final List<WebElement> webElements = Lists.newArrayList(webElement, webElement2);

        when(searchContext.findElements(By.cssSelector("cssStyle[generated=true]"))).thenReturn(webElements);

        final String name = "cssStyle";
        final AttributeFilter[] filters = new AttributeFilter[] {filter1, filter2};
        when(filter1.isCssFilterSupported()).thenReturn(true);
        when(filter1.getCssFilter()).thenReturn("[generated=true]");
        when(filter2.isCssFilterSupported()).thenReturn(false);
        when(filter2.applyFilter(anyCollection())).thenAnswer(new Answer<Collection<FluentWebElement>>() {
            @Override
            public Collection<FluentWebElement> answer(final InvocationOnMock invocation) throws Throwable {
                return (Collection<FluentWebElement>) invocation.getArguments()[0];
            }
        });

        search.find(name, filters).present();
        verify(searchContext).findElements(By.cssSelector("cssStyle[generated=true]"));
    }

    @Test
    public void findCheckCssIsWellFormedWithPostSelectorAndByLocator() {
        final WebElement webElement = mock(WebElement.class);
        final WebElement webElement2 = mock(WebElement.class);
        final List<WebElement> webElements = Lists.newArrayList(webElement, webElement2);

        when(searchContext.findElements(By.cssSelector("cssStyle[generated=true]"))).thenReturn(webElements);

        final By locator = By.cssSelector("cssStyle");
        final AttributeFilter[] filters = new AttributeFilter[] {filter1, filter2};
        when(filter1.isCssFilterSupported()).thenReturn(true);
        when(filter1.getCssFilter()).thenReturn("[generated=true]");
        when(filter1.applyFilter(anyCollection())).thenAnswer(new Answer<Collection<FluentWebElement>>() {
            @Override
            public Collection<FluentWebElement> answer(final InvocationOnMock invocation) throws Throwable {
                return (Collection<FluentWebElement>) invocation.getArguments()[0];
            }
        });
        when(filter2.isCssFilterSupported()).thenReturn(false);
        when(filter2.applyFilter(anyCollection())).thenAnswer(new Answer<Collection<FluentWebElement>>() {
            @Override
            public Collection<FluentWebElement> answer(final InvocationOnMock invocation) throws Throwable {
                return (Collection<FluentWebElement>) invocation.getArguments()[0];
            }
        });

        search.find(locator, filters).present();
        verify(searchContext).findElements(By.cssSelector("cssStyle")); // By parameter doesn't support CSS filtering.
    }

    @Test
    public void findPostSelectorFilterWithElementThatMatch() {
        final String name = "cssStyle";
        final AttributeFilter[] filters = new AttributeFilter[] {filter1};
        when(filter1.isCssFilterSupported()).thenReturn(false);
        when(filter1.applyFilter(anyCollection())).thenAnswer(new Answer<Collection<FluentWebElement>>() {
            @Override
            public Collection<FluentWebElement> answer(final InvocationOnMock invocation) throws Throwable {
                return (Collection<FluentWebElement>) invocation.getArguments()[0];
            }
        });
        final WebElement webElement = mock(WebElement.class);
        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(Collections.singletonList(webElement));

        final FluentList fluentList = search.find(name, filters);
        assertThat(fluentList).hasSize(1);
    }

    @Test
    public void findPostSelectorFilterWithElementThatDontMatch() {
        final String name = "cssStyle";
        final AttributeFilter[] filters = new AttributeFilter[] {filter1};
        when(filter1.isCssFilterSupported()).thenReturn(false);
        final WebElement webElement = mock(WebElement.class);
        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(Collections.singletonList(webElement));

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
        final String name = "cssStyle";
        final AttributeFilter[] filters = new AttributeFilter[] {filter1};
        when(filter1.isCssFilterSupported()).thenReturn(false);
        final WebElement webElement = mock(WebElement.class);
        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(Collections.singletonList(webElement));
        when(filter1.getAttribut()).thenReturn("text");
        when(webElement.getText()).thenReturn("Ok");

        assertThat(search.find(name, filters).present()).isFalse();
        assertThat(search.find(name, filters).optional().isPresent()).isFalse();

        verify(filter1, times(2)).applyFilter(any(Collection.class));
    }

    @Test
    public void findByPosition() {
        final String name = "cssStyle";
        final WebElement webElement1 = mock(WebElement.class);
        when(webElement1.getTagName()).thenReturn("span");
        final WebElement webElement2 = mock(WebElement.class);
        when(webElement2.getTagName()).thenReturn("a");
        final List<WebElement> webElements = new ArrayList<>();
        webElements.add(webElement1);
        webElements.add(webElement2);
        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(webElements);

        final FluentWebElement fluentWebElement = search.find(name).index(1);
        assertThat(fluentWebElement.tagName()).isEqualTo("a");
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowErrorWhenPositionNotFound() {
        final String name = "cssStyle";
        search.find(name).index(0).now();
    }

    @Test
    public void findFirstShouldReturnFirst() {
        final String name = "cssStyle";
        final WebElement webElement1 = mock(WebElement.class);
        when(webElement1.getTagName()).thenReturn("span");
        final WebElement webElement2 = mock(WebElement.class);
        when(webElement2.getTagName()).thenReturn("a");
        final List<WebElement> webElements = new ArrayList<>();
        webElements.add(webElement1);
        webElements.add(webElement2);
        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(webElements);

        final FluentWebElement fluentWebElement = search.el(name);
        assertThat(fluentWebElement.tagName()).isEqualTo("span");
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowErrorWhenFirstNotFound() {
        final String name = "cssStyle";
        final FluentWebElement fluentWebElement = search.el(name);
        assertThat(fluentWebElement.tagName()).isEqualTo("span");
    }

}
