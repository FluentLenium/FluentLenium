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

package org.fluentlenium.core;

import org.openqa.selenium.WebDriver;

/**
 * Use the Page Object Pattern to have more resilient tests.
 */
public abstract class FluentPage extends Fluent {

    public FluentPage() {
        super();
    }

    public FluentPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Url of the Page
     *
     * @return page URL
     */
    public String getUrl() {
        return null;
    }

    /**
     * Should check if the navigator is on correct page.
     *
     * For example :
     * assertThat(title()).isEqualTo("Page 1");
     * assertThat("#reallyImportantField").hasSize(1);
     */
    public void isAt() {
    }

    /**
     * Go to the url defined in the page
     */
    public final void go() {
        goTo(getUrl());
    }


}
