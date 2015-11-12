/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package org.fluentlenium.core.filter;

import org.fluentlenium.core.filter.matcher.ContainsWordMatcher;
import org.fluentlenium.core.filter.matcher.Matcher;

public final class FilterConstructor {

    private FilterConstructor() {
    }

    /**
     * Create a filter by name
     *
     * @param name element name
     * @param type filter type
     * @param matcherType class for matcherType
     * @return filter object
     */
    public static Filter buildFilter(String name, FilterType type, Class matcherType) {
        if (matcherType.equals(ContainsWordMatcher.class)) {
            return new Filter(FilterType.NAME, name);
        }
        return null;
    }

    /**
     * Create a filter by name
     *
     * @param name element name
     * @return filter object
     */
    public static Filter withName(String name) {
        return new Filter(FilterType.NAME, name);
    }

    /**
     * Create a filter by id
     *
     * @param id element id
     * @return filter object
     */
    public static Filter withId(String id) {
        return new Filter(FilterType.ID, id);
    }

    /**
     * Create a filter by class
     *
     * @param klass CSS class name
     * @return filter object
     */
    public static Filter withClass(String klass) {
        return new Filter(FilterType.CLASS, klass);
    }

    /**
     * Create a filter by text
     *
     * @param text to filter in content
     * @return filter object
     */
    public static Filter withText(String text) {
        return new Filter(FilterType.TEXT, MatcherConstructor.equal(text));
    }

    /**
     * Create a filter by text
     *
     * @param text to filter in content
     * @return filter object
     */
    public static Filter containingText(String text) {
        return new Filter(FilterType.TEXT, MatcherConstructor.contains(text));
    }

    /**
     * Create a filter builder for the attribute
     *
     * @param attribute attribute name
     * @return filter builder object
     */
    public static FilterBuilder with(String attribute) {
        return new FilterBuilder(attribute);
    }

    /**
     * Create a filter builder for the attribute by name
     *
     * @return filter builder object
     */
    public static FilterBuilder withName() {
        return new FilterBuilder(FilterType.NAME);
    }

    /**
     * Create a filter builder for the attribute by id
     *
     * @return filter builder object
     */
    public static FilterBuilder withId() {
        return new FilterBuilder(FilterType.ID);
    }

    /**
     * Create a filter builder for the attribute by class
     *
     * @return filter builder object
     */
    public static FilterBuilder withClass() {
        return new FilterBuilder(FilterType.CLASS);
    }

    /**
     * Create a filter builder for the attribute by text
     *
     * @return filter builder object
     */
    public static FilterBuilder withText() {
        return new FilterBuilder(FilterType.TEXT);
    }

    /**
     * Create a filter by name with matcher
     * DEPRECATED : use withName().+convenient method
     *
     * @param matcher matcher object
     * @return filter object
     */
    @Deprecated
    public static Filter withName(Matcher matcher) {
        return new Filter(FilterType.NAME, matcher);
    }

    /**
     * Create a filter by id
     * DEPRECATED : use withId().+convenient method
     *
     * @param matcher matcher object
     * @return filter object
     */
    @Deprecated
    public static Filter withId(Matcher matcher) {
        return new Filter(FilterType.ID, matcher);
    }

    /**
     * Create a filter by text
     * DEPRECATED : use withText().+convenient method
     *
     * @param matcher matcher object
     * @return filter object
     */
    @Deprecated
    public static Filter withText(Matcher matcher) {
        return new Filter(FilterType.TEXT, matcher);
    }

    /**
     * Create a filter by a customAttribute
     * DEPRECATED : use with(customAttribute).+convenient method
     *
     * @param customAttribute custom attribute name
     * @param matcher matcher object
     * @return filter object
     */
    @Deprecated
    public static Filter with(String customAttribute, Matcher matcher) {
        return new Filter(customAttribute, matcher);
    }

    /**
     * Create a filter by a customAttribute
     * DEPRECATED : use with(customAttribute).+convenient method
     *
     * @param customAttribute custom attribute name
     * @param matcher string matcher
     * @return filter object
     */
    @Deprecated
    public static Filter with(String customAttribute, String matcher) {
        return new Filter(customAttribute, matcher);
    }

}
