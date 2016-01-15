package org.fest.assertions.fluentlenium.custom;

import org.fest.assertions.GenericAssert;
import org.fluentlenium.core.FluentPage;

/**
 * @deprecated fest-assert has not been maintained since 2013. This module will be removed from FluentLenium in a next future. Use fluentlenium-assertj instead.
 */
@Deprecated
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
