package org.fluentlenium.core.proxy;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class ProxiesTest {

    @Mock
    WebDriver driver;

    @Mock
    WebElement element1;

    @Mock
    WebElement element2;

    @Mock
    WebElement element3;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        when(driver.findElement(By.cssSelector("#element1"))).thenReturn(element1);
        when(element1.findElement(By.cssSelector("#element2"))).thenReturn(element2);
        when(element2.findElement(By.cssSelector("#element3"))).thenReturn(element3);

        when(driver.findElements(By.cssSelector("#element1"))).thenReturn(Arrays.asList(element1));
        when(element1.findElements(By.cssSelector("#element2"))).thenReturn(Arrays.asList(element2));
        when(element2.findElements(By.cssSelector("#element3"))).thenReturn(Arrays.asList(element3));
    }

    @Test
    public void testElementIsLazy() {
        WebElement elementProxy1 = LocatorProxies.createWebElement(new ElementLocator() {
            @Override
            public WebElement findElement() {
                return driver.findElement(By.cssSelector("#element1"));
            }

            @Override
            public List<WebElement> findElements() {
                return Arrays.asList(findElement());
            }
        });

        verifyZeroInteractions(driver);
        verifyZeroInteractions(element1);

        elementProxy1.click();
        Mockito.verify(element1).click();
    }

    @Test
    public void testElementChainIsLazy() {
        final WebElement element1Proxy = LocatorProxies.createWebElement(new ElementLocator() {
            @Override
            public WebElement findElement() {
                return driver.findElement(By.cssSelector("#element1"));
            }

            @Override
            public List<WebElement> findElements() {
                return Arrays.asList(findElement());
            }
        });

        final WebElement element2Proxy = LocatorProxies.createWebElement(new ElementLocator() {
            @Override
            public WebElement findElement() {
                return element1Proxy.findElement(By.cssSelector("#element2"));
            }

            @Override
            public List<WebElement> findElements() {
                return Arrays.asList(findElement());
            }
        });

        WebElement element3Proxy = LocatorProxies.createWebElement(new ElementLocator() {
            @Override
            public WebElement findElement() {
                return element2Proxy.findElement(By.cssSelector("#element3"));
            }

            @Override
            public List<WebElement> findElements() {
                return Arrays.asList(findElement());
            }
        });


        verifyZeroInteractions(driver);
        verifyZeroInteractions(element1);
        verifyZeroInteractions(element2);
        verifyZeroInteractions(element3);

        element3Proxy.click();
        Mockito.verify(driver).findElement(By.cssSelector("#element1"));
        Mockito.verify(element1).findElement(By.cssSelector("#element2"));
        Mockito.verify(element2).findElement(By.cssSelector("#element3"));
    }
}
