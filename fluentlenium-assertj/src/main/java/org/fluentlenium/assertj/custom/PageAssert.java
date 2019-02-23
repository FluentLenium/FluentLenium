package org.fluentlenium.assertj.custom;

import org.assertj.core.api.AbstractAssert;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;

public class PageAssert extends AbstractAssert<PageAssert, FluentPage> implements PageStateAssert {

    public PageAssert(FluentPage actual) {
        super(actual, PageAssert.class);
    }

    @Override
    public PageAssert hasElement(FluentWebElement element) {
        if (!element.present()) {
            failWithMessage("Element "
                    + element.toString() + " is not present on current page");
        }
        return this;
    }

    @Override
    public PageAssert hasElements(FluentList<? extends FluentWebElement> fluentList) {
        if (fluentList.isEmpty()) {
            failWithMessage("List "
                    + fluentList.toString() + " is empty");
        }
        return this;
    }

    @Override
    public PageAssert hasElementDisplayed(FluentWebElement element) {
        if (!element.displayed()) {
            failWithMessage("Element "
                    + element.toString() + " is not displayed on current page");
        }
        return this;
    }

    @Override
    public PageAssert hasTitle(String title) {
        try {
            actual.getDriver().getTitle();
        } catch (NullPointerException e) {
            failWithMessage("Current page has no title");
        }

        String pageTitle = actual.getDriver().getTitle();
        if (!pageTitle.equals(title)) {
            failWithMessage("Current page title is " + pageTitle
                    + ". Expected " + title);
        }
        return this;
    }

    @Override
    public PageAssert hasUrl(String url) {
        String pageUrl = actual.getDriver().getCurrentUrl();
        if (!pageUrl.equals(url)) {
            failWithMessage("Current page url is " + pageUrl
                    + ". Expected " + url);
        }
        return this;
    }

    @Override
    public PageAssert hasPageSourceContaining(String expected) {
        String pageSource = actual.getDriver().getPageSource();
        if (!pageSource.contains(expected)) {
            failWithMessage("Current page source does not contain: " + expected);
        }
        return this;
    }

    @Override
    public PageAssert hasExpectedUrl() {
        String url = actual.getUrl();

        if (url == null) {
            failWithMessage("Page has not defined @PageUrl");
        }

        if (url != null) {
            actual.isAtUsingUrl(url);
        }

        return this;
    }

    @Override
    public PageAssert hasExpectedElements() {
        By by = actual.getClassAnnotations().buildBy();

        if (by == null) {
            failWithMessage("Page has not defined @FindBy class level annotation");
        }

        if (by != null) {
            actual.isAtUsingSelector(by);
        }

        return this;
    }

    @Deprecated
    @Override
    public PageAssert isAt() {
        actual.isAt();
        return this;
    }

}
