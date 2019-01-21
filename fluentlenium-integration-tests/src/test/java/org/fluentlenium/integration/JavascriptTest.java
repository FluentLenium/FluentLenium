package org.fluentlenium.integration;

import org.assertj.core.api.Assertions;
import org.fluentlenium.core.script.FluentJavascript;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.with;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JavascriptTest extends IntegrationFluentTest {

    @BeforeEach
    void before() {
        goTo(JAVASCRIPT_URL);
        setScreenshotMode(TriggerMode.MANUAL);
        setHtmlDumpMode(TriggerMode.MANUAL);
    }

    @Test
    void checkTextParam() {
        assertThat(find("span", with("id").equalTo("default")).first().text()).isEqualTo("unchanged");

        assertThat(find("#default").first().text()).isEqualTo("unchanged");
        executeScript("change();");
        assertThat(find("#default").first().text()).isEqualTo("changed");
    }

    @Test
    void shouldExecuteScriptReturnString() {
        String script = "return 'string';";
        FluentJavascript fluentJavascript = executeScript(script);
        assertThat(fluentJavascript.isBooleanResult()).isFalse();
        assertThat(fluentJavascript.isDoubleResult()).isFalse();
        assertThat(fluentJavascript.isLongResult()).isFalse();
        assertThat(fluentJavascript.isListResult()).isFalse();
        assertThat(fluentJavascript.isStringResult()).isTrue();

        assertThat((Object) fluentJavascript.getStringResult()).isEqualTo(fluentJavascript.getResult());
        assertThat(fluentJavascript.getStringResult()).isEqualTo("string");
    }

    @Test
    void shouldExecuteScriptReturnBoolean() {
        String script = "return true;";
        FluentJavascript fluentJavascript = executeScript(script);
        assertThat(fluentJavascript.isBooleanResult()).isTrue();
        assertThat(fluentJavascript.isDoubleResult()).isFalse();
        assertThat(fluentJavascript.isLongResult()).isFalse();
        assertThat(fluentJavascript.isListResult()).isFalse();
        assertThat(fluentJavascript.isStringResult()).isFalse();

        assertThat((Object) fluentJavascript.getBooleanResult()).isEqualTo(fluentJavascript.getResult());
        assertThat(fluentJavascript.getBooleanResult()).isTrue();
    }

    @Test
    void shouldFailFastForNonExistingArguments() {
        assertThrows(NoSuchElementException.class,
                () -> {
                    String script = "arguments[0].click()";
                    executeScript(script, find(".notExistingOne").first().getElement());
                });
         }

    @Test
    void shouldExecuteScriptReturnDouble() {
        String script = "return 12.12;";
        FluentJavascript fluentJavascript = executeScript(script);
        assertThat(fluentJavascript.isBooleanResult()).isFalse();
        assertThat(fluentJavascript.isDoubleResult()).isTrue();
        assertThat(fluentJavascript.isLongResult()).isFalse();
        assertThat(fluentJavascript.isListResult()).isFalse();
        assertThat(fluentJavascript.isStringResult()).isFalse();

        assertThat((Object) fluentJavascript.getDoubleResult()).isEqualTo(fluentJavascript.getResult());
        assertThat(fluentJavascript.getDoubleResult()).isEqualTo(12.12D);
    }

    @Test
    void shouldExecuteScriptReturnLong() {
        String script = "return 5;";
        FluentJavascript fluentJavascript = executeScript(script);
        assertThat(fluentJavascript.isBooleanResult()).isFalse();
        assertThat(fluentJavascript.isDoubleResult()).isFalse();
        assertThat(fluentJavascript.isLongResult()).isTrue();
        assertThat(fluentJavascript.isListResult()).isFalse();
        assertThat(fluentJavascript.isStringResult()).isFalse();

        assertThat((Object) fluentJavascript.getLongResult()).isEqualTo(fluentJavascript.getResult());
        assertThat(fluentJavascript.getLongResult()).isEqualTo(5L);
    }

    @Test
    void shouldExecuteScriptReturnList() {
        String script = "return Array('string 1', 'string 2', 5, 12.12, true, Array('test 1', 'test 2'));";
        FluentJavascript fluentJavascript = executeScript(script);
        assertThat(fluentJavascript.isBooleanResult()).isFalse();
        assertThat(fluentJavascript.isDoubleResult()).isFalse();
        assertThat(fluentJavascript.isLongResult()).isFalse();
        assertThat(fluentJavascript.isListResult()).isTrue();
        assertThat(fluentJavascript.isStringResult()).isFalse();

        assertThat((Object) fluentJavascript.getListResult()).isEqualTo(fluentJavascript.getResult());
        Assertions.<Object>assertThat(fluentJavascript.getListResult())
                .containsExactly("string 1", "string 2", 5L, 12.12D, true, Arrays.asList("test 1", "test 2"));
        Assertions.<Object>assertThat(fluentJavascript.getListResult())
                .containsExactly("string 1", "string 2", 5L, 12.12D, true, Arrays.asList("test 1", "test 2"));
    }

    @Test
    void shouldExecuteAsyncScriptReturnString() {
        getDriver().manage().timeouts().setScriptTimeout(200, TimeUnit.MILLISECONDS);

        long start = System.nanoTime();

        FluentJavascript fluentJavascript = executeAsyncScript("window.setTimeout(arguments[arguments.length - 1], 100);");
        assertThat(System.nanoTime() - start).isGreaterThanOrEqualTo(100000000);
        assertThat(fluentJavascript.getResult()).isNull();
    }
}
