package org.fluentlenium.integration;

import com.google.common.base.Supplier;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.TimeoutException;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.containingText;
import static org.fluentlenium.core.filter.FilterConstructor.with;
import static org.fluentlenium.core.filter.FilterConstructor.withClass;
import static org.fluentlenium.core.filter.FilterConstructor.withId;
import static org.fluentlenium.core.filter.FilterConstructor.withName;
import static org.fluentlenium.core.filter.FilterConstructor.withText;
import static org.fluentlenium.core.filter.MatcherConstructor.regex;
import static org.junit.Assert.fail;

public class FluentLeniumFunctionalWaitTest extends IntegrationFluentTest {
    @Before
    public void before() {
        goTo(DEFAULT_URL);
    }

    @Test
    public void checkAwaitIsPresent() {
        await().atMost(1, NANOSECONDS).untilElement(new Supplier<FluentWebElement>() {
            @Override
            public FluentWebElement get() {
                return findFirst(".small");
            }
        }).present();
    }

    @Test
    public void checkAwaitIsClickable() {
        await().atMost(1, NANOSECONDS).untilElement(new Supplier<FluentWebElement>() {
            @Override
            public FluentWebElement get() {
                return findFirst(".small");
            }
        }).clickable();
    }

    @Test(expected = TimeoutException.class)
    public void checkAwaitDisabledIsClickableThrowTimeoutException() {
        await().atMost(1, NANOSECONDS).untilElement(new Supplier<FluentWebElement>() {
            @Override
            public FluentWebElement get() {
                return findFirst("input[disabled]");
            }
        }).clickable();
    }

