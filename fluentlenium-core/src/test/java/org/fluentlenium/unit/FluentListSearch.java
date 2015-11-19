package org.fluentlenium.unit;


import com.google.common.collect.Lists;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.filter.matcher.Matcher;
import org.fluentlenium.core.search.Search;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class FluentListSearch {
    @Mock
    Search search;

    FluentList<FluentWebElement> fluentList;

    @Mock
    Filter filter1;

    @Mock
    Matcher matcher1;

    @Mock
    Filter filter2;
    @Mock
    WebElement webElement;

    List<FluentWebElement> webElements;

    FluentWebElement fluentWebElement;

    @Before
    public void before() throws IntrospectionException, NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);
        webElements = new ArrayList<FluentWebElement>();
        fluentWebElement = new FluentWebElement(webElement);
        webElements.add(fluentWebElement);
        Field field = fluentWebElement.getClass().getDeclaredField("search");
        field.setAccessible(true);
        field.set(fluentWebElement, search);


        fluentList = new FluentListImpl<FluentWebElement>(webElements);
    }

    @Test
    public void findElementsIsSearched() {
        String name = "cssStyle";
        FluentList fluentList1 = new FluentListImpl<FluentWebElement>(webElements);
        when(search.find(name, null)).thenReturn(fluentList1);
        FluentList fluentListResponse = fluentList.find(name, null);
        assertThat(fluentListResponse).hasSize(1);
    }

    @Test
    public void findElementByPosition() {
        String name = "cssStyle";
        when(search.find(name, null)).thenReturn(new FluentListImpl<FluentWebElement>(webElements));
        FluentWebElement fluentWebElement = fluentList.find(name, 0, null);
        assertThat(fluentWebElement).isEqualTo(this.fluentWebElement);
    }


    @Test(expected = NoSuchElementException.class)
    public void when_no_element_matching_fill_then_throws_exceptions() {
        fluentList.text("toto");
        assertThat(fluentWebElement).isEqualTo(this.fluentWebElement);
    }

    @Test(expected = NoSuchElementException.class)
    public void ShouldThrowAnErrorWhenWrongPosition() {
        String name = "cssStyle";
        when(search.find(name, null)).thenReturn(new FluentListImpl<FluentWebElement>(webElements));
        FluentWebElement fluentWebElement = fluentList.find(name, 1, null);
    }

    @Test
    public void findFirstElement() {
        String name = "cssStyle";
        when(search.find(name, null)).thenReturn(new FluentListImpl<FluentWebElement>(webElements));
        FluentWebElement fluentWebElement = fluentList.findFirst(name, null);
        assertThat(fluentWebElement).isEqualTo(this.fluentWebElement);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowAnErrorWhenNoFirstPosition() {
        String name = "cssStyle";
        when(search.find(name, null)).thenReturn(new FluentListImpl<FluentWebElement>((Collection) Lists.newArrayList()));
        FluentWebElement fluentWebElement = fluentList.findFirst(name, null);
    }

}
