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
import org.openqa.selenium.Dimension;

import static org.junit.Assert.assertTrue;
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

    @Test
    public void testHasIdOk() throws Exception {
        when(fluentWebElement.getId()).thenReturn("some id");
        fluentWebElementAssert.hasId("some id");
        assertTrue(true);
    }

    @Test(expected = AssertionError.class)
    public void testHasIdKo() throws Exception {
        when(fluentWebElement.getId()).thenReturn("other id");
        fluentWebElementAssert.hasId("some id");
    }
	
	@Test
	public void testHasClassOk() throws Exception {
		when(fluentWebElement.getAttribute("class")).thenReturn("some class");
        fluentWebElementAssert.hasClass("some class");
	}

	@Test(expected = AssertionError.class)
	public void testHasClassKo() throws Exception {
		when(fluentWebElement.getAttribute("class")).thenReturn("other class");
        fluentWebElementAssert.hasClass("some class");
	}

    @Test(expected = AssertionError.class)
    public void testHasAttributeKo() throws Exception {
        when(fluentWebElement.getAttribute("attrName")).thenReturn(null);
        fluentWebElementAssert.hasAttribute("attrName");
    }

    @Test
    public void testHasAttributeOk() throws Exception {
        when(fluentWebElement.getAttribute("attrName")).thenReturn("attrValue");
        fluentWebElementAssert.hasAttribute("attrName");
    }

    @Test(expected = AssertionError.class)
    public void testHasTextKo() throws Exception {
        when(fluentWebElement.getText()).thenReturn("other text");
        fluentWebElementAssert.hasText("text to find");
    }

    @Test(expected = AssertionError.class)
    public void testHasNotTextKo() throws Exception {
        when(fluentWebElement.getText()).thenReturn("some text to find");
        fluentWebElementAssert.hasNotText("text to find");
    }

    @Test
    public void testHasNotTextOk() throws Exception {
        when(fluentWebElement.getText()).thenReturn("other text");
        fluentWebElementAssert.hasNotText("text to find");
    }

    @Test
    public void testHasTextOk() throws Exception {
        when(fluentWebElement.getText()).thenReturn("text to find");
        fluentWebElementAssert.hasText("text to find");
    }

    @Test(expected = AssertionError.class)
    public void testHasNameKo() throws Exception {
        when(fluentWebElement.getName()).thenReturn("other name");
        fluentWebElementAssert.hasName("name");
    }

    @Test
    public void testHasNameOk() throws Exception {
        when(fluentWebElement.getName()).thenReturn("name");
        fluentWebElementAssert.hasName("name");
    }

    @Test(expected = AssertionError.class)
    public void testHasValueKo() throws Exception {
        when(fluentWebElement.getValue()).thenReturn("other value");
        fluentWebElementAssert.hasValue("value");
    }

    @Test
    public void testHasValueOk() throws Exception {
        when(fluentWebElement.getValue()).thenReturn("value");
        fluentWebElementAssert.hasValue("value");
    }

    @Test(expected = AssertionError.class)
    public void testHasSizeKo() throws Exception {
        when(fluentWebElement.getSize()).thenReturn(new Dimension(1, 2));
        fluentWebElementAssert.hasSize(5, 7);
    }

    @Test
    public void testHasSizeOk() throws Exception {
        when(fluentWebElement.getSize()).thenReturn(new Dimension(1, 2));
        fluentWebElementAssert.hasSize(1, 2);
    }

    @Test(expected = AssertionError.class)
    public void testContainsAttributeWithValueNoAttributeError() throws Exception {
        when(fluentWebElement.getAttribute("attrName")).thenReturn(null);
        fluentWebElementAssert.containsAttribute("attrName").withValue("value");
    }

    @Test(expected = AssertionError.class)
    public void testContainsAttributeWithValueWrongAttributeValueError() throws Exception {
        when(fluentWebElement.getAttribute("attrName")).thenReturn("actual value");
        fluentWebElementAssert.containsAttribute("attrName").withValue("expected value");
    }

    @Test
    public void testContainsAttributeWithValueOk() throws Exception {
        when(fluentWebElement.getAttribute("attrName")).thenReturn("value");
        fluentWebElementAssert.containsAttribute("attrName").withValue("value");
    }

}
