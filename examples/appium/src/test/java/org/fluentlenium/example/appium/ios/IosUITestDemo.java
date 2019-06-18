package org.fluentlenium.example.appium.ios;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.example.appium.ExampleFluentTest;
import org.fluentlenium.example.appium.app.ios.UITestDemoAppHomePage;
import org.junit.Test;

public class IosUITestDemo extends ExampleFluentTest {

    @Page
    private UITestDemoAppHomePage UITestDemoAppHomePage;

    @Test
    public void shouldCorrectlyAdd() {
        UITestDemoAppHomePage.clickAboutLink().verifyIfIsLoaded();
    }

}
