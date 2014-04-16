/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package org.fluentlenium.assertj.unit;


import org.fluentlenium.assertj.FluentLeniumAssertions;
import org.fluentlenium.assertj.custom.PageAssert;
import org.fluentlenium.core.FluentPage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class PageFestAssertTest {
    @Mock
    FluentPage fluentPage;
    @InjectMocks
    PageAssert pageAssert = FluentLeniumAssertions.assertThat(fluentPage);

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testIsAt() {
        pageAssert.isAt();
        verify(fluentPage).isAt();
    }


}
