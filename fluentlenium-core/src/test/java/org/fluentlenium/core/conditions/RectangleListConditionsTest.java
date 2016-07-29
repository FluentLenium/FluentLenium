package org.fluentlenium.core.conditions;


import com.google.common.base.Predicate;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.context.FluentThread;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class RectangleListConditionsTest {

    @Mock
    private WebElement webElement1;

    @Mock
    private WebElement webElement2;

    @Mock
    private WebElement webElement3;

    @Mock
    private FluentAdapter adapter;

    private FluentWebElement fluentWebElement1;

    private FluentWebElement fluentWebElement2;

    private FluentWebElement fluentWebElement3;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        FluentThread.set(adapter);

        fluentWebElement1 = new FluentWebElement(webElement1);
        fluentWebElement2 = new FluentWebElement(webElement2);
        fluentWebElement3 = new FluentWebElement(webElement3);
    }

    @After
    public void after() {
        reset(webElement1);
        reset(webElement2);
        reset(webElement3);
    }

    @Test
    public void fromEachElementConditions() {
        EachElementConditions conditions = new EachElementConditions(Arrays.asList(fluentWebElement1, fluentWebElement2, fluentWebElement3));

        final RectangleConditions rectConditions = conditions.hasRectangle();

        when(webElement1.getRect()).thenReturn(new Rectangle(1, 2, 3, 4));
        when(webElement2.getRect()).thenReturn(new Rectangle(1, 2, 3, 4));
        when(webElement3.getRect()).thenReturn(new Rectangle(1, 2, 3, 4));

        assertThat(rectConditions.isVerified(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return input.equals(new Rectangle(1, 2, 3, 4));
            }
        })).isTrue();

        assertThat(rectConditions.not().isVerified(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return input.equals(new Rectangle(1, 2, 3, 4));
            }
        })).isFalse();

        assertThat(rectConditions.withX(1)).isTrue();
        assertThat(rectConditions.withX(2)).isFalse();
        assertThat(rectConditions.withX(3)).isFalse();
        assertThat(rectConditions.withX(4)).isFalse();

        assertThat(rectConditions.withX().equalTo(1)).isTrue();
        assertThat(rectConditions.withX().equalTo(2)).isFalse();
        assertThat(rectConditions.withX().equalTo(3)).isFalse();
        assertThat(rectConditions.withX().equalTo(4)).isFalse();

        assertThat(rectConditions.withY(1)).isFalse();
        assertThat(rectConditions.withY(2)).isTrue();
        assertThat(rectConditions.withY(3)).isFalse();
        assertThat(rectConditions.withY(4)).isFalse();

        assertThat(rectConditions.withY().equalTo(1)).isFalse();
        assertThat(rectConditions.withY().equalTo(2)).isTrue();
        assertThat(rectConditions.withY().equalTo(3)).isFalse();
        assertThat(rectConditions.withY().equalTo(4)).isFalse();

        assertThat(rectConditions.withHeight(1)).isFalse();
        assertThat(rectConditions.withHeight(2)).isFalse();
        assertThat(rectConditions.withHeight(3)).isTrue();
        assertThat(rectConditions.withHeight(4)).isFalse();

        assertThat(rectConditions.withHeight().equalTo(1)).isFalse();
        assertThat(rectConditions.withHeight().equalTo(2)).isFalse();
        assertThat(rectConditions.withHeight().equalTo(3)).isTrue();
        assertThat(rectConditions.withHeight().equalTo(4)).isFalse();

        assertThat(rectConditions.withWidth(1)).isFalse();
        assertThat(rectConditions.withWidth(2)).isFalse();
        assertThat(rectConditions.withWidth(3)).isFalse();
        assertThat(rectConditions.withWidth(4)).isTrue();

        assertThat(rectConditions.withWidth().equalTo(1)).isFalse();
        assertThat(rectConditions.withWidth().equalTo(2)).isFalse();
        assertThat(rectConditions.withWidth().equalTo(3)).isFalse();
        assertThat(rectConditions.withWidth().equalTo(4)).isTrue();

        assertThat(rectConditions.withPosition(1, 2)).isTrue();
        assertThat(rectConditions.withPosition(3, 4)).isFalse();

        when(webElement1.getRect()).thenReturn(new Rectangle(1, 2, 3, 4));
        when(webElement2.getRect()).thenReturn(new Rectangle(1, 2, 3, 4));
        when(webElement3.getRect()).thenReturn(new Rectangle(10, 20, 30, 40));

        assertThat(rectConditions.withX(1)).isFalse();
        assertThat(rectConditions.withX(2)).isFalse();
        assertThat(rectConditions.withX(3)).isFalse();
        assertThat(rectConditions.withX(4)).isFalse();

        assertThat(rectConditions.withY(1)).isFalse();
        assertThat(rectConditions.withY(2)).isFalse();
        assertThat(rectConditions.withY(3)).isFalse();
        assertThat(rectConditions.withY(4)).isFalse();

        assertThat(rectConditions.withHeight(1)).isFalse();
        assertThat(rectConditions.withHeight(2)).isFalse();
        assertThat(rectConditions.withHeight(3)).isFalse();
        assertThat(rectConditions.withHeight(4)).isFalse();

        assertThat(rectConditions.withWidth(1)).isFalse();
        assertThat(rectConditions.withWidth(2)).isFalse();
        assertThat(rectConditions.withWidth(3)).isFalse();
        assertThat(rectConditions.withWidth(4)).isFalse();

        assertThat(rectConditions.withPosition(1, 2)).isFalse();
        assertThat(rectConditions.withPosition(10, 20)).isFalse();
        assertThat(rectConditions.withPosition(100, 200)).isFalse();

        assertThat(rectConditions.withDimension(4, 3)).isFalse();
        assertThat(rectConditions.withDimension(40, 30)).isFalse();
        assertThat(rectConditions.withDimension(400, 300)).isFalse();

        assertThat(rectConditions.withPositionAndDimension(1, 2, 4, 3)).isFalse();
        assertThat(rectConditions.withPositionAndDimension(10, 20, 40, 30)).isFalse();
        assertThat(rectConditions.withPositionAndDimension(100, 200, 400, 300)).isFalse();
    }

    @Test
    public void fromAtLeastOneElementConditions() {
        AtLeastOneElementConditions conditions = new AtLeastOneElementConditions(Arrays.asList(fluentWebElement1, fluentWebElement2, fluentWebElement3));

        RectangleConditions rectConditions = conditions.hasRectangle();

        when(webElement1.getRect()).thenReturn(new Rectangle(1, 2, 3, 4));
        when(webElement2.getRect()).thenReturn(new Rectangle(1, 2, 3, 4));
        when(webElement3.getRect()).thenReturn(new Rectangle(10, 20, 30, 40));

        assertThat(rectConditions.isVerified(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return input.equals(new Rectangle(1, 2, 3, 4));
            }
        })).isTrue();

        assertThat(rectConditions.not().isVerified(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return input.equals(new Rectangle(1, 2, 3, 4));
            }
        })).isTrue();

        assertThat(rectConditions.withX(1)).isTrue();
        assertThat(rectConditions.withX(2)).isFalse();
        assertThat(rectConditions.withX(3)).isFalse();
        assertThat(rectConditions.withX(4)).isFalse();

        assertThat(rectConditions.withX().equalTo(1)).isTrue();
        assertThat(rectConditions.withX().equalTo(2)).isFalse();
        assertThat(rectConditions.withX().equalTo(3)).isFalse();
        assertThat(rectConditions.withX().equalTo(4)).isFalse();

        assertThat(rectConditions.withX(10)).isTrue();
        assertThat(rectConditions.withX(20)).isFalse();
        assertThat(rectConditions.withX(30)).isFalse();
        assertThat(rectConditions.withX(40)).isFalse();

        assertThat(rectConditions.withX(100)).isFalse();
        assertThat(rectConditions.withX(200)).isFalse();
        assertThat(rectConditions.withX(300)).isFalse();
        assertThat(rectConditions.withX(400)).isFalse();

        assertThat(rectConditions.withY(1)).isFalse();
        assertThat(rectConditions.withY(2)).isTrue();
        assertThat(rectConditions.withY(3)).isFalse();
        assertThat(rectConditions.withY(4)).isFalse();

        assertThat(rectConditions.withY().equalTo(1)).isFalse();
        assertThat(rectConditions.withY().equalTo(2)).isTrue();
        assertThat(rectConditions.withY().equalTo(3)).isFalse();
        assertThat(rectConditions.withY().equalTo(4)).isFalse();

        assertThat(rectConditions.withY(10)).isFalse();
        assertThat(rectConditions.withY(20)).isTrue();
        assertThat(rectConditions.withY(30)).isFalse();
        assertThat(rectConditions.withY(40)).isFalse();

        assertThat(rectConditions.withY(100)).isFalse();
        assertThat(rectConditions.withY(200)).isFalse();
        assertThat(rectConditions.withY(300)).isFalse();
        assertThat(rectConditions.withY(400)).isFalse();

        assertThat(rectConditions.withHeight(1)).isFalse();
        assertThat(rectConditions.withHeight(2)).isFalse();
        assertThat(rectConditions.withHeight(3)).isTrue();
        assertThat(rectConditions.withHeight(4)).isFalse();

        assertThat(rectConditions.withHeight().equalTo(1)).isFalse();
        assertThat(rectConditions.withHeight().equalTo(2)).isFalse();
        assertThat(rectConditions.withHeight().equalTo(3)).isTrue();
        assertThat(rectConditions.withHeight().equalTo(4)).isFalse();

        assertThat(rectConditions.withHeight(10)).isFalse();
        assertThat(rectConditions.withHeight(20)).isFalse();
        assertThat(rectConditions.withHeight(30)).isTrue();
        assertThat(rectConditions.withHeight(40)).isFalse();

        assertThat(rectConditions.withHeight(100)).isFalse();
        assertThat(rectConditions.withHeight(200)).isFalse();
        assertThat(rectConditions.withHeight(300)).isFalse();
        assertThat(rectConditions.withHeight(400)).isFalse();

        assertThat(rectConditions.withWidth(1)).isFalse();
        assertThat(rectConditions.withWidth(2)).isFalse();
        assertThat(rectConditions.withWidth(3)).isFalse();
        assertThat(rectConditions.withWidth(4)).isTrue();

        assertThat(rectConditions.withWidth().equalTo(1)).isFalse();
        assertThat(rectConditions.withWidth().equalTo(2)).isFalse();
        assertThat(rectConditions.withWidth().equalTo(3)).isFalse();
        assertThat(rectConditions.withWidth().equalTo(4)).isTrue();

        assertThat(rectConditions.withWidth(10)).isFalse();
        assertThat(rectConditions.withWidth(20)).isFalse();
        assertThat(rectConditions.withWidth(30)).isFalse();
        assertThat(rectConditions.withWidth(40)).isTrue();

        assertThat(rectConditions.withWidth(100)).isFalse();
        assertThat(rectConditions.withWidth(200)).isFalse();
        assertThat(rectConditions.withWidth(300)).isFalse();
        assertThat(rectConditions.withWidth(400)).isFalse();

        assertThat(rectConditions.withPosition(1, 2)).isTrue();
        assertThat(rectConditions.withPosition(10, 20)).isTrue();
        assertThat(rectConditions.withPosition(100, 200)).isFalse();

        assertThat(rectConditions.withDimension(4, 3)).isTrue();
        assertThat(rectConditions.withDimension(40, 30)).isTrue();
        assertThat(rectConditions.withDimension(400, 300)).isFalse();

        assertThat(rectConditions.withPositionAndDimension(1, 2, 4, 3)).isTrue();
        assertThat(rectConditions.withPositionAndDimension(10, 20, 40, 30)).isTrue();
        assertThat(rectConditions.withPositionAndDimension(100, 200, 400, 300)).isFalse();
    }

}

