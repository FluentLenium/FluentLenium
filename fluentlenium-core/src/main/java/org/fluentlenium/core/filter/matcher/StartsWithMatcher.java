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

package org.fluentlenium.core.filter.matcher;

import java.util.regex.Pattern;

public class StartsWithMatcher extends Matcher {
    /**
     * Constructor using a string as a value
     *
     * @param value as string for class constructor
     */
    public StartsWithMatcher(String value) {
        super(value);
    }

    /**
     * Constructor using a pattern as a value
     *
     * @param value as pattern for class constructor
     */
    public StartsWithMatcher(Pattern value) {
        super(value);
    }

    @Override
    public MatcherType getMatcherType() {
        return MatcherType.START_WITH;
    }

    @Override
    public boolean isSatisfiedBy(String o) {
        return CalculateService.startsWith(getPattern(), getValue(), o);
    }

}
