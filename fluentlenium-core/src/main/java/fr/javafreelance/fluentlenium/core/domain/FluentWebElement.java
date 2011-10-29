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

package fr.javafreelance.fluentlenium.core.domain;

import fr.javafreelance.fluentlenium.core.action.FluentDefaultActions;
import fr.javafreelance.fluentlenium.core.filter.Filter;
import fr.javafreelance.fluentlenium.core.search.Search;
import fr.javafreelance.fluentlenium.core.search.SearchActions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

/**
 * WebElementCustom include a Selenium WebElement. It provides a lot of shortcuts to make selenium more fluent
 */
public class FluentWebElement implements FluentDefaultActions, SearchActions {
    private final WebElement webElement;
    private final Search search;

    public FluentWebElement(WebElement webElement) {
        this.webElement = webElement;
        this.search = new Search(webElement);
    }

    /**
     * Click on the element
     */
    public void click() {
        webElement.click();
    }

    /**
     * Clear the element
     */
    public void clear() {
        webElement.clear();
    }

    /**
     * Submit the element
     */
    public void submit() {
        webElement.submit();
    }

    /**
     * Set the text elelent
     *
     * @param text
     */
    public void text(String... text) {
        webElement.clear();
        if (text.length != 0) {
            webElement.sendKeys(text[0]);
        }
    }

    /**
     * return the name of the element
     *
     * @return
     */
    public String getName() {
        return webElement.getAttribute("name");
    }

    /**
     * return any value of custom attribute (generated=true will return "true" if getAttribute("generated") is called.
     *
     * @param attribute
     * @return
     */
    public String getAttribute(String attribute) {
        return webElement.getAttribute(attribute);
    }

    /**
     * return the id of the elements
     *
     * @return
     */
    public String getId() {
        return webElement.getAttribute("id");
    }

    /**
     * return the text of the element
     *
     * @return
     */
    public String getText() {
        return webElement.getText();
    }

    /**
     * return the value of the elements
     *
     * @return
     */
    public String getValue() {
        return webElement.getAttribute("value");
    }

    /**
     * return true if the element is displayed, otherway return false
     *
     * @return
     */
    public boolean isDisplayed() {
        return webElement.isDisplayed();
    }

    /**
     * return true if the element is enabled, otherway return false
     *
     * @return
     */
    public boolean isEnabled() {
        return webElement.isEnabled();
    }

    /**
     * return true if the element is selected, otherway false
     *
     * @return
     */
    public boolean isSelected() {
        return webElement.isSelected();
    }

    /**
     * return the tag name
     *
     * @return
     */
    public String getTagName() {
        return webElement.getTagName();
    }

    /**
     * return the size of the elements
     *
     * @return
     */
    public Dimension getSize() {
        return webElement.getSize();
    }

    /**
     * find elements into the childs with the corresponding filters
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentList find(String name, Filter... filters) {
        return search.find(name, filters);
    }

    /**
     * find elements into the childs with the corresponding filters at the given positiokn
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentWebElement find(String name, Integer number, Filter... filters) {
        return search.find(name, number, filters);
    }

    /**
     * find elements into the childs with the corresponding filters at the first position
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentWebElement findFirst(String name, Filter... filters) {
        return search.findFirst(name, filters);
    }
}
