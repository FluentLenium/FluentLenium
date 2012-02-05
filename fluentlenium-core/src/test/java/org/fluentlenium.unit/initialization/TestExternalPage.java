package org.fluentlenium.unit.initialization;

import org.fluentlenium.core.FluentPage;

public class TestExternalPage extends FluentPage {


    @Override
    public String getUrl() {
        return "http://www.google.fr";
    }
}