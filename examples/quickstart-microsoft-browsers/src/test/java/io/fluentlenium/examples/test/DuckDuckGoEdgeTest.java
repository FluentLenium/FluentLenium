package org.fluentlenium.examples.test;

import com.google.common.collect.ImmutableMap;
import org.fluentlenium.adapter.testng.FluentTestNg;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.examples.pages.DuckDuckMainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DuckDuckGoEdgeTest extends FluentTestNg {

    private static final String PATH_TO_EDGE_DRIVER = "/Users/s.radzyminski/Downloads/msedgedriver";
    private static final String EDGE_DRIVER_PROPERTY = "webdriver.edge.driver";

    @Page
    private DuckDuckMainPage duckDuckMainPage;

    @BeforeClass
    public static void setup() {
        if (System.getProperty(EDGE_DRIVER_PROPERTY) == null) {
            System.setProperty(EDGE_DRIVER_PROPERTY, PATH_TO_EDGE_DRIVER);
        }
    }

    @Override
    public WebDriver newWebDriver() {
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