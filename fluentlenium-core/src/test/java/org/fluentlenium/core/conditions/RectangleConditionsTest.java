package org.fluentlenium.core.conditions;

import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RectangleConditionsTest {
    @Mock
    private WebElement webElement;

    @Mock
    private Rectangle rectangle;

    @Mock
    private WebDriver driver;

    private FluentWebElement fluentWebElement;
    private WebElementConditions conditions;
    private FluentAdapter fluentAdapter;

    @Before
    public void before() {
        when(webElement.getRect()).thenReturn(rectangle);

        fluentAdapter = new FluentAdapter(driver);
        fluentWebElement = new FluentWebElement(webElement, fluentAdapter, new DefaultComponentInstantiator(fluentAdapter));
        conditions = new WebElementConditions(fluentWebElement);
    }

    @After
    public void after() {
        reset(webElement);
    }

    @Test
    public void withX() {
        when(rectangle.getX()).thenReturn(1);

        assertThat(conditions.rectangle().x(1)).isTrue();
        assertThat(conditions.rectangle().x(2)).isFalse();

        assertThat(conditions.rectangle().x().equalTo(1)).isTrue();
        assertThat(conditions.rectangle().x().equalTo(2)).isFalse();
    }

    @Test
    public void notWithX() {
        when(rectangle.getX()).thenReturn(1);

        assertThat(conditions.rectangle().not().x(1)).isFalse();
        assertThat(conditions.rectangle().not().x(2)).isTrue();

        assertThat(conditions.rectangle().not().x().equalTo(1)).isFalse();
        assertThat(conditions.rectangle().not().x().equalTo(2)).isTrue();
    }

    @Test
    public void notHasRectangleWithX() {
        when(rectangle.getX()).thenReturn(1);

        assertThat(conditions.not().rectangle().x(1)).isFalse();
        assertThat(conditions.not().rectangle().x(2)).isTrue();

        assertThat(conditions.not().rectangle().x().equalTo(1)).isFalse();
        assertThat(conditions.not().rectangle().x().equalTo(2)).isTrue();
    }

    @Test
    public void withY() {
        when(rectangle.getY()).thenReturn(1);

        assertThat(conditions.rectangle().y(1)).isTrue();
        assertThat(conditions.rectangle().y(2)).isFalse();

        assertThat(conditions.rectangle().y().equalTo(1)).isTrue();
        assertThat(conditions.rectangle().y().equalTo(2)).isFalse();
    }

    @Test
    public void withPosition() {
        when(rectangle.getX()).thenReturn(3);
        when(rectangle.getY()).thenReturn(4);
        when(rectangle.getPoint()).thenReturn(new Point(3, 4));

        assertThat(conditions.rectangle().position(3, 4)).isTrue();
        assertThat(conditions.rectangle().position(3, 5)).isFalse();
    }

    @Test
    public void withWidth() {
        when(rectangle.getWidth()).thenReturn(1);

        assertThat(conditions.rectangle().width(1)).isTrue();
        assertThat(conditions.rectangle().width(2)).isFalse();

        assertThat(conditions.rectangle().width().equalTo(1)).isTrue();
        assertThat(conditions.rectangle().width().equalTo(2)).isFalse();
    }

    @Test
    public void withHeight() {
        when(rectangle.getHeight()).thenReturn(1);

        assertThat(conditions.rectangle().height(1)).isTrue();
        assertThat(conditions.rectangle().height(2)).isFalse();

        assertThat(conditions.rectangle().height(1)).isTrue();
        assertThat(conditions.rectangle().height(2)).isFalse();

        assertThat(conditions.rectangle().height().equalTo(1)).isTrue();
        assertThat(conditions.rectangle().height().equalTo(2)).isFalse();
    }

    @Test
    public void withDimension() {
        when(rectangle.getWidth()).thenReturn(3);
        when(rectangle.getHeight()).thenReturn(4);
        when(rectangle.getDimension()).thenReturn(new Dimension(3, 4));

        assertThat(conditions.rectangle().dimension(3, 4)).isTrue();
        assertThat(conditions.rectangle().dimension(3, 5)).isFalse();
    }

    @Test
    public void withPositionAndDimension() {
        when(rectangle.getX()).thenReturn(3);
        when(rectangle.getY()).thenReturn(4);
        when(rectangle.getPoint()).thenReturn(new Point(3, 4));

        when(rectangle.getWidth()).thenReturn(5);
        when(rectangle.getHeight()).thenReturn(6);
        when(rectangle.getDimension()).thenReturn(new Dimension(5, 6));

        assertThat(conditions.rectangle().positionAndDimension(3, 4, 5, 6)).isTrue();
        assertThat(conditions.rectangle().positionAndDimension(3, 4, 5, 7)).isFalse();
    }

}
