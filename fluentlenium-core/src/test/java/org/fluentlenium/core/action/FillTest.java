package org.fluentlenium.core.action;

import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
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

@RunWith(MockitoJUnitRunner.class)
public class FillTest {
    @Mock
    private WebDriver driver;

    @Mock
    private WebElement element1;

    @Mock
    private WebElement element2;

    @Mock
    private WebElement element3;

    @Mock
    private WebElement element4;

    private FluentAdapter fluentAdapter;

    private DefaultComponentInstantiator instantiator;

    @Before
    public void before() {
        fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(driver);
        instantiator = new DefaultComponentInstantiator(fluentAdapter);
    }

    @After
    public void after() {
        reset(driver, element1, element2, element3, element4);
    }

    public FluentWebElement el(WebElement element) {
        return new FluentWebElement(element, fluentAdapter, new DefaultComponentInstantiator(fluentAdapter));
    }

    @Test
    public void testFillList() {
        FluentList<FluentWebElement> list = instantiator
                .asFluentList(Arrays.asList(element1, element2, element3, element4));
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

        Fill fill = new Fill(el(element1));

        verify(driver, never()).findElement(any(By.class));

        fill.withText("1", "2", "3");

        verify(element1).sendKeys("1");
    }
}
