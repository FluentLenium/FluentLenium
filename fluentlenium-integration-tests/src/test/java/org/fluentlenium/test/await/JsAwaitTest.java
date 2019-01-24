package org.fluentlenium.test.await;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.test.IntegrationFluentTest.SIZE_CHANGE_URL;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsAwaitTest extends IntegrationFluentTest {
    private static final String INPUT_NAME_LNAME_CSS_SELECTOR = "input[name=\"lname\"]";
    private static final String NEWROW_CSS_SELECTOR = ".newrow";
    private static final String NEWROW1_CSS_SELECTOR = ".newrow1";

    @Page
    SizeChangePage1 sizeChangePage;

    @Test
    void waitForListChangeUsingCssSelectorDirectly() {
        goTo(SIZE_CHANGE_URL);
        await().atMost(7, TimeUnit.SECONDS).until($(NEWROW1_CSS_SELECTOR)).size().greaterThan(2);
    }

    @Test
    void newListRowDisplayed() {
        goTo(SIZE_CHANGE_URL);
        await().until(() -> $(NEWROW_CSS_SELECTOR).first().displayed());
    }

    @Test
    void newListRowPresent() {
        goTo(SIZE_CHANGE_URL);
        await().until(() -> $(NEWROW_CSS_SELECTOR).present());
    }

    @Test
    void newListRowElementElementReferenceChangeIsAbleToWaitAfterDomParentChange() {
        goTo(sizeChangePage);
        FluentList<FluentWebElement> fluentWebElements = sizeChangePage.getNewRows();
        await().until(() -> fluentWebElements.first().displayed());
        await().until(() -> !fluentWebElements.present());
    }

    @Test
    void selectedAwaitTest() {
        goTo(SIZE_CHANGE_URL);
        await().until($("#mySelect option[value=\"mercedes\"]")).selected();
    }

    @Test
    void inputEnabled() {
        goTo(SIZE_CHANGE_URL);
        await().until($(INPUT_NAME_LNAME_CSS_SELECTOR)).enabled();
    }

    @Test
    void inputClickable() {
        goTo(SIZE_CHANGE_URL);
        await().until($(INPUT_NAME_LNAME_CSS_SELECTOR)).clickable();
    }

    @Test
    void waitForListChangeUsingListVariable() {
        FluentList<FluentWebElement> myList = $(".row");
        goTo(SIZE_CHANGE_URL);
        await().until(myList).size().greaterThan(2);
    }

    @Test
    void waitForListChangeFromPageObject() {
        goTo(sizeChangePage);
        await().until(sizeChangePage.getRows()).size().greaterThan(2);
    }

    @Test
    void waitForListChangeUsingListVariableExpectingTooMuch() {
        assertThrows(TimeoutException.class,
                () -> {
                    FluentList<FluentWebElement> myList = $(".row");
                    goTo(SIZE_CHANGE_URL);
                    await().atMost(2, TimeUnit.SECONDS).until(myList).size().greaterThan(3);
                });
    }
}

class SizeChangePage1 extends FluentPage {
    @FindBy(css = ".row")
    private FluentList<FluentWebElement> rows;

    @FindBy(css = ".newrow")
    private FluentList<FluentWebElement> newRows;

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
        return newRows;
    }
}