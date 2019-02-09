package org.fluentlenium.test.await.not;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;

class HappyPathsNegatedAwaitTest extends IntegrationFluentTest {

    private static final String NONEXISTING_CSS = "nonexisting";

    @FindBy(id = NONEXISTING_CSS)
    private FluentWebElement nonExistingElement;

    @BeforeEach
    void setUp() {
        goTo(DISAPPEARING_EL_URL);
    }

    @Test
    void findByNotPresentAtAll() {
        await().until(nonExistingElement).not().present();
    }

    @Test
    void findByNotEnabledAtAll() {
        await().until(nonExistingElement).not().enabled();
    }

    @Test
    void findByNotSelectedAtAll() {
        await().until(nonExistingElement).not().selected();
    }

    @Test
    void findByNotDisplayedAtAll() {
        await().until(nonExistingElement).not().displayed();
    }

    @Test
    void findByNotClickableAtAll() {
        await().until(nonExistingElement).not().clickable();
    }

    @Test
    void elNotPresentAtAll() {
        await().until(el(NONEXISTING_CSS)).not().present();
    }

    @Test
    void elNotEnabledAtAll() {
        await().until(el(NONEXISTING_CSS)).not().enabled();
    }

    @Test
    void elNotSelectedAtAll() {
        await().until(el(NONEXISTING_CSS)).not().selected();
    }

    @Test
    void elNotDisplayedAtAll() {
        await().until(el(NONEXISTING_CSS)).not().displayed();
    }

    @Test
    void elNotClickableAtAll() {
        await().until(el(NONEXISTING_CSS)).not().clickable();
    }

}
