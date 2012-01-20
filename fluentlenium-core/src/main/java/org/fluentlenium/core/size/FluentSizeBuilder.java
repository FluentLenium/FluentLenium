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

package org.fluentlenium.core.size;

import com.google.common.base.Predicate;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.search.Search;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class FluentSizeBuilder {

    String selector;
    private FluentWait wait;
    private Search search;
    private List<Filter> filter = new ArrayList<Filter>();

    public FluentSizeBuilder(Search search, FluentWait fluentWait, String selector, List<Filter> filter) {
        this.selector = selector;
        this.wait = fluentWait;
        this.search = search;
        this.filter = filter;
    }

    /**
     * Equals
     *
     * @param size
     */
    public void eq(final int size) {
        Predicate isPresent = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                return getSize() == size;
            }
        };
        wait.until(isPresent);
    }

    /**
     * Not equals
     *
     * @param size
     */
    public void ne(final int size) {
        Predicate isPresent = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                return getSize() != size;
            }
        };
        wait.until(isPresent);
    }

    /**
     * Less than
     *
     * @param size
     */
    public void lt(final int size) {
        Predicate isPresent = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                return getSize() < size;
            }
        };
        wait.until(isPresent);
    }

    /**
     * Less than or equals
     *
     * @param size
     */
    public void le(final int size) {
        Predicate isPresent = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                return getSize() <= size;
            }
        };
        wait.until(isPresent);
    }

    /**
     * Greater than
     *
     * @param size
     */
    public void gt(final int size) {
        Predicate isPresent = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                return getSize() > size;
            }
        };
        wait.until(isPresent);
    }

    /**
     * Greater than or equals
     *
     * @param size
     */
    public void ge(final int size) {
        Predicate isPresent = new com.google.common.base.Predicate<WebDriver>() {
            public boolean apply(@Nullable WebDriver webDriver) {
                return getSize() >= size;
            }
        };
        wait.until(isPresent);
    }

    private int getSize() {
        int size1;
        if (filter.size() > 0) {
            size1 = search.find(selector, (Filter[]) filter.toArray(new Filter[filter.size()])).size();
        } else {
            size1 = search.find(selector).size();
        }
        return size1;
    }


}
