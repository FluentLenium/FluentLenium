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

import fr.javafreelance.fluentlenium.core.filter.matcher.Matcher;


public final class FilterConstructor {

    private FilterConstructor() {
    }

    /**
     * Create a filter by name
     *
     * @param name
     * @return
     */
    public static Filter withName(String name) {
        return new Filter(FilterType.NAME, name);
    }

    /**
     * Create a filter by id
     *
     * @param id
     * @return
     */
    public static Filter withId(String id) {
        return new Filter(FilterType.ID, id);
    }

    /**
     * Create a filter by a customattribute
     *
     * @param customAttribute
     * @param value
     * @return
     */
    public static Filter with(String customAttribute, String value) {
        return new Filter(customAttribute, value);
    }

    /**
     * Create a filter by text
     *
     * @param text
     * @return
     */
    public static Filter withText(String text) {
        return new Filter(FilterType.TEXT, MatcherConstructor.equal(text));
    }

    /**
     * Create a filter by name with matcher
     *
     * @param matcher
     * @return
     */
    public static Filter withName(Matcher matcher) {
        return new Filter(FilterType.NAME, matcher);
    }

    /**
     * Create a filter by id
     *
     * @param matcher
     * @return
     */
    public static Filter withId(Matcher matcher) {
        return new Filter(FilterType.ID, matcher);
    }

    /**
     * Create a filter by text
     *
     * @param matcher
     * @return
     */
    public static Filter withText(Matcher matcher) {
        return new Filter(FilterType.TEXT, matcher);
    }


    /**
     * Create a filter by a customattribute
     *
     * @param customAttribute
     * @param matcher
     * @return
     */
    public static Filter with(String customAttribute, Matcher matcher) {
        return new Filter(customAttribute, matcher);
    }


}
