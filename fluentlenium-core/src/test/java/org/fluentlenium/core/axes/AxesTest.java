package org.fluentlenium.core.axes;


import org.fluentlenium.core.FluentDriver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AxesTest {
    @Mock
    private WebElement element;

    @Mock
    private WebDriver driver;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAncestors() {
        Axes axes = new Axes(driver, element);

        List<WebElement> elements = Arrays.asList(mock(WebElement.class), mock(WebElement.class), mock(WebElement.class));
        when(element.findElements(By.xpath("ancestor::*"))).thenReturn(elements);

        assertThat(axes.ancestors().toElements()).isEqualTo(elements);
    }

    @Test
    public void testDescendants() {
        Axes axes = new Axes(driver, element);

        List<WebElement> elements = Arrays.asList(mock(WebElement.class), mock(WebElement.class), mock(WebElement.class));
        when(element.findElements(By.xpath("descendant::*"))).thenReturn(elements);

        assertThat(axes.descendants().toElements()).isEqualTo(elements);
    }

    @Test
    public void testFollowings() {
        Axes axes = new Axes(driver, element);

        List<WebElement> elements = Arrays.asList(mock(WebElement.class), mock(WebElement.class), mock(WebElement.class));
        when(element.findElements(By.xpath("following::*"))).thenReturn(elements);

        assertThat(axes.followings().toElements()).isEqualTo(elements);
    }

    @Test
    public void testFollowingSiblings() {
        Axes axes = new Axes(driver, element);

        List<WebElement> elements = Arrays.asList(mock(WebElement.class), mock(WebElement.class), mock(WebElement.class));
        when(element.findElements(By.xpath("following-sibling::*"))).thenReturn(elements);

        assertThat(axes.followingSiblings().toElements()).isEqualTo(elements);
    }

    @Test
    public void testPrecedings() {
        Axes axes = new Axes(driver, element);

        List<WebElement> elements = Arrays.asList(mock(WebElement.class), mock(WebElement.class), mock(WebElement.class));
        when(element.findElements(By.xpath("preceding::*"))).thenReturn(elements);

        assertThat(axes.precedings().toElements()).isEqualTo(elements);
    }

    @Test
    public void testPrecedingSiblings() {
        Axes axes = new Axes(driver, element);

        List<WebElement> elements = Arrays.asList(mock(WebElement.class), mock(WebElement.class), mock(WebElement.class));
        when(element.findElements(By.xpath("preceding-sibling::*"))).thenReturn(elements);

        assertThat(axes.precedingSiblings().toElements()).isEqualTo(elements);
    }

    @Test
    public void testParent() {
        Axes axes = new Axes(driver, element);

        WebElement parent = mock(WebElement.class);
        when(element.findElement(By.xpath("parent::*"))).thenReturn(parent);

        assertThat(axes.parent().getElement()).isEqualTo(parent);
    }

}
