package org.fluentlenium.core.proxy;

import com.google.common.base.Supplier;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

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
        ElementListSupplierLocator locator = new ElementListSupplierLocator(new Supplier<List<WebElement>>() {
            @Override
            public List<WebElement> get() {
                return Arrays.asList();
            }
        });

        Assertions.assertThat(locator.findElement()).isNull();
        Assertions.assertThat(locator.findElements()).isEmpty();
    }
}
