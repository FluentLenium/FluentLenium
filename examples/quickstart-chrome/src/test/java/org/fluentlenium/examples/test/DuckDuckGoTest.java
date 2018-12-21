package org.fluentlenium.examples.test;

import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.examples.pages.DuckDuckMainPage;
import org.junit.Test;

public class DuckDuckGoTest extends FluentTest {

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