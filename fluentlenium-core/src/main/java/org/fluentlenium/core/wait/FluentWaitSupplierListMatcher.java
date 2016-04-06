package org.fluentlenium.core.wait;

import com.google.common.base.Supplier;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.Search;

public class FluentWaitSupplierListMatcher extends AbstractWaitElementListMatcher {

    static final String SUPPLIER = "Supplier";

    private final Supplier<? extends FluentList<? extends FluentWebElement>> selector;

    public FluentWaitSupplierListMatcher(Search search, FluentWait fluentWait, Supplier<? extends FluentList<? extends FluentWebElement>> selector) {
        super(search, fluentWait, SUPPLIER + " " + String.valueOf(selector));
        this.selector = selector;
    }

    @Override
    FluentList<? extends FluentWebElement> find() {
        return selector.get();
    }
}
