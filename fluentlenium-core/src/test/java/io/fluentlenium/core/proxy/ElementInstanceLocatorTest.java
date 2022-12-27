package io.fluentlenium.core.proxy;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebElement;

@RunWith(MockitoJUnitRunner.class)
public class ElementInstanceLocatorTest {
    @Mock
    private WebElement element;

    @Test
    public void testWithMockElement() {
        ElementInstanceLocator locator = new ElementInstanceLocator(element);

        Assertions.assertThat(locator.findElement()).isSameAs(element);
        Assertions.assertThat(locator.findElements()).containsExactly(element);

        Assertions.assertThat(locator.getWrappedElement()).isSameAs(element);
    }

    @Test
    public void testWithNullElement() {
        ElementInstanceLocator locator = new ElementInstanceLocator(null);

        Assertions.assertThat(locator.findElement()).isNull();
        Assertions.assertThat(locator.findElements()).isEmpty();

        Assertions.assertThat(locator.getWrappedElement()).isNull();
    }
}
