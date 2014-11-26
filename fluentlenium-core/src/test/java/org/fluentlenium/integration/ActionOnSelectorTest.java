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
import org.openqa.selenium.NoSuchElementException;

import static org.fest.assertions.Assertions.assertThat;

public class ActionOnSelectorTest extends LocalFluentCase {


    @Test
    public void checkFillAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").getValues()).contains("John");
        $("#name").first().text("zzz");
        assertThat($("#name").getValues()).contains("zzz");
    }

    @Test
    public void checkClearAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").first().getValue()).isEqualTo("John");
        $("#name").first().clear();
        assertThat($("#name").first().getValue()).isEqualTo("");
    }

    @Test
    public void checkClickAction() {
        goTo(DEFAULT_URL);
        assertThat(title()).contains("Selenium");
        $("#linkToPage2").first().click();
        assertThat(title()).isEqualTo("Page 2");
    }

    @Test
    public void checkClickActionWrongSelector() {
        goTo(DEFAULT_URL);
        assertThat(title()).contains("Selenium");
        try {
            click("#BLUB");
            org.junit.Assert.fail("NoSuchElementException should have been thrown!");
        } catch(NoSuchElementException nsee) {
            assertThat(nsee.getMessage()).startsWith("No Element found");
        }
    }    
    
      @Test
    public void checkDoubleClickAction() {
        goTo(DEFAULT_URL);
        assertThat(title()).contains("Selenium");
        $("#linkToPage2").first().doubleClick();
        assertThat(title()).isEqualTo("Page 2");
    }

    @Test
    public void checkTextAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").getValues()).contains("John");
        assertThat($(".small").first().getText()).contains("Small 1");
    }
}
