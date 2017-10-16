package org.fluentlenium.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.integration.localtest.IntegrationFluentTest.SIZE_CHANGE_URL;

import java.util.concurrent.TimeUnit;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.FindBy;

public class JsAwaitTest extends IntegrationFluentTest {
    private static final String INPUT_NAME_LNAME_CSS_SELECTOR = "input[name=\"lname\"]";
    private static final String NEWROW_CSS_SELECTOR = ".newrow";
    private static final String NEWROW1_CSS_SELECTOR = ".newrow1";

    @Page
    SizeChangePage sizeChangePage;

    @Test
    public void waitForListChangeUsingCssSelectorDirectly() {
        goTo(SIZE_CHANGE_URL);
        await().atMost(7, TimeUnit.SECONDS).until($(NEWROW1_CSS_SELECTOR)).size().greaterThan(2);
    }

    @Test
    public void newListRowDisplayed() {
        goTo(SIZE_CHANGE_URL);
        await().until($(NEWROW_CSS_SELECTOR)).displayed();
    }

    @Test
    public void newListRowPresent() {
        goTo(SIZE_CHANGE_URL);
        await().until($(NEWROW_CSS_SELECTOR)).present();
    }

    @Test
    public void newListRowElementElementReferenceChangeIsAbleToWaitAfterDomParentChange() {
        goTo(sizeChangePage);
        FluentList<FluentWebElement> fluentWebElements = sizeChangePage.getNewRows();
        await().until(fluentWebElements).displayed();
        await().until(fluentWebElements).not().present();
    }

    @Test
    public void selectedAwaitTest() {
        goTo(SIZE_CHANGE_URL);
        await().until($("#mySelect option[value=\"mercedes\"]")).selected();
    }

    @Test
    public void inputEnabled() {
        goTo(SIZE_CHANGE_URL);
        await().until($(INPUT_NAME_LNAME_CSS_SELECTOR)).enabled();
    }

    @Test
    public void inputClickable() {
        goTo(SIZE_CHANGE_URL);
        await().until($(INPUT_NAME_LNAME_CSS_SELECTOR)).clickable();
    }

    @Test
    public void waitForListChangeUsingListVariable() {
        FluentList<FluentWebElement> myList = $(".row");
        goTo(SIZE_CHANGE_URL);
        await().until(myList).size().greaterThan(2);
    }

    @Test
    public void waitForListChangeFromPageObject() {
        goTo(sizeChangePage);
        await().until(sizeChangePage.getRows()).size().greaterThan(2);
    }

    @Test(expected = TimeoutException.class)
    public void waitForListChangeUsingListVariableExpectingTooMuch() {
        FluentList<FluentWebElement> myList = $(".row");
        goTo(SIZE_CHANGE_URL);
        await().atMost(2, TimeUnit.SECONDS).until(myList).size().greaterThan(3);
    }
}

class SizeChangePage extends FluentPage {
    @FindBy(css = ".row")
    private FluentList<FluentWebElement> rows;

    @FindBy(css = ".newrow")
    private FluentList<FluentWebElement> nerows;

    @Override
    public String getUrl() {
        return SIZE_CHANGE_URL;
    }

    @Override
    public void isAt() {
        assertThat(getDriver().getTitle()).isEqualTo("size change page");
    }

    FluentList<FluentWebElement> getRows() {
        return rows;
    }

    FluentList<FluentWebElement> getNewRows() {
        return nerows;
    }
}
