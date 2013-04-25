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

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.Filter;

import java.util.function.Predicate;


public interface SearchActions {

    FluentList<FluentWebElement> find(String name);

    FluentList<FluentWebElement> find(String name, Filter... filters);

    FluentList<FluentWebElement> find(String name, Predicate<FluentWebElement>... predicate);

    FluentWebElement find(String name, Integer number, Filter... filters);

    FluentWebElement findFirst(String name, Filter... filters);
}
