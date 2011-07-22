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
    public String getUrl() {
        return null;
    }

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
        if (getUrl() == null) {
            throw new IllegalArgumentException("No URL have been defined for this page");
        }
        getDriver().get(getUrl());
    }

    public static void assertAt(FluentPage fluent) {
        fluent.isAt();
    }

    public static void go(FluentPage page) {
        page.go();
    }
}
