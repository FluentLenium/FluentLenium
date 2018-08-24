package org.fluentlenium.integration;


import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.Assert.assertEquals;

public class WaitComponentsListTest extends IntegrationFluentTest {
    private final static int POSITION_OF_REPLACED_ROW = 1;
    private final static String VALUE_OF_REPLACED_ROW = "replaced row2";
    @Page
    private RowsListPage rowsListPage;

    @Test
    public void shouldReturnActualValueFromTheChangedList() {
        goTo(ELEMENT_REPLACE_URL);
        await().until(rowsListPage.getComponentsList()).size().greaterThan(3);
        assertEquals(rowsListPage.getComponentsList().get(POSITION_OF_REPLACED_ROW).getText(), VALUE_OF_REPLACED_ROW);
    }

    @Test
    public void shouldReturnActualElementWithGetFromTheChangedListWhenUsedPreviously() {
        goTo(ELEMENT_REPLACE_URL);
        rowsListPage.getComponentsList().get(1).getText();
        await().until(rowsListPage.getComponentsList()).size().greaterThan(3);
        assertEquals(rowsListPage.getComponentsList().get(POSITION_OF_REPLACED_ROW).getText(), VALUE_OF_REPLACED_ROW);
    }

    @Test
    public void shouldReturnActualElementWithGetFromTheChangedListAfterListReset() {
        goTo(ELEMENT_REPLACE_URL);
        rowsListPage.getComponentsList().get(1).getText();
        FluentList<RowComponent> rowsList = rowsListPage.getComponentsList().reset();
        await().until(rowsList).size().greaterThan(3);
        assertEquals(rowsList.get(POSITION_OF_REPLACED_ROW).getText(), VALUE_OF_REPLACED_ROW);
    }

    @Test
    public void shouldReturnActualValueFromResetElementWithGetFromTheChangedListAfterListReset() {
        goTo(ELEMENT_REPLACE_URL);
        rowsListPage.getComponentsList().get(1).getText();
        FluentList<RowComponent> rowsList = rowsListPage.getComponentsList().reset();
        await().until(rowsList).size().greaterThan(3);
        assertEquals(rowsList.get(POSITION_OF_REPLACED_ROW).reset().as(RowComponent.class).getText(), VALUE_OF_REPLACED_ROW);
    }

    @Test
    public void shouldReturnActualElementWithIndexFromTheChangedListAfterListReset() {
        goTo(ELEMENT_REPLACE_URL);
        rowsListPage.getComponentsList().get(1).getText();
        FluentList<RowComponent> rowsList = rowsListPage.getComponentsList().reset();
        await().until(rowsList).size().greaterThan(3);
        assertEquals(rowsList.index(POSITION_OF_REPLACED_ROW).getText(), VALUE_OF_REPLACED_ROW);
    }

    @Test
    public void shouldReturnActualValueFromResetElementWithIndexFromTheChangedListAfterListReset() {
        goTo(ELEMENT_REPLACE_URL);
        RowsListPage rowsPage = newInstance(RowsListPage.class);
        rowsPage.getComponentsList().get(1).getText();
        FluentList<RowComponent> rowsList = rowsPage.getComponentsList().reset();
        await().until(rowsList).size().greaterThan(3);
        assertEquals(rowsList.index(1).reset().as(RowComponent.class).getText(), VALUE_OF_REPLACED_ROW);
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
