package org.fluentlenium.test.await;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@SuppressWarnings({"PMD.GodClass", "PMD.ExcessivePublicCount"})
public class FluentLeniumWaitElementTest extends IntegrationFluentTest {
    @FindBy(className = "small")
    private FluentList<FluentWebElement> smallElements;

    @FindBy(className = "not-found")
    private FluentList<FluentWebElement> notFoundElements;

    @FindBy(tagName = "input")
    private FluentList<FluentWebElement> inputElement;

    @FindBy(tagName = "input")
    private FluentList<FluentWebElement> inputElements;

    @FindBy(css = "input[disabled]")
    private FluentWebElement inputDisabledElement;

    @FindBy(css = "input[disabled]")
    private FluentList<FluentWebElement> inputDisabledElements;

    @FindBy(className = "not-found")
    private FluentWebElement notFoundElement;

    @FindBy(id = "default")
    private FluentWebElement defaultElement;

    @FindBy(id = "default")
    private FluentList<FluentWebElement> defaultElements;

    @FindBy(id = "invisible")
    private FluentList<FluentWebElement> invisibleElements;

    @FindBy(id = "nonexistent")
    private FluentWebElement nonexistentElement;

    @FindBy(id = "nonexistent")
    private FluentList<FluentWebElement> nonexistentElements;

    @FindBy(id = "disabled")
    private FluentList<FluentWebElement> disabledElements;

    @FindBy(id = "disabled")
    private FluentWebElement disabledElement;

    @BeforeEach
    void before() {
        goTo(DEFAULT_URL);
    }

    @Test
    void checkAwaitIsPresent() {
        await().atMost(1, NANOSECONDS).until(smallElements).present();
    }

    @Test
    void checkAwaitIsClickable() {
        await().atMost(1, NANOSECONDS).until(smallElements).clickable();
    }

    @Test
    void checkAwaitIsClickableForSingleElement() {
        await().atMost(1, NANOSECONDS).until(inputElement).clickable();
    }

    @Test
    void checkAwaitDisabledIsClickableThrowTimeoutException() {
        assertThrows(TimeoutException.class,
                () -> await().atMost(1, NANOSECONDS).until(inputDisabledElements).clickable());
    }

    @Test
    void checkAwaitDisabledIsClickableThrowTimeoutExceptionForSingleElement() {
        assertThrows(TimeoutException.class,
                () -> await().atMost(1, NANOSECONDS).until(inputDisabledElement).clickable());
    }

    @Test
    void awaitForElementXPosition() {
        await().until(inputDisabledElements).rectangle().x().greaterThan(0);
    }

    @Test
    void awaitForElementXPositionElementNotFound() {
        assertThrows(TimeoutException.class,
                () -> await().until(notFoundElements).rectangle().x(0));
    }

    @Test
    void checkAwaitHasSize() {
        await().atMost(1, NANOSECONDS).untilEach(smallElements).size(3);
    }

    @Test
    void checkUseCustomMessage() {
        try {
            await().withMessage("toto").atMost(1, NANOSECONDS).until(notFoundElement).present();
            fail();
        } catch (TimeoutException e) {
            assertThat(e.getMessage()).contains("toto");
        }
    }

    @Test
    void checkAwaitContainsText() {
        await().atMost(1, NANOSECONDS).until(smallElements).text().contains("Small 1");
    }

    @Test
    void checkAwaitHasText() {
        await().atMost(1, NANOSECONDS).until(smallElements).text().equalTo("Small 1");
    }

    @Test
    void checkAwaitContainsName() {
        await().atMost(1, NANOSECONDS).until(smallElements).name("name");
    }

    @Test
    void checkAwaitContainsId() {
        await().atMost(1, NANOSECONDS).until(smallElements).id("id2");
    }

    @Test
    void checkHasAttribute() {
        await().atMost(1, NANOSECONDS).until(inputElements).attribute("value", "John");
    }

