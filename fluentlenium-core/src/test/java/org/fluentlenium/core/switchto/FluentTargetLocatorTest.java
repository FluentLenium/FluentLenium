package org.fluentlenium.core.switchto;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FluentTargetLocatorTest {
    private final Object self = new Object();

    @Mock
    private FluentControl control;

    private ComponentInstantiator instantiator;

    @Mock
    private WebDriver.TargetLocator targetLocator;

    private FluentTargetLocatorImpl fluentTargetLocator;

    @Before
    public void before() {
        instantiator = new DefaultComponentInstantiator(control);
        fluentTargetLocator = new FluentTargetLocatorImpl<>(self, instantiator, targetLocator);
    }

    @Test
    public void frameIndex() {
        assertThat(fluentTargetLocator.frame(3)).isSameAs(self);
        verify(targetLocator).frame(3);
    }

    @Test
    public void frameName() {
        assertThat(fluentTargetLocator.frame("name")).isSameAs(self);
        verify(targetLocator).frame("name");
    }

    @Test
    public void frameElement() {
        WebElement webElement = mock(WebElement.class);
        assertThat(fluentTargetLocator.frame(webElement)).isSameAs(self);
        verify(targetLocator).frame(webElement);
    }

    @Test
    public void frameFluentElement() {
        FluentWebElement fluentWebElement = mock(FluentWebElement.class);
        WebElement element = mock(WebElement.class);
        when(fluentWebElement.getElement()).thenReturn(element);
        assertThat(fluentTargetLocator.frame(fluentWebElement)).isSameAs(self);
        verify(targetLocator).frame(element);
    }

    @Test
    public void parentFrame() {
        assertThat(fluentTargetLocator.parentFrame()).isSameAs(self);
        verify(targetLocator).parentFrame();
    }

    @Test
    public void windowName() {
        assertThat(fluentTargetLocator.window("name")).isSameAs(self);
        verify(targetLocator).window("name");
    }

    @Test
    public void defaultContent() {
        assertThat(fluentTargetLocator.defaultContent()).isSameAs(self);
        verify(targetLocator).defaultContent();
    }

    @Test
    public void activeElement() {
        WebElement element = mock(WebElement.class);

        when(targetLocator.activeElement()).thenReturn(element);
        FluentWebElement activeElement = fluentTargetLocator.activeElement();

        assertThat(activeElement).isNotNull();
        assertThat(activeElement.getElement()).isSameAs(element);
    }

    @Test
    public void alert() {
        Alert alertMock = mock(Alert.class);
        when(targetLocator.alert()).thenReturn(alertMock);

        fluentTargetLocator.alert();
        verify(targetLocator).alert();
    }

}
