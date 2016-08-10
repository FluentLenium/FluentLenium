package org.fluentlenium.core.conditions;

import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class RectangleConditionsTest {
    @Mock
    private WebElement webElement;

    @Mock
    private Rectangle rectangle;

    private FluentWebElement fluentWebElement;
    private WebElementConditions conditions;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        when(webElement.getRect()).thenReturn(rectangle);

        fluentWebElement = new FluentWebElement(webElement);
        conditions = new WebElementConditions(fluentWebElement);
    }

    @After
    public void after() {
        reset(webElement);
    }

    @Test
    public void withX() {
        when(rectangle.getX()).thenReturn(1);

        assertThat(conditions.hasRectangle().withX(1)).isTrue();
        assertThat(conditions.hasRectangle().withX(2)).isFalse();

        assertThat(conditions.hasRectangle().withX().equalTo(1)).isTrue();
        assertThat(conditions.hasRectangle().withX().equalTo(2)).isFalse();
    }

    @Test
    public void notWithX() {
        when(rectangle.getX()).thenReturn(1);

        assertThat(conditions.hasRectangle().not().withX(1)).isFalse();
        assertThat(conditions.hasRectangle().not().withX(2)).isTrue();

        assertThat(conditions.hasRectangle().not().withX().equalTo(1)).isTrue();
        assertThat(conditions.hasRectangle().not().withX().equalTo(2)).isFalse();
    }

    @Test
    public void notHasRectangleWithX() {
        when(rectangle.getX()).thenReturn(1);

        assertThat(conditions.not().hasRectangle().withX(1)).isFalse();
        assertThat(conditions.not().hasRectangle().withX(2)).isTrue();

        assertThat(conditions.not().hasRectangle().withX().equalTo(1)).isTrue();
        assertThat(conditions.not().hasRectangle().withX().equalTo(2)).isFalse();
    }

    @Test
    public void withY() {
        when(rectangle.getY()).thenReturn(1);

        assertThat(conditions.hasRectangle().withY(1)).isTrue();
        assertThat(conditions.hasRectangle().withY(2)).isFalse();

        assertThat(conditions.hasRectangle().withY().equalTo(1)).isTrue();
        assertThat(conditions.hasRectangle().withY().equalTo(2)).isFalse();
    }

    @Test
    public void withPosition() {
        when(rectangle.getX()).thenReturn(3);
        when(rectangle.getY()).thenReturn(4);
        when(rectangle.getPoint()).thenReturn(new Point(3, 4));

        assertThat(conditions.hasRectangle().withPosition(3, 4)).isTrue();
        assertThat(conditions.hasRectangle().withPosition(3, 5)).isFalse();
    }

    @Test
    public void withWidth() {
        when(rectangle.getWidth()).thenReturn(1);

        assertThat(conditions.hasRectangle().withWidth(1)).isTrue();
        assertThat(conditions.hasRectangle().withWidth(2)).isFalse();

        assertThat(conditions.hasRectangle().withWidth().equalTo(1)).isTrue();
        assertThat(conditions.hasRectangle().withWidth().equalTo(2)).isFalse();
    }

    @Test
    public void withHeight() {
        when(rectangle.getHeight()).thenReturn(1);

        assertThat(conditions.hasRectangle().withHeight(1)).isTrue();
        assertThat(conditions.hasRectangle().withHeight(2)).isFalse();

        assertThat(conditions.hasRectangle().withHeight(1)).isTrue();
        assertThat(conditions.hasRectangle().withHeight(2)).isFalse();

        assertThat(conditions.hasRectangle().withHeight().equalTo(1)).isTrue();
        assertThat(conditions.hasRectangle().withHeight().equalTo(2)).isFalse();
    }

    @Test
    public void withDimension() {
        when(rectangle.getWidth()).thenReturn(3);
        when(rectangle.getHeight()).thenReturn(4);
        when(rectangle.getDimension()).thenReturn(new Dimension(3, 4));

        assertThat(conditions.hasRectangle().withDimension(3, 4)).isTrue();
        assertThat(conditions.hasRectangle().withDimension(3, 5)).isFalse();
    }

    @Test
    public void withPositionAndDimension() {
        when(rectangle.getX()).thenReturn(3);
        when(rectangle.getY()).thenReturn(4);
        when(rectangle.getPoint()).thenReturn(new Point(3, 4));

        when(rectangle.getWidth()).thenReturn(5);
        when(rectangle.getHeight()).thenReturn(6);
        when(rectangle.getDimension()).thenReturn(new Dimension(5, 6));

        assertThat(conditions.hasRectangle().withPositionAndDimension(3, 4, 5, 6)).isTrue();
        assertThat(conditions.hasRectangle().withPositionAndDimension(3, 4, 5, 7)).isFalse();
    }

}
