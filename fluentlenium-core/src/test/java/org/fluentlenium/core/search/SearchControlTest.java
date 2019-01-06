package org.fluentlenium.core.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Unit test for {@link SearchControl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchControlTest {

    @Spy
    private SearchControl searchControl;
    @Mock
    private FluentWebElement fluentWebElement1;
    @Mock
    private FluentWebElement fluentWebElement2;
    @Mock
    private FluentWebElement fluentWebElement3;
    @Mock
    private WebElement webElement1;
    @Mock
    private WebElement webElement2;
    @Mock
    private WebElement webElement3;

    @Test
    public void shouldConvertListOfFluentWebElementToListOfWebElements() {
        when(fluentWebElement1.getWrappedElement()).thenReturn(webElement1);
        when(fluentWebElement2.getWrappedElement()).thenReturn(webElement2);
        when(fluentWebElement3.getWrappedElement()).thenReturn(webElement3);

        searchControl.$$(List.of(fluentWebElement1, fluentWebElement2, fluentWebElement3));

        ArgumentCaptor<List<WebElement>> captor = ArgumentCaptor.forClass(List.class);

        verify(searchControl).find(captor.capture());
        assertThat(captor.getValue()).containsExactlyInAnyOrder(webElement1, webElement2, webElement3);
    }

    @Test
    public void shouldWrapWrappedElementInNewFluentWebElement() {
        when(fluentWebElement1.getWrappedElement()).thenReturn(webElement1);

        searchControl.el(fluentWebElement1);

        ArgumentCaptor<WebElement> captor = ArgumentCaptor.forClass(WebElement.class);

        verify(searchControl).el(captor.capture());
        assertThat(captor.getValue()).isEqualTo(webElement1);
    }

    @Test
    public void shouldFindFirstElementByCssSelectorAndFilters() {
        String selector = "css.selector";
        SearchFilter searchFilter = mock(SearchFilter.class);
        FluentList<FluentWebElement> fluentList = setupFluentList();
        when(searchControl.find(selector, searchFilter)).thenReturn(fluentList);

        assertThat(searchControl.el(selector, searchFilter)).isEqualTo(fluentWebElement1);
    }

    @Test
    public void shouldFindFirstElementByFilters() {
        SearchFilter searchFilter = mock(SearchFilter.class);
        FluentList<FluentWebElement> fluentList = setupFluentList();
        when(searchControl.find(searchFilter)).thenReturn(fluentList);

        assertThat(searchControl.el(searchFilter)).isEqualTo(fluentWebElement1);
    }

    @Test
    public void shouldFindFirstElementByLocatorAndFilters() {
        By locator = By.cssSelector("css.selector");
        SearchFilter searchFilter = mock(SearchFilter.class);
        FluentList<FluentWebElement> fluentList = setupFluentList();
        when(searchControl.find(locator, searchFilter)).thenReturn(fluentList);

        assertThat(searchControl.el(locator, searchFilter)).isEqualTo(fluentWebElement1);
    }

    private FluentList<FluentWebElement> setupFluentList() {
        List<FluentWebElement> elements = List.of(fluentWebElement1, fluentWebElement2, fluentWebElement3);
        FluentControl fluentControl = mock(FluentControl.class);
        ComponentInstantiator componentInstantiator = mock(ComponentInstantiator.class);
        return new FluentListImpl<>(FluentWebElement.class, elements, fluentControl, componentInstantiator);
    }
}
