package org.fluentlenium.core;

/**
 * Control a Page Object.
 *
 * @see FluentPage
 */
public interface FluentPageControl extends FluentControl {

    /**
     * Url of the Page
     *
     * @return page URL
     */
    String getUrl();

    /**
     * Should check if the navigator is on correct page.
     * <p>
     * For example :
     * assertThat(title()).isEqualTo("Page 1");
     * assertThat("#reallyImportantField").hasSize(1);
     */
    void isAt();

    /**
     * Go to the url defined in the page
     */
    void go();
}
