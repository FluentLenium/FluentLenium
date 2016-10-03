package org.fluentlenium.core.domain;

import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.filter.matcher.Matcher;
import org.fluentlenium.core.search.Search;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FluentListSearchTest {
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

    @Mock
    WebDriver driver;

    List<FluentWebElement> webElements;

    FluentWebElement fluentWebElement;

    private FluentAdapter fluentAdapter;

    @Before
    public void before() throws IntrospectionException, NoSuchFieldException, IllegalAccessException {
        webElements = new ArrayList<>();
        fluentAdapter = new FluentAdapter(driver);
        fluentWebElement = new FluentWebElement(webElement, fluentAdapter, new DefaultComponentInstantiator(fluentAdapter));
        webElements.add(fluentWebElement);
        Field field = fluentWebElement.getClass().getDeclaredField("search");
        field.setAccessible(true);
        field.set(fluentWebElement, search);

        fluentList = fluentAdapter.newFluentList(webElements);
    }

    @Test
    public void findElementsIsSearched() {
        String name = "cssStyle";
        FluentList fluentList1 = fluentAdapter.newFluentList(webElements);
        when(search.find(name, null)).thenReturn(fluentList1);
        FluentList fluentListResponse = fluentList.find(name, null);
        assertThat(fluentListResponse).hasSize(1);
    }

    @Test
    public void findElementByPosition() {
        String name = "cssStyle";
        when(search.find(name, null)).thenReturn(fluentAdapter.newFluentList(webElements));
        FluentWebElement fluentWebElement = fluentList.find(name, null).index(0);
        assertThat(fluentWebElement).isEqualTo(this.fluentWebElement);
    }

    @Test(expected = NoSuchElementException.class)
    public void whenNoElementMatchingFillThenThrowsExceptions() {
        fluentList.write("toto");
        assertThat(fluentWebElement).isEqualTo(this.fluentWebElement);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowAnErrorWhenWrongPosition() {
        String name = "cssStyle";
        when(search.find(name, null)).thenReturn(fluentAdapter.newFluentList(webElements));
        FluentWebElement fluentWebElement = fluentList.find(name, null).index(1);
        fluentWebElement.now();
    }

    @Test
    public void findFirstElement() {
        String name = "cssStyle";
        when(search.find(name, null)).thenReturn(fluentAdapter.newFluentList(webElements));
        FluentWebElement fluentWebElement = fluentList.el(name, null);
        assertThat(fluentWebElement).isEqualTo(this.fluentWebElement);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowAnErrorWhenNoFirstPosition() {
        String name = "cssStyle";
        when(search.find(name, null)).thenReturn(fluentAdapter.newFluentList());
        FluentWebElement fluentWebElement = fluentList.el(name, null);
        fluentWebElement.now();
    }

}
