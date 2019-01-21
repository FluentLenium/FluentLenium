/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package org.fluentlenium.integration;

import org.assertj.core.api.Assertions;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

class PageAdapterTest extends FluentAdapter {

    private final WebDriver driver = new HtmlUnitDriver(true);

    @Page
    private InjectedIndexPage page;

    private InjectedIndexPage reference;

    /**
     * simulate page injection before init
     */
    @BeforeEach
    void before() {
        initFluent(driver);

        page = newInstance(InjectedIndexPage.class);
        page.testVariable = "test";
        reference = page;
    }

    @AfterEach
    void after() {
        releaseFluent();
    }

    @Test
    void checkGoToOnInjectedPage() {
        page.go();
        Assertions.assertThat(window().title()).contains("Selenium");
    }

    @Test
    void checkInstanceVariableIsUnmodified() {
        Assertions.assertThat(page.testVariable).isEqualTo("test");
    }

    @Test
    void checkPageReferenceIsSame() {
        Assertions.assertThat(page).isSameAs(reference);
    }
}

class InjectedIndexPage extends IndexPage {

    /* default */ String testVariable;
}
