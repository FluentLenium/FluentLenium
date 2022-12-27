package io.fluentlenium.test.await.negate;

import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

class NegatedAwaitFluentListTest extends IntegrationFluentTest {

    private static final String SELECTOR = ".row";

    @FindBy(css = SELECTOR)
    private FluentList<FluentWebElement> rows;

    @FindBy(id = "mySelect")
    private FluentWebElement mySelect;

    @BeforeEach
    void setUp() {
        goTo(DISAPPEARING_EL_URL);
    }

    @Test
    void awaitNotPresent() {
        await().until($(SELECTOR)).present();
        executeScript("removeRow();");
        await().until($(SELECTOR)).not().present();
    }

    @Test
    void awaitForElementDisappearanceFindBy() {
        await().until($(SELECTOR)).present();
        await().atMost(2, TimeUnit.SECONDS)
                .pollingEvery(250)
                .until($(SELECTOR))
                .not().present();
    }

    @Test
    void awaitForElementDisappearanceEl() {
        await().until(rows).present();
        await().atMost(2, TimeUnit.SECONDS)
                .pollingEvery(500)
                .until(rows)
                .not().present();
    }

    @Test
    void awaitNotDisplayed() {
        await().until($(SELECTOR)).displayed();
        executeScript("makeRowNotDisplayed()");
        await().until($(SELECTOR)).not().displayed();
        await().until($(SELECTOR)).present();
    }

    @Test
    void awaitNotSelected() {
        FluentList<FluentWebElement> volvo = mySelect.$("[value=volvo]");
        FluentList<FluentWebElement> saab = mySelect.$("[value=saab]");
        await().until(saab).selected();
        await().until(volvo).not().selected();
        executeScript("setVolvoSelected();");
        await().until(saab).not().selected();
        await().until(volvo).selected();
    }

    @Test
    void awaitNotEnabled() {
        await().until($(SELECTOR)).present();
        await().until($(SELECTOR)).enabled();
        executeScript("makeRowDisabled();");
        await().until($(SELECTOR)).not().enabled();
    }

    @Test
    void awaitNotClickable() {
        await().until($(SELECTOR)).present();
        await().until($(SELECTOR)).clickable();
        executeScript("makeRowDisabled();");
        await().until($(SELECTOR)).not().clickable();
    }

}
