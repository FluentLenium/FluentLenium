package fr.javafreelance.unit.initialization;

import fr.javafreelance.fluentlenium.core.FluentPage;

public class TestExternalPage extends FluentPage {


    @Override
    public String getUrl() {
        return "http://www.google.fr";
    }
}