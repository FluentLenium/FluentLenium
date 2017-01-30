package org.fluentlenium.assertj.custom;

import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.FluentPage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PageAssertJTest {
    @Mock
    private FluentPage fluentPage;
    private PageAssert pageAssert;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        pageAssert = FluentLeniumAssertions.assertThat(fluentPage);
    }

    @Test
    public void testIsAt() {
        pageAssert.isAt();
        verify(fluentPage).isAt();
    }

    @Test
    public void testAssertMethodInherited() {
        when(fluentPage.getUrl()).thenReturn("http://lOcAlHOST/");
        FluentLeniumAssertions.assertThat(fluentPage.getUrl()).containsIgnoringCase("localhost");
    }
}
