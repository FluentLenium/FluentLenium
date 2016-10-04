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
        getConfiguration().setScreenshotMode(TriggerMode.MANUAL);
        getConfiguration().setHtmlDumpMode(TriggerMode.MANUAL);
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
        final String script = "return 'string';";
        final FluentJavascript fluentJavascript = executeScript(script);
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
        final String script = "return true;";
        final FluentJavascript fluentJavascript = executeScript(script);
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
        final String script = "return 12.12;";
        final FluentJavascript fluentJavascript = executeScript(script);
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
        final String script = "return 5;";
        final FluentJavascript fluentJavascript = executeScript(script);
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
        final String script = "return Array('string 1', 'string 2', 5, 12.12, true, Array('test 1', 'test 2'));";
        final FluentJavascript fluentJavascript = executeScript(script);
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

        final Stopwatch stopwatch = Stopwatch.createStarted();
        final FluentJavascript fluentJavascript = executeAsyncScript("window.setTimeout(arguments[arguments.length - 1], 100);");
        assertThat(stopwatch.elapsed(TimeUnit.MILLISECONDS)).isGreaterThanOrEqualTo(100);
        assertThat(fluentJavascript.getResult()).isNull();
    }
}
