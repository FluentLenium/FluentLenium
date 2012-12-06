/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import com.sun.istack.internal.Nullable;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.search.Search;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.ArrayList;
import java.util.List;

import static org.fluentlenium.core.wait.WaitMessage.*;

public class FluentSizeBuilder {

    String selector;
    private FluentWait wait;
    private Search search;
    private List<Filter> filters = new ArrayList<Filter>();

    public FluentSizeBuilder(Search search, FluentWait fluentWait, String selector, List<Filter> filters) {
        this.selector = selector;
        this.wait = fluentWait;
        this.search = search;
        this.filters = filters;
    }

    /**
     * Equals
     *
     * @param size
     */
    public void equalTo(final int size) {
        Predicate isPresent = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                return getSize() == size;
            }
        };
        FluentWaitMatcher.until(wait, isPresent, filters, equalToMessage(selector, size));
    }

    /**
     * Not equals
     *
     * @param size
     */
    public void notEqualTo(final int size) {
        Predicate isPresent = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                return getSize() != size;
            }
        };
        FluentWaitMatcher.until(wait, isPresent, filters, notEqualToMessage(selector, size));
    }

    /**
     * Less than
     *
     * @param size
     */
    public void lessThan(final int size) {
        Predicate isPresent = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                return getSize() < size;
            }
        };
        FluentWaitMatcher.until(wait, isPresent, filters, lessThanMessage(selector, size));
    }

    /**
     * Less than or equals
     *
     * @param size
     */
    public void lessThanOrEqualTo(final int size) {
        Predicate isPresent = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                return getSize() <= size;
            }
        };
        FluentWaitMatcher.until(wait, isPresent, filters, lessThanOrEqualToMessage(selector, size));
    }

    /**
     * Greater than
     *
     * @param size
     */
    public void greaterThan(final int size) {
        Predicate isPresent = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                return getSize() > size;
            }
        };
        FluentWaitMatcher.until(wait, isPresent, filters, greatherThanMessage(selector, size));
    }

    /**
     * Greater than or equals
     *
     * @param size
     */
    public void greaterThanOrEqualTo(final int size) {
        Predicate isPresent = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                return getSize() >= size;
            }
        };
        FluentWaitMatcher.until(wait, isPresent, filters, greatherThanOrEqualToMessage(selector, size));
    }

    private int getSize() {
        if (filters.size() > 0) {
            return search.find(selector, (Filter[]) filters.toArray(new Filter[filters.size()])).size();
        } else {
            return search.find(selector).size();
        }
    }


}