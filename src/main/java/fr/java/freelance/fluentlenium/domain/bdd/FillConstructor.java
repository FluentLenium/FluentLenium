package fr.java.freelance.fluentlenium.domain.bdd;


import fr.java.freelance.fluentlenium.core.Fluent;
import fr.java.freelance.fluentlenium.domain.FluentDefaultActions;
import fr.java.freelance.fluentlenium.filter.Filter;
import org.openqa.selenium.WebDriver;

public class FillConstructor extends Fluent {
    private String cssSelector;
    private Filter[] filters;
    private FluentDefaultActions fluentList;

    public FillConstructor(String cssSelector, WebDriver webDriver, Filter... filters) {
        super(webDriver);
        this.cssSelector = cssSelector;
        this.filters = filters;
    }

    public FillConstructor(FluentDefaultActions list, WebDriver driver, Filter[] filters) {
        super(driver);
        this.filters = filters;
        this.fluentList = list;
    }

    public void with(String... values) {
        if (fluentList != null) {
            fluentList.text(values);
        } else {
            $(cssSelector, filters).text(values);
        }
    }
}
