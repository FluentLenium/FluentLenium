/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package org.fest.assertions.fluentlenium.unit;

import static org.mockito.Mockito.when;

import org.fest.assertions.fluentlenium.FluentLeniumAssertions;
import org.fest.assertions.fluentlenium.custom.FluentWebElementAssert;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.Dimension;

@RunWith(MockitoJUnitRunner.class)
public class FluentWebElementTest {
    @Mock
    FluentWebElement fluentWebElement;

    FluentWebElementAssert fluentWebElementAssert;

    @Before
    public void setup() {
        fluentWebElementAssert = FluentLeniumAssertions.assertThat(fluentWebElement);
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

    @Test
    public void testHasIdOk() throws Exception {
        when(fluentWebElement.getId()).thenReturn("some id");
        fluentWebElementAssert.hasId("some id");
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
