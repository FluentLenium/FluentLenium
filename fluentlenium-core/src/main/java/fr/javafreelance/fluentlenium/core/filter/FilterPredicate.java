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

package fr.javafreelance.fluentlenium.core.filter;

import com.google.common.base.Predicate;
import fr.javafreelance.fluentlenium.core.domain.FluentWebElement;

/**
 * Filter a FluentWebElement collection to return only the elements with the same text
 */
public class FilterPredicate implements Predicate<FluentWebElement> {
    private final Filter filter;

    public FilterPredicate(Filter text) {
        this.filter = text;
    }

    public boolean apply(FluentWebElement webElementCustom) {

        String attribute = returnTextIfTextAttributeElseAttributeValue(webElementCustom);
        if (filter != null && filter.getMatcher().isSatisfiedBy(attribute)) {
            return true;
        }
        return false;
    }

    private String returnTextIfTextAttributeElseAttributeValue(FluentWebElement webElementCustom) {
        return ("text".equalsIgnoreCase(filter.getAttribut())) ? webElementCustom.getText() : webElementCustom.getAttribute(filter.getAttribut());
    }

}
