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

package org.fluentlenium.core.domain;

import org.fluentlenium.core.FluentThread;
import org.fluentlenium.core.action.FillConstructor;
import org.fluentlenium.core.action.FluentDefaultActions;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.search.Search;
import org.fluentlenium.core.search.SearchActions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

/**
 * WebElementCustom include a Selenium WebElement. It provides a lot of shortcuts to make selenium more fluent
 */
public class FluentWebElement implements FluentDefaultActions<FluentWebElement>, SearchActions<FluentWebElement> {
    private final WebElement webElement;
    private final Search search;

    public FluentWebElement(WebElement webElement) {
        this.webElement = webElement;
        this.search = new Search(webElement);
    }

    /**
     * Click on the element
     * @return fluent web element
     */
    public FluentWebElement click() {
        webElement.click();
        return this;
    }

    /**
     * Double Click on the element
     * @return fluent web element
     */
    public FluentWebElement doubleClick() {
        Action action = new Actions(FluentThread.get().getDriver()).doubleClick(webElement).build();
        action.perform();
        return this;
    }

    /**
     * Clear the element
     * @return fluent web element
     */
    public FluentWebElement clear() {
        if (!isInputOfTypeFile()) {
            webElement.clear();
        }
        return this;
    }

    /**
     * Submit the element
     * @return fluent web element
     */
    public FluentWebElement submit() {
        webElement.submit();
        return this;
    }

    /**
     * Set the text element
     *
     * @param text value to set
     * @return fluent web element
     */
    public FluentWebElement text(String... text) {
        clear();
        if (text.length != 0) {
            webElement.sendKeys(text[0]);
        }
        return this;
    }

    /**
     * return the name of the element
     *
     * @return name of the element
     */
    public String getName() {
        return webElement.getAttribute("name");
    }

    /**
     * return any value of custom attribute (generated=true will return "true" if getAttribute("generated") is called.
     *
     * @param attribute custom attribute name
     * @return name value
     */
    public String getAttribute(String attribute) {
        return webElement.getAttribute(attribute);
    }

    /**
     * return the id of the elements
     *
     * @return id of element
     */
    public String getId() {
        return webElement.getAttribute("id");
    }

    /**
     * return the visible text of the element
     *
     * @return text of element
     */
    public String getText() {
        return webElement.getText();
    }

    /**
     * return the text content of the element (even invisible through textContent attribute)
     *
     * @return text content of element
     */
    public String getTextContent() {
        return webElement.getAttribute("textContent");
    }

    /**
     * return the value of the elements
     *
     * @return value of attribute
     */
    public String getValue() {
        return webElement.getAttribute("value");
    }

    /**
     * return true if the element is displayed, other way return false
     *
     * @return boolean value of isDisplayed check
     */
    public boolean isDisplayed() {
        return webElement.isDisplayed();
    }

    /**
     * return true if the element is enabled, other way return false
     *
     * @return boolean value of isEnabled check
     */
    public boolean isEnabled() {
        return webElement.isEnabled();
    }

    /**
     * return true if the element is selected, other way false
     *
     * @return boolean value of isSelected check
     */
    public boolean isSelected() {
        return webElement.isSelected();
    }

    /**
     * return the tag name
     *
     * @return string value of tag name
     */
    public String getTagName() {
        return webElement.getTagName();
    }

    /**
     * return the webElement
     *
     * @return web element
     */
    public WebElement getElement() {
        return webElement;
    }

    /**
     * return the size of the elements
     *
     * @return dimension/size of element
     */
    public Dimension getSize() {
        return webElement.getSize();
    }

    /**
     * find elements into the children with the corresponding filters
     *
     * @param name name of element
     * @param filters filters set
     * @return list of Fluent web elements
     */
    public FluentList<FluentWebElement> find(String name, Filter... filters) {
        return search.find(name, filters);
    }
    
    /**
     * find elements in the children with the corresponding filters
     *
     * @param filters filters set
     * @return list of Fluent web elements
     */
    public FluentList<FluentWebElement> find(Filter... filters) {
        return search.find(filters);
    }

    /**
     * find elements into the children with the corresponding filters at the given position
     *
     * @param name name of element
     * @param filters filters set
     * @return fluent web element
     */
    public FluentWebElement find(String name, Integer number, Filter... filters) {
        return search.find(name, number, filters);
    }

    /**
     * find element in the children with the corresponding filters at the given position
     * 
     * @param number index of element
     * @param filters filters set
     * @return fluent web element
     */
    public FluentWebElement find(Integer number, Filter... filters) {
        return search.find(number, filters);
    }

    /**
     * find elements into the children with the corresponding filters at the first position
     *
     * @param name name of element
     * @param filters filters set
     * @return fluent web element
     */
    public FluentWebElement findFirst(String name, Filter... filters) {
        return search.findFirst(name, filters);
    }
    
    /**
     * find element in the children with the corresponding filters at the first position
     * 
     * @param filters filters set
     * @return fluent web element
     */
    public FluentWebElement findFirst(Filter... filters) {
        return search.findFirst(filters);
    }

    /**
     *  return the innerHTML content of the web element
     *  does not work with HTMLUnit
     *  @return the underlying html content
     */
    public String html() {
        return webElement.getAttribute("innerHTML");
    }

    /**
     * Construct a FillConstructor in order to allow easy fill
     * Be careful - only the visible elements are filled
     * @return fill constructor
     */
    public FillConstructor fill() {
      return new FillConstructor(this, FluentThread.get().getDriver());
    }

    /**
     * This method return true if the current FluentWebElement is an input of type file
     * @return boolean value for is input file type
     */
    private boolean isInputOfTypeFile(){
        return ("input".equalsIgnoreCase(this.getTagName()) &&
            "file".equalsIgnoreCase(this.getAttribute("type")));
    }
}
