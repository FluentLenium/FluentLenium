package org.fluentlenium.integration;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.fluentlenium.core.script.FluentJavascript;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.with;

public class JavascriptTest extends IntegrationFluentTest {

    @Before
    public void before() {
        goTo(JAVASCRIPT_URL);
        setScreenshotMode(TriggerMode.MANUAL);
        setHtmlDumpMode(TriggerMode.MANUAL);
    }

    @Test
    public void checkTextParam() {
        assertThat(find("span", with("id").equalTo("default")).first().text()).isEqualTo("unchanged");

        assertThat(find("#default").first().text()).isEqualTo("unchanged");
        executeScript("change();");
        assertThat(find("#default").first().text()).isEqualTo("changed");
    }

    @Test
    public void shouldExecuteScriptReturnString() {
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
    public void shouldExecuteScriptReturnBoolean() {
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
    public void shouldExecuteScriptReturnDouble() {
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
    public void shouldExecuteScriptReturnLong() {
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
    public void shouldExecuteScriptReturnList() {
        String script = "return Array('string 1', 'string 2', 5, 12.12, true, Array('test 1', 'test 2'));";
        FluentJavascript fluentJavascript = executeScript(script);
        assertThat(fluentJavascript.isBooleanResult()).isFalse();
        assertThat(fluentJavascript.isDoubleResult()).isFalse();
        assertThat(fluentJavascript.isLongResult()).isFalse();
        assertThat(fluentJavascript.isListResult()).isTrue();
        assertThat(fluentJavascript.isStringResult()).isFalse();

        assertThat((Object) fluentJavascript.getListResult()).isEqualTo(fluentJavascript.getResult());
        assertThat(fluentJavascript.getListResult())
                .containsExactly("string 1", "string 2", 5L, 12.12D, true, Lists.newArrayList("test 1", "test 2"));
    }

    @Test
    public void shouldExecuteAsyncScriptReturnString() {
        getDriver().manage().timeouts().setScriptTimeout(200, TimeUnit.MILLISECONDS);

        Stopwatch stopwatch = Stopwatch.createStarted();
        FluentJavascript fluentJavascript = executeAsyncScript("window.setTimeout(arguments[arguments.length - 1], 100);");
        assertThat(stopwatch.elapsed(TimeUnit.MILLISECONDS)).isGreaterThanOrEqualTo(100);
        assertThat(fluentJavascript.getResult()).isNull();
    }
}
