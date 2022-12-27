package io.fluentlenium.examples.test;

import io.fluentlenium.configuration.FluentConfiguration;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.examples.pages.DuckDuckMainPage;
import org.junit.Test;

@FluentConfiguration(capabilities = "{\"goog:chromeOptions\": {\"args\": [\"headless\",\"disable-gpu\"]}}")
public class DuckDuckGoHeadlessTest extends AbstractChromeTest {

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