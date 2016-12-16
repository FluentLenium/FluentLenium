package org.fluentlenium.core.action;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.Select;

import java.util.Iterator;

/**
 * Select form filling features.
 *
 * @param <E> type of element to fill
 */
public class FillSelect<E extends FluentWebElement> extends BaseFill<E> {

    /**
     * Creates a new fill, from a list of element.
     *
     * @param list list of element to fill
     */
    public FillSelect(FluentList<E> list) {
        super(list);
    }

    /**
     * Creates a new fill, from a single element.
     *
     * @param element element to fill
     */
    public FillSelect(E element) {
        super(element);
    }

    @Override
    protected FluentList<E> getElements() {
        FluentList<E> elements = super.getElements();
        Iterator<E> iterator = elements.iterator();
        while (iterator.hasNext()) {
            FluentWebElement next = iterator.next();
            if (next.tagName() == null || !next.tagName().equalsIgnoreCase("select")) {
                iterator.remove();
            }
        }
        return elements;
    }

    /**
     * Select all options that have a value matching the argument for the Select element.
     *
     * @param value the select matching string
     * @return fill select constructor
     */
    public FillSelect withValue(String value) {
        FluentList<E> elements = getElements();

        if (elements.size() == 0) {
            throw new NoSuchElementException("No select element found");
        }

        for (FluentWebElement element : elements) {
            Select select = new Select(element.getElement());
            select.selectByValue(value);
        }
        return this;
    }

    /**
     * Select the option by its index for the Select element.
     *
     * @param index the select index value
     * @return fill select constructor
     */
    public FillSelect withIndex(int index) {
        boolean noSuchElement = true;
        for (E element : getElements()) {
            Select select = new Select(element.getElement());
            try {
                select.selectByIndex(index);
                noSuchElement = false;
            } catch (NoSuchElementException e) { // NOPMD EmptyCatchBlock
            }
        }

        if (noSuchElement) {
            throw new NoSuchElementException("No select element found with option index=" + index);
        }

        return this;
    }

    /**
     * Select all options that display text matching the argument for the Select element.
     *
     * @param text the select string part
     * @return fill select constructor
     */
    public FillSelect withText(String text) {
        FluentList<E> elements = getElements();

        if (elements.size() == 0) {
            throw new NoSuchElementException("No select element found");
        }

        for (FluentWebElement element : elements) {
            Select select = new Select(element.getElement());
            select.selectByVisibleText(text);
        }

        return this;
    }
}
