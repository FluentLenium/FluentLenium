package org.fest.assertions.fluentlenium.unit;


import org.fest.assertions.fluentlenium.FluentLeniumAssertions;
import org.fest.assertions.fluentlenium.custom.PageAssert;
import org.fluentlenium.core.FluentPage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class PageFestAssertTest {
    @Mock
    FluentPage fluentPage;
    PageAssert pageAssert;

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


}
