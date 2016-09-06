package org.fluentlenium.core.wait;

import com.google.common.base.Supplier;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.Search;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;

public class FluentWaitSupplierListMatcher extends AbstractWaitElementListMatcher {

    static final String SUPPLIER = "Supplier";

    private final Supplier<? extends FluentList<? extends FluentWebElement>> selector;

    protected FluentWaitSupplierListMatcher(Search search, FluentWait fluentWait, Supplier<? extends FluentList<? extends FluentWebElement>> selector) {
        super(search, fluentWait, SUPPLIER + " " + String.valueOf(selector));
        this.selector = selector;
    }

    @Override
    public FluentWaitSupplierListMatcher not() {
        FluentWaitSupplierListMatcher negatedConditions = new FluentWaitSupplierListMatcher(search, wait, selector);
        negatedConditions.negation = !negation;
        return negatedConditions;
    }

    @Override
    protected FluentList<? extends FluentWebElement> find() {
        try {
            return selector.get().now();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return search.getInstantiator().newFluentList();
        }
    }
}
