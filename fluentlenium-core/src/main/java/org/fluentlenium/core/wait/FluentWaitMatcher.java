package org.fluentlenium.core.wait;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentThread;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.filter.FilterType;
import org.fluentlenium.core.search.Search;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

import static org.fluentlenium.core.wait.WaitMessage.hasAttributeMessage;
import static org.fluentlenium.core.wait.WaitMessage.hasIdMessage;
import static org.fluentlenium.core.wait.WaitMessage.hasNameMessage;
import static org.fluentlenium.core.wait.WaitMessage.hasSizeMessage;
import static org.fluentlenium.core.wait.WaitMessage.hasTextMessage;
import static org.fluentlenium.core.wait.WaitMessage.isAboveMessage;
import static org.fluentlenium.core.wait.WaitMessage.isClickableMessage;
import static org.fluentlenium.core.wait.WaitMessage.isDisplayedMessage;
import static org.fluentlenium.core.wait.WaitMessage.isEnabledMessage;
import static org.fluentlenium.core.wait.WaitMessage.isNotPresentMessage;
import static org.fluentlenium.core.wait.WaitMessage.isPresentMessage;

public class FluentWaitMatcher {
    private List<Filter> filters = new ArrayList<>();
    private Search search;
    private By locator;
    private FluentWait wait;

    public FluentWaitMatcher(Search search, FluentWait fluentWait, String selector) {
        this.locator = By.cssSelector(selector);
        wait = fluentWait;
        this.search = search;
    }

    public FluentWaitMatcher(Search search, FluentWait fluentWait, By locator) {
        this.locator = locator;
        wait = fluentWait;
        this.search = search;
    }

