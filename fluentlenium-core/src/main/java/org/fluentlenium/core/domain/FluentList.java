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

import org.fluentlenium.core.action.FluentDefaultActions;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.search.SearchActions;

import java.util.List;

/**
 *
 */
public interface FluentList<E extends FluentWebElement> extends List<E>, FluentDefaultActions<FluentList>, SearchActions {

    /**
     * Return the first element of the list
     * If none, return NoSuchElementException
     *
     * @return
     * @throws org.openqa.selenium.NoSuchElementException
     */
    E first();

    /**
     * Click on all elements on the list
     * Only the visible elements are filled
     */
    @Override
    FluentList click();

    /**
     * Fill  all elements on the list with the corresponding cell in the with table.
     * Only the visible elements are filled
     * If there is more elements on the list than in the with table, the last element of the table is repeated
     */
    @Override
    FluentList text(String... with);

    /**
     * submit on all elements on the list
     * Only the visible elements are submitted
     */
    @Override
    FluentList<E> submit();

    /**
     * Return the value of elements on the list
     *
     * @return
     */
    List<String> getValues();

    /**
     * Return the id of elements on the list
     *
     * @return
     */
    List<String> getIds();

    /**
     * Return a custom attribute of elements on the list
     *
     * @return
     */
    List<String> getAttributes(String attribute);

    /**
     * Return the name of elements on the list
     *
     * @return
     */
    List<String> getNames();

    /**
     * Return the texts of list elements
     *
     * @return
     */
    List<String> getTexts();

    /**
     * Return the value of the first element in the list
     *
     * @return
     */
    String getValue();

    /**
     * Return the id of the first element on the list
     *
     * @return
     */
    String getId();

    /**
     * Return a custom attribute of the first element on the list
     *
     * @return
     */
    String getAttribute(String attribute);

    /**
     * Return the name of the first element on the list
     *
     * @return
     */
    String getName();

    /**
     * Return the text of the first element on the list
     *
     * @return
     */
    String getText();

    /**
     * find elements into the children with the corresponding filters
     *
     * @param name
     * @param filters
     * @return
     */
    @Override
    FluentList<E> find(String name, Filter... filters);

    /**
     * find elements into the children with the corresponding filters at the position indicated by the number
     *
     * @param name
     * @param number
     * @param filters
     * @return
     */
    @Override
    E find(String name, Integer number, Filter... filters);

    /**
     * find elements into the children with the corresponding filters at the first position
     *
     * @param name
     * @param filters
     * @return
     */
    @Override
    E findFirst(String name, Filter... filters);


    /**
     * Clear all elements on the list
     * Only the visible elements are filled
     */
    FluentList<E> clearAll();

    /**
     * Clear all elements on the list
     * Only the visible elements are filled
     */
    void clear();
}
