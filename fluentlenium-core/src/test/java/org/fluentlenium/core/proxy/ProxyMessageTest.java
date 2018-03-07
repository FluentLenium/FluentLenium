package org.fluentlenium.core.proxy;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ProxyMessageTest {
    @Mock
    private WebElement element1;

    @Mock
    private WebElement element2;

    @Mock
    private WebElement element3;

    @Mock
    private ElementLocator locator;

    @Test
    public void testNoSuchElementMessage() {
        Mockito.when(locator.findElement()).thenReturn(element1);

        WebElement proxy = LocatorProxies.createWebElement(locator);
        LocatorProxies.now(proxy);

        NoSuchElementException noSuchElementException = LocatorProxies.noSuchElement(proxy);

        Assertions.assertThat(noSuchElementException).hasMessageStartingWith("Element locator (Lazy Element) is not present");
    }

    @Test
    public void testNoSuchElementListMessage() {
        Mockito.when(locator.findElements()).thenReturn(Arrays.asList(element1, element2, element3));

        List<WebElement> proxy = LocatorProxies.createWebElementList(locator);
        LocatorProxies.now(proxy);

        NoSuchElementException noSuchElementException = LocatorProxies.noSuchElement(proxy);

        Assertions.assertThat(noSuchElementException)
                .hasMessageStartingWith("Elements locator (Lazy Element List) is not present");
    }
}
