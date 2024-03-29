package io.fluentlenium.example.appium.android;

import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.example.appium.ExampleFluentTest;
import io.fluentlenium.example.appium.app.android.SwiftNoteHomePage;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AndroidSwiftNotesApp extends ExampleFluentTest {

    private static final String SAMPLE_TITLE = "SampleTitle";
    private static final String SAMPLE_BODY = "SampleBody";

    @Page
    private SwiftNoteHomePage noteApp;

    @Test
    public void verifyAndroidSdkConfiguration() {
        // sdk must be available and environment variable configured. see pom.xml
        assertThat(System.getenv("ANDROID_HOME")).isNotNull();
    }

    @Test
    public void shouldCorrectlyAddNote() {
        noteApp
                .verifyIfIsLoaded()
                .verifyNoteCount(0)
                .clickAddNote()
                .addNote(SAMPLE_TITLE, SAMPLE_BODY)
                .verifyIfIsLoaded()
                .verifyNoteCount(1)
                .clickAddNote()
                .addNoteUsingAdb(SAMPLE_TITLE, SAMPLE_BODY)
                .verifyIfIsLoaded()
                .verifyNoteCount(2);
    }

    @Test
    public void searchTest() {
        noteApp.search("FluentLenium");
    }

    @Test
    public void screenshotTest() {
        takeScreenshot();
    }

}

