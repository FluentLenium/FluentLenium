package org.fluentlenium.core.inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

/**
 * Unit test for {@link ElementLocatorSearchContext}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ElementLocatorSearchContextTest {

    @Mock
    private By by;
    @Mock
    private ElementLocator elementLocator;
    @Mock
    private WebElement element1;
    @Mock
    private WebElement element2;
    @Mock
    private WebElement webElement1;
    @Mock
    private WebElement webElement2;
    @Mock
    private WebElement webElement3;
    @Mock
    private WebElement webElement4;
    private ElementLocatorSearchContext locatorSearchContext;

    @Test
    public void shouldFindElements() {
        locatorSearchContext = new ElementLocatorSearchContext(elementLocator);
        when(elementLocator.findElements()).thenReturn(ImmutableList.of(element1, element2));
        when(element1.findElements(by)).thenReturn(ImmutableList.of(webElement1, webElement2));
        when(element2.findElements(by)).thenReturn(ImmutableList.of(webElement3, webElement4));

        assertThat(locatorSearchContext.findElements(by)).containsExactly(webElement1, webElement2, webElement3, webElement4);
    }

    @Test
    public void shouldFindElement() {
        locatorSearchContext = new ElementLocatorSearchContext(elementLocator);
        when(elementLocator.findElement()).thenReturn(element1);
        when(element1.findElement(by)).thenReturn(element2);

        assertThat(locatorSearchContext.findElement(by)).isSameAs(element2);
    }
}
