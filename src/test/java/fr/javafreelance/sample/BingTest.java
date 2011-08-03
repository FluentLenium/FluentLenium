package fr.javafreelance.sample;

import fr.javafreelance.integration.localTest.LocalFluentCase;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class BingTest extends LocalFluentCase {

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
        assertTrue(title().contains("FluentLenium"));
    }

    @Test
    public void title_of_bing_should_contain_search_query_name_using_pure_jUnit_with_jquery_syntax() {
        goTo("http://www.bing.com");
        $("#sb_form_q").text("FluentLenium");
        $("#sb_form_go").submit();
        assertTrue(title().contains("FluentLenium"));
    }

}
