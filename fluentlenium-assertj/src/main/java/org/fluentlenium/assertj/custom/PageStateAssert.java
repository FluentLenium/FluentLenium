package org.fluentlenium.assertj.custom;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;

public interface PageStateAssert {

    /**
     * Check if current page has given FluentWebElement present
     *
     * @param element FluentWebElement
     * @return page assertion object
     */
    PageAssert hasElement(FluentWebElement element);

    /**
     * Check if current page has at list one element of given FluentList present
     *
     * @param fluentList FluentList
     * @return page assertion object
     */
    PageAssert hasElements(FluentList<? extends FluentWebElement> fluentList);

    /**
     * Check if current page has given FluentWebElement displayed
     *
     * @param element FluentWebElement
     * @return page assertion object
     */
    PageAssert hasElementDisplayed(FluentWebElement element);

    /**
     * Check if current page has given title
     *
     * @param title String
     * @return page assertion object
     */
    PageAssert hasTitle(String title);

    /**
     * Check if current page has given url (String)
     *
     * @param url String
     * @return page assertion object
     */
    PageAssert hasUrl(String url);

    /**
     * Check if current page has page source containing given String
     *
     * @param expected String
     * @return page assertion object
     */
    PageAssert hasPageSourceContaining(String expected);

    /**
     * Check if current page has url defined by @PageUrl annotation
     *
     * @return page assertion object
     */
    PageAssert hasExpectedUrl();

    /**
     * Check if current page has element defined by @FindBy class level annotation
     *
     * @return page assertion object
     */
    PageAssert hasExpectedElements();

    /**
     * check if it is at the current page. Call the page.isAt() methods
     * Will be removed in future releases. Please use new {@link PageAssert}
     *
     * @return page assertion object
     */
    @Deprecated
    PageAssert isAt();
}
