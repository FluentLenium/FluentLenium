package org.fluentlenium.examples.quickstart;

import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.configuration.FluentConfiguration;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

@FluentConfiguration(webDriver = "htmlunit")
public class DuckDuckGoTest extends FluentTest {
    @Test
    public void title_of_duck_duck_go_should_contain_search_query_name() {
        goTo("https://duckduckgo.com");
        $("#search_form_input_homepage").fill().with("FluentLenium");
        $("#search_button_homepage").submit();
        assertThat(title()).contains("FluentLenium");
    }
}
