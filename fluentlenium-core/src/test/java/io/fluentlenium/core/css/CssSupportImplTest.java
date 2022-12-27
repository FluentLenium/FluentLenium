package io.fluentlenium.core.css;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import io.fluentlenium.core.script.FluentJavascript;import io.fluentlenium.core.script.JavascriptControl;import io.fluentlenium.core.wait.AwaitControl;import io.fluentlenium.core.wait.FluentWait;import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebDriverException;

import io.fluentlenium.core.script.FluentJavascript;
import io.fluentlenium.core.script.JavascriptControl;
import io.fluentlenium.core.wait.AwaitControl;
import io.fluentlenium.core.wait.FluentWait;

/**
 * Unit test for {@link CssSupportImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class CssSupportImplTest {

    private static final String CSS_TEXT = "some: css";

    @Mock
    private JavascriptControl javascriptControl;
    @Mock
    private AwaitControl awaitControl;
    @Mock
    private FluentJavascript fluentJavascript;
    @Mock
    private FluentWait fluentWait;
    @Mock
    private FluentWait fluentWaitExplicit;

    private CssSupportImpl cssSupport;

    @Before
    public void setup() {
        when(awaitControl.await()).thenReturn(fluentWait);
        when(fluentWait.explicitlyFor(anyLong())).thenReturn(fluentWaitExplicit);

        cssSupport = new CssSupportImpl(javascriptControl, awaitControl);
    }

    @Test
    public void shouldInjectCss() {
        when(javascriptControl.executeScript(anyString())).thenReturn(fluentJavascript);

        cssSupport.inject(CSS_TEXT);

        verify(javascriptControl, times(1)).executeScript(startsWith("cssText = \"some: css\";"));
        verifyNoMoreInteractions(javascriptControl);
    }

    @Test
    public void shouldInjectUnescapedCss() {
        when(javascriptControl.executeScript(anyString())).thenReturn(fluentJavascript);

        cssSupport.inject("#location {\ndisplay: none\n}");

        verify(javascriptControl, times(1)).executeScript(startsWith("cssText = \"#location {display: none}\";"));
        verifyNoMoreInteractions(javascriptControl);
    }

    @Test
    @Ignore("Find a way to mock IOUtils.toString() to throw exception")
    public void shouldThrowIOErrorDuringInjectingCss() {
    }

    @Test
    public void shouldInjectResource() {
        when(javascriptControl.executeScript(anyString())).thenReturn(fluentJavascript);

        cssSupport.injectResource("/org/fluentlenium/core/css/dummy_resource.js");

        verify(javascriptControl, times(1)).executeScript(startsWith("cssText = \"dummy: content\";"));
        verifyNoMoreInteractions(javascriptControl);
    }

    @Test
    @Ignore("Find a way to mock IOUtils.toString() to throw exception")
    public void shouldThrowIOErrorDuringInjectingResource() {
    }

    @Test
    public void shouldThrowNPEIfResourceIsNotFound() {
        String cssResourceName = "/an/invalid/path.js";

        assertThatThrownBy(() -> cssSupport.injectResource(cssResourceName)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowWebDriverExceptionBeforeReachingMaxRetryCount() {
        when(javascriptControl.executeScript(startsWith("cssText = \"some: css\";"))).thenThrow(WebDriverException.class)
                                                                                     .thenReturn(fluentJavascript);

        cssSupport.inject(CSS_TEXT);

        verify(javascriptControl, times(2)).executeScript(startsWith("cssText = \"some: css\";"));
        verify(awaitControl, times(1)).await();
        verifyNoMoreInteractions(javascriptControl);
        verifyNoMoreInteractions(awaitControl);
    }

    @Test
    public void shouldThrowWebDriverExceptionAfterReachingMaxRetryCount() {
        when(javascriptControl.executeScript(startsWith("cssText = \"some: css\";"))).thenThrow(WebDriverException.class);

        assertThatThrownBy(() -> cssSupport.inject(CSS_TEXT)).isInstanceOf(WebDriverException.class);

        verify(javascriptControl, times(10)).executeScript(startsWith("cssText = \"some: css\";"));
        verify(awaitControl, times(9)).await();
        verifyNoMoreInteractions(javascriptControl);
        verifyNoMoreInteractions(awaitControl);
    }
}
