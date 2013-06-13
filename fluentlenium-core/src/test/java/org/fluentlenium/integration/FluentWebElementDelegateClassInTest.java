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

import static org.fest.assertions.Assertions.*;

import org.fluentlenium.core.domain.*;
import org.fluentlenium.integration.localtest.*;
import org.junit.*;
import org.openqa.selenium.*;

public class FluentWebElementDelegateClassInTest extends LocalFluentCase {
    ALink linkToPage2;

    public static class ALink {
        private final WebElement webElement;

        public ALink(WebElement webElement) {
            this.webElement = webElement;
        }

        public void clickIfDisplayed() {
            if (webElement.isDisplayed()) {
                webElement.click();
            }
        }
    }

    @Test
    public void when_web_element_in_test_then_they_are_instanciated() {
        goTo(LocalFluentCase.DEFAULT_URL);
        linkToPage2.clickIfDisplayed();
        assertThat(url()).isEqualTo(LocalFluentCase.DEFAULT_URL + "page2.html");
    }
}