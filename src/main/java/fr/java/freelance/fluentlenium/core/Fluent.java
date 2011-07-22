package fr.java.freelance.fluentlenium.core;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.sun.istack.internal.Nullable;
import fr.java.freelance.fluentlenium.domain.FluentDefaultActions;
import fr.java.freelance.fluentlenium.domain.FluentList;
import fr.java.freelance.fluentlenium.domain.FluentWebElement;
import fr.java.freelance.fluentlenium.domain.bdd.FillConstructor;
import fr.java.freelance.fluentlenium.filter.Filter;
import fr.java.freelance.fluentlenium.filter.FilterType;
import fr.java.freelance.fluentlenium.filter.TextFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Util Class which offers some shortcut to webdriver methods
 */
public class Fluent {
    private WebDriver driver;

    public Fluent(WebDriver driver) {
        this.driver = driver;
    }

    public Fluent() {
    }

    protected void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    protected WebDriver getDriver() {
        return driver;
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
     * Create a filter by name
     *
     * @param name
     * @return
     */
    protected Filter withName(String name) {
        return new Filter(FilterType.NAME, name);
    }

    /**
     * Create a filter by id
     *
     * @param id
     * @return
     */
    protected Filter withId(String id) {
        return new Filter(FilterType.ID, id);
    }

    /**
     * Create a filter by text
     *
     * @param text
     * @return
     */
    protected Filter withText(String text) {
        return new Filter(FilterType.TEXT, text);
    }


    /**
     * Central methods to find elements on the page. Can provide some filters. Able to use css1, css2, css3, see WebDriver  restrictions
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentList $(String name, final Filter... filters) {
        StringBuilder sb = new StringBuilder(name);
        List<Filter> postFilterSelector = new ArrayList<Filter>();
        if (filters != null) {
            for (Filter selector : filters) {
                if (selector.getAttribut() != FilterType.TEXT.name()) {
                    sb.append(selector.toString());
                } else {
                    postFilterSelector.add(selector);
                }
            }
        }
        List<FluentWebElement> preFiltered = select(sb.toString());
        Collection<FluentWebElement> postFiltered = preFiltered;
        for (Filter selector : postFilterSelector) {
            postFiltered = Collections2.filter(postFiltered, new TextFilter(selector.getName()));
        }

        return new FluentList(postFiltered);
    }


    /**
     * Central methods a find element on the page, the number indicat the index of the desired element on the list. Can provide some filters. Able to use css1, css2, css3, see WebDriver  restrictions
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentWebElement $(String name, Integer number, final Filter... filters) {
        List<FluentWebElement> listFiltered = $(name, filters);
        return listFiltered.get(number);
    }




    private List<FluentWebElement> select(String cssSelector) {
        return Lists.transform(driver.findElements(By.cssSelector(cssSelector)), new Function<WebElement, FluentWebElement>() {
            public FluentWebElement apply(@Nullable WebElement webElement) {
                return new FluentWebElement((webElement));
            }
        });
    }

    /**
     * return the lists corresponding to the cssSelector with it filters
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentList find(String name, final Filter... filters) {
        return $(name, filters);
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
        return $(name, number, filters);
    }

    /**
     * Return the first elements corresponding to the name and the filters
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentWebElement findFirst(String name, final Filter... filters) {
        FluentList fluentList = $(name, filters);
        return fluentList.first();
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
     * Submit all elements that are in cssSelector with its filters
     * Be careful - only the visible elements are submitted
     *
     * @param cssSelector
     */
    public List<String> text(String cssSelector, Filter... filters) {
        return $(cssSelector, filters).getTexts();
    }

    /**
     * Value all elements that are in cssSelector with its filters
     * Be careful - only the visible elements are returned
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
     * @param fluentList
     */
    public void click(FluentDefaultActions fluentList) {
        fluentList.click();
    }

    /**
     * Submit all elements that are in the list
     * Be careful - only the visible elements are cleared
     *
     * @param fluentList
     */
    public void clear(FluentDefaultActions fluentList) {
        fluentList.clear();
    }

    /**
     * Submit all elements that are in the list
     * Be careful - only the visible elements are submitted
     *
     * @param fluentList
     */
    public void submit(FluentDefaultActions fluentList) {
        fluentList.submit();
    }




}
