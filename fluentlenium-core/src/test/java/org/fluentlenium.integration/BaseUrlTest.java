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

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class BaseUrlTest extends LocalFluentCase {
    @Page
    Page2Relative pageRelative;

    @Page
    Page2 page;

    @Override
    public String getDefaultBaseUrl() {
        return DEFAULT_URL;
    }

    @Test
    public void baseUrlShouldBeUsedForRelativeUrlInGoTo() {
        goTo("/page2.html");
        assertThat(title()).isEqualTo("Page 2");
    }

    @Test
    public void baseUrlShouldNotBeUsedForAbsoluteUrlInGoTo() {
        goTo(DEFAULT_URL);
        assertThat(title()).isEqualTo("Fluent Selenium Documentation");
    }

    @Test
    public void baseUrlShouldBeUsedForRelativeUrlInPageGo() {
        goTo(pageRelative);
        pageRelative.isAt();
    }

    @Test
    public void baseUrlShouldNotBeUsedForAbsoluteUrlInPageGo() {
        goTo(page);
        page.isAt();
    }

}

class Page2Relative extends FluentPage {

    @Override
    public String getUrl() {
        return "/page2.html";
    }

    @Override
    public void isAt() {
        assertThat($("title").first().getText()).isEqualTo("Page 2");
    }
}

