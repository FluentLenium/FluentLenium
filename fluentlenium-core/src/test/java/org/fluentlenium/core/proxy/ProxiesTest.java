package org.fluentlenium.core.proxy;

import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.core.domain.WrapsElements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

        when(driver.findElements(By.cssSelector("#element1"))).thenReturn(Arrays.asList(element1));
        when(element1.findElements(By.cssSelector("#element2"))).thenReturn(Arrays.asList(element2));
        when(element2.findElements(By.cssSelector("#element3"))).thenReturn(Arrays.asList(element3));
    }

    @Test
    public void testElementIsLazy() {
        final WebElement elementProxy1 = LocatorProxies.createWebElement(new ElementLocator() {
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
        verify(element1).click();
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

        final WebElement element3Proxy = LocatorProxies.createWebElement(new ElementLocator() {
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
        verify(driver).findElement(By.cssSelector("#element1"));
        verify(element1).findElement(By.cssSelector("#element2"));
        verify(element2).findElement(By.cssSelector("#element3"));
    }

    @Test
    public void testAlreadyLoadedElementsShouldBeLoaded() {
        final WebElement webElement = LocatorProxies.createWebElement(element1);

        assertThat(LocatorProxies.loaded(webElement)).isTrue();
        assertThat(((WrapsElement) webElement).getWrappedElement()).isSameAs(element1);
    }

    @Test
    public void testAlreadyLoadedElementListShouldBeLoaded() {
        final List<WebElement> webElementList = LocatorProxies.createWebElementList(Arrays.asList(element1, element2, element3));

        assertThat(LocatorProxies.loaded(webElementList)).isTrue();
        assertThat(((WrapsElements) webElementList).getWrappedElements()).containsExactly(element1, element2, element3);
    }

    @Test
    public void testNullElementShouldThrowNoSuchElementException() {
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                LocatorProxies.createWebElement((WebElement) null);
            }
        }).isExactlyInstanceOf(NoSuchElementException.class);

        final WebElement proxy = LocatorProxies.createWebElement(new ElementLocator() {
            @Override
            public WebElement findElement() {
                return null;
            }

            @Override
            public List<WebElement> findElements() {
                return null;
            }
        });

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                LocatorProxies.now(proxy);
            }
        }).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testNullElementListShouldNotThrowException() {
        final List<WebElement> webElementList = LocatorProxies.createWebElementList((List<WebElement>) null);
        assertThat(webElementList).isEmpty();

        final List<WebElement> webElementList2 = LocatorProxies.createWebElementList(new ElementLocator() {
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
        final List<WebElement> webElementList = LocatorProxies.createWebElementList(Collections.<WebElement>emptyList());
        assertThat(webElementList).isEmpty();

        final List<WebElement> webElementList2 = LocatorProxies.createWebElementList(new ElementLocator() {
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

        final ElementLocator locator = mock(ElementLocator.class);
        when(locator.findElement()).thenReturn(element1);
        when(locator.toString()).thenReturn("element1-locator");

        final WebElement webElement = LocatorProxies.createWebElement(locator);
        assertThat(webElement.toString()).isEqualTo("element1-locator (Lazy Element)");

        assertThat(LocatorProxies.loaded(webElement)).isFalse();

        LocatorProxies.now(webElement);

        assertThat(webElement.toString()).isEqualTo("element1-locator (" + element1.toString() + ")");
    }

    @Test
    public void testHashcode() {
        when(element1.toString()).thenReturn("element1");

        final ElementLocator locator = mock(ElementLocator.class);
        when(locator.findElement()).thenReturn(element1);

        final WebElement webElement = LocatorProxies.createWebElement(locator);
        assertThat(webElement.hashCode()).isEqualTo(2048 + locator.hashCode());

        assertThat(LocatorProxies.loaded(webElement)).isFalse();

        LocatorProxies.now(webElement);

        assertThat(webElement.hashCode()).isEqualTo(element1.hashCode());
    }

    @Test
    public void testEquals() {
        final ElementLocator locator = mock(ElementLocator.class);
        when(locator.findElement()).thenReturn(element1);

        final WebElement webElement = LocatorProxies.createWebElement(locator);
        final WebElement sameWebElement = LocatorProxies.createWebElement(locator);

        assertThat(webElement).isEqualTo(sameWebElement);

        final ElementLocator otherLocator = mock(ElementLocator.class);
        when(otherLocator.findElement()).thenReturn(element2);
        final WebElement otherWebElement = LocatorProxies.createWebElement(otherLocator);

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
        final ElementLocator locator = mock(ElementLocator.class);
        when(locator.findElement()).thenReturn(element1);
        when(locator.findElements()).thenReturn(Arrays.asList(element1, element2, element3));

        final WebElement webElement = LocatorProxies.createWebElement(locator);

        final ElementLocator otherLocator = mock(ElementLocator.class);
        final WebElement otherWebElement = LocatorProxies.createWebElement(otherLocator);

        assertThat(LocatorProxies.loaded(webElement)).isFalse();
        assertThat(LocatorProxies.present(webElement)).isTrue();
        assertThat(LocatorProxies.loaded(webElement)).isTrue();

        assertThat(LocatorProxies.loaded(otherWebElement)).isFalse();
        assertThat(LocatorProxies.present(otherWebElement)).isFalse();
        assertThat(LocatorProxies.loaded(otherWebElement)).isFalse();

        final List<WebElement> webElementList = LocatorProxies.createWebElementList(locator);

        assertThat(LocatorProxies.loaded(webElementList)).isFalse();
        assertThat(LocatorProxies.present(webElementList)).isTrue();
        assertThat(LocatorProxies.loaded(webElementList)).isTrue();

        when(locator.findElements()).thenReturn(Collections.<WebElement>emptyList());

        LocatorProxies.reset(webElementList);

        assertThat(LocatorProxies.loaded(webElementList)).isFalse();
        assertThat(LocatorProxies.present(webElementList)).isFalse();
        assertThat(LocatorProxies.loaded(webElementList)).isTrue();
    }

    @Test
    public void testLocatorGetter() {
        final ElementLocator locator = mock(ElementLocator.class);
        final WebElement webElement = LocatorProxies.createWebElement(locator);

        final LocatorHandler locatorHandler = LocatorProxies.getLocatorHandler(webElement);
        assertThat(locatorHandler.getLocator()).isSameAs(locator);
    }

    @Test
    public void testFirst() {
        final ElementLocator locator = mock(ElementLocator.class);
        when(locator.findElements()).thenReturn(Arrays.asList(element1, element2, element3));

        final List<WebElement> webElementList = LocatorProxies.createWebElementList(locator);
        final WebElement first = LocatorProxies.first(webElementList);

        assertThat(LocatorProxies.loaded(first)).isFalse();
        assertThat(first).isEqualTo(element1);
    }

    @Test
    public void testLast() {
        final ElementLocator locator = mock(ElementLocator.class);
        when(locator.findElements()).thenReturn(Arrays.asList(element1, element2, element3));

        final List<WebElement> webElementList = LocatorProxies.createWebElementList(locator);
        final WebElement last = LocatorProxies.last(webElementList);

        assertThat(LocatorProxies.loaded(last)).isFalse();
        assertThat(last).isEqualTo(element3);
    }

    @Test
    public void testIndex() {
        final ElementLocator locator = mock(ElementLocator.class);
        when(locator.findElements()).thenReturn(Arrays.asList(element1, element2, element3));

        final List<WebElement> webElementList = LocatorProxies.createWebElementList(locator);
        final WebElement atIndex = LocatorProxies.index(webElementList, 1);

        assertThat(LocatorProxies.loaded(atIndex)).isFalse();
        assertThat(atIndex).isEqualTo(element2);
    }

    @Test
    public void testStateElement() {
        final ElementLocator locator = mock(ElementLocator.class);
        when(locator.findElement()).thenReturn(element1);

        final WebElement webElement = LocatorProxies.createWebElement(locator);

        assertThat(LocatorProxies.present(webElement)).isTrue();
        webElement.isEnabled();

        when(element1.isEnabled()).thenThrow(StaleElementReferenceException.class);

        assertThat(LocatorProxies.present(webElement)).isFalse();

        reset(element1);
        when(element1.isEnabled()).thenThrow(StaleElementReferenceException.class);

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                webElement.isEnabled();
            }
        }).isExactlyInstanceOf(StaleElementReferenceException.class);

        verify(element1, times(6)).isEnabled();
    }
}
