package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.conditions.RectangleConditions;
import org.fluentlenium.core.conditions.WebElementConditions;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FluentWaitIntegerMatcherTest {
    @Mock
    private FluentDriver fluent;

    private FluentWait wait;

    @Mock
    private FluentWebElement fluentWebElement;

    @Mock
    private WebElement element;

    @Before
    public void before() {
        wait = new FluentWait(fluent);
        wait.atMost(1L, TimeUnit.MILLISECONDS);
        wait.pollingEvery(1L, TimeUnit.MILLISECONDS);

        when(fluentWebElement.conditions()).thenReturn(new WebElementConditions(fluentWebElement));
        when(fluentWebElement.getElement()).thenReturn(element);
        when(fluentWebElement.now()).thenReturn(fluentWebElement);
    }

    @After
    public void after() {
        reset(fluent);
        reset(fluentWebElement);
        reset(element);
    }

    @Test
    public void testInteger() {
        when(element.getRect()).thenReturn(new Rectangle(1, 2, 100, 200));

        final RectangleConditions rectangleConditions = wait.until(fluentWebElement).rectangle();

        rectangleConditions.verify(new Predicate<Rectangle>() {
            @Override
            public boolean apply(final Rectangle input) {
                return true;
            }
        });

        rectangleConditions.x().equalTo(1);
        rectangleConditions.x().not().equalTo(2);

        rectangleConditions.x().greaterThanOrEqualTo(1);
        rectangleConditions.x().not().greaterThanOrEqualTo(2);

        rectangleConditions.x().greaterThan(0);
        rectangleConditions.x().not().greaterThan(1);

        rectangleConditions.x().lessThanOrEqualTo(1);
        rectangleConditions.x().not().lessThanOrEqualTo(0);

        rectangleConditions.x().lessThan(2);
        rectangleConditions.x().not().lessThan(1);

        rectangleConditions.x().verify(new Predicate<Integer>() {
            @Override
            public boolean apply(final Integer input) {
                return input == 1;
            }
        });

        rectangleConditions.x().not().verify(new Predicate<Integer>() {
            @Override
            public boolean apply(final Integer input) {
                return input != 1;
            }
        });

        rectangleConditions.not().x().not().verify(new Predicate<Integer>() {
            @Override
            public boolean apply(final Integer input) {
                return input == 1;
            }
        });

    }
}
