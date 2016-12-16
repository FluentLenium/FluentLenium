package org.fluentlenium.integration;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

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

    @FindBy(id = "unvisible")
    private FluentList<FluentWebElement> unvisibleElements;

    @FindBy(id = "nonexistent")
    private FluentWebElement nonexistentElement;

    @FindBy(id = "nonexistent")
    private FluentList<FluentWebElement> nonexistentElements;

    @FindBy(id = "disabled")
    private FluentList<FluentWebElement> disabledElements;

    @FindBy(id = "disabled")
    private FluentWebElement disabledElement;

    @Before
    public void before() {
        goTo(DEFAULT_URL);
    }

    @Test
    public void checkAwaitIsPresent() {
        await().atMost(1, NANOSECONDS).until(smallElements).present();
    }

    @Test
    public void checkAwaitIsClickable() {
        await().atMost(1, NANOSECONDS).until(smallElements).clickable();
    }

    @Test
    public void checkAwaitIsClickableForSingleElement() {
        await().atMost(1, NANOSECONDS).until(inputElement).clickable();
    }

    @Test(expected = TimeoutException.class)
    public void checkAwaitDisabledIsClickableThrowTimeoutException() {
        await().atMost(1, NANOSECONDS).until(inputDisabledElements).clickable();
    }

    @Test(expected = TimeoutException.class)
    public void checkAwaitDisabledIsClickableThrowTimeoutExceptionForSingleElement() {
        await().atMost(1, NANOSECONDS).until(inputDisabledElement).clickable();
    }

    @Test
    public void awaitForElementXPosition() {
        await().until(inputDisabledElements).rectangle().x(5);
    }

    @Test(expected = TimeoutException.class)
    public void awaitForElementXPositionElementNotFound() {
        await().until(notFoundElements).rectangle().x(0);
    }

    @Test
    public void checkAwaitHasSize() {
        await().atMost(1, NANOSECONDS).untilEach(smallElements).size(3);
    }

    @Test
    public void checkUseCustomMessage() {
        try {
            await().withMessage("toto").atMost(1, NANOSECONDS).until(notFoundElement).present();
            fail();
        } catch (TimeoutException e) {
            assertThat(e.getMessage()).contains("toto");
        }
    }

    @Test
    public void checkAwaitContainsText() {
        await().atMost(1, NANOSECONDS).until(smallElements).text().contains("Small 1");
    }

    @Test
    public void checkAwaitHasText() {
        await().atMost(1, NANOSECONDS).until(smallElements).text().equalTo("Small 1");
    }

    @Test
    public void checkAwaitContainsName() {
        await().atMost(1, NANOSECONDS).until(smallElements).name("name");
    }

    @Test
    public void checkAwaitContainsId() {
        await().atMost(1, NANOSECONDS).until(smallElements).id("id2");
    }

    @Test
    public void checkHasAttribute() {
        await().atMost(1, NANOSECONDS).until(inputElements).attribute("value", "John");
    }

    @Test
    public void whenElementIsPresentThenAreDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilEach(defaultElements).displayed();
    }

    @Test
    public void whenElementIsPresentThenIsDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(defaultElements).displayed();
    }

    @Test(expected = TimeoutException.class)
    public void whenElementIsNotDisplayedThenAreDisplayedThrowsException() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilEach(unvisibleElements).displayed();
    }

    @Test(expected = TimeoutException.class)
    public void whenElementIsNotDisplayedThenIsDisplayedThrowsException() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(unvisibleElements).displayed();
    }

    @Test
    public void whenElementIsNotPresentThenAreNotDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilEach(nonexistentElements).not().displayed();
    }

    @Test
    public void whenElementIsNotPresentThenIsNotDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(nonexistentElements).not().displayed();
    }

    @Test
    public void whenElementIsNotDisplayedThenAreNotDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilEach(unvisibleElements).not().displayed();
    }

    @Test
    public void whenElementIsNotDisplayedThenIsNotDisplayedReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(unvisibleElements).not().displayed();
    }

    @Test(expected = TimeoutException.class)
    public void whenElementIsDisplayedThenAreNotDisplayedThrowsException() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilEach(defaultElements).not().displayed();
    }

    @Test(expected = TimeoutException.class)
    public void whenElementIsDisplayedThenIsNotDisplayedThrowsException() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(defaultElements).not().displayed();
    }

    @Test
    public void whenElementIsEnabledThenAreEnabledReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilEach(defaultElements).enabled();
    }

    @Test
    public void whenElementIsEnabledThenIsEnabledReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(defaultElements).enabled();
    }

    @Test
    public void whenSingleElementIsEnabledThenIsEnabledReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(defaultElement).enabled();
    }

    @Test
    public void whenSingleElementIsEnabledThenIsEnabledReturnTrueWhenArgumentIsLambda() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(() -> defaultElement.enabled());
    }

    @Test(expected = TimeoutException.class)
    public void whenSingleNonexistingElementThenIsEnabledThrowsException() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(nonexistentElement).enabled();
    }

    @Test(expected = TimeoutException.class)
    public void whenSingleNonexistingElementThenIsEnabledThrowsExceptionWhenArgumentIsLambda() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(() -> nonexistentElement.enabled());
    }

    @Test(expected = TimeoutException.class)
    public void whenElementIsNotEnabledThenAreEnabledThrowsException() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilEach(disabledElements).enabled();
    }

    @Test(expected = TimeoutException.class)
    public void whenElementIsNotEnabledThenIsEnabledThrowsException() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(disabledElements).enabled();
    }

    @Test
    public void whenElementIsNotEnabledThenIsNotEnabledReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(disabledElements).not().enabled();
    }

    @Test(expected = TimeoutException.class)
    public void whenSingleElementIsNotEnabledThenIsEnabledThrowsException() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(disabledElement).enabled();
    }

    @Test
    public void whenSingleElementIsNotEnabledThenIsNotEnabledReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(disabledElement).not().enabled();
    }

    @Test
    public void whenElementIsNotDisplayedThenIsPresentReturnTrue() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until(unvisibleElements).present();
    }

    @Test(expected = TimeoutException.class)
    public void checkPolling() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(1500, TimeUnit.MILLISECONDS).until(defaultElements).text().equalTo("wait");
    }

    @Test
    public void checkIsAt() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(1000, TimeUnit.MILLISECONDS).untilPage(new FluentPage()).isAt();
    }

    @Test
    public void checkLoaded() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(1000, TimeUnit.MILLISECONDS).untilPage().isLoaded();
    }

    @Test
    public void checkPredicate() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(800, TimeUnit.MILLISECONDS).untilPredicate(predicate -> true);
    }

    @Test(expected = TimeoutException.class)
    public void checkPredicateFail() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1000).untilPredicate(input -> false);
    }

    @Test
    public void checkFunction() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(1000, TimeUnit.MILLISECONDS).until(new Function<FluentControl, Boolean>() {
            @Override
            public Boolean apply(FluentControl fluent) {
                return true;
            }
        });
    }

    @Test(expected = TimeoutException.class)
    public void checkFunctionFail() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1000).until(new Function<FluentControl, Boolean>() {
            @Override
            public Boolean apply(FluentControl fluent) {
                return false;
            }
        });
    }

    @Test
    public void checkSupplier() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(1000, TimeUnit.MILLISECONDS).until(() -> true);
    }

    @Test(expected = TimeoutException.class)
    public void checkSupplierFail() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1000).until(() -> false);
    }

    private static class MyFluentPage extends FluentPage {
        @Override
        public void isAt() {
            assertThat(find("#newField").texts()).contains("new");
        }

        @Override
        public String getUrl() {
            return IntegrationFluentTest.JAVASCRIPT_URL;
        }
    }

}


