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

package org.fluentlenium.unit.initialization;

import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.fest.assertions.Assertions.assertThat;

public class BeforeInitialization extends FluentTest {
    public WebDriver webDriver = new HtmlUnitDriver();
    public TestPrivatePageWithElement page2;
    public TestPrivatePage2 page;

    @Before
    public void beforeTest() {
        page2 = createPage(TestPrivatePageWithElement.class);
        page = createPage(TestPrivatePage2.class);
    }

    @Test
    public void test_no_exception() {
        page.go();
    }

    @Test
    public void test_internal_fluentwebelement_instantiate() {
        TestPrivatePageWithElement page = createPage(TestPrivatePageWithElement.class);
        assertThat(page.myElement).isNotNull();
    }

    @Test
    public void test_superclass_fluentwebelement_instantiate() {
        TestPrivatePageWithElementSubclass page = createPage(TestPrivatePageWithElementSubclass.class);
        assertThat(page.myElement).isNotNull();
    }

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }


}

class TestPrivatePage2 extends FluentPage {


    @Override
    public String getUrl() {
        return "http://www.google.fr";
    }
}

class TestPrivatePageWithElement extends FluentPage {

    FluentWebElement myElement;

    @Override
    public String getUrl() {
        return "http://www.google.fr";
    }
}

class TestPrivatePageWithElementSubclass extends TestPrivatePageWithElement {

}

