/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentThread;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.filter.FilterType;
import org.fluentlenium.core.search.Search;

import java.util.ArrayList;
import java.util.List;

import static org.fluentlenium.core.wait.WaitMessage.*;

public class FluentWaitMatcher {
    private List<Filter> filters = new ArrayList<Filter>();
    private Search search;
    private String selector;
    private FluentWait wait;

    public FluentWaitMatcher(Search search, FluentWait fluentWait, String selector) {
        this.selector = selector;
        wait = fluentWait;
        this.search = search;
    }

    /**
     * WARNING - Should be change in a next version to hasAttribute("myAttribute").value("myValue")
     *
     * @param attribute
     * @param value
     */
    public Fluent hasAttribute(final String attribute, final String value) {
        Predicate hasAttribute = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return find().getAttributes(attribute).contains(value);
            }
        };
        until(wait, hasAttribute, filters, hasAttributeMessage(selector, attribute, value));
        return FluentThread.get();

    }

    static void until(FluentWait wait, Predicate present, List<Filter> filters, String defaultMessage) {
        StringBuilder message = new StringBuilder(defaultMessage);
        if (filters != null && !filters.isEmpty()) {
            for (Filter filter : filters) {
                message.append(filter.toString());
            }
            message.append(" Filters : ");
        }
        wait.withMessage(message.toString()).until(present);

    }

    /**
     * check if the FluentWait has the corresponding id
     *
     * @param value
     */
    public Fluent hasId(final String value) {
        Predicate hasId = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return find().getIds().contains(value);
            }
        };
        until(wait, hasId, filters, hasIdMessage(selector, value));
        return FluentThread.get();
    }

    /**
     * check if the FluentWait has the corresponding name
     *
     * @param value
     */
    public Fluent hasName(final String value) {
        Predicate hasName = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return find().getNames().contains(value);
            }
        };
        until(wait, hasName, filters, hasNameMessage(selector, value));
        return FluentThread.get();
    }

    /**
     * Check that the element have a customized size
     *
     * @return
     */
    public FluentSizeBuilder hasSize() {
        return new FluentSizeBuilder(search, wait, selector, filters);
    }

    /**
     * Check that the element have the size indicated
     *
     * @param size
     */
    public Fluent hasSize(final int size) {
        Predicate hasSize = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return find().size() == size;
            }
        };
        until(wait, hasSize, filters, hasSizeMessage(selector, size));
        return FluentThread.get();

    }

    /**
     * check if the FluentWait contains the corresponding text
     *
     * @param value
     */
    public Fluent containsText(final String value) {
        Predicate hasText = new com.google.common.base.Predicate<Fluent>() {
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
        until(wait, hasText, filters, hasTextMessage(selector, value));
        return FluentThread.get();
    }

    /**
     * check if the FluentWait has the exact corresponding text
     *
     * @param value
     */
    public Fluent hasText(final String value) {
        Predicate hasText = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return find().getTexts().contains(value);
            }
        };
        until(wait, hasText, filters, hasTextMessage(selector, value));
        return FluentThread.get();

    }

    /**
     * Check that the element is present
     */
    public Fluent isPresent() {
        Predicate isPresent = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                int size = find().size();
                return size > 0;
            }
        };

        until(wait, isPresent, filters, isPresentMessage(selector));
        return FluentThread.get();

    }

    /**
     * Check that the element is not present
     */
    public Fluent isNotPresent() {
        Predicate isNotPresent = new com.google.common.base.Predicate<Fluent>() {
            public boolean apply(Fluent fluent) {
                return find().isEmpty();
            }
        };

        until(wait, isNotPresent, filters, isNotPresentMessage(selector));
        return FluentThread.get();

    }

    /**
     * Check that the elements are all displayed
     *
     * @return
     */
    public Fluent areDisplayed() {
        Predicate isVisible = new com.google.common.base.Predicate<Fluent>() {
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
        until(wait, isVisible, filters, isDisplayedMessage(selector));
        return FluentThread.get();
    }

    /**
     * Check that the elements are all not displayed
     *
     * @return
     */
    public Fluent areNotDisplayed() {
        Predicate isNotVisible = new com.google.common.base.Predicate<Fluent>() {
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
        until(wait, isNotVisible, filters, isDisplayedMessage(selector));
        return FluentThread.get();
    }

    /**
     * Check that the elements are all enabled
     *
     * @return
     */
    public Fluent areEnabled() {
        Predicate isEnabled = new com.google.common.base.Predicate<Fluent>() {
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
        until(wait, isEnabled, filters, isEnabledMessage(selector));
        return FluentThread.get();
    }

    private FluentList<FluentWebElement> find() {
        if (filters.size() > 0) {
            return findWithFilter();
        } else {
            return search.find(selector);
        }
    }

    private FluentList<FluentWebElement> findWithFilter() {
        return search.find(selector, (Filter[]) filters.toArray(new Filter[filters.size()]));
    }

    /**
     * Create a filter builder for the attribute
     *
     * @param attribute
     * @return
     */
    public FluentWaitBuilder with(String attribute) {
        return new FluentWaitBuilder(this, attribute);
    }

    /**
     * Create a filter builder for the attribute by id
     *
     * @param
     * @return
     */
    public FluentWaitBuilder withId() {
        return new FluentWaitBuilder(this, FilterType.ID);
    }

    /**
     * Check that the element has the corrsponding id
     *
     * @param value
     * @return
     */
    public FluentWaitMatcher withId(final String value) {
        filters.add(org.fluentlenium.core.filter.FilterConstructor.withId(value));
        return this;
    }

    /**
     * Create a filter builder for the attribute by name
     *
     * @param
     * @return
     */
    public FluentWaitBuilder withName() {
        return new FluentWaitBuilder(this, FilterType.NAME);
    }

    /**
     * Check that the element has the corrsponding name
     *
     * @param value
     * @return
     */
    public FluentWaitMatcher withName(final String value) {
        filters.add(org.fluentlenium.core.filter.FilterConstructor.withName(value));
        return this;
    }


    /**
     * Create a filter builder for the attribute by class
     *
     * @param
     * @return
     */
    public FluentWaitBuilder withClass() {
        return new FluentWaitBuilder(this, FilterType.CLASS);
    }

    /**
     * Check that the element has the corresponding class
     *
     * @param value
     * @return
     */
    public FluentWaitMatcher withClass(final String value) {
        filters.add(org.fluentlenium.core.filter.FilterConstructor.withClass(value));
        return this;
    }

    /**
     * Create a filter builder for the attribute by text
     *
     * @param
     * @return
     */
    public FluentWaitBuilder withText() {
        return new FluentWaitBuilder(this, FilterType.TEXT);
    }

    /**
     * Check that the element has the corresponding text
     *
     * @param value
     * @return
     */
    public FluentWaitMatcher withText(final String value) {
        filters.add(org.fluentlenium.core.filter.FilterConstructor.withText(value));
        return this;
    }

    void addFilter(Filter filter) {
        this.filters.add(filter);
    }


}
