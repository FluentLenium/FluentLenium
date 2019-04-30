package org.fluentlenium.core.proxy;

import org.fluentlenium.core.domain.WrapsElements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProxiesTest {

    @Mock
    private WebDriver driver;

    @Mock
    private WebElement element1;

    @Mock
    private WebElement element2;

    @Mock
    private WebElement element3;

    @Before
    public void before() {
        when(driver.findElement(By.cssSelector("#element1"))).thenReturn(element1);
        when(element1.findElement(By.cssSelector("#element2"))).thenReturn(element2);
        when(element2.findElement(By.cssSelector("#element3"))).thenReturn(element3);
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
                return singletonList(findElement());
            }
        });

        verifyZeroInteractions(driver);
        verifyZeroInteractions(element1);

        elementProxy1.click();
        verify(element1).click();
    }

    @Test
    public void testElementChainIsLazy() {
        WebElement element1Proxy = LocatorProxies.createWebElement(new ElementLocator() {
            @Override
            public WebElement findElement() {
                return driver.findElement(By.cssSelector("#element1"));
            }

            @Override
            public List<WebElement> findElements() {
                return singletonList(findElement());
            }
        });

        WebElement element2Proxy = LocatorProxies.createWebElement(new ElementLocator() {
            @Override
            public WebElement findElement() {
                return element1Proxy.findElement(By.cssSelector("#element2"));
            }

            @Override
            public List<WebElement> findElements() {
                return singletonList(findElement());
            }
        });

        WebElement element3Proxy = LocatorProxies.createWebElement(new ElementLocator() {
            @Override
            public WebElement findElement() {
                return element2Proxy.findElement(By.cssSelector("#element3"));
            }

            @Override
            public List<WebElement> findElements() {
                return singletonList(findElement());
            }
        });

        verifyZeroInteractions(driver);
        verifyZeroInteractions(element1);
        verifyZeroInteractions(element2);
        verifyZeroInteractions(element3);

        element3Proxy.click();
        verify(driver).findElement(By.cssSelector("#element1"));
        verify(element1).findElement(By.cssSelector("#element2"));
        verify(element2).findElement(By.cssSelector("#element3"));
    }

    @Test
    public void testAlreadyLoadedElementsShouldBeLoaded() {
        WebElement webElement = LocatorProxies.createWebElement(element1);

        assertThat(LocatorProxies.loaded(webElement)).isTrue();
        assertThat(((WrapsElement) webElement).getWrappedElement()).isSameAs(element1);
    }

    @Test
    public void testAlreadyLoadedElementListShouldBeLoaded() {
        List<WebElement> webElementList = LocatorProxies.createWebElementList(Arrays.asList(element1, element2, element3));

        assertThat(LocatorProxies.loaded(webElementList)).isTrue();
        assertThat(((WrapsElements) webElementList).getWrappedElements())
                .containsExactlyInAnyOrder(element1, element2, element3);
    }

    @Test
    public void testNullElementShouldThrowNoSuchElementException() {
        assertThatThrownBy(() -> LocatorProxies.createWebElement((WebElement) null))
                .isExactlyInstanceOf(NoSuchElementException.class);

        WebElement proxy = LocatorProxies.createWebElement(new ElementLocator() {
            @Override
            public WebElement findElement() {
                return null;
            }

            @Override
            public List<WebElement> findElements() {
                return null;
            }
        });

        assertThatThrownBy(() -> LocatorProxies.now(proxy)).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testNullElementListShouldNotThrowException() {
        List<WebElement> webElementList = LocatorProxies.createWebElementList((List<WebElement>) null);
        assertThat(webElementList).isEmpty();

        List<WebElement> webElementList2 = LocatorProxies.createWebElementList(new ElementLocator() {
            @Override
            public WebElement findElement() {
                return null;
            }

            @Override
            public List<WebElement> findElements() {
                return null;
            }
        });
        assertThat(webElementList2).isEmpty();
    }

    @Test
    public void testEmptyElementListShouldNotThrowException() {
        List<WebElement> webElementList = LocatorProxies.createWebElementList(Collections.emptyList());
        assertThat(webElementList).isEmpty();

        List<WebElement> webElementList2 = LocatorProxies.createWebElementList(new ElementLocator() {
            @Override
            public WebElement findElement() {
                return null;
            }

            @Override
            public List<WebElement> findElements() {
                return Collections.emptyList();
            }
        });
        assertThat(webElementList2).isEmpty();
    }

    @Test
    public void testToString() {
        when(element1.toString()).thenReturn("element1");

        ElementLocator locator = mock(ElementLocator.class);
        when(locator.findElement()).thenReturn(element1);
        when(locator.toString()).thenReturn("element1-locator");

        WebElement webElement = LocatorProxies.createWebElement(locator);
        assertThat(webElement.toString()).isEqualTo("element1-locator (Lazy Element)");

        assertThat(LocatorProxies.loaded(webElement)).isFalse();

        LocatorProxies.now(webElement);

        assertThat(webElement.toString()).isEqualTo("element1-locator (" + element1.toString() + ")");
    }

    @Test
    public void testHashcode() {
        ElementLocator locator = mock(ElementLocator.class);
        when(locator.findElement()).thenReturn(element1);

        WebElement webElement = LocatorProxies.createWebElement(locator);
        assertThat(webElement.hashCode()).isEqualTo(2048 + locator.hashCode());

        assertThat(LocatorProxies.loaded(webElement)).isFalse();

        LocatorProxies.now(webElement);

        assertThat(webElement.hashCode()).isEqualTo(element1.hashCode());
    }

    @Test
    public void testEquals() {
        ElementLocator locator = mock(ElementLocator.class);
        when(locator.findElement()).thenReturn(element1);

        WebElement webElement = LocatorProxies.createWebElement(locator);
        WebElement sameWebElement = LocatorProxies.createWebElement(locator);

        assertThat(webElement).isEqualTo(sameWebElement);

        ElementLocator otherLocator = mock(ElementLocator.class);
        when(otherLocator.findElement()).thenReturn(element2);
        WebElement otherWebElement = LocatorProxies.createWebElement(otherLocator);

        assertThat(webElement).isNotEqualTo(otherWebElement);

        assertThat(LocatorProxies.loaded(webElement)).isFalse();
        assertThat(LocatorProxies.loaded(sameWebElement)).isFalse();
        assertThat(LocatorProxies.loaded(otherWebElement)).isFalse();

        LocatorProxies.now(webElement);

        assertThat(webElement).isEqualTo(sameWebElement);

        assertThat(LocatorProxies.loaded(webElement)).isTrue();
        assertThat(LocatorProxies.loaded(sameWebElement)).isTrue();
        assertThat(LocatorProxies.loaded(otherWebElement)).isFalse();

        LocatorProxies.reset(webElement);
        LocatorProxies.reset(sameWebElement);
        LocatorProxies.reset(otherWebElement);

        assertThat(LocatorProxies.loaded(webElement)).isFalse();
        assertThat(LocatorProxies.loaded(sameWebElement)).isFalse();
        assertThat(LocatorProxies.loaded(otherWebElement)).isFalse();

        LocatorProxies.now(webElement);

        assertThat(sameWebElement).isEqualTo(webElement);

        assertThat(LocatorProxies.loaded(webElement)).isTrue();
        assertThat(LocatorProxies.loaded(sameWebElement)).isTrue();
        assertThat(LocatorProxies.loaded(otherWebElement)).isFalse();

        assertThat(webElement).isNotEqualTo(otherWebElement);

        assertThat(LocatorProxies.loaded(otherWebElement)).isTrue();
    }

    @Test
    public void testIsPresent() {
        ElementLocator locator = mock(ElementLocator.class);
        when(locator.findElement()).thenReturn(element1);
        when(locator.findElements()).thenReturn(Arrays.asList(element1, element2, element3));

        WebElement webElement = LocatorProxies.createWebElement(locator);

        ElementLocator otherLocator = mock(ElementLocator.class);
        WebElement otherWebElement = LocatorProxies.createWebElement(otherLocator);

        assertThat(LocatorProxies.loaded(webElement)).isFalse();
        assertThat(LocatorProxies.present(webElement)).isTrue();
        assertThat(LocatorProxies.loaded(webElement)).isTrue();

        assertThat(LocatorProxies.loaded(otherWebElement)).isFalse();
        assertThat(LocatorProxies.present(otherWebElement)).isFalse();
        assertThat(LocatorProxies.loaded(otherWebElement)).isFalse();

        List<WebElement> webElementList = LocatorProxies.createWebElementList(locator);

        assertThat(LocatorProxies.loaded(webElementList)).isFalse();
        assertThat(LocatorProxies.present(webElementList)).isTrue();
        assertThat(LocatorProxies.loaded(webElementList)).isTrue();

        when(locator.findElements()).thenReturn(Collections.emptyList());

        LocatorProxies.reset(webElementList);

        assertThat(LocatorProxies.loaded(webElementList)).isFalse();
        assertThat(LocatorProxies.present(webElementList)).isFalse();
        assertThat(LocatorProxies.loaded(webElementList)).isTrue();
    }

    @Test
    public void testLocatorGetter() {
        ElementLocator locator = mock(ElementLocator.class);
        WebElement webElement = LocatorProxies.createWebElement(locator);

        LocatorHandler locatorHandler = LocatorProxies.getLocatorHandler(webElement);
        assertThat(locatorHandler.getLocator()).isSameAs(locator);
    }

    @Test
    public void testFirst() {
        ElementLocator locator = mock(ElementLocator.class);
        when(locator.findElements()).thenReturn(Arrays.asList(element1, element2, element3));

        List<WebElement> webElementList = LocatorProxies.createWebElementList(locator);
        WebElement first = LocatorProxies.first(webElementList);

        assertThat(LocatorProxies.loaded(first)).isFalse();
        assertThat(first).isEqualTo(element1);
    }

    @Test
    public void testLast() {
        ElementLocator locator = mock(ElementLocator.class);
        when(locator.findElements()).thenReturn(Arrays.asList(element1, element2, element3));

        List<WebElement> webElementList = LocatorProxies.createWebElementList(locator);
        WebElement last = LocatorProxies.last(webElementList);

        assertThat(LocatorProxies.loaded(last)).isFalse();
        assertThat(last).isEqualTo(element3);
    }

    @Test
    public void testIndex() {
        ElementLocator locator = mock(ElementLocator.class);
        when(locator.findElements()).thenReturn(Arrays.asList(element1, element2, element3));

        List<WebElement> webElementList = LocatorProxies.createWebElementList(locator);
        WebElement atIndex = LocatorProxies.index(webElementList, 1);

        assertThat(LocatorProxies.loaded(atIndex)).isFalse();
        assertThat(atIndex).isEqualTo(element2);
    }

    @Test
    public void testStateElement() {
        ElementLocator locator = mock(ElementLocator.class);
        when(locator.findElement()).thenReturn(element1);

        WebElement webElement = LocatorProxies.createWebElement(locator);

        assertThat(LocatorProxies.present(webElement)).isTrue();
        webElement.isEnabled();

        when(element1.isEnabled()).thenThrow(StaleElementReferenceException.class);

        assertThat(LocatorProxies.present(webElement)).isFalse();

        reset(element1);
        when(element1.isEnabled()).thenThrow(StaleElementReferenceException.class);

        assertThatThrownBy(webElement::isEnabled).isExactlyInstanceOf(StaleElementReferenceException.class);

        verify(element1, times(6)).isEnabled();
    }
}