    @Test
    void whenElementIsPresentThenAreDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilEach(defaultElements).displayed();
    }

    @Test
    void whenElementIsPresentThenIsDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(defaultElements).displayed();
    }

    @Test
    void whenElementIsNotDisplayedThenAreDisplayedThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(1, NANOSECONDS).untilEach(invisibleElements).displayed();
                });
    }

    @Test
    void whenElementIsNotDisplayedThenIsDisplayedThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(1, NANOSECONDS).until(invisibleElements).displayed();
                });
    }

    @Test
    void whenElementIsNotPresentThenAreNotDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilEach(nonexistentElements).not().displayed();
    }

    @Test
    void whenElementIsNotPresentThenIsNotDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(nonexistentElements).not().displayed();
    }

    @Test
    void whenElementIsNotDisplayedThenAreNotDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilEach(invisibleElements).not().displayed();
    }

    @Test
    void whenElementIsNotDisplayedThenIsNotDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(invisibleElements).not().displayed();
    }

    @Test
    void whenElementIsDisplayedThenAreNotDisplayedThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(1, NANOSECONDS).untilEach(defaultElements).not().displayed();
                });
    }

    @Test
    void whenElementIsDisplayedThenIsNotDisplayedThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(1, NANOSECONDS).until(defaultElements).not().displayed();
                });
    }

    @Test
    void whenElementIsEnabledThenAreEnabledReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilEach(defaultElements).enabled();
    }

    @Test
    void whenElementIsEnabledThenIsEnabledReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(defaultElements).enabled();
    }

    @Test
    void whenSingleElementIsEnabledThenIsEnabledReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(defaultElement).enabled();
    }

    @Test
    void whenSingleElementIsEnabledThenIsEnabledReturnTrueWhenArgumentIsLambda() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(() -> defaultElement.enabled());
    }

    @Test
    void whenSingleNonexistingElementThenIsEnabledThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(1, NANOSECONDS).until(nonexistentElement).enabled();
                });
    }

    @Test
    void whenSingleNonexistingElementThenIsEnabledThrowsExceptionWhenArgumentIsLambda() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(1, NANOSECONDS).until(() -> nonexistentElement.enabled());
                });
    }

    @Test
    void whenElementIsNotEnabledThenAreEnabledThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(1, NANOSECONDS).untilEach(disabledElements).enabled();
                });
    }

    @Test
    void whenElementIsNotEnabledThenIsEnabledThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(1, NANOSECONDS).until(disabledElements).enabled();
                });
    }

    @Test
    void whenElementIsNotEnabledThenIsNotEnabledReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(disabledElements).not().enabled();
    }

    @Test
    void whenSingleElementIsNotEnabledThenIsEnabledThrowsException() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(1, NANOSECONDS).until(disabledElement).enabled();
                });
    }

    @Test
    void whenSingleElementIsNotEnabledThenIsNotEnabledReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(disabledElement).not().enabled();
    }

    @Test
    void whenElementIsNotDisplayedThenIsPresentReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(invisibleElements).present();
    }

    @Test
    void checkPolling() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().pollingEvery(1500, TimeUnit.MILLISECONDS).until(defaultElements)
                            .text().equalTo("wait");
                });
    }

    @Test
    void checkIsAt() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(1000, TimeUnit.MILLISECONDS).untilPage(new FluentPage()).isAt();
    }

    @Test
    void checkLoaded() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(1000, TimeUnit.MILLISECONDS).untilPage().isLoaded();
    }

    @Test
    void checkPredicate() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(800, TimeUnit.MILLISECONDS).untilPredicate(predicate -> true);
    }

    @Test
    void checkPredicateFail() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(1000).untilPredicate(input -> false);
                });
    }

    @Test
    void checkFunction() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(1000, TimeUnit.MILLISECONDS).until(fluent -> true);
    }

    @Test
    void checkFunctionFail() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(1000).until(fluent -> false);
                });
    }

    @Test
    void checkSupplier() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(1000, TimeUnit.MILLISECONDS).until(() -> true);
    }

    @Test
    void checkSupplierFail() {
        assertThrows(TimeoutException.class,
                () -> {
                    goTo(JAVASCRIPT_URL);
                    await().atMost(1000).until(() -> false);
                });
    }

}


