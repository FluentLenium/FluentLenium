package io.fluentlenium.test.await;

import static org.assertj.core.api.Assertions.assertThat;
import static io.fluentlenium.test.IntegrationFluentTest.SIZE_CHANGE_URL;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.TimeUnit;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.test.IntegrationFluentTest;
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
    void waitForListChangeFromPageObjectUsingFluentListAnnotationWithReset() {
        goTo(sizeChangePage);
        sizeChangePage.getRowsByAnnotation().reset();
        await().until(sizeChangePage.getRowsByAnnotation()).size().equalTo(3);
        assertThat(sizeChangePage.getRowsByAnnotation()).hasSize(3);
    }

    @Test
    void waitForListChangeFromPageObjectUsingFluentListAnnotationWithResetInside() {
        goTo(sizeChangePage);
        await().until(sizeChangePage.getRowsByAnnotation().reset()).size().equalTo(3);
        assertThat(sizeChangePage.getRowsByAnnotation()).hasSize(3);
    }

    @Test
    void waitForListChangeFromPageObjectUsingFluentListAnnotation() {
        goTo(sizeChangePage);
        await().until(sizeChangePage.getRowsByAnnotation()).size().equalTo(3);
        assertThat(sizeChangePage.getRowsByAnnotation()).hasSize(3);
    }

    @Test
    void waitForListChangeFromPageObjectUsingList() {
        goTo(sizeChangePage);
        await().until(sizeChangePage.getRowsBySelector()).size().equalTo(3);
        assertThat(sizeChangePage.getRowsBySelector()).hasSize(3);
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

    FluentList<FluentWebElement> getRowsByAnnotation() {
        return rows;
    }
    FluentList<FluentWebElement> getRowsBySelector() {
        return $(".row");
    }
}
