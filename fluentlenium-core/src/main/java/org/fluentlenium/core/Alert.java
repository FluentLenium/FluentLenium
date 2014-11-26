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

package org.fluentlenium.core;

import org.openqa.selenium.WebDriver;

/**
 * Util Class for manage alert
 */
public class Alert {

  private WebDriver webDriver;

  public Alert(WebDriver webDriver) {
    this.webDriver = webDriver;
  }

  /**
   * When an alert box pops up, click on "OK"
   */
  public void accept() {
    webDriver.switchTo().alert().accept();
  }

  /**
   * When an alert box pops up, click on "Cancel"
   */
  public void dismiss() {
    webDriver.switchTo().alert().dismiss();
  }

  /**
   * @return text of an alert box
   */
  public String getText() {
      return webDriver.switchTo().alert().getText();
  }

  /**
   * Entering an input value
   *
   * @param s
   *          field to enter
   */
  public void prompt(String s) {
    webDriver.switchTo().alert().sendKeys(s);
    accept();
  }

  /**
   * Switch to an alert box
   * @throws NoAlertPresentException if there is currently no alert box
   */
  public void switchTo() {
    webDriver.switchTo().alert();
  }

}
