package org.fluentlenium.core.url;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UrlTemplateTest {

    @Test
    public void testRender() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}/{param3}");
        final String url = urlParametersTemplate.add("test1").add("test2").add("test3").render();
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, String>() {
            @Override
            public String apply(UrlParameter input) {
                return input.getName();
            }
        })).containsExactly("param1", "param2", "param3");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, Boolean>() {
            @Override
            public Boolean apply(UrlParameter input) {
                return input.isOptional();
            }
        })).containsExactly(false, false, false);
        assertThat(url).isEqualTo("/abc/test1/def/test2/test3");
    }

    @Test
    public void testRenderOptionalParameter() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def{?/param2}/ghi{?/param3}");
        final String url = urlParametersTemplate.add("test1").add("test2").render();
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, String>() {
            @Override
            public String apply(UrlParameter input) {
                return input.getName();
            }
        })).containsExactly("param1", "param2", "param3");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, Boolean>() {
            @Override
            public Boolean apply(UrlParameter input) {
                return input.isOptional();
            }
        })).containsExactly(false, true, true);
        assertThat(url).isEqualTo("/abc/test1/def/test2/ghi");

        urlParametersTemplate.clear();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                urlParametersTemplate.render();
            }
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Value for parameter param1 is missing");
    }

    @Test
    public void testRenderNullOptionalParameter() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def{?/param2}/ghi{?/param3}");
        final String url = urlParametersTemplate.add("test1").add(null).add("test3").render();
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, String>() {
            @Override
            public String apply(UrlParameter input) {
                return input.getName();
            }
        })).containsExactly("param1", "param2", "param3");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, Boolean>() {
            @Override
            public Boolean apply(UrlParameter input) {
                return input.isOptional();
            }
        })).containsExactly(false, true, true);
        assertThat(url).isEqualTo("/abc/test1/def/ghi/test3");
    }

    @Test
    public void testRenderNullOptionalPathParameter() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}{?/def/param2}{?/ghi/param3}");
        final String url = urlParametersTemplate.add("test1").add(null).add("test3").render();
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, String>() {
            @Override
            public String apply(UrlParameter input) {
                return input.getName();
            }
        })).containsExactly("param1", "param2", "param3");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, Boolean>() {
            @Override
            public Boolean apply(UrlParameter input) {
                return input.isOptional();
            }
        })).containsExactly(false, true, true);
        assertThat(url).isEqualTo("/abc/test1/ghi/test3");
    }

    @Test
    public void testParse() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}/{param3}");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, String>() {
            @Override
            public String apply(UrlParameter input) {
                return input.getName();
            }
        })).containsExactly("param1", "param2", "param3");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, Boolean>() {
            @Override
            public Boolean apply(UrlParameter input) {
                return input.isOptional();
            }
        })).containsExactly(false, false, false);

        final ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/v2/v3");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(3);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param2", "param3");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v2", "v3");
    }

    @Test
    public void testParseOptionalParameter() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}{?/param3}");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, String>() {
            @Override
            public String apply(UrlParameter input) {
                return input.getName();
            }
        })).containsExactly("param1", "param2", "param3");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, Boolean>() {
            @Override
            public Boolean apply(UrlParameter input) {
                return input.isOptional();
            }
        })).containsExactly(false, false, true);

        final ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/v2");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(2);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param2");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v2");
    }

    @Test
    public void testParseOptionalPathParameter() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}{?/def/param2}{?/param3}");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, String>() {
            @Override
            public String apply(UrlParameter input) {
                return input.getName();
            }
        })).containsExactly("param1", "param2", "param3");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, Boolean>() {
            @Override
            public Boolean apply(UrlParameter input) {
                return input.isOptional();
            }
        })).containsExactly(false, true, true);

        final ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/v2");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(2);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param2");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v2");
    }

    @Test
    public void testParseOptionalMiddleParameter() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def{?/param2}/ghi/{param3}");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, String>() {
            @Override
            public String apply(UrlParameter input) {
                return input.getName();
            }
        })).containsExactly("param1", "param2", "param3");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, Boolean>() {
            @Override
            public Boolean apply(UrlParameter input) {
                return input.isOptional();
            }
        })).containsExactly(false, true, false);

        final ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/ghi/v3");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(2);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param3");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v3");
    }

    @Test
    public void testParseOptionalPathMiddleParameter() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}{?/def/param2}{/ghi/param3}");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, String>() {
            @Override
            public String apply(UrlParameter input) {
                return input.getName();
            }
        })).containsExactly("param1", "param2", "param3");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, Boolean>() {
            @Override
            public Boolean apply(UrlParameter input) {
                return input.isOptional();
            }
        })).containsExactly(false, true, false);

        final ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/ghi/v3");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(2);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param3");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v3");
    }

    @Test
    public void testParseNotMatchingOptionalMiddleParameter() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def{?/param2}/ghi/{param3}");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, String>() {
            @Override
            public String apply(UrlParameter input) {
                return input.getName();
            }
        })).containsExactly("param1", "param2", "param3");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, Boolean>() {
            @Override
            public Boolean apply(UrlParameter input) {
                return input.isOptional();
            }
        })).containsExactly(false, true, false);

        final ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/ghi");
        assertThat(parsed.matches()).isFalse();
        assertThat(parsed.parameters()).hasSize(0);
    }

    @Test
    public void testParseNotMatchingUrl() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}{?/param3}");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, String>() {
            @Override
            public String apply(UrlParameter input) {
                return input.getName();
            }
        })).containsExactly("param1", "param2", "param3");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, Boolean>() {
            @Override
            public Boolean apply(UrlParameter input) {
                return input.isOptional();
            }
        })).containsExactly(false, false, true);

        final ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/abc/v2");
        assertThat(parsed.matches()).isFalse();
        assertThat(parsed.parameters()).hasSize(0);
    }

    @Test
    public void testParseNotMatchingStartingUrl() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}{?/param3}");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, String>() {
            @Override
            public String apply(UrlParameter input) {
                return input.getName();
            }
        })).containsExactly("param1", "param2", "param3");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, Boolean>() {
            @Override
            public Boolean apply(UrlParameter input) {
                return input.isOptional();
            }
        })).containsExactly(false, false, true);

        final ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/v2/v3/ghi");
        assertThat(parsed.matches()).isFalse();
        assertThat(parsed.parameters()).hasSize(0);
    }

    @Test
    public void testParseMatchingWithTrailingSlash() {
        final UrlTemplate urlParametersTemplate = new UrlTemplate("/abc/{param1}/def/{param2}{?/param3}");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, String>() {
            @Override
            public String apply(UrlParameter input) {
                return input.getName();
            }
        })).containsExactly("param1", "param2", "param3");
        assertThat(Collections2.transform(urlParametersTemplate.getParameters(), new Function<UrlParameter, Boolean>() {
            @Override
            public Boolean apply(UrlParameter input) {
                return input.isOptional();
            }
        })).containsExactly(false, false, true);

        final ParsedUrlTemplate parsed = urlParametersTemplate.parse("/abc/v1/def/v2/v3/");
        assertThat(parsed.matches()).isTrue();
        assertThat(parsed.parameters()).hasSize(3);
        assertThat(parsed.parameters().keySet()).containsExactly("param1", "param2", "param3");
        assertThat(parsed.parameters().values()).containsExactly("v1", "v2", "v3");
    }

    @Test
    public void testDuplicateParameters() {
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                new UrlTemplate("/abc/{param1}{?/def/param1}{?/ghi/param3}");
            }
        }).isInstanceOf(IllegalStateException.class).hasMessage("Multiple parameters are defined with the same name (param1).");
    }
}
