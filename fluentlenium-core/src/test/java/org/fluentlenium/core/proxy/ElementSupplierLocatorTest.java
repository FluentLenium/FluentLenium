package org.fluentlenium.core.proxy;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebElement;

@RunWith(MockitoJUnitRunner.class)
public class ElementSupplierLocatorTest {
    @Mock
    private WebElement element;

    @Test
    public void testWithMockElement() {
        ElementSupplierLocator locator = new ElementSupplierLocator(() -> element);

        Assertions.assertThat(locator.findElement()).isSameAs(element);
        Assertions.assertThat(locator.findElements()).containsExactly(element);
    }

    @Test
    public void testWithNullElement() {
        ElementSupplierLocator locator = new ElementSupplierLocator(() -> null);

        Assertions.assertThat(locator.findElement()).isNull();
        Assertions.assertThat(locator.findElements()).isEmpty();
    }
}
