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

package org.fluentlenium.core.domain;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.fluentlenium.core.action.FluentDefaultActions;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.search.SearchActions;
import org.openqa.selenium.NoSuchElementException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Map the list to a FluentList in order to offers some events like click(), submit(), value() ...
 */
public class  FluentList<E extends FluentWebElement> extends ArrayList<E> implements FluentDefaultActions<FluentList>, SearchActions {

    public FluentList(Collection<E> listFiltered) {
        super(listFiltered);
    }

    /**
     * Return the first element of the list
     * If none, return NoSuchElementException
     *
     * @return
     * @throws NoSuchElementException
     */
    public E first() {
        if (this.size() == 0) {
            throw new NoSuchElementException("Element not found");
        }
        return this.get(0);
    }

    /**
     * Click on all elements on the list
     * Only the visible elements are filled
     */
    public FluentList click() {
        for (E fluentWebElement : this) {
            if (fluentWebElement.isEnabled()) {
                fluentWebElement.click();
            }
        }
        return this;
    }

    /**
     * Fill  all elements on the list with the corresponding cell in the with table.
     * Only the visible elements are filled
     * If there is more elements on the list than in the with table, the last element of the table is repeated
     */
    public FluentList text(String... with) {
        boolean atMostOne=false;
        if (with.length > 0) {
            int id = 0;
            String value;

            for (E fluentWebElement : this) {
                if (fluentWebElement.isDisplayed()) {
                    if (with.length > id) {
                        value = with[id++];
                    } else {
                        value = with[with.length - 1];
                    }
                    if (fluentWebElement.isEnabled()) {
                        atMostOne=true;
                        fluentWebElement.clear();
                        fluentWebElement.text(value);
                    }
                }
            }
            if (atMostOne==false){
                throw new NoSuchElementException("No element is displayed or enabled. Can't set a new value.");
            }
        }
        return this;
    }

    /**
     * Clear all elements on the list
     * Only the visible elements are filled
     */
    public FluentList clearAll() {
        for (E fluentWebElement : this) {
            if (fluentWebElement.isEnabled()) {
                fluentWebElement.clear();
            }
        }
        return this;
    }


    /**
     * Clear all elements on the list
     * Only the visible elements are filled
     */
    public void clear() {
        for (E fluentWebElement : this) {
            if (fluentWebElement.isEnabled()) {
                fluentWebElement.clear();
            }
        }
    }


    /**
     * submit on all elements on the list
     * Only the visible elements are submitted
     */
    public FluentList submit() {
        for (E fluentWebElement : this) {
            if (fluentWebElement.isEnabled()) {
                fluentWebElement.submit();
            }
        }
        return this;
    }

    /**
     * Return the value of elements on the list
     *
     * @return
     */
    public List<String> getValues() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getValue();
            }
        });
    }

    /**
     * Return the id of elements on the list
     *
     * @return
     */
    public List<String> getIds() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getId();
            }
        });
    }

    /**
     * Return a custom attribute of elements on the list
     *
     * @return
     */
    public List<String> getAttributes(final String attribute) {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getAttribute(attribute);
            }
        });
    }

    /**
     * Return the name of elements on the list
     *
     * @return
     */
    public List<String> getNames() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getName();
            }
        });
    }

    /**
     * Return the texts of list elements
     *
     * @return
     */
    public List<String> getTexts() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getText();
            }
        });
    }


    /**
     * Return the value of the first element in the list
     *
     * @return
     */
    public String getValue() {
        if (this.size() > 0) {
            return this.get(0).getValue();
        }
        return null;
    }

    /**
     * Return the id of the first element on the list
     *
     * @return
     */
    public String getId() {
        if (this.size() > 0) {
            return this.get(0).getId();
        }
        return null;
    }

    /**
     * Return a custom attribute of the first element on the list
     *
     * @return
     */
    public String getAttribute(final String attribute) {
        if (this.size() > 0) {
            return this.get(0).getAttribute(attribute);
        }
        return null;
    }

    /**
     * Return the name of the first element on the list
     *
     * @return
     */
    public String getName() {
        if (this.size() > 0) {
            return this.get(0).getName();
        }
        return null;
    }

    /**
     * Return the text of the first element on the list
     *
     * @return
     */
    public String getText() {
        if (this.size() > 0) {
            return this.get(0).getText();
        }
        return null;
    }

    /**
     * find elements into the children with the corresponding filters
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentList find(String name, Filter... filters) {
        List<E> finds = new ArrayList<E>();
        for (E e : this) {
            finds.addAll(e.find(name, filters));
        }
        return new FluentList(finds);
    }


    /**
     * find elements into the children with the corresponding filters at the position indicated by the number
     *
     * @param name
     * @param number
     * @param filters
     * @return
     */
    public E find(String name, Integer number, Filter... filters) {
        FluentList<E> fluentList = find(name, filters);
        if (number >= fluentList.size()) {
            throw new NoSuchElementException("No such element with position :" + number + ". Number of elements available :" + fluentList.size());
        }
        return fluentList.get(number);
    }

    /**
     * find elements into the children with the corresponding filters at the first position
     *
     * @param name
     * @param filters
     * @return
     */
    public E findFirst(String name, Filter... filters) {
        return find(name, 0, filters);
    }
}

