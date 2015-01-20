/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package org.fluentlenium.integration;


import com.google.common.base.Stopwatch;
import java.util.concurrent.TimeUnit;
import org.fluentlenium.core.domain.FluentJavascript;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.with;

public class JavascriptTest extends LocalFluentCase {

  @Before
  public void setup() {
    goTo(JAVASCRIPT_URL);
    setSnapshotMode(Mode.NEVER_TAKE_SNAPSHOT);
  }

  @Test
  public void checkTextParam() {
    assertThat(find("span", with("id").equalTo("default")).first().getText()).isEqualTo("unchanged");

    assertThat(find("#default").first().getText()).isEqualTo("unchanged");
    executeScript("change();");
    assertThat(find("#default").first().getText()).isEqualTo("changed");
  }

  @Test
  public void should_executeScript_return_String() {
    final String script = "return 'string';";
    final FluentJavascript fluentJavascript = executeScript(script);
    assertThat(fluentJavascript.getScript()).isEqualTo(script);
    assertThat(fluentJavascript.isBooleanResult()).isFalse();
    assertThat(fluentJavascript.isDoubleResult()).isFalse();
    assertThat(fluentJavascript.isLongResult()).isFalse();
    assertThat(fluentJavascript.isListResult()).isFalse();
    assertThat(fluentJavascript.isStringResult()).isTrue();

    assertThat((Object) fluentJavascript.getStringResult()).isEqualTo(fluentJavascript.getResult());
    assertThat(fluentJavascript.getStringResult()).isEqualTo("string");
  }

  @Test
  public void should_executeScript_return_Boolean() {
    final String script = "return true;";
    final FluentJavascript fluentJavascript = executeScript(script);
    assertThat(fluentJavascript.getScript()).isEqualTo(script);
    assertThat(fluentJavascript.isBooleanResult()).isTrue();
    assertThat(fluentJavascript.isDoubleResult()).isFalse();
    assertThat(fluentJavascript.isLongResult()).isFalse();
    assertThat(fluentJavascript.isListResult()).isFalse();
    assertThat(fluentJavascript.isStringResult()).isFalse();

    assertThat((Object) fluentJavascript.getBooleanResult()).isEqualTo(fluentJavascript.getResult());
    assertThat(fluentJavascript.getBooleanResult()).isTrue();
  }

  @Test
  public void should_executeScript_return_Double() {
    final String script = "return 12.12;";
    final FluentJavascript fluentJavascript = executeScript(script);
    assertThat(fluentJavascript.getScript()).isEqualTo(script);
    assertThat(fluentJavascript.isBooleanResult()).isFalse();
    assertThat(fluentJavascript.isDoubleResult()).isTrue();
    assertThat(fluentJavascript.isLongResult()).isFalse();
    assertThat(fluentJavascript.isListResult()).isFalse();
    assertThat(fluentJavascript.isStringResult()).isFalse();

    assertThat((Object) fluentJavascript.getDoubleResult()).isEqualTo(fluentJavascript.getResult());
    assertThat(fluentJavascript.getDoubleResult()).isEqualTo(12.12D);
  }

  @Test
  public void should_executeScript_return_Long() {
    final String script = "return 5;";
    final FluentJavascript fluentJavascript = executeScript(script);
    assertThat(fluentJavascript.getScript()).isEqualTo(script);
    assertThat(fluentJavascript.isBooleanResult()).isFalse();
    assertThat(fluentJavascript.isDoubleResult()).isFalse();
    assertThat(fluentJavascript.isLongResult()).isTrue();
    assertThat(fluentJavascript.isListResult()).isFalse();
    assertThat(fluentJavascript.isStringResult()).isFalse();

    assertThat((Object) fluentJavascript.getLongResult()).isEqualTo(fluentJavascript.getResult());
    assertThat(fluentJavascript.getLongResult()).isEqualTo(5L);
  }

  @Test
  public void should_executeScript_return_List() {
    final String script = "return Array('string');";
    final FluentJavascript fluentJavascript = executeScript(script);
    assertThat(fluentJavascript.getScript()).isEqualTo(script);
    assertThat(fluentJavascript.isBooleanResult()).isFalse();
    assertThat(fluentJavascript.isDoubleResult()).isFalse();
    assertThat(fluentJavascript.isLongResult()).isFalse();
    assertThat(fluentJavascript.isListResult()).isTrue();
    assertThat(fluentJavascript.isStringResult()).isFalse();

    assertThat((Object) fluentJavascript.getListResult()).isEqualTo(fluentJavascript.getResult());
    assertThat(fluentJavascript.getListResult()).containsExactly("string");
  }


  @Test
  public void should_executeAsyncScript_return_String() {
    getDriver().manage().timeouts().setScriptTimeout(100, TimeUnit.MILLISECONDS);

    final Stopwatch stopwatch = Stopwatch.createStarted();
    final FluentJavascript fluentJavascript = executeAsyncScript(
        "window.setTimeout(arguments[arguments.length - 1], 100);");
    assertThat(stopwatch.elapsed(TimeUnit.MILLISECONDS)).isGreaterThanOrEqualTo(100);
    assertThat(fluentJavascript.getResult()).isNull();
  }
}
