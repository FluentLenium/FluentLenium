package io.fluentlenium.core;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link FluentPage}.
 */
@RunWith(MockitoJUnitRunner.class)
public class FluentPageParameterQueryTest {

    @Mock
    private FluentControl fluentControl;
    private FluentPage fluentPage;

    @Before
    public void setup() {
        fluentPage = spy(new FluentPage(fluentControl));
    }

    @Test
    public void shouldReturnParameterValue() {
        when(fluentControl.url()).thenReturn("/abc/param1val/def/param2val/param3val");
        when(fluentPage.getUrl()).thenReturn("/abc/{param1}/def/{param2}/{param3}");

        assertThat(fluentPage.getParam("param1")).isEqualTo("param1val");
    }

    @Test
    public void shouldReturnNullWhenParameterIsNotPresent() {
        when(fluentControl.url()).thenReturn("/abc/param1val/def/param2val/param3val");
        when(fluentPage.getUrl()).thenReturn("/abc/{param1}/def/{param2}/{param3}");

        assertThat(fluentPage.getParam("param4")).isNull();
    }

    @Test
    public void shouldThrowExceptionWhenTheGivenParamNameIsNull() {
        assertThatIllegalArgumentException().isThrownBy(() -> fluentPage.getParam(null))
                .withMessage("The parameter name to query should not be blank.");
    }

    @Test
    public void shouldThrowExceptionWhenTheGivenParamNameIsEmpty() {
        assertThatIllegalArgumentException().isThrownBy(() -> fluentPage.getParam(""))
                .withMessage("The parameter name to query should not be blank.");
    }

    @Test
    public void shouldReturnOptionalParameterValueWhenPresent() {
        when(fluentControl.url()).thenReturn("/abc/param1val/def/param2val/param3val");
        when(fluentPage.getUrl()).thenReturn("/abc{?/param1}/def/{param2}/{param3}");

        assertThat(fluentPage.getParam("param1")).isEqualTo("param1val");
    }

    @Test
    public void shouldReturnNullWhenOptionalParameterValueIsNotPresent() {
        when(fluentControl.url()).thenReturn("/abc/def/param2val/param3val");
        when(fluentPage.getUrl()).thenReturn("/abc{?/param1}/def/{param2}/{param3}");

        assertThat(fluentPage.getParam("param1")).isNull();
    }

    @Test
    public void shouldReturnCachedParametersWhenTheSameUrlIsParsed() {
        when(fluentControl.url()).thenReturn("/abc/param1val/def/param2val/param3val");
        when(fluentPage.getUrl()).thenReturn("/abc/{param1}/def/{param2}/{param3}");

        fluentPage.getParam("param1");
        fluentPage.getParam("param2");

        verify(fluentPage, times(1)).parseUrl(anyString());
    }

    @Test
    public void shouldReturnNewlyParsedParametersWhenADifferentUrlIsParsed() {
        when(fluentControl.url()).thenReturn("/abc/param1val/def/param2val/param3val")
                .thenReturn("/abc/param1val/");
        when(fluentPage.getUrl()).thenReturn("/abc/{param1}/def/{param2}/{param3}");

        fluentPage.getParam("param1");
        fluentPage.getParam("param2");

        verify(fluentPage, times(2)).parseUrl(anyString());
    }
}
