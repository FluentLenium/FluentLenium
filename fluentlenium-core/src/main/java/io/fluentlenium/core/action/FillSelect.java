package io.fluentlenium.core.action;

import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.Select;

import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;

import java.util.function.Consumer;

/**
 * Provides functionality to select values in {@code <select>} elements.
 * <p>
 * Documentation can also be found at the FluentLenium website at
 * <a href="https://fluentlenium.io/docs/locators/#filling-forms">Locators / Filling Forms</a>.
 *
 * @param <E> type of element to fill
 */
public class FillSelect<E extends FluentWebElement> extends BaseFill<E> {

    /**
     * Creates a new fill from a list of elements.
     *
     * @param list list of elements to fill
     */
    public FillSelect(FluentList<E> list) {
        super(list);
    }

    /**
     * Creates a new fill from a single element.
     *
     * @param element element to fill
     */
    public FillSelect(E element) {
        super(element);
    }

    /**
     * Excludes elements that don't have a tag name or their tag name is not {@code select},
     * so that elements with only {@code select} tags are tried to be filled.
     */
    @Override
    protected FluentList<E> getElements() {
        FluentList<E> elements = super.getElements();
        elements.removeIf(next -> next.tagName() == null || !next.tagName().equalsIgnoreCase("select"));
        return elements;
    }

    /**
     * Select an option by its index for the Select element.
     *
     * @param index the select index value
     * @return this FillSelect instance
     * @throws NoSuchElementException if no select element is found
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
     * Select all options that have a value matching the argument for the Select element.
     *
     * @param value the value to select by
     * @return this FillSelect instance
     * @throws NoSuchElementException if no select element is found
     */
    public FillSelect withValue(String value) {
        return doSelect(select -> select.selectByValue(value));
    }

    /**
     * Select all options that display text matching the argument for the Select element.
     *
     * @param text the visible text to select by
     * @return this FillSelect instance
     * @throws NoSuchElementException if no select element is found
     */
    public FillSelect withText(String text) {
        return doSelect(select -> select.selectByVisibleText(text));
    }

    private FillSelect doSelect(Consumer<Select> elementSelector) {
        FluentList<E> elements = getElements();

        if (elements.isEmpty()) {
            throw new NoSuchElementException("No select element found");
        }

        for (FluentWebElement element : elements) {
            Select select = new Select(element.getElement());
            elementSelector.accept(select);
        }

        return this;
    }
}
