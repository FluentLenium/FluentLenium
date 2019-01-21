package org.fluentlenium.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.integration.localtest.IntegrationFluentTest.SIZE_CHANGE_URL;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.TimeUnit;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.FindBy;

class WaitSizeTest extends IntegrationFluentTest {
    @Page
    SizeChangePage sizeChangePage;

    @Test
    void waitForListChangeUsingCssSelectorDirectly() {
        goTo(SIZE_CHANGE_URL);
        await().until($(".row")).size().greaterThan(2);
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
                    await().atMost(2, TimeUnit.SECONDS).until(myList)
                            .size().greaterThan(3);
                });
    }

    @Test
    void waitForListChangeUsingListVariableAndEqualMethod() {
        FluentList<FluentWebElement> myList = $(".row");
        goTo(SIZE_CHANGE_URL);
        await().until(myList).size().equalTo(3);
    }

    @Test
    void waitForListChangeToValueDefinedInSizeMethod() {
        FluentList<FluentWebElement> myList = $(".row");
        goTo(SIZE_CHANGE_URL);
        await().until(myList).size(3);
    }
}

class SizeChangePage extends FluentPage {
    @FindBy(css = ".row")
    FluentList<FluentWebElement> rows;

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
}
