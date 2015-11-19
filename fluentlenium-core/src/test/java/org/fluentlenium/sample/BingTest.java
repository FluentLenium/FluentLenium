package org.fluentlenium.sample;

import org.fluentlenium.adapter.FluentTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BingTest extends FluentTest {

    @Test
    public void title_of_bing_should_contain_search_query_name_using_festassert() {
        goTo("http://www.bing.com");
        fill("#sb_form_q").with("FluentLenium");
        submit("#sb_form_go");
        assertThat(title()).contains("FluentLenium");
    }

    @Test
    public void title_of_bing_should_contain_search_query_name_using_pure_jUnit() {
        goTo("http://www.bing.com");
        fill("#sb_form_q").with("FluentLenium");
        submit("#sb_form_go");
        assertThat(title()).contains("FluentLenium");
    }

    @Test
    public void title_of_bing_should_contain_search_query_name_using_pure_jUnit_with_jquery_syntax() {
        goTo("http://www.bing.com");
        $("#sb_form_q").text("FluentLenium");
        $("#sb_form_go").submit();
        assertThat(title()).contains("FluentLenium");
    }

}
