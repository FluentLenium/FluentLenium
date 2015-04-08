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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FillSelectConstructor extends org.fluentlenium.core.Fluent {
    private String cssSelector;
    private Filter[] filters;

    public FillSelectConstructor(String cssSelector, WebDriver webDriver, Filter... filters) {
        super(webDriver);
        this.cssSelector = cssSelector;
        this.filters = filters;
    }
    
    public FillSelectConstructor(WebDriver webDriver, Filter... filters) {
        super(webDriver);
        this.cssSelector = "*";
        this.filters = filters;
    }

    /**
     * Select all options that have a value matching the argument for the Select element.
     *
     * @param value
     */
    public FillSelectConstructor withValue(String value) {
        WebElement selectElement = findFirst(cssSelector, filters).getElement();
        Select select = new Select(selectElement);
        select.selectByValue(value);
        return this;
    }

    /**
     * Select the option by its index for the Select element.
     *
     * @param index
     */
    public FillSelectConstructor withIndex(int index) {
        WebElement selectElement = findFirst(cssSelector, filters).getElement();
        Select select = new Select(selectElement);
        select.selectByIndex(index);
        return this;
    }

    /**
     * Select all options that display text matching the argument for the Select element.
     *
     * @param text
     */
    public FillSelectConstructor withText(String text) {
        WebElement selectElement = findFirst(cssSelector, filters).getElement();
        Select select = new Select(selectElement);
        select.selectByVisibleText(text);
        return this;
    }
}
