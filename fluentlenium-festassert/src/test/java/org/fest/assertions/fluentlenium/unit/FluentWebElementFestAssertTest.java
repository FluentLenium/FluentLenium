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


import org.fluentlenium.core.domain.FluentWebElement;
import org.fest.assertions.fluentlenium.FluentLeniumAssertions;
import org.fest.assertions.fluentlenium.custom.FluentWebElementAssert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class FluentWebElementFestAssertTest {
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
    public void testIsEnabledKo() throws Exception {
        when(fluentWebElement.isEnabled()).thenReturn(false);
        fluentWebElementAssert.isEnabled();
    }


    @Test
    public void testIsNotEnabledOk() throws Exception {
        when(fluentWebElement.isEnabled()).thenReturn(false);
        fluentWebElementAssert.isNotEnabled();
        assertTrue(true);
    }


    @Test(expected = AssertionError.class)
    public void testIsNotEnabledKo() throws Exception {
        when(fluentWebElement.isEnabled()).thenReturn(true);
        fluentWebElementAssert.isNotEnabled();
    }

    @Test
    public void testIsDisplayedOk() throws Exception {
        when(fluentWebElement.isDisplayed()).thenReturn(true);
        fluentWebElementAssert.isDisplayed();
        assertTrue(true);
    }

    @Test(expected = AssertionError.class)
    public void testIsDisplayedKo() throws Exception {
        when(fluentWebElement.isDisplayed()).thenReturn(false);
        fluentWebElementAssert.isDisplayed();
    }

    @Test
    public void testIsNotDisplayed() throws Exception {
        when(fluentWebElement.isDisplayed()).thenReturn(false);
        fluentWebElementAssert.isNotDisplayed();
        assertTrue(true);
    }

    @Test(expected = AssertionError.class)
    public void testIsSelected() throws Exception {
        when(fluentWebElement.isDisplayed()).thenReturn(true);
        fluentWebElementAssert.isNotDisplayed();
    }

    @Test
    public void testIsNotSelectedOk() throws Exception {
        when(fluentWebElement.isSelected()).thenReturn(false);
        fluentWebElementAssert.isNotSelected();
        assertTrue(true);
    }

    @Test(expected = AssertionError.class)
    public void testIsNotSelectedKo() throws Exception {
        when(fluentWebElement.isSelected()).thenReturn(true);
        fluentWebElementAssert.isNotSelected();
    }

    @Test
    public void testIsSelectedOk() throws Exception {
        when(fluentWebElement.isSelected()).thenReturn(true);
        fluentWebElementAssert.isSelected();
        assertTrue(true);
    }

    @Test(expected = AssertionError.class)
    public void testIsSelectedKo() throws Exception {
        when(fluentWebElement.isSelected()).thenReturn(false);
        fluentWebElementAssert.isSelected();
    }

}
