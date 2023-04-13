package io.fluentlenium.examples.test;

import com.google.common.collect.ImmutableMap;
import io.fluentlenium.adapter.testng.FluentTestNg;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.examples.pages.DuckDuckMainPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DuckDuckGoEdgeTest extends FluentTestNg {

    @Page
    private DuckDuckMainPage duckDuckMainPage;

    @Override
    public WebDriver newWebDriver() {
        WebDriverManager.edgedriver().setup();
        return new EdgeDriver();
    }

    @Test
    public void titleOfDuckDuckGoShouldContainSearchQueryName() {
        String searchPhrase = "searchPhrase";

        goTo(duckDuckMainPage)
                .typeSearchPhraseIn(searchPhrase)
                .submitSearchForm()
                .assertIsPhrasePresentInTheResults(searchPhrase);
    }

    @Test
    public void resultPageUrlShouldContainSearchQueryName() {
        String searchPhrase = "searchPhrase";
        String duckDuckUrl = "https://duckduckgo.com";

        getChromiumApi().sendCommand("Page.navigate", ImmutableMap.of("url", duckDuckUrl));
        getChromiumApi().sendCommand("Input.insertText", ImmutableMap.of("text", searchPhrase));
        getChromiumApi().sendCommand("Input.dispatchKeyEvent", sendEnterKeyEventParams());
        Response response = getChromiumApi().sendCommandAndGetResponse("Page.getNavigationHistory", ImmutableMap.of());

        assertIsPhrasePresentInTheResultsPageUrl(searchPhrase, response);
    }

    private Map<String, String> sendEnterKeyEventParams() {
        return ImmutableMap.of("type", "char", "text", "\r");
    }

    private void assertIsPhrasePresentInTheResultsPageUrl(String searchPhrase, Response response) {
        assertThat(response.getValue().toString()).contains(searchPhrase);
    }

}