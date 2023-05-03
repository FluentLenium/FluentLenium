package io.fluentlenium.core;

import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.url.ParsedUrlTemplate;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link FluentPage}.
 */
@RunWith(MockitoJUnitRunner.class)
public class FluentPageTest {

    @Mock
    private FluentControl fluentControl;
    private FluentPage fluentPage;

    @Before
    public void setup() {
        fluentPage = spy(new FluentPage(fluentControl));
    }

    @Test
    public void shouldBeIsAtUsingSelectorBy() {
        By by = mock(By.class);
        FluentWebElement element1 = mock(FluentWebElement.class);
        FluentWebElement element2 = mock(FluentWebElement.class);
        FluentList fluentList = mock(FluentList.class);
        when(fluentPage.$(by)).thenReturn(fluentList);
        when(fluentList.first()).thenReturn(element1);
        when(element1.now()).thenReturn(element2);

        assertThatCode(() -> fluentPage.isAtUsingSelector(by)).doesNotThrowAnyException();
        verify(element1).now();
    }

    @Test
    public void shouldFailIsAtUsingSelectorBy() {
        By by = mock(By.class);
        FluentWebElement element1 = mock(FluentWebElement.class);
        FluentList fluentList = mock(FluentList.class);
        when(fluentPage.$(by)).thenReturn(fluentList);
        when(fluentList.first()).thenReturn(element1);
        when(element1.now()).thenThrow(NoSuchElementException.class);

        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> fluentPage.isAtUsingSelector(by))
                .withMessageStartingWith(
                        "@FindBy element not found for page io.fluentlenium.core"
                                + ".FluentPage");
    }

    @Test
    public void shouldGoToUrlWithoutParams() {
        when(fluentPage.getUrl()).thenReturn("/abc/def");
        doNothing().when(fluentPage).goTo("/abc/def");

        fluentPage.go();

        verify(fluentPage).goTo("/abc/def");
    }

    @Test
    public void shouldFailGoingToUrlWithoutParams() {
        when(fluentPage.getUrl()).thenReturn(null);

        assertThatIllegalStateException().isThrownBy(() -> fluentPage.go()).withMessage(
                "An URL should be defined on the page. Use @PageUrl annotation or override getUrl() method.");
    }

    @Test
    public void shouldGoToUrlWithParams() {
        when(fluentPage.getUrl("param1val")).thenReturn("/abc/param1val");
        doNothing().when(fluentPage).goTo("/abc/param1val");

        fluentPage.go("param1val");

        verify(fluentPage).goTo("/abc/param1val");
    }

    @Test
    public void shouldFailGoingToUrlWithParams() {
        when(fluentPage.getUrl("param1val")).thenReturn(null);

        assertThatIllegalStateException().isThrownBy(() -> fluentPage.go("param1val")).withMessage(
                "An URL should be defined on the page. Use @PageUrl annotation or override getUrl() method.");
    }

    @Test
    public void shouldParsePageUrl() {
        when(fluentControl.url()).thenReturn("/abc/param1val/def/param2val/param3val");
        when(fluentPage.getUrl()).thenReturn("/abc/{param1}/def/{param2}/{param3}");

        Assertions.assertThat(fluentPage.parseUrl().parameters()).containsOnlyKeys("param1", "param2", "param3");
    }

    @Test
    public void shouldParseUrl() {
        when(fluentPage.getUrl()).thenReturn("/abc/{param1}/def/{param2}/{param3}");

        ParsedUrlTemplate parsedUrlTemplate = fluentPage.parseUrl("/abc/param1val/def/param2val/param3val");

        assertThat(parsedUrlTemplate.parameters()).containsOnlyKeys("param1", "param2", "param3");
    }

    @Test
    public void shouldNotParseUrlIfNoUrlDefinedOnThePage() {
        when(fluentPage.getUrl()).thenReturn(null);

        assertThatIllegalStateException().isThrownBy(() -> fluentPage.parseUrl("/abc/param1val/def/param2val/param3val"))
                .withMessage("An URL should be defined on the page. Use @PageUrl annotation or "
                        + "override getUrl() method.");
    }
}
