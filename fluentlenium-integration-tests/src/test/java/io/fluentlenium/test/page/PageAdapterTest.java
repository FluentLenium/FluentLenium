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

package io.fluentlenium.test.page;

import io.fluentlenium.adapter.FluentAdapter;import io.fluentlenium.core.annotation.Page;import io.fluentlenium.pages.IndexPage;import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import io.fluentlenium.adapter.FluentAdapter;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.pages.IndexPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

class PageAdapterTest extends FluentAdapter {

    @Page
    private InjectedIndexPage page;

    private InjectedIndexPage reference;

    @BeforeAll
    public static void setUpChrome() {
        WebDriverManager.chromedriver().setup();
    }

    /**
     * simulate page injection before init
     */
    @BeforeEach
    void before() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        initFluent(new ChromeDriver(chromeOptions));

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
