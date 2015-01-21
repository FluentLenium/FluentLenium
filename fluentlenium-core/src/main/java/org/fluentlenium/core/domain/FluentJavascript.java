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

package org.fluentlenium.core.domain;

import java.util.Arrays;
import java.util.List;
import org.fluentlenium.core.Fluent;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * Map result of executed javascript.
 *
 * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
 */
public class FluentJavascript extends Fluent {
  private final Object result;
  private final Object[] args;
  private final String script;

  public FluentJavascript(WebDriver driver, boolean async, String script, Object... args) {
    super(driver);
    this.script = script;
    this.args = Arrays.copyOf(args, args.length);
    if (async) {
      this.result = ((JavascriptExecutor) driver).executeAsyncScript(script, args);
    } else {
      this.result = ((JavascriptExecutor) driver).executeScript(script, args);
    }
  }

  /**
   * @return true if the result of javascript execution can be retrieved to String.
   * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
   */
  public boolean isStringResult() {
    return result instanceof String;
  }

  /**
   * @return true if the result of javascript execution can be retrieved to Boolean.
   * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
   */
  public boolean isBooleanResult() {
    return result instanceof Boolean;
  }

  /**
   * @return true if the result of javascript execution can be retrieved to Double.
   * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
   */
  public boolean isDoubleResult() {
    return result instanceof Double;
  }

  /**
   * @return true if the result of javascript execution can be retrieved to Double.
   * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
   */
  public boolean isLongResult() {
    return result instanceof Long;
  }

  /**
   * @return true if the result of javascript execution can be retrieved to Double.
   * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
   */
  public boolean isListResult() {
    return result instanceof List;
  }

  /**
   * @return the result of javascript execution.
   * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
   */
  public Object getResult() {
    return result;
  }

  /**
   * @return the result of javascript execution cast as Double.
   * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
   */
  public Double getDoubleResult() {
    return (Double) result;
  }

  /**
   * @return the result of javascript execution cast as Long.
   * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
   */
  public Boolean getBooleanResult() {
    return (Boolean) result;
  }

  /**
   * @return the result of javascript execution cast as Long.
   * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
   */
  public Long getLongResult() {
    return (Long) result;
  }

  /**
   * @return the result of javascript execution cast as String.
   * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
   */
  public String getStringResult() {
    return (String) result;
  }

  /**
   * @return the result of javascript execution cast as List.
   * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object...)
   */
  public List getListResult() {
    return (List) result;
  }

  /**
   * @return script executed by the caller.
   */
  public String getScript() {
    return script;
  }

  /**
   * @return args used by the caller.
   */
  public Object[] getArgs() {
    return args;
  }
}
