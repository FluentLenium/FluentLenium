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
    public void testInteger() {
        when(element.getRect()).thenReturn(new Rectangle(1, 2, 100, 200));

        final RectangleConditions rectangleConditions = new FluentWaitElementMatcher(search, wait, fluentWebElement).rectangle();

        rectangleConditions.verify(new Predicate<Rectangle>() {
            @Override
            public boolean apply(Rectangle input) {
                return true;
            }
        });

        rectangleConditions.withX().equalTo(1);
        rectangleConditions.withX().not().equalTo(2);

        rectangleConditions.withX().greaterThanOrEqualTo(1);
        rectangleConditions.withX().not().greaterThanOrEqualTo(2);

        rectangleConditions.withX().greaterThan(0);
        rectangleConditions.withX().not().greaterThan(1);

        rectangleConditions.withX().lessThanOrEqualTo(1);
        rectangleConditions.withX().not().lessThanOrEqualTo(0);

        rectangleConditions.withX().lessThan(2);
        rectangleConditions.withX().not().lessThan(1);

        rectangleConditions.withX().verify(new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input == 1;
            }
        });

        rectangleConditions.withX().not().verify(new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input != 1;
            }
        });

        rectangleConditions.not().withX().not().verify(new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input == 1;
            }
        });

    }
}
