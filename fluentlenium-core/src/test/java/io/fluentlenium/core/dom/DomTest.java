package io.fluentlenium.core.dom;

import io.fluentlenium.adapter.FluentAdapter;
import io.fluentlenium.core.components.ComponentInstantiator;
import io.fluentlenium.core.components.DefaultComponentInstantiator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DomTest {
    @Mock
    private WebElement element;

    @Mock
    private WebDriver driver;

    private ComponentInstantiator instantiator;

    private FluentAdapter fluentAdapter;

    @Before
    public void before() {
        fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(driver);

        instantiator = new DefaultComponentInstantiator(fluentAdapter);
    }

    @Test
    public void testAncestors() {
        Dom dom = new Dom(element, instantiator);

        List<WebElement> elements = Arrays.asList(mock(WebElement.class), mock(WebElement.class), mock(WebElement.class));
        when(element.findElements(By.xpath("ancestor::*"))).thenReturn(elements);

        assertThat(dom.ancestors().toElements()).isEqualTo(elements);
    }

    @Test
    public void testDescendants() {
        Dom dom = new Dom(element, instantiator);

        List<WebElement> elements = Arrays.asList(mock(WebElement.class), mock(WebElement.class), mock(WebElement.class));
        when(element.findElements(By.xpath("descendant::*"))).thenReturn(elements);

        assertThat(dom.descendants().toElements()).isEqualTo(elements);
    }

    @Test
    public void testFollowings() {
        Dom dom = new Dom(element, instantiator);

        List<WebElement> elements = Arrays.asList(mock(WebElement.class), mock(WebElement.class), mock(WebElement.class));
        when(element.findElements(By.xpath("following::*"))).thenReturn(elements);

        assertThat(dom.followings().toElements()).isEqualTo(elements);
    }

    @Test
    public void testFollowingSiblings() {
        Dom dom = new Dom(element, instantiator);

        List<WebElement> elements = Arrays.asList(mock(WebElement.class), mock(WebElement.class), mock(WebElement.class));
        when(element.findElements(By.xpath("following-sibling::*"))).thenReturn(elements);

        assertThat(dom.followingSiblings().toElements()).isEqualTo(elements);
    }

    @Test
    public void testPrecedingElementsInList() {
        Dom dom = new Dom(element, instantiator);

        List<WebElement> elements = Arrays.asList(mock(WebElement.class), mock(WebElement.class), mock(WebElement.class));
        when(element.findElements(By.xpath("preceding::*"))).thenReturn(elements);

        assertThat(dom.precedings().toElements()).isEqualTo(elements);
    }

    @Test
    public void testPrecedingSiblings() {
        Dom dom = new Dom(element, instantiator);

        List<WebElement> elements = Arrays.asList(mock(WebElement.class), mock(WebElement.class), mock(WebElement.class));
        when(element.findElements(By.xpath("preceding-sibling::*"))).thenReturn(elements);

        assertThat(dom.precedingSiblings().toElements()).isEqualTo(elements);
    }

    @Test
    public void testParent() {
        Dom dom = new Dom(element, instantiator);

        WebElement parent = mock(WebElement.class);
        when(element.findElement(By.xpath("parent::*"))).thenReturn(parent);

        assertThat(dom.parent().getElement()).isEqualTo(parent);
    }

}
