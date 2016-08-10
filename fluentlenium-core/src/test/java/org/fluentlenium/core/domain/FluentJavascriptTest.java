package org.fluentlenium.core.domain;


import org.fluentlenium.core.script.FluentJavascript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;


public class FluentJavascriptTest {
    @Mock
    private JavascriptWebDriver driver;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

    }

    @After
    public void after() {
        reset(driver);
    }


    @Test
    public void testSync() {
        String scriptRet = "value";

        when(driver.executeScript("script", "arg1", "arg2")).thenReturn(scriptRet);
        FluentJavascript fluentJavascript = new FluentJavascript(driver, false, "script", "arg1", "arg2");

        assertThat(fluentJavascript.isStringResult()).isTrue();
        assertThat(fluentJavascript.getStringResult()).isEqualTo(scriptRet);
        assertThat(fluentJavascript.getResult()).isEqualTo(scriptRet);

        Boolean booleanRet = true;

        when(driver.executeScript("script", "arg1", "arg2")).thenReturn(booleanRet);
        fluentJavascript = new FluentJavascript(driver, false, "script", "arg1", "arg2");

        assertThat(fluentJavascript.getBooleanResult()).isEqualTo(booleanRet);
        assertThat(fluentJavascript.isBooleanResult()).isTrue();

        Long longRet = 1L;

        when(driver.executeScript("script", "arg1", "arg2")).thenReturn(longRet);
        fluentJavascript = new FluentJavascript(driver, false, "script", "arg1", "arg2");

        assertThat(fluentJavascript.getLongResult()).isEqualTo(longRet);
        assertThat(fluentJavascript.isLongResult()).isTrue();

        Double doubleRet = 1.5;

        when(driver.executeScript("script", "arg1", "arg2")).thenReturn(doubleRet);
        fluentJavascript = new FluentJavascript(driver, false, "script", "arg1", "arg2");

        assertThat(fluentJavascript.getDoubleResult()).isEqualTo(doubleRet);
        assertThat(fluentJavascript.isDoubleResult()).isTrue();

        List<String> listRet = Collections.emptyList();

        when(driver.executeScript("script", "arg1", "arg2")).thenReturn(listRet);
        fluentJavascript = new FluentJavascript(driver, false, "script", "arg1", "arg2");

        assertThat(fluentJavascript.getListResult()).isEqualTo(listRet);
        assertThat(fluentJavascript.getListResult(String.class)).isEqualTo(listRet);
        assertThat(fluentJavascript.isListResult()).isTrue();
    }

    @Test
    public void testAsync() {
        String scriptRet = "value";

        when(driver.executeAsyncScript("script", "arg1", "arg2")).thenReturn(scriptRet);
        FluentJavascript fluentJavascript = new FluentJavascript(driver, true, "script", "arg1", "arg2");

        assertThat(fluentJavascript.getStringResult()).isEqualTo(scriptRet);
        assertThat(fluentJavascript.getResult()).isEqualTo(scriptRet);
    }

    private static abstract class JavascriptWebDriver implements WebDriver, JavascriptExecutor {

    }
}