    @Test
    public void checkAwaitHasSize() {
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find(".small");
            }
        }).each().hasSize(3);
    }

    @Test
    public void checkAwaitHasTextWithText() {
        await().atMost(1, NANOSECONDS).untilElement(new Supplier<FluentWebElement>() {
            @Override
            public FluentWebElement get() {
                return findFirst(".small", withText("Small 1"));
            }
        }).text().equals("Small 1");
    }

    @Test
    public void checkAwaitContainsNameWithName() {
        await().atMost(1, NANOSECONDS).untilElement(new Supplier<FluentWebElement>() {
            @Override
            public FluentWebElement get() {
                return findFirst(".small", withName("name"));
            }
        }).name("name");
    }

    @Test
    public void checkAwaitContainsNameWithClass() {
        await().atMost(1, NANOSECONDS).untilElement(new Supplier<FluentWebElement>() {
            @Override
            public FluentWebElement get() {
                return findFirst("span", withClass("small"));
            }
        }).name("name");
    }

    @Test
    public void checkAwaitContainsNameWithClassRegex() {
        await().atMost(1, NANOSECONDS).untilElement(new Supplier<FluentWebElement>() {
            @Override
            public FluentWebElement get() {
                return findFirst("span", withClass().contains(regex("smal?")));
            }
        }).name("name");
    }

    @Test
    public void checkAwaitContainsNameWithClassAndContainsWord() {
        await().atMost(1, NANOSECONDS).untilElement(new Supplier<FluentWebElement>() {
            @Override
            public FluentWebElement get() {
                return findFirst("span", withClass().containsWord("small"));
            }
        }).name("name");
    }

    @Test
    public void checkAwaitContainsTextWithText() {
        await().atMost(1, NANOSECONDS).untilElement(new Supplier<FluentWebElement>() {
            @Override
            public FluentWebElement get() {
                return findFirst(".small", withText("Small 1"), containingText("Small 1"));
            }
        });
    }

    @Test
    public void checkUseCustomMessage() {
        try {
            await().withMessage("toto").atMost(1, NANOSECONDS).untilElement(new Supplier<FluentWebElement>() {
                @Override
                public FluentWebElement get() {
                    return findFirst(".small", withText("Small 1"));
                }
            }).text().contains("Small 21");
            fail();
        } catch (TimeoutException e) {
            assertThat(e.getMessage()).contains("toto");
        }
    }

    @Test
    public void checkAwaitContainsText() {
        await().atMost(1, NANOSECONDS).untilElement(new Supplier<FluentWebElement>() {
            @Override
            public FluentWebElement get() {
                return findFirst(".small");
            }
        }).text().contains("Small 1");
    }

    @Test
    public void checkAwaitContainsIdWithId() {
        await().atMost(1, NANOSECONDS).untilElement(new Supplier<FluentWebElement>() {
            @Override
            public FluentWebElement get() {
                return findFirst(".small", withId("id2"));
            }
        }).id("id2");
    }

    @Test
    public void checkAwaitStartsWithName() {
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find(".small", withName().startsWith("name"));
            }
        }).each().hasSize(2);
    }

    @Test
    public void checkAwaitContainsIdWithIdContains() {
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find(".small", withId().contains("id"));
            }
        }).each().hasSize(2);
    }

    @Test
    public void checkAwaitHasText() {
        await().atMost(1, NANOSECONDS).untilElement(new Supplier<FluentWebElement>() {
            @Override
            public FluentWebElement get() {
                return findFirst(".small");
            }
        }).text("Small 1");
    }

    @Test
    public void checkAwaitContainsName() {
        await().atMost(1, NANOSECONDS).untilElement(new Supplier<FluentWebElement>() {
            @Override
            public FluentWebElement get() {
                return findFirst(".small");
            }
        }).name("name");
    }

    @Test
    public void checkAwaitContainsId() {
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find(".small");
            }
        }).id("id2");
    }

    @Test
    public void checkAwaitContainsTextWithTextMatcher() {
        await().atMost(1, NANOSECONDS).untilElement(new Supplier<FluentWebElement>() {
            @Override
            public FluentWebElement get() {
                return findFirst(".small", withText().contains("Small 1"));
            }
        }).present();
    }

    @Test
    public void when_a_element_is_not_present_then_isNotPresent_return_true() {
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find(".small", withText().contains("notPresent"));
            }
        }).not().present();
    }

    @Test(expected = TimeoutException.class)
    public void when_a_element_is_present_then_isNotPresent_throw_an_exception() {
        await().atMost(1, NANOSECONDS).untilElement(new Supplier<FluentWebElement>() {
            @Override
            public FluentWebElement get() {
                return findFirst(".small", withText().contains("Small 1"));
            }
        }).not().present();
    }

    @Test
    public void checkAwaitStartWithRegex() {
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find(".small", with("id").startsWith(regex(".d")));
            }
        }).each().hasSize(2);
    }

    @Test
    public void checkAwaitStartWithString() {
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find(".small", with("id").startsWith("id"));
            }
        }).each().hasSize(2);
    }

    @Test
    public void checkAwaitNotStartWith() {
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find(".small", with("id").notStartsWith("id"));
            }
        }).each().hasSize(1);
    }

    @Test
    public void checkAwaitNotStartWithRegex() {
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find(".small", with("id").notStartsWith(regex("id")));
            }
        }).each().hasSize(1);
    }

    @Test
    public void checkAwaitEndsWithRegex() {
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find(".small", with("id").endsWith(regex("2")));
            }
        }).each().hasSize(1);
    }

    @Test
    public void checkAwaitNotEndsWith() {
        await().atMost(1, NANOSECONDS).untilElement(new Supplier<FluentWebElement>() {
            @Override
            public FluentWebElement get() {
                return findFirst(".small", with("id").notEndsWith("2"));
            }
        }).id("id");
    }

    @Test
    public void checkAwaitNotEndsWithRegex() {
        await().atMost(1, NANOSECONDS).untilElement(new Supplier<FluentWebElement>() {
            @Override
            public FluentWebElement get() {
                return findFirst(".small", with("id").notEndsWith(regex("2")));
            }
        }).id("id");
    }

    @Test
    public void checkAwaitNotContains() {
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find(".small", with("id").notContains("d"));
            }
        }).each().hasSize(1);
    }

    @Test
    public void checkAwaitNotContainsRegex() {
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find(".small", with("id").notContains(regex("d")));
            }
        }).each().hasSize(1);
    }

    @Test
    public void checkAwaitEquals() {
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find(".small", with("id").notContains("d"));
            }
        }).size().equalTo(1);
    }

    @Test
    public void checkAwaitNotEquals() {
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find(".small", with("id").notContains("d"));
            }
        }).size().not().equalTo(10);
    }

    @Test
    public void checkAwaitLessThan() {
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find(".small", with("id").notContains("d"));
            }
        }).size().lessThan(4);
    }

    @Test
    public void checkAwaitLessThanOrEquals() {
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find(".small", with("id").notContains("d"));
            }
        }).size().lessThanOrEqualTo(1);
    }

    @Test
    public void checkAwaitGreaterThan() {
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find(".small", with("id").notContains("d"));
            }
        }).size().greaterThan(-1);
    }

    @Test
    public void checkAwaitGreaterThanOrEquals() {
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find(".small", with("id").notContains("d"));
            }
        }).size().greaterThanOrEqualTo(1);
    }

    @Test
    public void checkWithValue() {
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find("input", with("value").equalTo("John"));
            }
        }).each().hasSize(4);
    }

    @Test
    public void checkMultipleFilter() {
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find(".small", with("id").startsWith(regex("id")), with("text").endsWith("2"));
            }
        }).each().hasSize(1);
    }

    @Test
    public void checkHasAttribute() {
        await().atMost(1, NANOSECONDS).untilElement(new Supplier<FluentWebElement>() {
            @Override
            public FluentWebElement get() {
                return findFirst("input");
            }
        }).attribute("value", "John");
    }

    @Test
    public void checkHasAttributeWithOthersFilters() {
        await().atMost(1, NANOSECONDS).untilElement(new Supplier<FluentWebElement>() {
            @Override
            public FluentWebElement get() {
                return findFirst("input", with("value").equalTo("John"));
            }
        }).attribute("value", "John");
    }

    @Test
    public void when_element_is_present_then_areDisplayed_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find("#default");
            }
        }).each().displayed();
    }

    @Test
    public void when_element_is_present_then_isDisplayed_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find("#default");
            }
        }).displayed();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_not_displayed_then_areDisplayed_throws_exception() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find("#unvisible");
            }
        }).each().displayed();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_not_displayed_then_isDisplayed_throws_exception() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find("#unvisible");
            }
        }).displayed();
    }

    @Test
    public void when_element_is_not_present_then_areNotDisplayed_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find("#nonexistent");
            }
        }).each().not().displayed();
    }

    @Test
    public void when_element_is_not_present_then_isNotDisplayed_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find("#nonexistent");
            }
        }).each().not().displayed();
    }

    @Test
    public void when_element_is_not_displayed_then_areNotDisplayed_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find("#unvisible");
            }
        }).each().not().displayed();
    }

    @Test
    public void when_element_is_not_displayed_then_isNotDisplayed_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find("#unvisible");
            }
        }).not().displayed();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_displayed_then_areNotDisplayed_throws_exception() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find("#default");
            }
        }).each().not().displayed();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_displayed_then_isNotDisplayed_throws_exception() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find("#default");
            }
        }).not().displayed();
    }

    @Test
    public void when_element_is_enabled_then_areEnabled_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find("#default");
            }
        }).each().enabled();
    }

    @Test
    public void when_element_is_enabled_then_isEnabled_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find("#default");
            }
        }).enabled();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_not_enabled_then_areEnabled_throws_exception() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find("#disabled");
            }
        }).each().enabled();
    }

    @Test(expected = TimeoutException.class)
    public void when_element_is_not_enabled_then_isEnabled_throws_exception() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find("#disabled");
            }
        }).enabled();
    }

    @Test
    public void when_element_is_not_displayed_then_isPresent_return_true() {
        goTo(JAVASCRIPT_URL);
        await().atMost(1, NANOSECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find("#unvisible");
            }
        }).present();
    }

    @Test(expected = TimeoutException.class)
    public void checkPolling() {
        goTo(JAVASCRIPT_URL);
        await().pollingEvery(1500, TimeUnit.MILLISECONDS).untilElements(new Supplier<FluentList<FluentWebElement>>() {
            @Override
            public FluentList<FluentWebElement> get() {
                return find("#default");
            }
        }).text().equals("wait");
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


