package org.fluentlenium.integration;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.MatcherConstructor.regex;
import static org.junit.Assert.fail;

public class FluentLeniumWaitElementTest extends LocalFluentCase {
    @Before
    public void before() {
        goTo(DEFAULT_URL);
    }

    @FindBy(className = "small")
    private FluentList<FluentWebElement> smallElements;

    @FindBy(tagName = "input")
    private FluentList<FluentWebElement> inputElements;

    @FindBy(className = "not-found")
    private FluentWebElement notFoundElement;

    @Test
    public void checkAwaitIsPresent() {
        await().atMost(1, NANOSECONDS).until(smallElements).isPresent();
    }

    @Test
    public void checkAwaitIsClickable() throws Exception {
        await().atMost(1, NANOSECONDS).until(smallElements).isClickable();
    }

    @Test
    public void checkAwaitHasSize() {
        await().atMost(1, NANOSECONDS).until(smallElements).hasSize(3);
    }

    @Test
    public void checkUseCustomMessage() {
        try {
            await().withMessage("toto").atMost(1, NANOSECONDS).until(notFoundElement).isPresent();
            fail();
        } catch (TimeoutException e) {
            assertThat(e.getMessage()).contains("toto");
        }
    }

    @Test
    public void checkAwaitContainsText() {
        await().atMost(1, NANOSECONDS).until(smallElements).containsText("Small 1");
    }

    @Test
    public void checkAwaitHasText() {
        await().atMost(1, NANOSECONDS).until(smallElements).hasText("Small 1");
    }

    @Test
    public void checkAwaitContainsName() {
        await().atMost(1, NANOSECONDS).until(smallElements).hasName("name");
    }

    @Test
    public void checkAwaitContainsId() {
        await().atMost(1, NANOSECONDS).until(smallElements).hasId("id2");
    }

    @Test
    public void checkHasAttribute() {
        await().atMost(1, NANOSECONDS).until(inputElements).hasAttribute("value", "John");
    }

    @Test
    public void when_element_is_present_then_isDisplayed_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#default").areDisplayed();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_not_displayed_then_isDisplayed_throws_exception() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#unvisible").areDisplayed();
    }

    @Test
    public void when_element_is_not_present_then_areNotDisplayed_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#nonexistent").areNotDisplayed();
    }

    @Test
    public void when_element_is_not_displayed_then_areNotDisplayed_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#unvisible").areNotDisplayed();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_displayed_then_areNotDisplayed_throws_exception() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#default").areNotDisplayed();
    }

    @Test
    public void when_element_is_enabled_then_areEnabled_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#default").areEnabled();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_not_enabled_then_areEnabled_throws_exception() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#disabled").areEnabled();
    }

    @Test
    public void when_element_is_not_displayed_then_isPresent_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).until("#unvisible").isPresent();
    }

    @Test(expected = TimeoutException.class)
    public void checkPolling() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(1500, TimeUnit.MILLISECONDS).until("#default").hasText("wait");
    }

    @Test
    public void checkIsAt() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(1000, TimeUnit.MILLISECONDS).untilPage(new FluentPage() {
            @Override
            public void isAt() {
            }
        }).isAt();
    }

    @Test
    public void checkLoaded() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(1000, TimeUnit.MILLISECONDS).untilPage().isLoaded();
    }

    @Test
    public void checkPredicate() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(800, TimeUnit.MILLISECONDS).untilPredicate(new Predicate<Fluent>() {
            @Override
            public boolean apply(Fluent o) {
                return true;
            }
        })
        ;
    }

    @Test(expected = TimeoutException.class)
    public void checkPredicateFail() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1000).untilPredicate(new Predicate<Fluent>() {
            @Override
            public boolean apply(Fluent o) {
                return false;
            }
        })
        ;
    }

    @Test
    public void checkFunction() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(1000, TimeUnit.MILLISECONDS).until(new Function<Fluent, Boolean>() {
            @Override
            public Boolean apply(Fluent fluent) {
                return true;
            }
        });
    }

    @Test(expected = TimeoutException.class)
    public void checkFunctionFail() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1000).until(new Function<Fluent, Boolean>() {
            @Override
            public Boolean apply(Fluent fluent) {
                return false;
            }
        });
    }

    @Test
    public void checkSupplier() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(1000, TimeUnit.MILLISECONDS).until(new Supplier<Boolean>() {
            @Override
            public Boolean get() {
                return true;
            }
        });
    }

    @Test(expected = TimeoutException.class)
    public void checkSupplierFail() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1000).until(new Supplier<Boolean>() {
            @Override
            public Boolean get() {
                return false;
            }
        });
    }

    private static class MyFluentPage extends FluentPage {
        @Override
        public void isAt() {
            assertThat(find("#newField").getTexts()).contains("new");
        }

        @Override
        public String getUrl() {
            return LocalFluentCase.JAVASCRIPT_URL;
        }
    }

}


