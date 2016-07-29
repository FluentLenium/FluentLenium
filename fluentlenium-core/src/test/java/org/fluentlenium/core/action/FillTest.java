package org.fluentlenium.core.action;

import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.Search;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FillTest {
    @Mock
    private WebDriver driver;

    @Mock
    private Search search;

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
    }

    @After
    public void after() {
        reset(driver, element1, element2, element3, element4);
    }

    @Test
    public void testFillList() {
        FluentListImpl<FluentWebElement> list = new FluentListImpl<>(Arrays.asList(new FluentWebElement(element1), new FluentWebElement(element2), new FluentWebElement(element3), new FluentWebElement(element4)));
        Fill fill = new Fill(list);

        when(element2.isDisplayed()).thenReturn(true);
        when(element2.isEnabled()).thenReturn(true);

        when(element3.isDisplayed()).thenReturn(true);

        when(element4.isDisplayed()).thenReturn(true);
        when(element4.isEnabled()).thenReturn(true);

        verify(driver, never()).findElement(any(By.class));

        fill.withText("1", "2", "3");

        verify(element1, never()).sendKeys(anyString());
        verify(element2).sendKeys("1");
        verify(element3, never()).sendKeys(anyString());
        verify(element4).sendKeys("3");
    }

    @Test
    public void testFillElement() {
        when(element1.isDisplayed()).thenReturn(true);
        when(element1.isEnabled()).thenReturn(true);

        Fill fill = new Fill(new FluentWebElement(element1));

        verify(driver, never()).findElement(any(By.class));

        fill.withText("1", "2", "3");

        verify(element1).sendKeys("1");
    }
}
