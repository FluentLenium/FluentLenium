package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.Search;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.fluentlenium.core.wait.FluentWaitMessages.hasAttributeMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.hasIdMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.hasNameMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.hasPositionXMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.hasTextMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isAboveMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isClickableMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isDisplayedMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isEnabledMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isNotPresentMessage;
import static org.fluentlenium.core.wait.FluentWaitMessages.isPresentMessage;

/**
 * Base Matcher for waiting on a single element.
 */
public abstract class AbstractWaitElementMatcher extends AbstractWaitMatcher {
    protected Search search;
    protected String selectionName;
    protected FluentWait wait;

    public AbstractWaitElementMatcher(Search search, FluentWait wait, String selectionName) {
        this.selectionName = selectionName;
        this.wait = wait;
        this.search = search;
    }

    /**
     * WARNING - Should be change in a next version to hasAttribute("myAttribute").value("myValue")
     *
     * @param value attribute value
     */
    public void hasPositionX(final Integer value) {
        Predicate<Fluent> hasPositionX = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                FluentList<? extends FluentWebElement> fluentWebElements = find();
                for (FluentWebElement fluentWebElement : fluentWebElements) {
                    if (fluentWebElement.getElement().getLocation().getX() == value) {
                        return true;
                    }
                }
                return false;
            }
        };
        until(wait, hasPositionX, hasPositionXMessage(selectionName, value));
    }

    /**
     *   * check if the FluentWait is above top screen border  *  * @return fluent
     */
    public void isAboveScreenOrInvisble() {
        Predicate<Fluent> isAbove = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return Iterables.all(find(), isAboveScreenTopOrInvisible());
            }
        };
        until(wait, isAbove, isAboveMessage(find().getIds()));
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

    /**
     * WARNING - Should be change in a next version to hasAttribute("myAttribute").value("myValue")
     *
     * @param attribute attribute name
     * @param value     attribute value
     */
    public void hasAttribute(final String attribute, final String value) {
        Predicate<Fluent> hasAttribute = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return find().getAttributes(attribute).contains(value);
            }
        };
        until(wait, hasAttribute, hasAttributeMessage(selectionName, attribute, value));
    }

    /**
     * check if the FluentWait has the corresponding id
     *
     * @param value id value
     */
    public void hasId(final String value) {
        Predicate<Fluent> hasId = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return find().getIds().contains(value);
            }
        };
        until(wait, hasId, hasIdMessage(selectionName, value));
    }

    /**
     * check if the FluentWait has the corresponding name
     *
     * @param value name value
     */
    public void hasName(final String value) {
        Predicate<Fluent> hasName = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return find().getNames().contains(value);
            }
        };
        until(wait, hasName, hasNameMessage(selectionName, value));
    }

    /**
     * check if the FluentWait contains the corresponding text
     *
     * @param value text in contains check
     */
    public void containsText(final String value) {
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
        until(wait, hasText, hasTextMessage(selectionName, value));
    }

    /**
     * check if the FluentWait has the exact corresponding text
     *
     * @param value text in contains check
     */
    public void hasText(final String value) {
        Predicate<Fluent> hasText = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return find().getTexts().contains(value);
            }
        };
        until(wait, hasText, hasTextMessage(selectionName, value));
    }

    /**
     * Check that the element is present
     */
    public void isPresent() {
        Predicate<Fluent> isPresent = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                int size = find().size();
                return size > 0;
            }
        };

        until(wait, isPresent, isPresentMessage(selectionName));
    }

    /**
     * Check that the element is not present
     */
    public void isNotPresent() {
        Predicate<Fluent> isNotPresent = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return find().isEmpty();
            }
        };

        until(wait, isNotPresent, isNotPresentMessage(selectionName));
    }

    /**
     * Check that one or more element is displayed
     */
    public void isDisplayed() {
        Predicate<Fluent> isVisible = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                FluentList<? extends FluentWebElement> fluentWebElements = find();
                for (FluentWebElement fluentWebElement : fluentWebElements) {
                    if (fluentWebElement.isDisplayed()) {
                        return true;
                    }
                }
                return false;
            }
        };
        until(wait, isVisible, isDisplayedMessage(selectionName));
    }

    /**
     * Check that one or more elements is not displayed
     */
    public void isNotDisplayed() {
        Predicate<Fluent> isNotVisible = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                FluentList<? extends FluentWebElement> fluentWebElements = find();
                if (fluentWebElements.size() > 0) {
                    for (FluentWebElement fluentWebElement : fluentWebElements) {
                        if (!fluentWebElement.isDisplayed()) {
                            return true;
                        }
                    }
                    return false;
                }
                return true;
            }
        };
        until(wait, isNotVisible, isDisplayedMessage(selectionName));
    }

    /**
     * Check that one or more element is enabled
     */
    public void isEnabled() {
        Predicate<Fluent> isEnabled = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                FluentList<? extends FluentWebElement> fluentWebElements = find();
                if (fluentWebElements.size() > 0) {
                    for (FluentWebElement fluentWebElement : fluentWebElements) {
                        if (fluentWebElement.isEnabled()) {
                            return true;
                        }
                    }
                    return false;
                }
                return false;
            }
        };
        until(wait, isEnabled, isEnabledMessage(selectionName));
    }

    /**
     * Check that the elements are all clickable
     */
    public void isClickable() {
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

        until(wait, isClickable, isClickableMessage(selectionName));
    }

    /**
     * Find the elements from configured matcher.
     *
     * @return fuent list of matching elements.
     */
    abstract protected FluentList<? extends FluentWebElement> find();

}
