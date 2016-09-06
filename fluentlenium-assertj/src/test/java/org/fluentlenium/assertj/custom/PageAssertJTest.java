package org.fluentlenium.assertj.custom;


import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.core.FluentPage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class PageAssertJTest {
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
