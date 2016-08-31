package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.conditions.RectangleConditions;
import org.fluentlenium.core.conditions.WebElementConditions;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.Search;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class FluentWaitRectangleMatcherTest {
    @Mock
    private Search search;

    @Mock
    private FluentDriver fluent;

    private FluentWait wait;

    @Mock
    private FluentWebElement fluentWebElement;

    @Mock
    private WebElement element;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        wait = new FluentWait(fluent, search);
        wait.atMost(1L, TimeUnit.MILLISECONDS);
        wait.pollingEvery(1L, TimeUnit.MILLISECONDS);

        when(search.getInstantiator()).thenReturn(new DefaultComponentInstantiator(fluent));

        when(fluentWebElement.conditions()).thenReturn(new WebElementConditions(fluentWebElement));
        when(fluentWebElement.getElement()).thenReturn(element);
        when(fluentWebElement.now()).thenReturn(fluentWebElement);
    }

    @After
    public void after() {
        reset(search);
        reset(fluent);
        reset(fluentWebElement);
        reset(element);
    }

    @Test
    public void testRectangle() {
        when(element.getRect()).thenReturn(new Rectangle(1, 2, 100, 200));

        final RectangleConditions rectangleConditions = new FluentWaitElementMatcher(search, wait, fluentWebElement).hasRectangle();

        rectangleConditions.isVerified(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return true;
            }
        });

        rectangleConditions.withX(1);
        rectangleConditions.withY(2);

        rectangleConditions.withX().equalTo(1);
        rectangleConditions.withY().equalTo(2);

        rectangleConditions.withHeight(100);
        rectangleConditions.withHeight().equalTo(100);

        rectangleConditions.withWidth(200);
        rectangleConditions.withWidth().equalTo(200);

        rectangleConditions.withPosition(1, 2);
        rectangleConditions.withDimension(200, 100);
        rectangleConditions.withPositionAndDimension(1, 2, 200, 100);

        rectangleConditions.not().isVerified(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return false;
            }
        });

        rectangleConditions.not().withX(3);
        rectangleConditions.not().withY(4);

        rectangleConditions.not().withX().equalTo(3);
        rectangleConditions.not().withY().equalTo(4);

        rectangleConditions.not().withHeight(300);
        rectangleConditions.not().withHeight().equalTo(400);

        rectangleConditions.not().withWidth(300);
        rectangleConditions.not().withWidth().equalTo(400);

        rectangleConditions.not().withPosition(3, 4);
        rectangleConditions.not().withDimension(400, 300);
        rectangleConditions.not().withPositionAndDimension(3, 4, 400, 300);

    }
}
