package org.fluentlenium.core;

import org.apache.http.message.BasicNameValuePair;
import org.fluentlenium.core.url.ParsedUrlTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FluentPageUrlTemplateTest {

    private FluentPage fluentPage;

    private FluentPage fluentPage2;

    @Mock
    private FluentControl control;

    @Before
    public void before() {
        fluentPage = Mockito.spy(new FluentPage(control) {
            @Override
            public String getUrl() {
                return "/abc/{param1}/def/{param2}";
            }
        });

        fluentPage2 = Mockito.spy(new FluentPage(control) {
            @Override
            public String getUrl() {
                return "abc/{param1}/def/{param2}/";
            }
        });
    }

    @Test
    public void testGetUrlParams() {
        String url = fluentPage.getUrl("test1", "test2");
        assertThat(url).isEqualTo("/abc/test1/def/test2");
    }

    @Test
    public void testGetUrlParams2() {
        String url = fluentPage2.getUrl("test1", "test2");
        assertThat(url).isEqualTo("abc/test1/def/test2/");
    }

    @Test
    public void testGetUrlMissingParams() {
        assertThatThrownBy(() -> fluentPage.getUrl("test1")).isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Value for parameter param2 is missing.");
    }

    @Test
    public void testGetUrlMissingParams2() {
        assertThatThrownBy(() -> fluentPage2.getUrl("test1")).isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Value for parameter param2 is missing.");
    }

    @Test
    public void testGoUrlParams() {
        fluentPage.go("test1", "test2");
        verify(control).goTo("/abc/test1/def/test2");
    }

    @Test
    public void testGoUrlParams2() {
        fluentPage2.go("test1", "test2");
        verify(control).goTo("abc/test1/def/test2/");
    }

    @Test
    public void testGoMissingParams() {
        assertThatThrownBy(() -> fluentPage.go("test1")).isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Value for parameter param2 is missing.");
    }

    @Test
    public void testGoMissingParams2() {
        assertThatThrownBy(() -> fluentPage2.go("test1")).isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Value for parameter param2 is missing.");
    }

    @Test
    public void testGetParameters() {
        Mockito.when(control.url()).thenReturn("/abc/test1/def/test2");
        ParsedUrlTemplate parsedUrl = fluentPage.parseUrl();

        assertThat(parsedUrl.matches()).isTrue();
        assertThat(parsedUrl.parameters()).hasSize(2);
        assertThat(parsedUrl.parameters().keySet()).containsExactly("param1", "param2");
        assertThat(parsedUrl.parameters().values()).containsExactly("test1", "test2");
    }

    @Test
    public void testGetParameters2() {
        Mockito.when(control.url()).thenReturn("/abc/test1/def/test2");
        ParsedUrlTemplate parsedUrl = fluentPage2.parseUrl();

        assertThat(parsedUrl.matches()).isTrue();
        assertThat(parsedUrl.parameters()).hasSize(2);
        assertThat(parsedUrl.parameters().keySet()).containsExactly("param1", "param2");
        assertThat(parsedUrl.parameters().values()).containsExactly("test1", "test2");
    }

    @Test
    public void testGetParametersQueryString() {
        Mockito.when(control.url()).thenReturn("/abc/test1/def/test2?param1=qp1&param2=qp2");
        ParsedUrlTemplate parsedUrl = fluentPage.parseUrl();

        assertThat(parsedUrl.matches()).isTrue();
        assertThat(parsedUrl.parameters()).hasSize(2);
        assertThat(parsedUrl.parameters().keySet()).containsExactly("param1", "param2");
        assertThat(parsedUrl.parameters().values()).containsExactly("test1", "test2");

        assertThat(parsedUrl.queryParameters())
                .containsExactly(new BasicNameValuePair("param1", "qp1"), new BasicNameValuePair("param2", "qp2"));
    }

    @Test
    public void testIsAt() {
        Mockito.when(control.url()).thenReturn("/abc/test1/def/test2");
        fluentPage.isAt();
    }

    @Test
    public void testIsAt2() {
        Mockito.when(control.url()).thenReturn("/abc/test1/def/test2");
        fluentPage2.isAt();
    }

    @Test
    public void testIsAtFailing() {
        Mockito.when(control.url()).thenReturn("/abc/test1/test2");
        assertThatThrownBy(() -> fluentPage.isAt()).isInstanceOf(AssertionError.class)
                .hasMessage("Current URL [/abc/test1/test2] doesn't match expected Page URL [/abc/{param1}/def/{param2}]");
    }

    @Test
    public void testIsAtFailing2() {
        Mockito.when(control.url()).thenReturn("/abc/test1/test2");
        assertThatThrownBy(() -> fluentPage2.isAt()).isInstanceOf(AssertionError.class)
                .hasMessage("Current URL [/abc/test1/test2] doesn't match expected Page URL [abc/{param1}/def/{param2}/]");
    }
}
