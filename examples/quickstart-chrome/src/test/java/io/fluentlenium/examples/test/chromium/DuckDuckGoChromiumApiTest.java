package io.fluentlenium.examples.test.chromium;

import com.google.common.collect.ImmutableMap;
import io.fluentlenium.adapter.junit.FluentTest;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.Response;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DuckDuckGoChromiumApiTest extends FluentTest {

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
