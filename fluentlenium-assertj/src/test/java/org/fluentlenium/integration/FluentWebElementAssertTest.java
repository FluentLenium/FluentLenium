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

package org.fluentlenium.integration;

import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class FluentWebElementAssertTest extends LocalFluentCase {

    @Test
    public void testIsEnabledOk() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#name")).isEnabled();
    }

    @Test(expected = AssertionError.class)
    public void testIsEnabledKo() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#disabled")).isEnabled();
    }


    @Test
    public void testIsNotEnabledOk() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#disabled")).isNotEnabled();
    }


    @Test(expected = AssertionError.class)
    public void testIsNotEnabledKo() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#name")).isNotEnabled();

    }

    @Test
    public void testIsDisplayedOk() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#disabled")).isDisplayed();
    }

    @Test(expected = AssertionError.class)
    public void testIsDisplayedKo() throws Exception {
        goTo(DEFAULT_URL);
        executeScript("document.getElementById(\"disabled\").style.display=\"none\";");
        assertThat(findFirst("#disabled")).isDisplayed();
    }

    @Test
    public void testIsNotDisplayed() throws Exception {
        goTo(DEFAULT_URL);
        executeScript("document.getElementById(\"disabled\").style.display=\"none\";");
        assertThat(findFirst("#disabled")).isNotDisplayed();

    }

    @Test(expected = AssertionError.class)
    public void testIsSelected() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#disabled")).isNotDisplayed();
    }

    @Test
    public void testIsNotSelectedOk() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#disabled")).isNotSelected();
    }

    @Test(expected = AssertionError.class)
    public void testIsNotSelectedKo() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#selected")).isNotSelected();

    }

    @Test
    public void testIsSelectedOk() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#selected")).isSelected();

    }

    @Test(expected = AssertionError.class)
    public void testIsSelectedKo() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(findFirst("#disabled")).isSelected();

    }

}
