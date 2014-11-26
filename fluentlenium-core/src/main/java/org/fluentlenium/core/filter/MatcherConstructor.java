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

import org.fluentlenium.core.filter.matcher.*;

import java.util.regex.Pattern;


public final class MatcherConstructor {

    private MatcherConstructor() {
    }


    /**
     * Create a matcher for a containing string
     *
     * @param matcher
     * @return
     */
    public static Matcher contains(String matcher) {
        return new ContainsMatcher(matcher);
    }

    /**
     * Create a matcher for a containing pattern
     *
     * @param pattern
     * @return
     */
    public static Matcher contains(Pattern pattern) {
        return new ContainsMatcher(pattern);
    }

    /**
     * Create a matcher for not containing a string
     *
     * @param matcher
     * @return
     */

    public static Matcher notContains(String matcher) {
        return new NotContainsMatcher(matcher);
    }

    /**
     * Create a matcher for not containing the pattern
     *
     * @param pattern
     * @return
     */
    public static Matcher notContains(Pattern pattern) {
        return new NotContainsMatcher(pattern);
    }

    /**
     * Create a matcher to equal the string matcher
     *
     * @param matcher
     * @return
     */
    public static Matcher equal(String matcher) {
        return new EqualMatcher(matcher);
    }

    /**
     * Create a Pattern given a regex. The regex is compile.
     *
     * @param pattern
     * @return
     */
    public static Pattern regex(String pattern) {
        return Pattern.compile(pattern);

    }

    /**
     * Create a matcher filtering by a string that start with the matcher
     *
     * @param matcher
     * @return
     */
    public static Matcher startsWith(String matcher) {
        return new StartsWithMatcher(matcher);
    }

    /**
     * Create a matcher filtering by a string that start with the matcher
     *
     * @param pattern
     * @return
     */
    public static Matcher startsWith(Pattern pattern) {
        return new StartsWithMatcher(pattern);
    }

    /**
     * Create a matcher filtering by a string that ends with the matcher
     *
     * @param matcher
     * @return
     */
    public static Matcher endsWith(String matcher) {
        return new EndsWithMatcher(matcher);
    }

    /**
     * Create a matcher filtering by a string that ends with the pattern
     *
     * @param pattern
     * @return
     */

    public static Matcher endsWith(Pattern pattern) {
        return new EndsWithMatcher(pattern);
    }

    /**
     * Create a matcher filtering by a string that not starts with the string params
     *
     * @param matcher
     * @return
     */
    public static Matcher notStartsWith(String matcher) {
        return new NotStartsWithMatcher(matcher);
    }

    /**
     * Create a matcher filtering by a string that not starts with the pattern params
     *
     * @param pattern
     * @return
     */

    public static Matcher notStartsWith(Pattern pattern) {
        return new NotStartsWithMatcher(pattern);
    }

    /**
     * Create a matcher filtering by a string that not ends with the string params
     *
     * @param matcher
     * @return
     */
    public static Matcher notEndsWith(String matcher) {
        return new NotEndsWithMatcher(matcher);
    }

    /**
     * Create a matcher filtering by a string that not ends with the pattern params
     *
     * @param pattern
     * @return
     */
    public static Matcher notEndsWith(Pattern pattern) {
        return new NotEndsWithMatcher(pattern);
    }

}
