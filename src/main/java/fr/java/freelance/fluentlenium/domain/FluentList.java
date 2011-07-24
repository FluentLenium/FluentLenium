package fr.java.freelance.fluentlenium.domain;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.sun.istack.internal.Nullable;
import org.openqa.selenium.NoSuchElementException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Map the list to a FluentList in order to offers some events like click(), submit(), value() ...
 */
public class FluentList<E extends FluentWebElement> extends ArrayList<E> implements FluentDefaultActions {

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
    public FluentWebElement first() {
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
        for (FluentWebElement fluentWebElement : this) {
            if (fluentWebElement.isDisplayed()) {
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
            for (FluentWebElement fluentWebElement : this) {
                if (fluentWebElement.isDisplayed()) {
                    if (with.length > id) {
                        value = with[id++];
                    } else {
                        value = with[with.length - 1];
                    }
                    fluentWebElement.clear();
                    fluentWebElement.text(value);
                }
            }
        }
    }

    /**
     * Clear all elements on the list
     * Only the visible elements are filled
     */
    public void clear() {
        for (FluentWebElement fluentWebElement : this) {
            if (fluentWebElement.isDisplayed()) {
                fluentWebElement.clear();
            }
        }
    }

    /**
     * submit on all elements on the list
     * Only the visible elements are submitted
     */
    public void submit() {
        for (FluentWebElement fluentWebElement : this) {
            if (fluentWebElement.isDisplayed()) {
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
        return Lists.transform(this, new Function<FluentWebElement, String>() {
            public String apply(@Nullable FluentWebElement webElement) {
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
        return Lists.transform(this, new Function<FluentWebElement, String>() {
            public String apply(@Nullable FluentWebElement webElement) {
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
        return Lists.transform(this, new Function<FluentWebElement, String>() {
            public String apply(@Nullable FluentWebElement webElement) {
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
        return Lists.transform(this, new Function<FluentWebElement, String>() {
            public String apply(@Nullable FluentWebElement webElement) {
                return webElement.getText();
            }
        });
    }


}
