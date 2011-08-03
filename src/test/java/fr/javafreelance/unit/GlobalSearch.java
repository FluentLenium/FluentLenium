package fr.javafreelance.unit;


import fr.javafreelance.fluentlenium.domain.FluentList;
import fr.javafreelance.fluentlenium.domain.FluentWebElement;
import fr.javafreelance.fluentlenium.filter.Filter;
import fr.javafreelance.fluentlenium.filter.matcher.Matcher;
import fr.javafreelance.fluentlenium.search.Search;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GlobalSearch {
    @Mock
    SearchContext searchContext;

    Search search;

    @Mock
    Filter filter1;

    @Mock
    Matcher matcher1;

    @Mock
    Filter filter2;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        search = new Search(searchContext);
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
        search.find(name, 0, null);
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

        FluentWebElement fluentWebElement = search.findFirst(name, null);
        assertThat(fluentWebElement.getTagName()).isEqualTo("span");
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowErrorWhenFirstNotFound() {
        String name = "cssStyle";
        FluentWebElement fluentWebElement = search.findFirst(name, null);
        assertThat(fluentWebElement.getTagName()).isEqualTo("span");
    }

}
