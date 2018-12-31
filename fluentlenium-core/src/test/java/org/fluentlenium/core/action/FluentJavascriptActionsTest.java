package org.fluentlenium.core.action;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.script.JavascriptControl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FluentJavascriptActionsTest {

    private final Object self = new Object();

    @Mock
    private JavascriptControl javascript;

    @Mock
    private FluentWebElement fluentWebElement;

    @Mock
    private WebElement element;

    FluentJavascriptActions actions;

    @Before
    public void before() {
        when(fluentWebElement.getElement()).thenReturn(element);
        when(fluentWebElement.getElement().getLocation()).thenReturn(new Point(1024, 768));
        actions = new FluentJavascriptActionsImpl(self, javascript, () -> fluentWebElement);
    }

    @Test
    public void testWithNoArgument() {
        actions.scrollIntoView();
        verify(javascript).executeScript("arguments[0].scrollIntoView();", element);
    }

    @Test
    public void testWithArgument() {
        actions.scrollIntoView(true);
        verify(javascript).executeScript("arguments[0].scrollIntoView(arguments[1]);", element, true);
    }

    @Test
    public void testToCenter() {
        actions.scrollToCenter();
        verify(javascript).executeScript("window.scrollTo(0,768 - window.innerHeight / 2)");
    }
}
