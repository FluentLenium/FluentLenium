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

import org.assertj.core.api.Assertions;
import org.fluentlenium.core.FluentAdapter;
import org.fluentlenium.core.annotation.Page;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class InjectedPageTest extends FluentAdapter {

    private final WebDriver driver = new HtmlUnitDriver(true);

    @Page
    InjectedPageAccueil page;

    InjectedPageAccueil reference;

    /**
     * simulate page injection before init
     */
    @Before
    public void before() {
        page = new InjectedPageAccueil();
        page.testVariable = "test";
        reference = page;

        initFluent(driver);
        initTest();
    }

    @After
    public void after() {
        quit();
    }

    @Test
    public void checkGoToOnInjectedPage() {
        page.go();
        Assertions.assertThat(title()).contains("Selenium");
    }

    @Test
    public void checkInstanceVariableIsUnmodified() {
        Assertions.assertThat(page.testVariable).isEqualTo("test");
    }

    @Test
    public void checkPageReferenceIsSame() {
        Assertions.assertThat(page).isSameAs(reference);
    }
}

class InjectedPageAccueil extends PageAccueil {

    String testVariable;
}