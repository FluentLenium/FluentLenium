package org.fluentlenium.core.url;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UrlTemplateTest {

    @Test
    public void testRender() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}/{param3}");
        final String url = urlParametersTemplate.add("test1").add("test2").add("test3").render();
        assertThat(urlParametersTemplate.getParameters())
                .containsExactly(new UrlParameter("param1"), new UrlParameter("param2"), new UrlParameter("param3"));
        assertThat(url).isEqualTo("/abc/test1/def/test2/test3");
    }

    @Test
    public void testRenderOptionalParameter() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{?param2}/ghi/{?param3}");
        final String url = urlParametersTemplate.add("test1").add("test2").render();
        assertThat(urlParametersTemplate.getParameters())
                .containsExactly(new UrlParameter("param1"), new UrlParameter("param2", true), new UrlParameter("param3", true));
        assertThat(url).isEqualTo("/abc/test1/def/test2/ghi");

        urlParametersTemplate.clear();

        assertThatThrownBy(() -> urlParametersTemplate.render()).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Value for parameter param1 is missing");
    }

    @Test
    public void testRenderNullOptionalParameter() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{?param2}/ghi/{?param3}");
        final String url = urlParametersTemplate.add("test1").add(null).add("test3").render();
        assertThat(urlParametersTemplate.getParameters())
                .containsExactly(new UrlParameter("param1"), new UrlParameter("param2", true), new UrlParameter("param3", true));
        assertThat(url).isEqualTo("/abc/test1/def/ghi/test3");
    }

    @Test
    public void testParse() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}/{param3}");
        assertThat(urlParametersTemplate.getParameters())
                .containsExactly(new UrlParameter("param1"), new UrlParameter("param2"), new UrlParameter("param3"));

        final ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/v2/v3");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(3);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param2", "param3");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v2", "v3");
    }

    @Test
    public void testParseOptionalParameter() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}/{?param3}");
        assertThat(urlParametersTemplate.getParameters())
                .containsExactly(new UrlParameter("param1"), new UrlParameter("param2"), new UrlParameter("param3", true));

        final ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/v2");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(2);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param2");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v2");
    }

    @Test
    public void testParseOptionalMiddleParameter() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{?param2}/ghi/{param3}");
        assertThat(urlParametersTemplate.getParameters())
                .containsExactly(new UrlParameter("param1"), new UrlParameter("param2", true), new UrlParameter("param3"));

        final ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/ghi/v3");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(2);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param3");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v3");
    }

    @Test
    public void testParseNotMatchingOptionalMiddleParameter() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{?param2}/ghi/{param3}");
        assertThat(urlParametersTemplate.getParameters())
                .containsExactly(new UrlParameter("param1"), new UrlParameter("param2", true), new UrlParameter("param3"));

        final ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/ghi");
        assertThat(parsed.matches()).isFalse();
        assertThat(parsed.parameters()).hasSize(0);
    }

    @Test
    public void testParseNotMatchingUrl() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}/{?param3}");
        assertThat(urlParametersTemplate.getParameters())
                .containsExactly(new UrlParameter("param1"), new UrlParameter("param2"), new UrlParameter("param3", true));

        final ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/abc/v2");
        assertThat(parsed.matches()).isFalse();
        assertThat(parsed.parameters()).hasSize(0);
    }

    @Test
    public void testParseNotMatchingStartingUrl() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}/{?param3}");
        assertThat(urlParametersTemplate.getParameters())
                .containsExactly(new UrlParameter("param1"), new UrlParameter("param2"), new UrlParameter("param3", true));

        final ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/v2/v3/ghi");
        assertThat(parsed.matches()).isFalse();
        assertThat(parsed.parameters()).hasSize(0);
    }

    @Test
    public void testParseMatchingWithTrailingSlash() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}/{?param3}");
        assertThat(urlParametersTemplate.getParameters())
                .containsExactly(new UrlParameter("param1"), new UrlParameter("param2"), new UrlParameter("param3", true));

        final ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/v2/v3/");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(3);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param2", "param3");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v2", "v3");
    }
}
