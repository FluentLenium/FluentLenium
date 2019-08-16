package org.fluentlenium.examples.test;

import com.google.common.collect.ImmutableMap;
import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.utils.ChromiumApi;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.Response;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DuckDuckGoChromiumApiTest extends FluentTest {

    private ChromiumApi chromium;
    private Response response;

    @Before
    public void setUp() {
        ChromeDriver driver = (ChromeDriver) getDriver();
        chromium = new ChromiumApi(driver);
    }

    @Override
    public WebDriver newWebDriver() {
        return new ChromeDriver();
    }

    @Test
    public void resultPageUrlShouldContainSearchQueryName() {
        String searchPhrase = "searchPhrase";
        String duckDuckUrl = "https://duckduckgo.com";

        chromium.sendCommand("Page.navigate", ImmutableMap.of("url", duckDuckUrl));
        chromium.sendCommand("Input.insertText", ImmutableMap.of("text", searchPhrase));
        chromium.sendCommand("Input.dispatchKeyEvent", sendEnterKeyEventParams());
        response = chromium.sendCommandAndGetResponse("Page.getNavigationHistory", ImmutableMap.of());

        assertIsPhrasePresentInTheResultsPageUrl(searchPhrase);
    }

    private Map<String, String> sendEnterKeyEventParams() {
        return ImmutableMap.of("type", "char", "text", "\r");
    }

    public void assertIsPhrasePresentInTheResultsPageUrl(String searchPhrase) {
        assertThat(response.getValue().toString()).contains(searchPhrase);
    }
}
