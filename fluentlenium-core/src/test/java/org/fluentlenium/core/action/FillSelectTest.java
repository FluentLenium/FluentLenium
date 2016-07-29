package org.fluentlenium.core.action;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.context.FluentThread;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class FillSelectTest {
    @Mock
    private WebDriver driver;

    @Mock
    private FluentAdapter fluentAdapter;

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
        MockitoAnnotations.initMocks(this);

        FluentThread.set(fluentAdapter);
        when(fluentAdapter.getDriver()).thenReturn(driver);

        when(element1.getTagName()).thenReturn("select");
        when(element2.getTagName()).thenReturn("span");
        when(element3.getTagName()).thenReturn("select");
        when(element4.getTagName()).thenReturn("select");

    }

    @After
    public void after() {
        reset(driver, fluentAdapter, element1, element2, element3, element4);
    }

    @Test
    public void testFill() {
        FillSelect fillConstructor = new FillSelect(driver);

        when(driver.findElements(By.cssSelector("*"))).thenReturn(Arrays.asList(element1, element2, element3, element4));

        WebElement option1 = mock(WebElement.class);
        WebElement option2 = mock(WebElement.class);
        WebElement option3 = mock(WebElement.class);
        WebElement option4 = mock(WebElement.class);

        when(element1.findElements(any(By.class))).thenReturn(Arrays.asList(option1));
        when(element2.findElements(any(By.class))).thenReturn(Arrays.asList(option2));
        when(element3.findElements(any(By.class))).thenReturn(Arrays.asList(option3));
        when(element4.findElements(any(By.class))).thenReturn(Arrays.asList(option4));

        fillConstructor.withValue("1");

        verify(option1).click();
        verify(option2, never()).click();
        verify(option3).click();
        verify(option4).click();

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                new FillSelect(new FluentListImpl<FluentWebElement>(), driver).withValue("1");
            }
        }).isExactlyInstanceOf(NoSuchElementException.class).withFailMessage("No select element found");
    }

    @Test
    public void testFillCss() {
        final FillSelect fillConstructor = new FillSelect(".test", driver);

        when(driver.findElements(By.cssSelector(".test"))).thenReturn(Arrays.asList(element1, element2, element3, element4));

        WebElement option1 = mock(WebElement.class);
        WebElement option2 = mock(WebElement.class);
        WebElement option3 = mock(WebElement.class);
        WebElement option4 = mock(WebElement.class);

        when(element1.findElements(any(By.class))).thenReturn(Arrays.asList(option1));
        when(element2.findElements(any(By.class))).thenReturn(Arrays.asList(option2));
        when(element3.findElements(any(By.class))).thenReturn(Arrays.asList(option3));
        when(element4.findElements(any(By.class))).thenReturn(Arrays.asList(option4));

        when(option1.getAttribute("index")).thenReturn("1");
        when(option3.getAttribute("index")).thenReturn("1");
        when(option4.getAttribute("index")).thenReturn("2");

        fillConstructor.withIndex(1);

        verify(option1).click();
        verify(option2, never()).click();
        verify(option3).click();
        verify(option4, never()).click();

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                fillConstructor.withIndex(5);
            }
        }).isExactlyInstanceOf(NoSuchElementException.class).withFailMessage("No select element found with option index=5");
    }

    @Test
    public void testFillBy() {
        final FillSelect fillConstructor = new FillSelect(By.cssSelector(".test"), driver);

        when(driver.findElements(By.cssSelector(".test"))).thenReturn(Arrays.asList(element1, element2, element3, element4));

        WebElement option1 = mock(WebElement.class);
        WebElement option2 = mock(WebElement.class);
        WebElement option3 = mock(WebElement.class);
        WebElement option4 = mock(WebElement.class);

        when(element1.findElements(any(By.class))).thenReturn(Arrays.asList(option1));
        when(element2.findElements(any(By.class))).thenReturn(Arrays.asList(option2));
        when(element3.findElements(any(By.class))).thenReturn(Arrays.asList(option3));
        when(element4.findElements(any(By.class))).thenReturn(Arrays.asList(option4));

        when(option1.getAttribute("index")).thenReturn("1");
        when(option3.getAttribute("index")).thenReturn("1");
        when(option4.getAttribute("index")).thenReturn("2");

        fillConstructor.withIndex(1);

        verify(option1).click();
        verify(option2, never()).click();
        verify(option3).click();
        verify(option4, never()).click();

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                fillConstructor.withIndex(5);
            }
        }).isExactlyInstanceOf(NoSuchElementException.class).withFailMessage("No select element found with option index=5");
    }

    @Test
    public void testFillList() {
        FluentListImpl<FluentWebElement> list = new FluentListImpl<>(Arrays.asList(new FluentWebElement(element1), new FluentWebElement(element2), new FluentWebElement(element3), new FluentWebElement(element4)));
        FillSelect fillConstructor = new FillSelect(list, driver);

        WebElement option1 = mock(WebElement.class);
        WebElement option2 = mock(WebElement.class);
        WebElement option3 = mock(WebElement.class);
        WebElement option4 = mock(WebElement.class);

        when(element1.findElements(any(By.class))).thenReturn(Arrays.asList(option1));
        when(element2.findElements(any(By.class))).thenReturn(Arrays.asList(option2));
        when(element3.findElements(any(By.class))).thenReturn(Arrays.asList(option3));
        when(element4.findElements(any(By.class))).thenReturn(Arrays.asList(option4));

        fillConstructor.withText("text");

        verify(option1).click();
        verify(option2, never()).click();
        verify(option3).click();
        verify(option4).click();

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                new FillSelect(new FluentListImpl<FluentWebElement>(), driver).withText("text");
            }
        }).isExactlyInstanceOf(NoSuchElementException.class).withFailMessage("No select element found");
    }

    @Test
    public void testFillElement() {
        FillSelect fillConstructor = new FillSelect(new FluentWebElement(element1), driver);

        WebElement option1 = mock(WebElement.class);

        when(element1.findElements(any(By.class))).thenReturn(Arrays.asList(option1));

        fillConstructor.withValue("1");

        verify(option1).click();
    }
}
