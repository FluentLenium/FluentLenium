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

package org.fest.assertions.fluentlenium.unit;


import org.fest.assertions.fluentlenium.FluentLeniumAssertions;
import org.fest.assertions.fluentlenium.custom.FluentWebElementAssert;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class FluentWebElementTest {
    @Mock
    FluentWebElement fluentWebElement;
    @InjectMocks
    FluentWebElementAssert fluentWebElementAssert = FluentLeniumAssertions.assertThat(fluentWebElement);

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testIsEnabledOk() {
        when(fluentWebElement.isEnabled()).thenReturn(true);
        fluentWebElementAssert.isEnabled();
    }

    @Test(expected = AssertionError.class)
    public void testIsEnabledKo() {
        when(fluentWebElement.isEnabled()).thenReturn(false);
        fluentWebElementAssert.isEnabled();
    }

    @Test
    public void testIsNotEnabledOk() {
        when(fluentWebElement.isEnabled()).thenReturn(false);
        fluentWebElementAssert.isNotEnabled();
    }

    @Test(expected = AssertionError.class)
    public void testIsNotEnabledKo() {
        when(fluentWebElement.isEnabled()).thenReturn(true);
        fluentWebElementAssert.isNotEnabled();
    }

    @Test
    public void testIsDisplayedOk() {
        when(fluentWebElement.isDisplayed()).thenReturn(true);
        fluentWebElementAssert.isDisplayed();
    }

    @Test(expected = AssertionError.class)
    public void testIsDisplayedKo() {
        when(fluentWebElement.isDisplayed()).thenReturn(false);
        fluentWebElementAssert.isDisplayed();
    }

    @Test
    public void testIsNotDisplayed() {
        when(fluentWebElement.isDisplayed()).thenReturn(false);
        fluentWebElementAssert.isNotDisplayed();
    }

    @Test(expected = AssertionError.class)
    public void testIsSelected() {
        when(fluentWebElement.isDisplayed()).thenReturn(true);
        fluentWebElementAssert.isNotDisplayed();
    }

    @Test
    public void testIsNotSelectedOk() {
        when(fluentWebElement.isSelected()).thenReturn(false);
        fluentWebElementAssert.isNotSelected();
    }

    @Test(expected = AssertionError.class)
    public void testIsNotSelectedKo() {
        when(fluentWebElement.isSelected()).thenReturn(true);
        fluentWebElementAssert.isNotSelected();
    }

    @Test
    public void testIsSelectedOk() {
        when(fluentWebElement.isSelected()).thenReturn(true);
        fluentWebElementAssert.isSelected();
    }

    @Test(expected = AssertionError.class)
    public void testIsSelectedKo() {
        when(fluentWebElement.isSelected()).thenReturn(false);
        fluentWebElementAssert.isSelected();
    }

}
