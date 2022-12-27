package io.fluentlenium.pages;

import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.Page;

public class Page4 extends FluentPage {

    @Page
    private IndexPage indexPage;

    private Page5 page5;

    @Override
    public void initFluent(FluentControl control) {
        super.initFluent(control);
        page5 = newInstance(Page5.class);
    }

    public IndexPage getIndexPage() {
        return indexPage;
    }

    public Page5 getPage5() {
        return page5;
    }
}
