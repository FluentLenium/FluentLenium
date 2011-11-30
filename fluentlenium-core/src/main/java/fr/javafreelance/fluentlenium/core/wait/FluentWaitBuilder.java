package fr.javafreelance.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import fr.javafreelance.fluentlenium.core.filter.Filter;
import fr.javafreelance.fluentlenium.core.filter.FilterType;
import fr.javafreelance.fluentlenium.core.search.Search;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class FluentWaitBuilder {
    String selector;
    private FluentWait wait;
    private Search search;
    private List<Filter> filter = new ArrayList<Filter>();

    public FluentWaitBuilder(Search search, FluentWait fluentWait, String selector) {
        this.selector = selector;
        wait = fluentWait;
        this.search = search;

    }


    public void isPresent() {
        Predicate isPresent = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                int size;
                if (filter.size() > 0) {
                    size = search.find(selector, (Filter[])filter.toArray(new Filter[filter.size()])).size();
                } else {
                    size = search.find(selector).size();
                }
                return size > 0;
            }
        };
        wait.until(isPresent);
    }

    public void hasSize(final int size) {
        Predicate isPresent = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                int size1;
                if (filter.size() > 0) {
                    size1 = search.find(selector, (Filter[])filter.toArray(new Filter[filter.size()])).size();
                } else {
                    size1 = search.find(selector).size();
                }
                return size1 == size;

            }
        };
        wait.until(isPresent);
    }

    /**
     * WARNING - SHould be change in a next version to hasAttribute("myAttribute").value("myValue")
     * @param attribute
     * @param value
     */
    public void hasAttribute(final String attribute, final String value) {
        Predicate isPresent = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                if (filter.size() > 0) {
                    return search.find(selector,(Filter[])filter.toArray(new Filter[filter.size()])).getAttributes("attribute").contains(value);
                } else {
                    return search.find(selector).getAttributes("attribute").contains(value);
                }
            }
        };
        wait.until(isPresent);
    }

    /**
     * check if the FluentWait has the corresponding text
     * @param value
     */
    public void hasText(final String value) {
        Predicate isPresent = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                int size1;
                if (filter.size() > 0) {
                    return search.find(selector,(Filter[])filter.toArray(new Filter[filter.size()])).getTexts().contains(value);
                } else {
                    return search.find(selector).getTexts().contains(value);
                }
            }
        };
        wait.until(isPresent);
    }
         /**
     * check if the FluentWait has the corresponding id
     * @param value
     */
    public void hasId(final String value) {
        Predicate isPresent = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                int size1;
                if (filter.size() > 0) {
                    return search.find(selector,(Filter[])filter.toArray(new Filter[filter.size()])).getIds().contains(value);
                } else {
                    return search.find(selector).getIds().contains(value);
                }
            }
        };
        wait.until(isPresent);
    }
                    /**
     * check if the FluentWait has the corresponding name
     * @param value
     */
    public void hasName(final String value) {
        Predicate isPresent = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                int size1;
                if (filter.size() > 0) {
                    return search.find(selector,(Filter[])filter.toArray(new Filter[filter.size()])).getNames().contains(value);
                } else {
                    return search.find(selector).getNames().contains(value);
                }
            }
        };
        wait.until(isPresent);
    }

    public FluentWaitBuilder withText(final String value) {
        filter.add(fr.javafreelance.fluentlenium.core.filter.FilterConstructor.withText(value));
        return this;
    }

    public FluentWaitBuilder withId(final String value) {
        filter.add(fr.javafreelance.fluentlenium.core.filter.FilterConstructor.withId(value));
        return this;
    }

    public FluentWaitBuilder withName(final String value) {
        filter.add(fr.javafreelance.fluentlenium.core.filter.FilterConstructor.withName(value));
        return this;
    }


    /**
     * Create a filter builder for the attribute
     *
     * @param attribute
     * @return
     */
    public FluentLeniumWaitBuilder with(String attribute) {
        return new FluentLeniumWaitBuilder(this, attribute);
    }

    /**
     * Create a filter builder for the attribute by name
     *
     * @param
     * @return
     */
    public FluentLeniumWaitBuilder withName() {
        return new FluentLeniumWaitBuilder(this, FilterType.NAME);
    }

    /**
     * Create a filter builder for the attribute by id
     *
     * @param
     * @return
     */
    public FluentLeniumWaitBuilder withId() {
        return new FluentLeniumWaitBuilder(this, FilterType.ID);
    }

    /**
     * Create a filter builder for the attribute by text
     *
     * @param
     * @return
     */
    public FluentLeniumWaitBuilder withText() {
        return new FluentLeniumWaitBuilder(this, FilterType.TEXT);
    }


    public void addFilter(Filter filter) {
       this.filter.add( filter);
    }
}
