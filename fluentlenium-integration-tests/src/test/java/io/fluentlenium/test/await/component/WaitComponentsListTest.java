package io.fluentlenium.test.await.component;

import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.core.components.ComponentInstantiator;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

class WaitComponentsListTest extends IntegrationFluentTest {

    private static final int POSITION_OF_REPLACED_ROW = 1;
    private static final String INITIAL_VALUE_OF_REPLACED_ROW = "row2";
    private static final String VALUE_OF_REPLACED_ROW = "replaced row2";

    @Page
    private RowsListPage rowsListPage;

    @Test
    void shouldReturnActualValueFromTheChangedList() {
        goTo(ELEMENT_REPLACE_URL);
        await().until(rowsListPage.getComponentsList()).size(2);
        assertThat(rowsListPage.getComponentsList()).hasSize(2);
        assertThat(rowsListPage.getComponentsList().get(POSITION_OF_REPLACED_ROW).getText())
                .isEqualTo(INITIAL_VALUE_OF_REPLACED_ROW);
        await().until(rowsListPage.getComponentsList()).size(4);
        assertThat(rowsListPage.getComponentsList()).hasSize(4);
        assertThat(rowsListPage.getComponentsList().reset().get(POSITION_OF_REPLACED_ROW).getText())
                .isEqualTo(VALUE_OF_REPLACED_ROW);
    }

    @Test
    void shouldReturnActualElementWithGetFromTheChangedListWhenUsedPreviously() {
        goTo(ELEMENT_REPLACE_URL);
        rowsListPage.getComponentsList().get(1).getText();
        await().until(rowsListPage.getComponentsList()).size().greaterThan(3);
        assertThat(rowsListPage.getComponentsList().get(POSITION_OF_REPLACED_ROW).getText())
                .isEqualTo(VALUE_OF_REPLACED_ROW);
    }

    @Test
    void shouldReturnActualElementWithGetFromTheChangedListAfterListReset() {
        goTo(ELEMENT_REPLACE_URL);
        rowsListPage.getComponentsList().get(1).getText();
        FluentList<RowComponent> rowsList = rowsListPage.getComponentsList().reset();
        await().until(rowsList).size().greaterThan(3);
        assertThat(rowsList.get(POSITION_OF_REPLACED_ROW).getText())
                .isEqualTo(VALUE_OF_REPLACED_ROW);
    }

    @Test
    void shouldReturnActualValueFromResetElementWithGetFromTheChangedListAfterListReset() {
        goTo(ELEMENT_REPLACE_URL);
        rowsListPage.getComponentsList().get(1).getText();
        FluentList<RowComponent> rowsList = rowsListPage.getComponentsList().reset();
        await().until(rowsList).size().greaterThan(3);
        assertThat(rowsList.get(POSITION_OF_REPLACED_ROW).reset().as(RowComponent.class).getText())
                .isEqualTo(VALUE_OF_REPLACED_ROW);
    }

    @Test
    void shouldReturnActualElementWithIndexFromTheChangedListAfterListReset() {
        goTo(ELEMENT_REPLACE_URL);
        rowsListPage.getComponentsList().get(1).getText();
        FluentList<RowComponent> rowsList = rowsListPage.getComponentsList().reset();
        await().until(rowsList).size().greaterThan(3);
        assertThat(rowsList.index(POSITION_OF_REPLACED_ROW).getText())
                .isEqualTo(VALUE_OF_REPLACED_ROW);
    }

    @Test
    void shouldReturnActualValueFromResetElementWithIndexFromTheChangedListAfterListReset() {
        goTo(ELEMENT_REPLACE_URL);
        RowsListPage rowsPage = newInstance(RowsListPage.class);
        rowsPage.getComponentsList().get(1).getText();
        FluentList<RowComponent> rowsList = rowsPage.getComponentsList().reset();
        await().until(rowsList).size().greaterThan(3);
        assertThat(rowsList.index(1).reset().as(RowComponent.class).getText())
                .isEqualTo(VALUE_OF_REPLACED_ROW);
    }

}

class RowsListPage extends FluentPage {
    @FindBy(css = ".row")
    private FluentList<RowComponent> componentsList;

    FluentList<RowComponent> getComponentsList() {
        return componentsList;
    }
}

class RowComponent extends FluentWebElement {
    @FindBy(css = ".inner-row")
    private FluentWebElement innerTag;

    RowComponent(WebElement element, FluentControl control, ComponentInstantiator instantiator) {
        super(element, control, instantiator);
    }

    String getText() {
        return innerTag.text();
    }
}
