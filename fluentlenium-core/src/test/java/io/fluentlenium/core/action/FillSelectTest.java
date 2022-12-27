package io.fluentlenium.core.action;

import io.fluentlenium.adapter.FluentAdapter;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.search.SearchControl;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FillSelectTest {
    @Mock
    private WebDriver driver;

    @Mock
    private SearchControl search;

    @Mock
    private WebElement element1;

    @Mock
    private WebElement element2;

    @Mock
    private WebElement element3;

    @Mock
    private WebElement element4;

    private FluentAdapter fluentAdapter;

    @Before
    public void before() {
        fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(driver);

        when(element1.getTagName()).thenReturn("select");
        when(element2.getTagName()).thenReturn("span");
        when(element3.getTagName()).thenReturn("select");
        when(element4.getTagName()).thenReturn("select");
    }

    @After
    public void after() {
        reset(driver, search, element1, element2, element3, element4);
    }

    @Test
    public void testFillCss() {
        FluentList<FluentWebElement> list = fluentAdapter.asFluentList(element1, element2, element3);
        FillSelect fillConstructor = new FillSelect(list);

        WebElement option1 = mock(WebElement.class);
        WebElement option2 = mock(WebElement.class);
        WebElement option3 = mock(WebElement.class);
        WebElement option4 = mock(WebElement.class);

        when(element1.findElements(any(By.class))).thenReturn(singletonList(option1));
        when(element2.findElements(any(By.class))).thenReturn(singletonList(option2));
        when(element3.findElements(any(By.class))).thenReturn(singletonList(option3));
        when(element4.findElements(any(By.class))).thenReturn(singletonList(option4));

        when(option1.getAttribute("index")).thenReturn("1");
        when(option3.getAttribute("index")).thenReturn("1");

        fillConstructor.withIndex(1);

        verify(option1).click();
        verify(option2, never()).click();
        verify(option3).click();
        verify(option4, never()).click();

        Assertions.assertThatThrownBy(() -> fillConstructor.withIndex(5)).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testFillList() {
        FluentList<FluentWebElement> list = fluentAdapter.asFluentList(element1, element2, element3, element4);
        FillSelect fillConstructor = new FillSelect(list);

        WebElement option1 = mock(WebElement.class);
        WebElement option2 = mock(WebElement.class);
        WebElement option3 = mock(WebElement.class);
        WebElement option4 = mock(WebElement.class);

        when(element1.findElements(any(By.class))).thenReturn(singletonList(option1));
        when(element2.findElements(any(By.class))).thenReturn(singletonList(option2));
        when(element3.findElements(any(By.class))).thenReturn(singletonList(option3));
        when(element4.findElements(any(By.class))).thenReturn(singletonList(option4));

        fillConstructor.withText("text");

        verify(option1).click();
        verify(option2, never()).click();
        verify(option3).click();
        verify(option4).click();

        Assertions.assertThatThrownBy(() -> new FillSelect(fluentAdapter.newFluentList()).withText("text"));
    }

    @Test
    public void testFillElement() {
        FillSelect fillConstructor = new FillSelect(fluentAdapter.newFluent(element1));

        WebElement option1 = mock(WebElement.class);

        when(element1.findElements(any(By.class))).thenReturn(singletonList(option1));

        fillConstructor.withValue("1");

        verify(option1).click();

        Assertions.assertThatThrownBy(() -> new FillSelect(fluentAdapter.newFluentList()).withValue("1"))
                .isExactlyInstanceOf(NoSuchElementException.class);
    }
}
