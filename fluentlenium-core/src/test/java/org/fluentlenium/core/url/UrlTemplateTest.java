package org.fluentlenium.core.url;

import org.junit.Test;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UrlTemplateTest {

    @Test
    public void testRender() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}/{param3}");
        String url = urlParametersTemplate.add("test1").add("test2").add("test3").render();
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::getName).collect(Collectors.toList()))
                .containsExactly("param1", "param2", "param3");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::isOptional).collect(Collectors.toList()))
                .containsExactly(false, false, false);
        assertThat(url).isEqualTo("/abc/test1/def/test2/test3");
    }

    @Test
    public void testRenderOptionalParameter() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def{?/param2}/ghi{?/param3}");
        String url = urlParametersTemplate.add("test1").add("test2").render();
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::getName).collect(Collectors.toList()))
                .containsExactly("param1", "param2", "param3");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::isOptional).collect(Collectors.toList()))
                .containsExactly(false, true, true);

        assertThat(url).isEqualTo("/abc/test1/def/test2/ghi");

        urlParametersTemplate.clear();

        assertThatThrownBy(urlParametersTemplate::render).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Value for parameter param1 is missing");
    }

    @Test
    public void testRenderNullOptionalParameter() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def{?/param2}/ghi{?/param3}");
        String url = urlParametersTemplate.add("test1").add(null).add("test3").render();
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::getName).collect(Collectors.toList()))
                .containsExactly("param1", "param2", "param3");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::isOptional).collect(Collectors.toList()))
                .containsExactly(false, true, true);
        assertThat(url).isEqualTo("/abc/test1/def/ghi/test3");
    }

    @Test
    public void testRenderNullOptionalPathParameter() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}{?/def/param2}{?/ghi/param3}");
        String url = urlParametersTemplate.add("test1").add(null).add("test3").render();
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::getName).collect(Collectors.toList()))
                .containsExactly("param1", "param2", "param3");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::isOptional).collect(Collectors.toList()))
                .containsExactly(false, true, true);
        assertThat(url).isEqualTo("/abc/test1/ghi/test3");
    }

    @Test
    public void testParse() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}/{param3}");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::getName).collect(Collectors.toList()))
                .containsExactly("param1", "param2", "param3");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::isOptional).collect(Collectors.toList()))
                .containsExactly(false, false, false);

        ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/v2/v3");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(3);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param2", "param3");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v2", "v3");
    }

    @Test
    public void testParseOptionalParameter() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}{?/param3}");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::getName).collect(Collectors.toList()))
                .containsExactly("param1", "param2", "param3");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::isOptional).collect(Collectors.toList()))
                .containsExactly(false, false, true);

        ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/v2");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(2);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param2");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v2");
    }

    @Test
    public void testParseOptionalPathParameter() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}{?/def/param2}{?/param3}");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::getName).collect(Collectors.toList()))
                .containsExactly("param1", "param2", "param3");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::isOptional).collect(Collectors.toList()))
                .containsExactly(false, true, true);

        ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/v2");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(2);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param2");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v2");
    }

    @Test
    public void testParseOptionalMiddleParameter() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def{?/param2}/ghi/{param3}");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::getName).collect(Collectors.toList()))
                .containsExactly("param1", "param2", "param3");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::isOptional).collect(Collectors.toList()))
                .containsExactly(false, true, false);

        ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/ghi/v3");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(2);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param3");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v3");
    }

    @Test
    public void testParseOptionalPathMiddleParameter() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}{?/def/param2}{/ghi/param3}");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::getName).collect(Collectors.toList()))
                .containsExactly("param1", "param2", "param3");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::isOptional).collect(Collectors.toList()))
                .containsExactly(false, true, false);

        ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/ghi/v3");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(2);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param3");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v3");
    }

    @Test
    public void testParseNotMatchingOptionalMiddleParameter() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def{?/param2}/ghi/{param3}");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::getName).collect(Collectors.toList()))
                .containsExactly("param1", "param2", "param3");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::isOptional).collect(Collectors.toList()))
                .containsExactly(false, true, false);

        ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/ghi");
        assertThat(parsed.matches()).isFalse();
        assertThat(parsed.parameters()).hasSize(0);
    }

    @Test
    public void testParseNotMatchingUrl() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}{?/param3}");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::getName).collect(Collectors.toList()))
                .containsExactly("param1", "param2", "param3");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::isOptional).collect(Collectors.toList()))
                .containsExactly(false, false, true);

        ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/abc/v2");
        assertThat(parsed.matches()).isFalse();
        assertThat(parsed.parameters()).hasSize(0);
    }

    @Test
    public void testParseNotMatchingStartingUrl() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}{?/param3}");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::getName).collect(Collectors.toList()))
                .containsExactly("param1", "param2", "param3");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::isOptional).collect(Collectors.toList()))
                .containsExactly(false, false, true);

        ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/v2/v3/ghi");
        assertThat(parsed.matches()).isFalse();
        assertThat(parsed.parameters()).hasSize(0);
    }

    @Test
    public void testParseMatchingWithTrailingSlash() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}{?/param3}");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::getName).collect(Collectors.toList()))
                .containsExactly("param1", "param2", "param3");
        assertThat(urlParametersTemplate.getParameters().stream().map(UrlParameter::isOptional).collect(Collectors.toList()))
                .containsExactly(false, false, true);

        ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/v2/v3/");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(3);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param2", "param3");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v2", "v3");
    }

    @Test
    public void testDuplicateParameters() {
        assertThatThrownBy(() -> new UrlTemplate("/abc/{param1}{?/def/param1}{?/ghi/param3}"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Multiple parameters are defined with the same name (param1).");
    }
}
