package org.fluentlenium.core.wait;

import com.google.common.base.Supplier;
import org.fluentlenium.core.conditions.FluentConditions;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.Search;

public class FluentWaitSupplierMatcher extends AbstractWaitElementMatcher {

    static final String SUPPLIER = "Supplier";

    private final Supplier<? extends FluentWebElement> selector;

    public FluentWaitSupplierMatcher(Search search, FluentWait fluentWait, Supplier<? extends FluentWebElement> selector) {
        super(search, fluentWait, SUPPLIER + " " + String.valueOf(selector));
        this.selector = selector;
    }

    @Override
    public FluentWaitSupplierMatcher not() {
        FluentWaitSupplierMatcher negatedConditions = new FluentWaitSupplierMatcher(search, wait, selector);
        negatedConditions.negation = !negation;
        return negatedConditions;
    }

    @Override
    protected FluentList<FluentWebElement> find() {
        FluentWebElement fluentWebElement = selector.get();
        FluentListImpl<FluentWebElement> elements = new FluentListImpl<>();
        if (fluentWebElement != null) {
            elements.add(fluentWebElement);
        }
        return elements;
    }
}
