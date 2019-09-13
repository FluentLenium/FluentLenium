package org.fluentlenium.examples.test.chromium;

import com.google.common.collect.ImmutableMap;
import org.fluentlenium.adapter.junit.FluentTest;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.Response;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DuckDuckGoChromiumApiTest extends FluentTest {

    private Response response;

    @Override
    public WebDriver newWebDriver() {
        return new ChromeDriver();
    }

    @Test
    public void resultPageUrlShouldContainSearchQueryName() {
        String searchPhrase = "searchPhrase";
        String duckDuckUrl = "https://duckduckgo.com";

        getChromiumApi().sendCommand("Page.navigate", ImmutableMap.of("url", duckDuckUrl));
        getChromiumApi().sendCommand("Input.insertText", ImmutableMap.of("text", searchPhrase));
        getChromiumApi().sendCommand("Input.dispatchKeyEvent", sendEnterKeyEventParams());
        response = getChromiumApi().sendCommandAndGetResponse("Page.getNavigationHistory", ImmutableMap.of());

        assertIsPhrasePresentInTheResultsPageUrl(searchPhrase);
    }

    private Map<String, String> sendEnterKeyEventParams() {
        return ImmutableMap.of("type", "char", "text", "\r");
    }

    private void assertIsPhrasePresentInTheResultsPageUrl(String searchPhrase) {
        assertThat(response.getValue().toString()).contains(searchPhrase);
    }
}
