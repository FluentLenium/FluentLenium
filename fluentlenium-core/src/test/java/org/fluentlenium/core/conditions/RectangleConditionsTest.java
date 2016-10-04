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
    private static final int RECTANGLE_X_VALID_POS = 1;
    private static final int RECTANGLE_X_INVALID_POS = 2;
    private static final int RECTANGLE_Y_VALID_POS = 1;
    private static final int RECTANGLE_Y_INVALID_POS = 2;
    private static final int RECTANGLE_X_POS = 3;
    private static final int RECTANGLE_Y_POS = 4;
    private static final int RECTANGLE_INVALID_Y_POS = 5;
    private static final int RECTANGLE_WIDTH = 1;
    private static final int RECTANGLE_INVALID_WIDTH = 2;
    private static final int RECTANGLE_HEIGHT = 1;
    private static final int RECTANGLE_INVALID_HEIGHT = 2;
    private static final int RECTANGLE_DIMENSION_WIDTH = 3;
    private static final int RECTANGLE_DIMENSION_HEIGHT = 4;
    private static final int RECTANGLE_DIMENSION_INVALID_HEIGHT = 5;
    private static final int RECTANGLE_POSITION_X = 3;
    private static final int RECTANGLE_POSITION_Y = 4;
    private static final int RECTANGLE_DIMENSION_WIDTH1 = 5;
    private static final int RECTANGLE_DIMENSION_HEIGHT1 = 6;
    private static final int RECTANGLE_DIMENSION_INVALID_HEIGHT1 = 7;

    @Mock
    private WebElement webElement;

    @Mock
    private Rectangle rectangle;

    @Mock
    private WebDriver driver;

    private WebElementConditions conditions;

    @Before
    public void before() {
        when(webElement.getRect()).thenReturn(rectangle);

        final FluentAdapter fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(driver);
        final FluentWebElement fluentWebElement = new FluentWebElement(webElement, fluentAdapter,
                new DefaultComponentInstantiator(fluentAdapter));
        conditions = new WebElementConditions(fluentWebElement);
    }

    @After
    public void after() {
        reset(webElement);
    }

    @Test
    public void withX() {
        when(rectangle.getX()).thenReturn(RECTANGLE_X_VALID_POS);

        assertThat(conditions.rectangle().x(RECTANGLE_X_VALID_POS)).isTrue();
        assertThat(conditions.rectangle().x(RECTANGLE_X_INVALID_POS)).isFalse();

        assertThat(conditions.rectangle().x().equalTo(RECTANGLE_X_VALID_POS)).isTrue();
        assertThat(conditions.rectangle().x().equalTo(RECTANGLE_X_INVALID_POS)).isFalse();
    }

    @Test
    public void notWithX() {
        when(rectangle.getX()).thenReturn(RECTANGLE_X_VALID_POS);

        assertThat(conditions.rectangle().not().x(RECTANGLE_X_VALID_POS)).isFalse();
        assertThat(conditions.rectangle().not().x(RECTANGLE_X_INVALID_POS)).isTrue();

        assertThat(conditions.rectangle().not().x().equalTo(RECTANGLE_X_VALID_POS)).isFalse();
        assertThat(conditions.rectangle().not().x().equalTo(RECTANGLE_X_INVALID_POS)).isTrue();
    }

    @Test
    public void notHasRectangleWithX() {
        when(rectangle.getX()).thenReturn(RECTANGLE_X_VALID_POS);

        assertThat(conditions.not().rectangle().x(RECTANGLE_X_VALID_POS)).isFalse();
        assertThat(conditions.not().rectangle().x(RECTANGLE_X_INVALID_POS)).isTrue();

        assertThat(conditions.not().rectangle().x().equalTo(RECTANGLE_X_VALID_POS)).isFalse();
        assertThat(conditions.not().rectangle().x().equalTo(RECTANGLE_X_INVALID_POS)).isTrue();
    }

    @Test
    public void withY() {
        when(rectangle.getY()).thenReturn(RECTANGLE_Y_VALID_POS);

        assertThat(conditions.rectangle().y(RECTANGLE_Y_VALID_POS)).isTrue();
        assertThat(conditions.rectangle().y(RECTANGLE_Y_INVALID_POS)).isFalse();

        assertThat(conditions.rectangle().y().equalTo(RECTANGLE_Y_VALID_POS)).isTrue();
        assertThat(conditions.rectangle().y().equalTo(RECTANGLE_Y_INVALID_POS)).isFalse();
    }

    @Test
    public void withPosition() {
        when(rectangle.getX()).thenReturn(RECTANGLE_X_POS);
        when(rectangle.getY()).thenReturn(RECTANGLE_Y_POS);
        when(rectangle.getPoint()).thenReturn(new Point(RECTANGLE_X_POS, RECTANGLE_Y_POS));

        assertThat(conditions.rectangle().position(RECTANGLE_X_POS, RECTANGLE_Y_POS)).isTrue();
        assertThat(conditions.rectangle().position(RECTANGLE_X_POS, RECTANGLE_INVALID_Y_POS)).isFalse();
    }

    @Test
    public void withWidth() {
        when(rectangle.getWidth()).thenReturn(RECTANGLE_WIDTH);

        assertThat(conditions.rectangle().width(RECTANGLE_WIDTH)).isTrue();
        assertThat(conditions.rectangle().width(RECTANGLE_INVALID_WIDTH)).isFalse();

        assertThat(conditions.rectangle().width().equalTo(RECTANGLE_WIDTH)).isTrue();
        assertThat(conditions.rectangle().width().equalTo(RECTANGLE_INVALID_WIDTH)).isFalse();
    }

    @Test
    public void withHeight() {
        when(rectangle.getHeight()).thenReturn(RECTANGLE_HEIGHT);

        assertThat(conditions.rectangle().height(RECTANGLE_HEIGHT)).isTrue();
        assertThat(conditions.rectangle().height(RECTANGLE_INVALID_HEIGHT)).isFalse();

        assertThat(conditions.rectangle().height(RECTANGLE_HEIGHT)).isTrue();
        assertThat(conditions.rectangle().height(RECTANGLE_INVALID_HEIGHT)).isFalse();

        assertThat(conditions.rectangle().height().equalTo(RECTANGLE_HEIGHT)).isTrue();
        assertThat(conditions.rectangle().height().equalTo(RECTANGLE_INVALID_HEIGHT)).isFalse();
    }

    @Test
    public void withDimension() {
        when(rectangle.getWidth()).thenReturn(RECTANGLE_DIMENSION_WIDTH);
        when(rectangle.getHeight()).thenReturn(RECTANGLE_DIMENSION_HEIGHT);
        when(rectangle.getDimension()).thenReturn(new Dimension(RECTANGLE_DIMENSION_WIDTH, RECTANGLE_DIMENSION_HEIGHT));

        assertThat(conditions.rectangle().dimension(RECTANGLE_DIMENSION_WIDTH, RECTANGLE_DIMENSION_HEIGHT)).isTrue();
        assertThat(conditions.rectangle().dimension(RECTANGLE_DIMENSION_WIDTH, RECTANGLE_DIMENSION_INVALID_HEIGHT)).isFalse();
    }

    @Test
    public void withPositionAndDimension() {
        when(rectangle.getX()).thenReturn(RECTANGLE_POSITION_X);
        when(rectangle.getY()).thenReturn(RECTANGLE_POSITION_Y);
        when(rectangle.getPoint()).thenReturn(new Point(RECTANGLE_POSITION_X, RECTANGLE_POSITION_Y));

        when(rectangle.getWidth()).thenReturn(RECTANGLE_DIMENSION_WIDTH1);
        when(rectangle.getHeight()).thenReturn(RECTANGLE_DIMENSION_HEIGHT1);
        when(rectangle.getDimension()).thenReturn(new Dimension(RECTANGLE_DIMENSION_WIDTH1, RECTANGLE_DIMENSION_HEIGHT1));

        assertThat(conditions.rectangle()
                .positionAndDimension(RECTANGLE_POSITION_X, RECTANGLE_POSITION_Y, RECTANGLE_DIMENSION_WIDTH1,
                        RECTANGLE_DIMENSION_HEIGHT1)).isTrue();
        assertThat(conditions.rectangle()
                .positionAndDimension(RECTANGLE_POSITION_X, RECTANGLE_POSITION_Y, RECTANGLE_DIMENSION_HEIGHT1,
                        RECTANGLE_DIMENSION_INVALID_HEIGHT1)).isFalse();
    }

}
