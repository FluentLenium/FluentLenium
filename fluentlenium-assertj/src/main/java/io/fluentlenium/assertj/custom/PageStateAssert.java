package io.fluentlenium.assertj.custom;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.PageUrl;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;

/**
 * Interface for asserting the state of {@link FluentPage} objects.
 */
public interface PageStateAssert {

    /**
     * Check if current page has given FluentWebElement present.
     *
     * @param element the element
     * @return page assertion object
     */
    PageAssert hasElement(FluentWebElement element);

    /**
     * Check if current page has at least one element of given FluentList present.
     *
     * @param fluentList the element list
     * @return page assertion object
     */
    PageAssert hasElements(FluentList<? extends FluentWebElement> fluentList);

    /**
     * Check if current page has given FluentWebElement displayed.
     *
     * @param element the element
     * @return page assertion object
     */
    PageAssert hasElementDisplayed(FluentWebElement element);

    /**
     * Check if current page has given title.
     *
     * @param title the expected title
     * @return page assertion object
     */
    PageAssert hasTitle(String title);

    /**
     * Check if current page has given url string.
     *
     * @param url the expected URL string
     * @return page assertion object
     */
    PageAssert hasUrl(String url);

    /**
     * Check if current page has page source containing given String.
     *
     * @param value the expected string value
     * @return page assertion object
     */
    PageAssert hasPageSourceContaining(String value);

    /**
     * Check if current page has url defined by its {@link PageUrl} annotation.
     *
     * @return page assertion object
     */
    PageAssert hasExpectedUrl();

    /**
     * Check if current page has element defined by its {@link org.openqa.selenium.support.FindBy},
     * {@link org.openqa.selenium.support.FindBys} or
     * {@link org.openqa.selenium.support.FindAll} class level annotation.
     *
     * @return page assertion object
     */
    PageAssert hasExpectedElements();

    /**
     * Check if it is at the current page. Call the page.isAt() methods
     * Will be removed in future releases. Please use new {@link PageAssert}.
     *
     * @return page assertion object
     */
    @Deprecated
    PageAssert isAt();
}
