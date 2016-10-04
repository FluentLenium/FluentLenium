package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RectangleListConditionsTest {

    @Mock
    private WebElement webElement1;

    @Mock
    private WebElement webElement2;

    @Mock
    private WebElement webElement3;

    @Mock
    private WebDriver webDriver;

    private FluentWebElement fluentWebElement1;

    private FluentWebElement fluentWebElement2;

    private FluentWebElement fluentWebElement3;

    @Before
    public void before() {
        final FluentAdapter fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(webDriver);
        final DefaultComponentInstantiator instantiator = new DefaultComponentInstantiator(fluentAdapter);

        fluentWebElement1 = new FluentWebElement(webElement1, fluentAdapter, instantiator);
        fluentWebElement2 = new FluentWebElement(webElement2, fluentAdapter, instantiator);
        fluentWebElement3 = new FluentWebElement(webElement3, fluentAdapter, instantiator);
    }

    @After
    public void after() {
        reset(webElement1);
        reset(webElement2);
        reset(webElement3);
    }

    @Test
    public void fromEachElementConditions() { // NOPMD ExcessiveMethodLength
        final EachElementConditions conditions = new EachElementConditions(
                Arrays.asList(fluentWebElement1, fluentWebElement2, fluentWebElement3));

        final RectangleConditions rectConditions = conditions.rectangle();

        when(webElement1.getRect()).thenReturn(new Rectangle(1, 2, 3, 4));
        when(webElement2.getRect()).thenReturn(new Rectangle(1, 2, 3, 4));
        when(webElement3.getRect()).thenReturn(new Rectangle(1, 2, 3, 4));

        assertThat(rectConditions.verify(new Predicate<Rectangle>() {
            @Override
            public boolean apply(final Rectangle input) {
                return input.equals(new Rectangle(1, 2, 3, 4));
            }
        })).isTrue();

        assertThat(rectConditions.not().verify(new Predicate<Rectangle>() {
            @Override
            public boolean apply(final Rectangle input) {
                return input.equals(new Rectangle(1, 2, 3, 4));
            }
        })).isFalse();

        assertThat(rectConditions.x(1)).isTrue();
        assertThat(rectConditions.x(2)).isFalse();
        assertThat(rectConditions.x(3)).isFalse();
        assertThat(rectConditions.x(4)).isFalse();

        assertThat(rectConditions.x().equalTo(1)).isTrue();
        assertThat(rectConditions.x().equalTo(2)).isFalse();
        assertThat(rectConditions.x().equalTo(3)).isFalse();
        assertThat(rectConditions.x().equalTo(4)).isFalse();

        assertThat(rectConditions.y(1)).isFalse();
        assertThat(rectConditions.y(2)).isTrue();
        assertThat(rectConditions.y(3)).isFalse();
        assertThat(rectConditions.y(4)).isFalse();

        assertThat(rectConditions.y().equalTo(1)).isFalse();
        assertThat(rectConditions.y().equalTo(2)).isTrue();
        assertThat(rectConditions.y().equalTo(3)).isFalse();
        assertThat(rectConditions.y().equalTo(4)).isFalse();

        assertThat(rectConditions.height(1)).isFalse();
        assertThat(rectConditions.height(2)).isFalse();
        assertThat(rectConditions.height(3)).isTrue();
        assertThat(rectConditions.height(4)).isFalse();

        assertThat(rectConditions.height().equalTo(1)).isFalse();
        assertThat(rectConditions.height().equalTo(2)).isFalse();
        assertThat(rectConditions.height().equalTo(3)).isTrue();
        assertThat(rectConditions.height().equalTo(4)).isFalse();

        assertThat(rectConditions.width(1)).isFalse();
        assertThat(rectConditions.width(2)).isFalse();
        assertThat(rectConditions.width(3)).isFalse();
        assertThat(rectConditions.width(4)).isTrue();

        assertThat(rectConditions.width().equalTo(1)).isFalse();
        assertThat(rectConditions.width().equalTo(2)).isFalse();
        assertThat(rectConditions.width().equalTo(3)).isFalse();
        assertThat(rectConditions.width().equalTo(4)).isTrue();

        assertThat(rectConditions.position(1, 2)).isTrue();
        assertThat(rectConditions.position(3, 4)).isFalse();

        when(webElement1.getRect()).thenReturn(new Rectangle(1, 2, 3, 4));
        when(webElement2.getRect()).thenReturn(new Rectangle(1, 2, 3, 4));
        when(webElement3.getRect()).thenReturn(new Rectangle(10, 20, 30, 40));

        assertThat(rectConditions.x(1)).isFalse();
        assertThat(rectConditions.x(2)).isFalse();
        assertThat(rectConditions.x(3)).isFalse();
        assertThat(rectConditions.x(4)).isFalse();

        assertThat(rectConditions.y(1)).isFalse();
        assertThat(rectConditions.y(2)).isFalse();
        assertThat(rectConditions.y(3)).isFalse();
        assertThat(rectConditions.y(4)).isFalse();

        assertThat(rectConditions.height(1)).isFalse();
        assertThat(rectConditions.height(2)).isFalse();
        assertThat(rectConditions.height(3)).isFalse();
        assertThat(rectConditions.height(4)).isFalse();

        assertThat(rectConditions.width(1)).isFalse();
        assertThat(rectConditions.width(2)).isFalse();
        assertThat(rectConditions.width(3)).isFalse();
        assertThat(rectConditions.width(4)).isFalse();

        assertThat(rectConditions.position(1, 2)).isFalse();
        assertThat(rectConditions.position(10, 20)).isFalse();
        assertThat(rectConditions.position(100, 200)).isFalse();

        assertThat(rectConditions.dimension(4, 3)).isFalse();
        assertThat(rectConditions.dimension(40, 30)).isFalse();
        assertThat(rectConditions.dimension(400, 300)).isFalse();

        assertThat(rectConditions.positionAndDimension(1, 2, 4, 3)).isFalse();
        assertThat(rectConditions.positionAndDimension(10, 20, 40, 30)).isFalse();
        assertThat(rectConditions.positionAndDimension(100, 200, 400, 300)).isFalse();
    }

    @Test
    public void fromAtLeastOneElementConditions() { // NOPMD ExcessiveMethodLength
        final AtLeastOneElementConditions conditions = new AtLeastOneElementConditions(
                Arrays.asList(fluentWebElement1, fluentWebElement2, fluentWebElement3));

        final RectangleConditions rectConditions = conditions.rectangle();

        when(webElement1.getRect()).thenReturn(new Rectangle(1, 2, 3, 4));
        when(webElement2.getRect()).thenReturn(new Rectangle(1, 2, 3, 4));
        when(webElement3.getRect()).thenReturn(new Rectangle(10, 20, 30, 40));

        assertThat(rectConditions.verify(new Predicate<Rectangle>() {
            @Override
            public boolean apply(final Rectangle input) {
                return input.equals(new Rectangle(1, 2, 3, 4));
            }
        })).isTrue();

        assertThat(rectConditions.not().verify(new Predicate<Rectangle>() {
            @Override
            public boolean apply(final Rectangle input) {
                return input.equals(new Rectangle(1, 2, 3, 4));
            }
        })).isTrue();

        assertThat(rectConditions.x(1)).isTrue();
        assertThat(rectConditions.x(2)).isFalse();
        assertThat(rectConditions.x(3)).isFalse();
        assertThat(rectConditions.x(4)).isFalse();

        assertThat(rectConditions.x().equalTo(1)).isTrue();
        assertThat(rectConditions.x().equalTo(2)).isFalse();
        assertThat(rectConditions.x().equalTo(3)).isFalse();
        assertThat(rectConditions.x().equalTo(4)).isFalse();

        assertThat(rectConditions.x(10)).isTrue();
        assertThat(rectConditions.x(20)).isFalse();
        assertThat(rectConditions.x(30)).isFalse();
        assertThat(rectConditions.x(40)).isFalse();

        assertThat(rectConditions.x(100)).isFalse();
        assertThat(rectConditions.x(200)).isFalse();
        assertThat(rectConditions.x(300)).isFalse();
        assertThat(rectConditions.x(400)).isFalse();

        assertThat(rectConditions.y(1)).isFalse();
        assertThat(rectConditions.y(2)).isTrue();
        assertThat(rectConditions.y(3)).isFalse();
        assertThat(rectConditions.y(4)).isFalse();

        assertThat(rectConditions.y().equalTo(1)).isFalse();
        assertThat(rectConditions.y().equalTo(2)).isTrue();
        assertThat(rectConditions.y().equalTo(3)).isFalse();
        assertThat(rectConditions.y().equalTo(4)).isFalse();

        assertThat(rectConditions.y(10)).isFalse();
        assertThat(rectConditions.y(20)).isTrue();
        assertThat(rectConditions.y(30)).isFalse();
        assertThat(rectConditions.y(40)).isFalse();

        assertThat(rectConditions.y(100)).isFalse();
        assertThat(rectConditions.y(200)).isFalse();
        assertThat(rectConditions.y(300)).isFalse();
        assertThat(rectConditions.y(400)).isFalse();

        assertThat(rectConditions.height(1)).isFalse();
        assertThat(rectConditions.height(2)).isFalse();
        assertThat(rectConditions.height(3)).isTrue();
        assertThat(rectConditions.height(4)).isFalse();

        assertThat(rectConditions.height().equalTo(1)).isFalse();
        assertThat(rectConditions.height().equalTo(2)).isFalse();
        assertThat(rectConditions.height().equalTo(3)).isTrue();
        assertThat(rectConditions.height().equalTo(4)).isFalse();

        assertThat(rectConditions.height(10)).isFalse();
        assertThat(rectConditions.height(20)).isFalse();
        assertThat(rectConditions.height(30)).isTrue();
        assertThat(rectConditions.height(40)).isFalse();

        assertThat(rectConditions.height(100)).isFalse();
        assertThat(rectConditions.height(200)).isFalse();
        assertThat(rectConditions.height(300)).isFalse();
        assertThat(rectConditions.height(400)).isFalse();

        assertThat(rectConditions.width(1)).isFalse();
        assertThat(rectConditions.width(2)).isFalse();
        assertThat(rectConditions.width(3)).isFalse();
        assertThat(rectConditions.width(4)).isTrue();

        assertThat(rectConditions.width().equalTo(1)).isFalse();
        assertThat(rectConditions.width().equalTo(2)).isFalse();
        assertThat(rectConditions.width().equalTo(3)).isFalse();
        assertThat(rectConditions.width().equalTo(4)).isTrue();

        assertThat(rectConditions.width(10)).isFalse();
        assertThat(rectConditions.width(20)).isFalse();
        assertThat(rectConditions.width(30)).isFalse();
        assertThat(rectConditions.width(40)).isTrue();

        assertThat(rectConditions.width(100)).isFalse();
        assertThat(rectConditions.width(200)).isFalse();
        assertThat(rectConditions.width(300)).isFalse();
        assertThat(rectConditions.width(400)).isFalse();

        assertThat(rectConditions.position(1, 2)).isTrue();
        assertThat(rectConditions.position(10, 20)).isTrue();
        assertThat(rectConditions.position(100, 200)).isFalse();

        assertThat(rectConditions.dimension(4, 3)).isTrue();
        assertThat(rectConditions.dimension(40, 30)).isTrue();
        assertThat(rectConditions.dimension(400, 300)).isFalse();

        assertThat(rectConditions.positionAndDimension(1, 2, 4, 3)).isTrue();
        assertThat(rectConditions.positionAndDimension(10, 20, 40, 30)).isTrue();
        assertThat(rectConditions.positionAndDimension(100, 200, 400, 300)).isFalse();
    }

}

