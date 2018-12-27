package org.fluentlenium.core.proxy;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebElement;

import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class ElementListSupplierLocatorTest {
    @Mock
    private WebElement element1;

    @Mock
    private WebElement element2;

    @Mock
    private WebElement element3;

    @Mock
    private WebElement element4;

    @Test
    public void testWithMockElements() {
        ElementListSupplierLocator locator = new ElementListSupplierLocator(
                Arrays.asList(element1, element2, element3, element4));

        Assertions.assertThat(locator.findElement()).isSameAs(element1);
        Assertions.assertThat(locator.findElements()).containsExactly(element1, element2, element3, element4);
    }

    @Test
    public void testWithNoElement() {
        ElementListSupplierLocator locator = new ElementListSupplierLocator(Arrays::asList);

        Assertions.assertThat(locator.findElement()).isNull();
        Assertions.assertThat(locator.findElements()).isEmpty();
    }
}
