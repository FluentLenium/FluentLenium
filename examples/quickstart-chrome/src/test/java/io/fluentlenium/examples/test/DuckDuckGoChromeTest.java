package io.fluentlenium.examples.test;

import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.examples.pages.DuckDuckMainPage;
import org.junit.Test;

public class DuckDuckGoChromeTest extends AbstractChromeTest {

    @Page
    private DuckDuckMainPage duckDuckMainPage;

    @Test
    public void titleOfDuckDuckGoShouldContainSearchQueryName() {
        String searchPhrase = "searchPhrase";

        goTo(duckDuckMainPage)
                .typeSearchPhraseIn(searchPhrase)
                .submitSearchForm()
                .assertIsPhrasePresentInTheResults(searchPhrase);
    }
}