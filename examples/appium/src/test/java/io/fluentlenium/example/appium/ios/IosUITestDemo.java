package org.fluentlenium.example.appium.ios;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.example.appium.ExampleFluentTest;
import org.fluentlenium.example.appium.app.ios.AboutPage;
import org.fluentlenium.example.appium.app.ios.HomePage;
import org.junit.Test;

public class IosUITestDemo extends ExampleFluentTest {

    @Page
    private HomePage homePage;

    @Page
    private AboutPage aboutPage;

    @Test
    public void shouldCorrectlySwitchView() {
        homePage.clickAboutLink().verifyIfIsLoaded();
    }

    @Test
    public void shouldCorrectlyAddNote() {
        String noteName = "Sample Note";
        String noteDescription = "SampleNoteDescription";

        aboutPage.clickBrowseButton();

        homePage
                .clickAddButton()
                .addName(noteName, noteDescription)
                .clickAboutLink()
                .verifyIfIsLoaded();
    }

}
