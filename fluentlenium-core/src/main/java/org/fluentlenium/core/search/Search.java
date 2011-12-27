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

package org.fluentlenium.core.search;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.filter.FilterPredicate;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Search implements SearchActions {
    private final SearchContext searchContext;

    public Search(SearchContext context) {
        this.searchContext = context;
    }

    /**
     * Central methods to find elements on the page. Can provide some filters. Able to use css1, css2, css3, see WebDriver  restrictions
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentList find(String name, final Filter... filters) {
        StringBuilder sb = new StringBuilder(name);
        List<Filter> postFilterSelector = new ArrayList<Filter>();
        if (filters != null&&filters.length>0) {
            for (Filter selector : filters) {
                if (selector.isPreFilter()) {
                    sb.append(selector.toString());
                } else {
                    postFilterSelector.add(selector);
                }
            }
        }
        List<FluentWebElement> preFiltered = select(sb.toString());
        Collection<FluentWebElement> postFiltered = preFiltered;
        for (Filter selector : postFilterSelector) {
            postFiltered = Collections2.filter(postFiltered, new FilterPredicate(selector));
        }

        return new FluentList(postFiltered);
    }

    private List<FluentWebElement> select(String cssSelector) {
        return Lists.transform(searchContext.findElements(By.cssSelector(cssSelector)), new Function<WebElement, FluentWebElement>() {
            public FluentWebElement apply(WebElement webElement) {
                return new FluentWebElement((webElement));
            }
        });
    }


    /**
     * Return the elements at the numner position into the the lists corresponding to the cssSelector with it filters
     *
     * @param name
     * @param number
     * @param filters
     * @return
     */
    public FluentWebElement find(String name, Integer number, final Filter... filters) {
        List<FluentWebElement> listFiltered = find(name, filters);
        if (number >= listFiltered.size()) {
            throw new NoSuchElementException("No such element with position :" + number + ". Number of elements available :" + listFiltered.size());
        }
        return listFiltered.get(number);
    }

    /**
     * Return the first elements corresponding to the name and the filters
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentWebElement findFirst(String name, final Filter... filters) {
        FluentList fluentList = find(name, filters);
        return fluentList.first();
    }

}
