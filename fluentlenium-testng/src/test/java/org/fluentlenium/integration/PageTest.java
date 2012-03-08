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
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.ComparisonFailure;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class PageTest extends LocalFluentCase {
    @Page
    PageAccueil page;

    @Page
    Page2 page2;


    @BeforeMethod
    public void beforeTest() {
        goTo(page);
    }

    @Test
    public void checkGoTo() {
        assertThat(title()).contains("Selenium");
    }

    @Test
    public void checkIsAt() {
        page.isAt();
    }

    @Test(expectedExceptions = ComparisonFailure.class)
    public void checkIsAtFailed() {
        page2.isAt();
    }

    @Test
    public void checkFollowLink() {
        page.goToNexPage();
        page2.isAt();
    }

    @Test
    public void checkFollowLinkWithBddStyle() {
        assertAt(page);
        page.goToNexPage();
        assertAt(page2);
    }
}

class PageAccueil extends FluentPage {

    FluentWebElement linkToPage2;

    @Override
    public String getUrl() {
        return LocalFluentCase.DEFAULT_URL;
    }

    @Override
    public void isAt() {
        assertThat($("title").first().getText()).contains("Selenium");
    }

    public void goToNexPage() {
        linkToPage2.click();
    }
}

class Page2 extends FluentPage {

    @Override
    public String getUrl() {
        return LocalFluentCase.DEFAULT_URL + "/page2.html";
    }

    @Override
    public void isAt() {
        assertThat($("title").first().getText()).isEqualTo("Page 2");
    }

}