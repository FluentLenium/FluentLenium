package org.fluentlenium.assertj.custom;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.core.conditions.WebElementConditions;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RectangleAssertTest {

    @Mock
    private FluentWebElement element;

    @Mock
    private WebElement webElement;

    @Mock
    private Rectangle rectangle;

    @Before
    public void before() {
        when(element.conditions()).thenReturn(new WebElementConditions(element));
        when(element.getElement()).thenReturn(webElement);
        when(webElement.getRect()).thenReturn(rectangle);
    }

    @Test
    public void withX() {
        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                assertThat(element).hasRectangle().withX(1);
            }
        }).hasMessage("element rectangle has x != 1");

        when(rectangle.getX()).thenReturn(1);
        assertThat(element).hasRectangle().withX(1);
    }

}
