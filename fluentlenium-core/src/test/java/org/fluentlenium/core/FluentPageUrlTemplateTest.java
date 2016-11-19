package org.fluentlenium.core;

import org.fluentlenium.core.url.ParsedUrlTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FluentPageUrlTemplateTest {

    private FluentPage fluentPage;

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
    }

    @Test
    public void testGetUrlParams() {
        final String url = fluentPage.getUrl("test1", "test2");
        assertThat(url).isEqualTo("/abc/test1/def/test2");
    }

    @Test
    public void testGetUrlMissingParams() {
        assertThatThrownBy(() -> fluentPage.getUrl("test1")).isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Value for parameter param2 is missing.");
    }

    @Test
    public void testGoUrlParams() {
        fluentPage.go("test1", "test2");
        verify(control).goTo("/abc/test1/def/test2");
    }

    @Test
    public void testGoMissingParams() {
        assertThatThrownBy(() -> fluentPage.go("test1")).isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Value for parameter param2 is missing.");
    }

    @Test
    public void testGetParameters() {
        Mockito.when(control.url()).thenReturn("/abc/test1/def/test2");
        final ParsedUrlTemplate parsedUrl = fluentPage.parseUrl();

        assertThat(parsedUrl.matches()).isTrue();
        assertThat(parsedUrl.parameters().size()).isEqualTo(2);
        assertThat(parsedUrl.parameters().keySet()).containsExactly("param1", "param2");
        assertThat(parsedUrl.parameters().values()).containsExactly("test1", "test2");
    }

    @Test
    public void testIsAt() {
        Mockito.when(control.url()).thenReturn("/abc/test1/def/test2");
        fluentPage.isAt();
    }

    @Test
    public void testIsAtFailing() {
        Mockito.when(control.url()).thenReturn("/abc/test1/test2");
        assertThatThrownBy(() -> fluentPage.isAt()).isInstanceOf(AssertionError.class)
                .hasMessage("Current URL [/abc/test1/test2] doesn't match expected Page URL [/abc/{param1}/def/{param2}]");
    }
}
