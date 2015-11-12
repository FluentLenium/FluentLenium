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

package org.fluentlenium.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;
import org.openqa.selenium.support.FindBy;

public class FindByOfListTest extends LocalFluentCase {

    @Page
    private PageIndex page;

    @Test
    public void should_findBy_retrieve_list() {
        page.go();
        page.isAt();
        Assertions.assertThat(page.smalls).hasSize(3);
        Assertions.assertThat(page.smalls.getTexts()).containsExactly("Small 1", "Small 2", "Small 3");
    }
}

class PageIndex extends FluentPage {

    @FindBy(className = "small")
    FluentList<FluentWebElement> smalls;

    @Override
    public String getUrl() {
        return LocalFluentCase.DEFAULT_URL;
    }

    @Override
    public void isAt() {
        assertThat(getDriver().getTitle()).contains("Selenium");
    }
}

