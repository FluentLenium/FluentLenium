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

package org.fluentlenium.core.action;

import org.fluentlenium.core.filter.Filter;
import org.openqa.selenium.WebDriver;

public class FillConstructor extends org.fluentlenium.core.Fluent {
    private String cssSelector;
    private Filter[] filters;
    private FluentDefaultActions fluentList;

    public FillConstructor(String cssSelector, WebDriver webDriver, Filter... filters) {
        super(webDriver);
        this.cssSelector = cssSelector;
        this.filters = filters;
    }
    
    public FillConstructor(WebDriver webDriver, Filter... filters) {
        super(webDriver);
        this.cssSelector = "*";
        this.filters = filters;
    }

    public FillConstructor(FluentDefaultActions list, WebDriver driver, Filter... filters) {
        super(driver);
        this.filters = filters.clone();
        this.fluentList = list;
    }

    /**
     * Set the values params as text for the fluentList or search a new list with the css selector and filters and add the values param on it
     *
     * @param values
     */
    public FillConstructor with(String... values) {
        if (fluentList != null) {
            fluentList.text(values);
        } else {
            $(cssSelector, filters).text(values);
        }
        return this;
    }
}
