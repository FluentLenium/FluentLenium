package org.fest.assertions.fluentlenium.custom;

import org.fest.assertions.GenericAssert;
import org.fluentlenium.core.FluentPage;

public class PageAssert extends GenericAssert<PageAssert, FluentPage> {


    public PageAssert(FluentPage actual) {
        super(PageAssert.class, actual);
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
