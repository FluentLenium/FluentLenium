package io.fluentlenium.example.appium.app.android;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.domain.FluentWebElement;

import java.util.concurrent.TimeUnit;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class SwiftNoteHomePage extends FluentPage {


    @AndroidFindBy(id = "action_search")
    private FluentWebElement searchButton;

    @AndroidFindBy(id = "com.moonpi.swiftnotes:id/newNote")
    private FluentWebElement plusButton;

    public SwiftNoteAddPage clickAddNote() {
        plusButton.click();
        return newInstance(SwiftNoteAddPage.class);
    }

    public SwiftNoteHomePage verifyIfIsLoaded() {
        await().atMost(5, TimeUnit.SECONDS).until(searchButton).present();
        return this;
    }

    public SwiftNoteHomePage verifyNoteCount(int expectedCount) {
        assertThat($(AppiumBy.id("com.moonpi.swiftnotes:id/relativeLayout"))).hasSize(expectedCount);
        return this;
    }

    public SwiftNoteHomePage search(String searchPhrase) {
        el(AppiumBy.accessibilityId("Search")).click();
        FluentWebElement searchInput = el(AppiumBy.id("com.moonpi.swiftnotes:id/search_src_text"));
        await().until(searchInput).displayed();
        searchInput.fill().with(searchPhrase);
        return this;
    }

}
