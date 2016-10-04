package org.fluentlenium.core.domain;

import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.filter.matcher.AbstractMacher;
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
    private Search search;

    private FluentList<FluentWebElement> fluentList;

    @Mock
    private Filter filter1;

    @Mock
    private AbstractMacher matcher1;

    @Mock
    private Filter filter2;

    @Mock
    private WebElement webElement;

    @Mock
    private WebDriver driver;

    private List<FluentWebElement> webElements;

    private FluentWebElement fluentWebElement;

    private FluentAdapter fluentAdapter;

    @Before
    public void before() throws IntrospectionException, NoSuchFieldException, IllegalAccessException {
        webElements = new ArrayList<>();
        fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(driver);
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
        when(search.find(name, (Filter) null)).thenReturn(fluentList1);
        FluentList fluentListResponse = fluentList.find(name, null);
        assertThat(fluentListResponse).hasSize(1);
    }

    @Test
    public void findElementByPosition() {
        String name = "cssStyle";
        when(search.find(name, (Filter) null)).thenReturn(fluentAdapter.newFluentList(webElements));
        FluentWebElement element = fluentList.find(name, null).index(0);
        assertThat(element).isEqualTo(this.fluentWebElement);
    }

    @Test(expected = NoSuchElementException.class)
    public void whenNoElementMatchingFillThenThrowsExceptions() {
        fluentList.write("toto");
        assertThat(fluentWebElement).isEqualTo(this.fluentWebElement);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowAnErrorWhenWrongPosition() {
        String name = "cssStyle";
        when(search.find(name, (Filter) null)).thenReturn(fluentAdapter.newFluentList(webElements));
        FluentWebElement element = fluentList.find(name, (Filter) null).index(1);
        element.now();
    }

    @Test
    public void findFirstElement() {
        String name = "cssStyle";
        when(search.find(name, (Filter) null)).thenReturn(fluentAdapter.newFluentList(webElements));
        FluentWebElement element = fluentList.el(name, (Filter) null);
        assertThat(element).isEqualTo(this.fluentWebElement);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowAnErrorWhenNoFirstPosition() {
        String name = "cssStyle";
        when(search.find(name, (Filter) null)).thenReturn(fluentAdapter.newFluentList());
        FluentWebElement element = fluentList.el(name, (Filter) null);
        element.now();
    }

}
