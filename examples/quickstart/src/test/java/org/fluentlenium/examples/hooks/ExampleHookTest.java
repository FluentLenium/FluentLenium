package org.fluentlenium.examples.hooks;

import java.util.concurrent.TimeUnit;

import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.hook.wait.Wait;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Wait
@Example
public class ExampleHookTest extends FluentTest {
    @Test
    public void titleOfDuckDuckGoShouldContainSearchQueryName() {
        goTo("https://duckduckgo.com");
        $("#search_form_input_homepage").fill().with("FluentLenium");
        $("#search_button_homepage").submit();
        await().atMost(5, TimeUnit.SECONDS).until($("#search_form_homepage")).not().present();
        assertThat(window().title()).contains("FluentLenium");
    }
}
