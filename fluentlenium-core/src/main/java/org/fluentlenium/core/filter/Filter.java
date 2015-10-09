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

package org.fluentlenium.core.filter;

import org.fluentlenium.core.filter.matcher.EqualMatcher;
import org.fluentlenium.core.filter.matcher.Matcher;


public class Filter {
    private final String attribut;
    private final Matcher matcher;

    /**
     * Construct a filter with a type and an associated value
     *
     * @param filterType
     * @param value
     */
    public Filter(FilterType filterType, String value) {
        this.attribut = filterType.name();
        this.matcher = new EqualMatcher(value);
    }

    /**
     * Construct a filter with a type and an associated matcher
     *
     * @param filterType
     * @param matcher
     */
    public Filter(FilterType filterType, Matcher matcher) {
        this.attribut = filterType.name();
        this.matcher = matcher;
    }

    /**
     * Construct a filter with a custom attribute and an associated value
     *
     * @param customAttribute
     * @param value
     */
    public Filter(String customAttribute, String value) {
        this.attribut = customAttribute;
        this.matcher = new EqualMatcher(value);
    }


    /**
     * Construct a filter with a custom attribute and an associated matcher
     *
     * @param customAttribute
     * @param matcher
     */
    public Filter(String customAttribute, Matcher matcher) {
        this.attribut = customAttribute;
        this.matcher = matcher;
    }

    public String getAttribut() {
        return attribut.toLowerCase();
    }

    public String toString() {
        String matcherAttribute = matcher != null ? matcher.getMatcherSymbol() : "";
        return "[" + attribut.toLowerCase() + matcherAttribute + "=\"" + matcher.getValue() + "\"]";
    }

    public Matcher getMatcher() {
        return matcher;
    }

    public boolean isPreFilter() {
        if ((matcher != null && matcher.isPreFilter()) && !FilterType.TEXT.name().equalsIgnoreCase(getAttribut())) {
            return true;
        }
        return false;
    }
}