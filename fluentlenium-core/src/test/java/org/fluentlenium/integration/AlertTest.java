/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License
 */
package org.fluentlenium.integration;

import static org.fest.assertions.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.fluentlenium.integration.localtest.SauceLabsFluentCase;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AlertTest extends SauceLabsFluentCase {

  @Page
  private AlertPage alertPage;

  @Override
  public WebDriver getDefaultDriver() {
    return new FirefoxDriver();
  }

  @Override
  public void setDefaultConfig() {
    withDefaultSearchWait(5, TimeUnit.HOURS);
  }

  @Test
  public void should_accept() {
    // Given
    // When
    alertPage.go();
    alertPage.maximizeWindow();
    // Then
    alertPage.isAt();
    click("#alertBox");
    acceptAlert();
    assertThat($("#result").getText()).isEqualTo("alertBox");

  }

  @Test
  public void should_confirm() {
    // Given
    // When
    alertPage.go();
    alertPage.maximizeWindow();
    // Then
    alertPage.isAt();
    click("#confirmBox");
    acceptAlert();
    assertThat($("#result").getText()).isEqualTo("confirmBox OK");

  }

  @Test
  public void should_dismiss() {
    // Given
    // When
    alertPage.go();
    alertPage.maximizeWindow();
    // Then
    alertPage.isAt();
    click("#confirmBox");
    dismissAlert();
    assertThat($("#result").getText()).isEqualTo("confirmBox CANCEL");

  }

  @Test
  public void should_prompt() {
    // Given
    // When
    alertPage.go();
    alertPage.maximizeWindow();
    // Then
    alertPage.isAt();
    click("#promptBox");
    promptAlert("it works");
    assertThat($("#result").getText()).isEqualTo("it works");

  }

}

class AlertPage extends FluentPage {
  @Override
  public String getUrl() {
    return LocalFluentCase.DEFAULT_URL + "alert.html";
  }

  @Override
  public void isAt() {
    assertThat($("#result").first().getText()).isEmpty();
  }
}
