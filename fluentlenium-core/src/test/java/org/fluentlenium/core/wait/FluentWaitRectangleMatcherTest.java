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
public class FluentWaitRectangleMatcherTest {
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
    public void testRectangle() {
        when(element.getRect()).thenReturn(new Rectangle(1, 2, 100, 200));

        final RectangleConditions rectangleConditions = wait.until(fluentWebElement).rectangle();

        rectangleConditions.verify(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return true;
            }
        });

        rectangleConditions.x(1);
        rectangleConditions.y(2);

        rectangleConditions.x().equalTo(1);
        rectangleConditions.y().equalTo(2);

        rectangleConditions.height(100);
        rectangleConditions.height().equalTo(100);

        rectangleConditions.width(200);
        rectangleConditions.width().equalTo(200);

        rectangleConditions.position(1, 2);
        rectangleConditions.dimension(200, 100);
        rectangleConditions.positionAndDimension(1, 2, 200, 100);

        rectangleConditions.not().verify(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return false;
            }
        });

        rectangleConditions.not().x(3);
        rectangleConditions.not().y(4);

        rectangleConditions.not().x().equalTo(3);
        rectangleConditions.not().y().equalTo(4);

        rectangleConditions.not().height(300);
        rectangleConditions.not().height().equalTo(400);

        rectangleConditions.not().width(300);
        rectangleConditions.not().width().equalTo(400);

        rectangleConditions.not().position(3, 4);
        rectangleConditions.not().dimension(400, 300);
        rectangleConditions.not().positionAndDimension(3, 4, 400, 300);

    }
}
