package io.fluentlenium.assertj.custom;

import io.fluentlenium.core.FluentPage;import io.fluentlenium.core.domain.FluentList;import io.fluentlenium.core.domain.FluentWebElement;import org.assertj.core.api.AbstractAssert;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;

/**
 * Default implementation for asserting {@link FluentPage} objects.
 */
public class PageAssert extends AbstractAssert<PageAssert, FluentPage> implements PageStateAssert {

    public PageAssert(FluentPage actual) {
        super(actual, PageAssert.class);
    }

    @Override
    public PageAssert hasElement(FluentWebElement element) {
        if (!element.present()) {
            failWithMessage("Element " + element.toString() + " is not present on current page");
        }
        return this;
    }

    @Override
    public PageAssert hasElements(FluentList<? extends FluentWebElement> fluentList) {
        if (fluentList.isEmpty()) {
            failWithMessage("No element selected by '"
                    + fluentList.toString() + "' is present on the page.");
        }
        return this;
    }

    @Override
    public PageAssert hasElementDisplayed(FluentWebElement element) {
        if (!element.displayed()) {
            failWithMessage("Element " + element.toString() + " is not displayed on current page");
        }
        return this;
    }

    @Override
    public PageAssert hasTitle(String title) {
        try {
            String pageTitle = actual.getDriver().getTitle();
            if (!pageTitle.equals(title)) {
                failWithMessage("Current page title is " + pageTitle + ". Expected " + title);
            }
        } catch (NullPointerException e) {
            failWithMessage("Current page has no title");
        }
        return this;
    }

    @Override
    public PageAssert hasUrl(String url) {
        String pageUrl = actual.getDriver().getCurrentUrl();
        if (!pageUrl.equals(url)) {
            failWithMessage("Current page url is " + pageUrl + ". Expected " + url);
        }
        return this;
    }

    @Override
    public PageAssert hasPageSourceContaining(String value) {
        String pageSource = actual.getDriver().getPageSource();
        if (!pageSource.contains(value)) {
            failWithMessage("Current page source does not contain: " + value);
        }
        return this;
    }

    @Override
    public PageAssert hasExpectedUrl() {
        String url = actual.getUrl();

        if (url == null) {
            failWithMessage("Page has not defined @PageUrl");
        }

        actual.isAtUsingUrl(url);

        return this;
    }

    @Override
    public PageAssert hasExpectedElements() {
        By by = actual.getClassAnnotations().buildBy();

        if (by == null) {
            failWithMessage("Page has not defined @FindBy class level annotation");
        }

        actual.isAtUsingSelector(by);

        return this;
    }

    @Deprecated
    @Override
    public PageAssert isAt() {
        actual.isAt();
        return this;
    }
}
