package org.fluentlenium.core.url;

import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UrlParametersTemplateTest {

    @Test
    public void testRender() {
        final UrlParametersTemplate urlParametersTemplate = new UrlParametersTemplate("/abc/{param1}/def/{param2}/{param3}");
        final String url = urlParametersTemplate.add("test1").add("test2").add("test3").render();
        assertThat(urlParametersTemplate.getParameters())
                .containsExactly(new UrlParameter("param1"), new UrlParameter("param2"), new UrlParameter("param3"));
        assertThat(url).isEqualTo("/abc/test1/def/test2/test3");
    }

    @Test
    public void testRenderOptionalParameter() {
        final UrlParametersTemplate urlParametersTemplate = new UrlParametersTemplate(
                "/abc/{param1}/def/{?param2}/ghi/{?param3}");
        final String url = urlParametersTemplate.add("test1").add("test2").render();
        assertThat(urlParametersTemplate.getParameters())
                .containsExactly(new UrlParameter("param1"), new UrlParameter("param2", true), new UrlParameter("param3", true));
        assertThat(url).isEqualTo("/abc/test1/def/test2/ghi");

        urlParametersTemplate.clear();

        assertThatThrownBy(() -> urlParametersTemplate.render()).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Parameter param1 is not defined");
    }

    @Test
    public void testParse() {
        final UrlParametersTemplate urlParametersTemplate = new UrlParametersTemplate("/abc/{param1}/def/{param2}/{param3}");
        assertThat(urlParametersTemplate.getParameters())
                .containsExactly(new UrlParameter("param1"), new UrlParameter("param2"), new UrlParameter("param3"));

        final Map<String, String> parse = urlParametersTemplate.parse("/abc/v1/def/v2/v3");
        assertThat(parse).hasSize(3);
        assertThat(parse.keySet()).containsExactly("param1", "param2", "param3");
        assertThat(parse.values()).containsExactly("v1", "v2", "v3");
    }

    @Test
    public void testParseOptionalParameter() {
        final UrlParametersTemplate urlParametersTemplate = new UrlParametersTemplate("/abc/{param1}/def/{param2}/{?param3}");
        assertThat(urlParametersTemplate.getParameters())
                .containsExactly(new UrlParameter("param1"), new UrlParameter("param2"), new UrlParameter("param3", true));

        final Map<String, String> parse = urlParametersTemplate.parse("/abc/v1/def/v2");
        assertThat(parse).hasSize(2);
        assertThat(parse.keySet()).containsExactly("param1", "param2");
        assertThat(parse.values()).containsExactly("v1", "v2");
    }
}
