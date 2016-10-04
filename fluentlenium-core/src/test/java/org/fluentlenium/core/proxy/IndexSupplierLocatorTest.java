package org.fluentlenium.core.proxy;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.Arrays;
import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class IndexSupplierLocatorTest {
    @Mock
    private ElementLocator elementLocator;

    @Mock
    private ElementLocator emptyLocator;

    @Mock
    private WebElement element1;

    @Mock
    private WebElement element2;

    @Mock
    private WebElement element3;

    @Mock
    private WebElement element4;

    @Before
    public void before() {
        Mockito.when(elementLocator.findElements()).thenReturn(Arrays.asList(element1, element2, element3, element4));
        Mockito.when(emptyLocator.findElements()).thenReturn(Collections.<WebElement>emptyList());
    }

    @Test
    public void testFirstElementLocator() {
        final ElementLocator locator = new FirstElementLocator(elementLocator);

        Assertions.assertThat(locator.findElement()).isSameAs(element1);
        Assertions.assertThat(locator.findElements()).containsExactly(element1);
    }

    @Test
    public void testFirstElementLocatorEmpty() {
        final ElementLocator locator = new FirstElementLocator(emptyLocator);

        Assertions.assertThat(locator.findElement()).isNull();
        Assertions.assertThat(locator.findElements()).isEmpty();
    }

    @Test
    public void testLastElementLocator() {
        final ElementLocator locator = new LastElementLocator(elementLocator);

        Assertions.assertThat(locator.findElement()).isSameAs(element4);
        Assertions.assertThat(locator.findElements()).containsExactly(element4);
    }

    @Test
    public void testLastElementLocatorEmpty() {
        final ElementLocator locator = new LastElementLocator(emptyLocator);

        Assertions.assertThat(locator.findElement()).isNull();
        Assertions.assertThat(locator.findElements()).isEmpty();
    }

    @Test
    public void testAtIndexElementLocator() {
        final ElementLocator locator = new AtIndexElementLocator(elementLocator, 2);

        Assertions.assertThat(locator.findElement()).isSameAs(element3);
        Assertions.assertThat(locator.findElements()).containsExactly(element3);
    }

    @Test
    public void testAtIndexElementLocatorEmpty() {
        final ElementLocator locator = new AtIndexElementLocator(emptyLocator, 2);

        Assertions.assertThat(locator.findElement()).isNull();
        Assertions.assertThat(locator.findElements()).isEmpty();
    }

}
