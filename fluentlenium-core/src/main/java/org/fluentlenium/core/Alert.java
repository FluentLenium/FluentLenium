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
   * @throws org.openqa.selenium.NoAlertPresentException if there is currently no alert box
   */
  public void switchTo() {
    webDriver.switchTo().alert();
  }

}
