package org.fluentlenium.pages;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;

public class Page5 extends FluentPage {

    @Page
    private IndexPage indexPage;

    public IndexPage getIndexPage() {
        return indexPage;
    }
}