    /**
     * WARNING - Should be change in a next version to hasAttribute("myAttribute").value("myValue")
     *
     * @param attribute attribute name
     * @param value     attribute value
     * @return fluent
     */
    public Fluent hasAttribute(final String attribute, final String value) {
        Predicate<Fluent> hasAttribute = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return find().getAttributes(attribute).contains(value);
            }
        };
        until(wait, hasAttribute, filters, hasAttributeMessage(locator, attribute, value));
        return FluentThread.get();

    }

    static void until(FluentWait wait, Predicate<Fluent> present, List<Filter> filters, String defaultMessage) {
        StringBuilder message = new StringBuilder(defaultMessage);
        if (filters != null && !filters.isEmpty()) {
            for (Filter filter : filters) {
                message.append(filter.toString());
            }
            message.append(" Filters : ");
        }
        if (wait.useCustomMessage()) {
            wait.untilPredicate(present);
        } else {
            wait.withMessage(message.toString()).untilPredicate(present);
        }

    }

    /**
     * check if the FluentWait has the corresponding id
     *
     * @param value id value
     * @return fluent
     */
    public Fluent hasId(final String value) {
        Predicate<Fluent> hasId = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return find().getIds().contains(value);
            }
        };
        until(wait, hasId, filters, hasIdMessage(locator, value));
        return FluentThread.get();
    }

    /**
     * check if the FluentWait has the corresponding name
     *
     * @param value name value
     * @return fluent
     */
    public Fluent hasName(final String value) {
        Predicate<Fluent> hasName = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return find().getNames().contains(value);
            }
        };
        until(wait, hasName, filters, hasNameMessage(locator, value));
        return FluentThread.get();
    }

    /**
     * Check that the element have a customized size
     *
     * @return fluent size builder
     */
    public FluentSizeBuilder hasSize() {
        return new FluentSizeBuilder(search, wait, locator, filters);
    }

    /**
     * check if the FluentWait is above top screen border
     *
     * @return fluent
     */
    public Fluent isAboveScreenOrInvisible() {
        Predicate<Fluent> isAbove = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                FluentList<FluentWebElement> elements = search
                        .find(locator, (Filter[]) filters.toArray(new Filter[filters.size()]));
                return Iterables.all(elements, isAboveScreenTopOrInvisible());
            }
        };
        until(wait, isAbove, filters, isAboveMessage(locator));
        return FluentThread.get();
    }

    private Predicate<FluentWebElement> isAboveScreenTopOrInvisible() {
        return new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement element) {
                WebElement wrapped = element.getElement();
                int bottomPosition = wrapped.getLocation().getY() + wrapped.getSize().getHeight();
                return !element.isDisplayed() || bottomPosition <= 0;
            }
        };
    }

    public FluentWaitAttributeMatcher<Integer> hasPositionX() {
        return new FluentWaitAttributeMatcher<>(wait, search, locator, filters, "position X",
                new Function<FluentWebElement, Integer>() {
                    @Override
                    public Integer apply(FluentWebElement element) {
                        return element.getElement().getLocation().getX();
                    }
                });
    }

    /**
     * Check that at least one element is displayed
     *
     * @return fluent
     */
    public Fluent anyDisplayed() {
        Predicate<Fluent> isVisible = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                FluentList<FluentWebElement> fluentWebElements = search
                        .find(locator, (Filter[]) filters.toArray(new Filter[filters.size()]));
                for (FluentWebElement fluentWebElement : fluentWebElements) {
                    if (fluentWebElement.isDisplayed()) {
                        return true;
                    }
                }
                return false;
            }
        };
        until(wait, isVisible, filters, isDisplayedMessage(locator));
        return FluentThread.get();
    }

    /**
     * Check that the element have the size indicated
     *
     * @param size integer value of size
     * @return fluent
     */
    public Fluent hasSize(final int size) {
        Predicate<Fluent> hasSize = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return find().size() == size;
            }
        };
        until(wait, hasSize, filters, hasSizeMessage(locator, size));
        return FluentThread.get();

    }

    /**
     * check if the FluentWait contains the corresponding text
     *
     * @param value text in contains check
     * @return fluent
     */
    public Fluent containsText(final String value) {
        Predicate<Fluent> hasText = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                List<String> texts = find().getTexts();
                if (texts != null) {
                    for (String text : texts) {
                        if (text.contains(value)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        };
        until(wait, hasText, filters, hasTextMessage(locator, value));
        return FluentThread.get();
    }

    /**
     * check if the FluentWait has the exact corresponding text
     *
     * @param value text in contains check
     * @return fluent
     */
    public Fluent hasText(final String value) {
        Predicate<Fluent> hasText = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return find().getTexts().contains(value);
            }
        };
        until(wait, hasText, filters, hasTextMessage(locator, value));
        return FluentThread.get();

    }

    /**
     * Check that the element is present
     *
     * @return fluent
     */
    public Fluent isPresent() {
        Predicate<Fluent> isPresent = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                int size = find().size();
                return size > 0;
            }
        };

        until(wait, isPresent, filters, isPresentMessage(locator));
        return FluentThread.get();

    }

    /**
     * Check that the element is not present
     *
     * @return fluent
     */
    public Fluent isNotPresent() {
        Predicate<Fluent> isNotPresent = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return find().isEmpty();
            }
        };

        until(wait, isNotPresent, filters, isNotPresentMessage(locator));
        return FluentThread.get();

    }

    /**
     * Check that the elements are all displayed
     *
     * @return fluent
     */
    public Fluent areDisplayed() {
        Predicate<Fluent> isVisible = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                FluentList<FluentWebElement> fluentWebElements = find();
                if (fluentWebElements.size() > 0) {
                    for (FluentWebElement fluentWebElement : fluentWebElements) {
                        if (!fluentWebElement.isDisplayed()) {
                            return false;
                        }
                    }
                    return true;
                }
                return false;
            }
        };
        until(wait, isVisible, filters, isDisplayedMessage(locator));
        return FluentThread.get();
    }

    /**
     * Check that the elements are all not displayed
     *
     * @return fluent
     */
    public Fluent areNotDisplayed() {
        Predicate<Fluent> isNotVisible = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                FluentList<FluentWebElement> fluentWebElements = findWithFilter();
                for (FluentWebElement fluentWebElement : fluentWebElements) {
                    if (fluentWebElement.isDisplayed()) {
                        return false;
                    }
                }
                return true;
            }
        };
        until(wait, isNotVisible, filters, isDisplayedMessage(locator));
        return FluentThread.get();
    }

    /**
     * Check that the elements are all enabled
     *
     * @return fluent
     */
    public Fluent areEnabled() {
        Predicate<Fluent> isEnabled = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                FluentList<FluentWebElement> fluentWebElements = find();
                if (fluentWebElements.size() > 0) {
                    for (FluentWebElement fluentWebElement : fluentWebElements) {
                        if (!fluentWebElement.isEnabled()) {
                            return false;
                        }
                    }
                    return true;
                }
                return false;
            }
        };
        until(wait, isEnabled, filters, isEnabledMessage(locator));
        return FluentThread.get();
    }

    /**
     * Check that the elements are all clickable
     */
    public Fluent isClickable() {
        Predicate<Fluent> isClickable = new com.google.common.base.Predicate<Fluent>() {

            @Override
            public boolean apply(Fluent input) {
                for (FluentWebElement element : find()) {
                    if (ExpectedConditions.elementToBeClickable(element.getElement()).apply(input.getDriver())
                            == null) {
                        return false;
                    }
                }
                return true;
            }
        };

        until(wait, isClickable, filters, isClickableMessage(locator));
        return FluentThread.get();
    }

    private FluentList<FluentWebElement> find() {
        if (filters.size() > 0) {
            return findWithFilter();
        } else {
            return search.find(locator);
        }
    }

    private FluentList<FluentWebElement> findWithFilter() {
        return search.find(locator, (Filter[]) filters.toArray(new Filter[filters.size()]));
    }

    /**
     * Create a filter builder for the attribute
     *
     * @param attribute attribute name
     * @return fluent wait builder
     */
    public FluentWaitBuilder with(String attribute) {
        return new FluentWaitBuilder(this, attribute);
    }

    /**
     * Create a filter builder for the attribute by id
     *
     * @return fluent wait builder
     */
    public FluentWaitBuilder withId() {
        return new FluentWaitBuilder(this, FilterType.ID);
    }

    /**
     * Check that the element has the corrsponding id
     *
     * @param value id name
     * @return fluent wait builder
     */
    public FluentWaitMatcher withId(final String value) {
        filters.add(org.fluentlenium.core.filter.FilterConstructor.withId(value));
        return this;
    }

    /**
     * Create a filter builder for the attribute by name
     *
     * @return fluent wait builder
     */
    public FluentWaitBuilder withName() {
        return new FluentWaitBuilder(this, FilterType.NAME);
    }

    /**
     * Check that the element has the corresponding name
     *
     * @param value element name
     * @return fluent wait builder
     */
    public FluentWaitMatcher withName(final String value) {
        filters.add(org.fluentlenium.core.filter.FilterConstructor.withName(value));
        return this;
    }

    /**
     * Create a filter builder for the attribute by class
     *
     * @return fluent wait builder
     */
    public FluentWaitBuilder withClass() {
        return new FluentWaitBuilder(this, FilterType.CLASS);
    }

    /**
     * Check that the element has the corresponding class
     *
     * @param value class name
     * @return fluent wait matcher
     */
    public FluentWaitMatcher withClass(final String value) {
        filters.add(org.fluentlenium.core.filter.FilterConstructor.withClass(value));
        return this;
    }

    /**
     * Create a filter builder for the attribute by text
     *
     * @return fluent wait builder
     */
    public FluentWaitBuilder withText() {
        return new FluentWaitBuilder(this, FilterType.TEXT);
    }

    /**
     * Check that the element has the corresponding text
     *
     * @param value the text value which should be included
     * @return fluent wait matcher
     */
    public FluentWaitMatcher withText(final String value) {
        filters.add(org.fluentlenium.core.filter.FilterConstructor.withText(value));
        return this;
    }

    void addFilter(Filter filter) {
        this.filters.add(filter);
    }

}
