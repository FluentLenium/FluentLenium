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

package fr.javafreelance.fluentlenium.core;

import fr.javafreelance.fluentlenium.core.action.FillConstructor;
import fr.javafreelance.fluentlenium.core.action.FluentDefaultActions;
import fr.javafreelance.fluentlenium.core.domain.FluentList;
import fr.javafreelance.fluentlenium.core.domain.FluentWebElement;
import fr.javafreelance.fluentlenium.core.filter.Filter;
import fr.javafreelance.fluentlenium.core.search.Search;
import fr.javafreelance.fluentlenium.core.search.SearchActions;
import fr.javafreelance.fluentlenium.core.wait.FluentLeniumWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * Util Class which offers some shortcut to webdriver methods
 */
public abstract class Fluent implements SearchActions {
    private WebDriver driver;
    private Search search;
    private FluentLeniumWait wait;

    public Fluent(WebDriver driver) {
        this.driver = driver;
        this.search = new Search(driver);
        this.wait = new FluentLeniumWait(driver,search);
    }

    public Fluent() {
    }

    protected final void setDriver(WebDriver driver) {
        this.driver = driver;
        this.search = new Search(driver);
        if (driver != null) {
            this.wait = new FluentLeniumWait(driver,search);
        }
    }

    protected WebDriver getDriver() {
        return driver;
    }

    public FluentLeniumWait await() {
        return wait;
    }


    /**
     * Return the title of the page
     *
     * @return
     */
    protected String title() {
        return driver.getTitle();
    }

    /**
     * Return the url of the page
     *
     * @return
     */
    protected String url() {
        return driver.getCurrentUrl();
    }

    /**
     * Return the source of the page
     *
     * @return
     */
    protected String pageSource() {
        return driver.getPageSource();
    }

    public void executeScript(String script) {
        ((JavascriptExecutor) driver).executeScript(script);
    }

    /**
     * Central methods to find elements on the page. Can provide some filters. Able to use css1, css2, css3, see WebDriver  restrictions
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentList $(String name, final Filter... filters) {
        return search.find(name, filters);
    }


    /**
     * Central methods a find element on the page, the number indicat the index of the desired element on the list. Can provide some filters. Able to use css1, css2, css3, see WebDriver  restrictions
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentWebElement $(String name, Integer number, final Filter... filters) {
        return search.find(name, number, filters);
    }


    /**
     * return the lists corresponding to the cssSelector with it filters
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentList find(String name, final Filter... filters) {
        return search.find(name, filters);
    }

    /**
     * Return the elements at the numner position into the the lists corresponding to the cssSelector with it filters
     *
     * @param name
     * @param number
     * @param filters
     * @return
     */
    public FluentWebElement find(String name, Integer number, final Filter... filters) {
        return search.find(name, number, filters);
    }

    /**
     * Return the first elements corresponding to the name and the filters
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentWebElement findFirst(String name, final Filter... filters) {
        return search.findFirst(name, filters);
    }

    /**
     * Construct a FillConstructor in order to allow easy fill
     * Be careful - only the visible elements are filled
     *
     * @param cssSelector
     */
    public FillConstructor fill(String cssSelector, Filter... filters) {
        return new FillConstructor(cssSelector, getDriver(), filters);
    }

    /**
     * Construct a FillConstructor in order to allow easy fill
     * Be careful - only the visible elements are filled
     *
     * @param list
     */
    public FillConstructor fill(FluentDefaultActions list, Filter... filters) {
        return new FillConstructor(list, getDriver(), filters);
    }

    /**
     * click all elements that are in cssSelector with its filters
     * Be careful - only the visible elements are clicked
     *
     * @param cssSelector
     */
    public void click(String cssSelector, Filter... filters) {
        $(cssSelector, filters).click();
    }

    /**
     * Submit all elements that are in cssSelector with its filters
     * Be careful - only the visible elements are cleared
     *
     * @param cssSelector
     */
    public void clear(String cssSelector, Filter... filters) {
        $(cssSelector, filters).clear();
    }

    /**
     * Submit all elements that are in cssSelector with its filters
     * Be careful - only the visible elements are submitted
     *
     * @param cssSelector
     */
    public void submit(String cssSelector, Filter... filters) {
        $(cssSelector, filters).submit();
    }

    /**
     * get a list all elements that are in cssSelector with its filters
     * Be careful - only the visible elements are submitted
     * //TODO UTILITY ? Deprecated ?
     *
     * @param cssSelector
     */
    public List<String> text(String cssSelector, Filter... filters) {
        return $(cssSelector, filters).getTexts();
    }

    /**
     * Value all elements that are in cssSelector with its filters
     * Be careful - only the visible elements are returned
     * //TODO UTILITY ? Deprecated ?
     *
     * @param cssSelector
     */
    public List<String> value(String cssSelector, Filter... filters) {
        return $(cssSelector, filters).getValues();
    }


    /**
     * click all elements that are in the list
     * Be careful - only the visible elements are clicked
     *
     * @param fluentObject
     */
    public void click(FluentDefaultActions fluentObject) {
        fluentObject.click();
    }

    /**
     * Submit all elements that are in the list
     * Be careful - only the visible elements are cleared
     *
     * @param fluentObject
     */
    public void clear(FluentDefaultActions fluentObject) {
        fluentObject.clear();
    }

    /**
     * Submit all elements that are in the list
     * Be careful - only the visible elements are submitted
     *
     * @param fluentObject
     */
    public void submit(FluentDefaultActions fluentObject) {
        fluentObject.submit();
    }


}
