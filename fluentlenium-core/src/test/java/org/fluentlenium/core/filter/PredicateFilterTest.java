package org.fluentlenium.core.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebElement;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;

import java.util.List;

/**
 * Unit test for {@link PredicateFilter}.
 */
@RunWith(MockitoJUnitRunner.class)
public class PredicateFilterTest {

    @Mock
    private WebElement webElement1;
    @Mock
    private WebElement webElement3;
    @Mock
    private FluentControl control;
    @Mock
    private ComponentInstantiator instantiator;

    @Test
    public void shouldApplyFilter() {
        when(webElement1.isSelected()).thenReturn(true);
        when(webElement3.isSelected()).thenReturn(true);

        FluentWebElement fluentWebElem1 = new FluentWebElement(webElement1, control, instantiator);
        FluentWebElement fluentWebElem2 = new FluentWebElement(mock(WebElement.class), control, instantiator);
        FluentWebElement fluentWebElem3 = new FluentWebElement(webElement3, control, instantiator);
        FluentWebElement fluentWebElem4 = new FluentWebElement(mock(WebElement.class), control, instantiator);

        List<FluentWebElement> elementsToFilter = List.of(fluentWebElem1, fluentWebElem2, fluentWebElem3, fluentWebElem4);
        List<FluentWebElement> filteredElements = List.of(fluentWebElem1, fluentWebElem3);

        PredicateFilter predicateFilter = new PredicateFilter(FluentWebElement::selected);

        assertThat(predicateFilter.applyFilter(elementsToFilter)).containsExactlyInAnyOrderElementsOf(filteredElements);
    }
}
