package org.fluentlenium.assertj.custom;

import org.assertj.core.api.AbstractAssert;
import org.fluentlenium.core.FluentPage;

public class PageAssert extends AbstractAssert<PageAssert, FluentPage> {

    public PageAssert(FluentPage actual) {
        super(actual, PageAssert.class);
    }

    /**
     * check if it is at the current page. Call the page.isAt() methods
     *
     * @return
     */
    public PageAssert isAt() {
        actual.isAt();
        return this;
    }

}
