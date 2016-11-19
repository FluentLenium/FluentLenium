package org.fluentlenium.core.url;

import org.junit.Test;

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

        final UrlParametersParsed parsed = urlParametersTemplate.parse("/abc/v1/def/v2/v3");
        assertThat(parsed.url()).isEqualTo("/abc/v1/def/v2/v3");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(3);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param2", "param3");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v2", "v3");
    }

    @Test
    public void testParseOptionalParameter() {
        final UrlParametersTemplate urlParametersTemplate = new UrlParametersTemplate("/abc/{param1}/def/{param2}/{?param3}");
        assertThat(urlParametersTemplate.getParameters())
                .containsExactly(new UrlParameter("param1"), new UrlParameter("param2"), new UrlParameter("param3", true));

        final UrlParametersParsed parsed = urlParametersTemplate.parse("/abc/v1/def/v2");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(2);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param2");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v2");
    }

    @Test
    public void testParseOptionalMiddleParameter() {
        final UrlParametersTemplate urlParametersTemplate = new UrlParametersTemplate("/abc/{param1}/def/{?param2}/ghi/{param3}");
        assertThat(urlParametersTemplate.getParameters())
                .containsExactly(new UrlParameter("param1"), new UrlParameter("param2", true), new UrlParameter("param3"));

        final UrlParametersParsed parsed = urlParametersTemplate.parse("/abc/v1/def/ghi/v3");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(2);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param3");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v3");
    }

    @Test
    public void testParseNotMatchingOptionalMiddleParameter() {
        final UrlParametersTemplate urlParametersTemplate = new UrlParametersTemplate("/abc/{param1}/def/{?param2}/ghi/{param3}");
        assertThat(urlParametersTemplate.getParameters())
                .containsExactly(new UrlParameter("param1"), new UrlParameter("param2", true), new UrlParameter("param3"));

        final UrlParametersParsed parsed = urlParametersTemplate.parse("/abc/v1/def/ghi");
        assertThat(parsed.matches()).isFalse();
        assertThat(parsed.parameters()).hasSize(0);
    }

    @Test
    public void testParseNotMatchingUrl() {
        final UrlParametersTemplate urlParametersTemplate = new UrlParametersTemplate("/abc/{param1}/def/{param2}/{?param3}");
        assertThat(urlParametersTemplate.getParameters())
                .containsExactly(new UrlParameter("param1"), new UrlParameter("param2"), new UrlParameter("param3", true));

        final UrlParametersParsed parsed = urlParametersTemplate.parse("/abc/v1/abc/v2");
        assertThat(parsed.matches()).isFalse();
        assertThat(parsed.parameters()).hasSize(0);
    }

    @Test
    public void testParseNotMatchingStartingUrl() {
        final UrlParametersTemplate urlParametersTemplate = new UrlParametersTemplate("/abc/{param1}/def/{param2}/{?param3}");
        assertThat(urlParametersTemplate.getParameters())
                .containsExactly(new UrlParameter("param1"), new UrlParameter("param2"), new UrlParameter("param3", true));

        final UrlParametersParsed parsed = urlParametersTemplate.parse("/abc/v1/def/v2/v3/ghi");
        assertThat(parsed.matches()).isFalse();
        assertThat(parsed.parameters()).hasSize(0);
    }

    @Test
    public void testParseMatchingWithTrailingSlash() {
        final UrlParametersTemplate urlParametersTemplate = new UrlParametersTemplate("/abc/{param1}/def/{param2}/{?param3}");
        assertThat(urlParametersTemplate.getParameters())
                .containsExactly(new UrlParameter("param1"), new UrlParameter("param2"), new UrlParameter("param3", true));

        final UrlParametersParsed parsed = urlParametersTemplate.parse("/abc/v1/def/v2/v3/");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(3);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param2", "param3");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v2", "v3");
    }
}
