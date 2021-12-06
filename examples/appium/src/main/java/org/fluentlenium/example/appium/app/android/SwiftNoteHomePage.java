package org.fluentlenium.example.appium.app.android;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;

import java.util.concurrent.TimeUnit;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withId;
import static org.openqa.selenium.By.id;

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
        assertThat($(id("com.moonpi.swiftnotes:id/relativeLayout"))).hasSize(expectedCount);
        return this;
    }

    public SwiftNoteHomePage search(String searchPhrase) {
        el(withId("Search")).click();
        FluentWebElement searchInput = el(id("com.moonpi.swiftnotes:id/search_src_text"));
        await().until(searchInput).displayed();
        searchInput.fill().with(searchPhrase);
        return this;
    }

}
