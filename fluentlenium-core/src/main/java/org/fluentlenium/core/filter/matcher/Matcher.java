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

package org.fluentlenium.core.filter.matcher;


import java.util.regex.Pattern;

public abstract class Matcher {
    private String value;
    private Pattern pattern;

    protected Matcher(String value) {
        this.value = value;
    }

    protected Matcher(Pattern value) {
        this.pattern = value;
    }

    /**
     * return the given value
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     * Return the matcher symbol
     *
     * @return
     */
    public String getMatcherSymbol() {
        return getMatcherType() != null ? getMatcherType().getCssRepresentations() : null;
    }

    public final boolean isPreFilter() {
        if (pattern != null || null == getMatcherSymbol()) {
            return false;
        }
        return true;
    }

    /**
     * return the pattern
     *
     * @return
     */
    protected Pattern getPattern() {
        return pattern;
    }

    /**
     * Return the matcher type
     *
     * @return
     */
    protected abstract MatcherType getMatcherType();

    /**
     * Check if the matcher is matched given the value
     *
     * @param value
     * @return
     */
    public abstract boolean isSatisfiedBy(String value);


}
