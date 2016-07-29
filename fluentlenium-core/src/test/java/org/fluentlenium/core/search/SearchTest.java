package org.fluentlenium.core.search;

import com.google.common.collect.Lists;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.filter.matcher.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SearchTest {
    @Mock
    private WebDriver driver;

    @Mock
    private SearchContext searchContext;

    @Mock
    private Filter filter1;

    @Mock
    private Matcher matcher1;

    @Mock
    private Filter filter2;

    private Search search;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        search = new Search(driver, searchContext);
    }

    @Test
    public void findCheckCssIsWellFormed() {
        String name = "cssStyle";
        Filter[] filters = new Filter[]{filter1, filter2};
        when(filter1.isPreFilter()).thenReturn(true);
        when(filter1.toString()).thenReturn("[generated=true]");
        when(filter2.isPreFilter()).thenReturn(true);
        when(filter2.toString()).thenReturn("[checked=ok]");

        search.find(name, filters);
        verify(searchContext).findElements(By.cssSelector("cssStyle[generated=true][checked=ok]"));
    }

    @Test
    public void can_loop_into_fluentWebElement_after_a_search() {
        WebElement webElement = mock(WebElement.class);
        WebElement webElement2 = mock(WebElement.class);
        List<WebElement> webElements = Lists.newArrayList(webElement, webElement2);

        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(webElements);
        for (FluentWebElement fluentWebElement : search.find("cssStyle")) {
            //just to check the cast
        }

    }

    @Test
    public void findCheckCssIsWellFormedWithPostSelector() {

        String name = "cssStyle";
        Filter[] filters = new Filter[]{filter1, filter2};
        when(filter1.isPreFilter()).thenReturn(true);
        when(filter1.toString()).thenReturn("[generated=true]");
        when(filter2.isPreFilter()).thenReturn(false);
        when(filter2.toString()).thenReturn("[checked=ok]");

        search.find(name, filters);
        verify(searchContext).findElements(By.cssSelector("cssStyle[generated=true]"));
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
        String name = "cssStyle";
        Filter[] filters = new Filter[]{filter1};
        when(filter1.isPreFilter()).thenReturn(false);
        WebElement webElement = mock(WebElement.class);
        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(Collections.singletonList(webElement));
        when(filter1.getMatcher()).thenReturn(matcher1);
        when(matcher1.isSatisfiedBy(Matchers.<String>anyObject())).thenReturn(false);

        FluentList fluentList = search.find(name, filters);
        assertThat(fluentList).hasSize(0);
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

        search.find(name, filters);

        verify(matcher1).isSatisfiedBy("Ok");
    }

    @Test
    public void findByPosition() {
        String name = "cssStyle";
        WebElement webElement1 = mock(WebElement.class);
        when(webElement1.getTagName()).thenReturn("span");
        WebElement webElement2 = mock(WebElement.class);
        when(webElement2.getTagName()).thenReturn("a");
        List<WebElement> webElements = new ArrayList<WebElement>();
        webElements.add(webElement1);
        webElements.add(webElement2);
        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(webElements);

        FluentWebElement fluentWebElement = search.find(name, 1);
        assertThat(fluentWebElement.getTagName()).isEqualTo("a");
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowErrorWhenPositionNotFound() {
        String name = "cssStyle";
        WebElement webElement = mock(WebElement.class);
        search.find(name, 0);
    }

    @Test
    public void findFirstShouldReturnFirst() {
        String name = "cssStyle";
        WebElement webElement1 = mock(WebElement.class);
        when(webElement1.getTagName()).thenReturn("span");
        WebElement webElement2 = mock(WebElement.class);
        when(webElement2.getTagName()).thenReturn("a");
        List<WebElement> webElements = new ArrayList<WebElement>();
        webElements.add(webElement1);
        webElements.add(webElement2);
        when(searchContext.findElements(By.cssSelector("cssStyle"))).thenReturn(webElements);

        FluentWebElement fluentWebElement = search.findFirst(name);
        assertThat(fluentWebElement.getTagName()).isEqualTo("span");
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowErrorWhenFirstNotFound() {
        String name = "cssStyle";
        FluentWebElement fluentWebElement = search.findFirst(name);
        assertThat(fluentWebElement.getTagName()).isEqualTo("span");
    }

}
