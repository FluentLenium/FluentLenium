package fr.java.freelance.fluentlenium.core;

import org.openqa.selenium.WebDriver;

/**
 * Use the Page Object Pattern to have more resilient tests.
 */
public abstract class FluentPage extends Fluent {


    public FluentPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Url of the Page
     *
     * @return
     */
    public abstract String getUrl();

    /**
     * Should shake if the navigator is on correct page.
     * <p/>
     * For example :
     * assertThat(title()).isEqualTo("Page 1");
     * assertThat("#reallyImportantField").hasSize(1);
     */
    public void isAt() {
    }

    /**
     * Go to the url defined in the page
     */
    public final void go() {
        getDriver().get(getUrl());
    }

    public static void assertOn(FluentPage fluent) {
        fluent.isAt();
    }

    public static void go(FluentPage page) {
        page.go();
    }
}
