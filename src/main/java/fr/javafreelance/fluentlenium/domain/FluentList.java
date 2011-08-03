package fr.javafreelance.fluentlenium.domain;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import fr.javafreelance.fluentlenium.action.FluentDefaultActions;
import fr.javafreelance.fluentlenium.filter.Filter;
import fr.javafreelance.fluentlenium.search.SearchActions;
import org.openqa.selenium.NoSuchElementException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Map the list to a FluentList in order to offers some events like click(), submit(), value() ...
 */
public class FluentList<E extends FluentWebElement> extends ArrayList<E> implements FluentDefaultActions, SearchActions {

    public FluentList(Collection<E> listFiltered) {
        super(listFiltered);
    }

    /**
     * Return the first element of the list
     * If none, return NoSuchElementException
     *
     * @return
     * @throws NoSuchElementException
     */
    public E first() {
        if (this.size() == 0) {
            throw new NoSuchElementException("Element not found");
        }
        return this.get(0);
    }

    /**
     * Click on all elements on the list
     * Only the visible elements are filled
     */
    public void click() {
        for (E fluentWebElement : this) {
            if (fluentWebElement.isEnabled()) {
                fluentWebElement.click();
            }
        }
    }

    /**
     * Fill  all elements on the list with the corresponding cell in the with table.
     * Only the visible elements are filled
     * If there is more elements on the list than in the with table, the last element of the table is repeated
     */
    public void text(String... with) {
        if (with.length > 0) {
            int id = 0;
            String value;
            for (E fluentWebElement : this) {
                if (fluentWebElement.isDisplayed()) {
                    if (with.length > id) {
                        value = with[id++];
                    } else {
                        value = with[with.length - 1];
                    }
                    if (fluentWebElement.isEnabled()) {
                        fluentWebElement.clear();
                        fluentWebElement.text(value);
                    }
                }
            }
        }
    }

    /**
     * Clear all elements on the list
     * Only the visible elements are filled
     */
    public void clear() {
        for (E fluentWebElement : this) {
            if (fluentWebElement.isEnabled()) {
                fluentWebElement.clear();
            }
        }
    }

    /**
     * submit on all elements on the list
     * Only the visible elements are submitted
     */
    public void submit() {
        for (E fluentWebElement : this) {
            if (fluentWebElement.isEnabled()) {
                fluentWebElement.submit();
            }
        }
    }

    /**
     * Return the value of elements on the list
     *
     * @return
     */
    public List<String> getValues() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getValue();
            }
        });
    }

    /**
     * Return the id of elements on the list
     *
     * @return
     */
    public List<String> getIds() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getId();
            }
        });
    }

    /**
     * Return the name of elements on the list
     *
     * @return
     */
    public List<String> getNames() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getName();
            }
        });
    }

    /**
     * Return the texts of list elements
     *
     * @return
     */
    public List<String> getTexts() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getText();
            }
        });
    }

    /**
     * find elements into the childs with the corresponding filters
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentList find(String name, Filter... filters) {
        final FluentList<E> aggregateAllResults = new FluentList(new ArrayList<E>());
        for (E e : this) {
            aggregateAllResults.addAll(e.find(name, filters));
        }
        return aggregateAllResults;
    }

    /**
     * find elements into the childs with the corresponding filters at the position indicated by the number
     *
     * @param name
     * @param number
     * @param filters
     * @return
     */
    public E find(String name, Integer number, Filter... filters) {
        FluentList<E> fluentList = find(name, filters);
        if (number >= fluentList.size()) {
            throw new NoSuchElementException("No such element with position :" + number + ". Number of elements available :" + fluentList.size());
        }
        return fluentList.get(number);
    }

    /**
     * find elements into the childs with the corresponding filters at the first position
     *
     * @param name
     * @param filters
     * @return
     */
    public E findFirst(String name, Filter... filters) {
        return find(name, 0, filters);
    }
}
