package org.fluentlenium.test.await.negate;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

class NegatedAwaitElementTest extends IntegrationFluentTest {

    @FindBy(className = "row")
    private FluentWebElement disappearingElement;

    @FindBy(id = "mySelect")
    private FluentWebElement mySelect;

    @BeforeEach
    void setUp() {
        goTo(DISAPPEARING_EL_URL);
    }

    @Test
    void awaitNotPresent() {
        await().until(disappearingElement).present();
        executeScript("removeRow();");
        await().until(disappearingElement).not().present();
    }

    @Test
    void awaitForElementDisappearanceFindBy() {
        await().until(disappearingElement).present();
        await().atMost(2, TimeUnit.SECONDS)
                .pollingEvery(250)
                .until(disappearingElement)
                .not().present();
    }

    @Test
    void awaitForElementDisappearanceEl() {
        await().until(el(".row")).present();
        await().atMost(2, TimeUnit.SECONDS)
                .pollingEvery(500)
                .until(el(".row"))
                .not().present();
    }

    @Test
    void awaitNotDisplayed() {
        await().until(disappearingElement).displayed();
        executeScript("makeRowNotDisplayed()");
        await().until(disappearingElement).not().displayed();
        await().until(disappearingElement).present();
    }

    @Test
    void awaitNotSelected() {
        FluentWebElement volvo = mySelect.el("[value=volvo]");
        FluentWebElement saab = mySelect.el("[value=saab]");
        await().until(saab).selected();
        await().until(volvo).not().selected();
        executeScript("setVolvoSelected();");
        await().until(saab).not().selected();
        await().until(volvo).selected();
    }

    @Test
    void awaitNotEnabled() {
        await().until(disappearingElement).enabled();
        executeScript("makeRowDisabled();");
        await().until(disappearingElement).not().enabled();
    }

    @Test
    void awaitNotClickable() {
        await().until(disappearingElement).clickable();
        executeScript("makeRowDisabled();");
        await().until(disappearingElement).not().clickable();
    }

}
