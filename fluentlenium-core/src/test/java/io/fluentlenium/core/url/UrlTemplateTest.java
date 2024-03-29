package io.fluentlenium.core.url;

import org.assertj.core.util.Lists;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit test for {@link UrlTemplate}.
 */
public class UrlTemplateTest {

    @Test
    public void testOnlyParameter() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("{param1}");
        String url = urlParametersTemplate.add("test1").render();
        assertThat(getParameterNames(urlParametersTemplate)).containsExactly("param1");
        assertThat(getParameterOptionals(urlParametersTemplate)).containsExactly(false);
        assertThat(url).isEqualTo("test1");
    }

    @Test
    public void testOnlyOptionalParameter() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("{?param1}");
        String url = urlParametersTemplate.add("test1").render();
        assertThat(getParameterNames(urlParametersTemplate)).containsExactly("param1");
        assertThat(getParameterOptionals(urlParametersTemplate)).containsExactly(true);
        assertThat(url).isEqualTo("test1");
    }

    @Test
    public void testRender() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}/{param3}");
        String url = urlParametersTemplate.add("test1").add("test2").add("test3").render();
        assertThat(getParameterNames(urlParametersTemplate)).containsExactly("param1", "param2", "param3");
        assertThat(getParameterOptionals(urlParametersTemplate)).containsExactly(false, false, false);
        assertThat(url).isEqualTo("/abc/test1/def/test2/test3");
    }

    @Test
    public void testRenderOptionalParameter() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def{?/param2}/ghi{?/param3}");
        String url = urlParametersTemplate.add("test1").add("test2").render();
        assertThat(getParameterNames(urlParametersTemplate)).containsExactly("param1", "param2", "param3");
        assertThat(getParameterOptionals(urlParametersTemplate)).containsExactly(false, true, true);

        assertThat(url).isEqualTo("/abc/test1/def/test2/ghi");

        urlParametersTemplate.clear();

        assertThatThrownBy(urlParametersTemplate::render).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Value for parameter param1 is missing");
    }

    @Test
    public void testRenderNullOptionalParameter() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def{?/param2}/ghi{?/param3}");
        String url = urlParametersTemplate.add("test1").add(null).add("test3").render();
        assertThat(getParameterNames(urlParametersTemplate)).containsExactly("param1", "param2", "param3");
        assertThat(getParameterOptionals(urlParametersTemplate)).containsExactly(false, true, true);
        assertThat(url).isEqualTo("/abc/test1/def/ghi/test3");
    }

    @Test
    public void testRenderNullOptionalPathParameter() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}{?/def/param2}{?/ghi/param3}");
        String url = urlParametersTemplate.add("test1").add(null).add("test3").render();
        assertThat(getParameterNames(urlParametersTemplate)).containsExactly("param1", "param2", "param3");
        assertThat(getParameterOptionals(urlParametersTemplate)).containsExactly(false, true, true);
        assertThat(url).isEqualTo("/abc/test1/ghi/test3");
    }

    @Test
    public void testParse() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}/{param3}");
        assertThat(getParameterNames(urlParametersTemplate)).containsExactly("param1", "param2", "param3");
        assertThat(getParameterOptionals(urlParametersTemplate)).containsExactly(false, false, false);

        ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/v2/v3");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(3);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param2", "param3");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v2", "v3");
    }

    @Test
    public void testParseWithEndingSlash() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}/{param3}/");
        assertThat(getParameterNames(urlParametersTemplate)).containsExactly("param1", "param2", "param3");
        assertThat(getParameterOptionals(urlParametersTemplate)).containsExactly(false, false, false);

        ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/v2/v3");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(3);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param2", "param3");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v2", "v3");
    }

    @Test
    public void shouldThrowExceptionIfUriHasInvalidSyntax() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}/{param3}/");
        assertThat(getParameterNames(urlParametersTemplate)).containsExactly("param1", "param2", "param3");
        assertThat(getParameterOptionals(urlParametersTemplate)).containsExactly(false, false, false);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> urlParametersTemplate.parse("/abc /v1/def/v2/v3"))
                .withCauseInstanceOf(URISyntaxException.class);
    }

    @Test
    public void testParseOptionalParameter() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}{?/param3}");
        assertThat(getParameterNames(urlParametersTemplate)).containsExactly("param1", "param2", "param3");
        assertThat(getParameterOptionals(urlParametersTemplate)).containsExactly(false, false, true);

        ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/v2");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(2);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param2");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v2");
    }

    @Test
    public void testParseOptionalPathParameter() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}{?/def/param2}{?/param3}");
        assertThat(getParameterNames(urlParametersTemplate)).containsExactly("param1", "param2", "param3");
        assertThat(getParameterOptionals(urlParametersTemplate)).containsExactly(false, true, true);

        ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/v2");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(2);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param2");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v2");
    }

    @Test
    public void testParseOptionalMiddleParameter() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def{?/param2}/ghi/{param3}");
        assertThat(getParameterNames(urlParametersTemplate)).containsExactly("param1", "param2", "param3");
        assertThat(getParameterOptionals(urlParametersTemplate)).containsExactly(false, true, false);

        ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/ghi/v3");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(2);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param3");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v3");
    }

    @Test
    public void testParseOptionalPathMiddleParameter() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}{?/def/param2}{/ghi/param3}");
        assertThat(getParameterNames(urlParametersTemplate)).containsExactly("param1", "param2", "param3");
        assertThat(getParameterOptionals(urlParametersTemplate)).containsExactly(false, true, false);

        ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/ghi/v3");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(2);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param3");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v3");
    }

    @Test
    public void testParseParametersFromQueryParameters() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("?param1={param1}&param2={param2}");
        assertThat(getParameterNames(urlParametersTemplate)).containsExactly("param1", "param2");
        assertThat(getParameterOptionals(urlParametersTemplate)).containsExactly(false, false);

        ParsedUrlTemplate parsed = urlParametersTemplate.parse("?param1=v1&param2=v2");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(2);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param2");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v2");
    }

    @Test
    public void testParseParametersFromPathAndQueryParameters() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}?param2={param2}&param3={param3}");
        assertThat(getParameterNames(urlParametersTemplate)).containsExactly("param1", "param2", "param3");
        assertThat(getParameterOptionals(urlParametersTemplate)).containsExactly(false, false, false);

        ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1?param2=v2&param3=v3");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(3);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param2", "param3");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v2", "v3");
    }

    @Test
    public void testParseNotMatchingOptionalMiddleParameter() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def{?/param2}/ghi/{param3}");
        assertThat(getParameterNames(urlParametersTemplate)).containsExactly("param1", "param2", "param3");
        assertThat(getParameterOptionals(urlParametersTemplate)).containsExactly(false, true, false);

        ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/ghi");
        assertThat(parsed.matches()).isFalse();
        assertThat(parsed.parameters()).isEmpty();
    }

    @Test
    public void testParseNotMatchingUrl() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}{?/param3}");
        assertThat(getParameterNames(urlParametersTemplate)).containsExactly("param1", "param2", "param3");
        assertThat(getParameterOptionals(urlParametersTemplate)).containsExactly(false, false, true);

        ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/abc/v2");
        assertThat(parsed.matches()).isFalse();
        assertThat(parsed.parameters()).isEmpty();
    }

    @Test
    public void testParseNotMatchingStartingUrl() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}{?/param3}");
        assertThat(getParameterNames(urlParametersTemplate)).containsExactly("param1", "param2", "param3");
        assertThat(getParameterOptionals(urlParametersTemplate)).containsExactly(false, false, true);

        ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/v2/v3/ghi");
        assertThat(parsed.matches()).isFalse();
        assertThat(parsed.parameters()).isEmpty();
    }

    @Test
    public void testParseMatchingWithTrailingSlash() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}{?/param3}");
        assertThat(getParameterNames(urlParametersTemplate)).containsExactly("param1", "param2", "param3");
        assertThat(getParameterOptionals(urlParametersTemplate)).containsExactly(false, false, true);

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

    @Test
    public void testSetSingleParameter() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def{?/param2}/ghi{?/param3}");

        urlParametersTemplate.put("param1", "test1");

        assertThat(urlParametersTemplate.render()).isEqualTo("/abc/test1/def/ghi");
    }

    @Test
    public void testThrowsExceptionForInvalidParameterName() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def{?/param2}/ghi{?/param3}");

        assertThatIllegalArgumentException().isThrownBy(() -> urlParametersTemplate.put("param4", "test4"))
                .withMessage("Invalid parameter name: param4");
    }

    @Test
    public void testSetParametersFromMap() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def{?/param2}/ghi{?/param3}");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "test1");
        parameters.put("param3", "test3");

        urlParametersTemplate.put(parameters);

        assertThat(urlParametersTemplate.render()).isEqualTo("/abc/test1/def/ghi/test3");
    }

    @Test
    public void testAddParametersFromList() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def{?/param2}/ghi{?/param3}");
        List<String> parameters = Lists.newArrayList("test1", "test2");

        urlParametersTemplate.addAll(parameters);

        assertThat(urlParametersTemplate.render()).isEqualTo("/abc/test1/def/test2/ghi");
    }

    @Test
    public void testAddParametersFromListContainingNull() {
        UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def{?/param2}/ghi{?/param3}");
        List<String> parameters = Arrays.asList("test1", null, "test3");

        urlParametersTemplate.addAll(parameters);

        assertThat(urlParametersTemplate.render()).isEqualTo("/abc/test1/def/ghi/test3");
    }

    private List<String> getParameterNames(UrlTemplate urlTemplate) {
        return urlTemplate.getParameters().stream().map(UrlParameter::getName).collect(toList());
    }

    private List<Boolean> getParameterOptionals(UrlTemplate urlTemplate) {
        return urlTemplate.getParameters().stream().map(UrlParameter::isOptional).collect(toList());
    }
}
