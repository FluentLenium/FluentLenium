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


public class FilterBuilder {

    String attribute;

    public FilterBuilder(String customAttribute) {
        this.attribute = customAttribute;
    }

    public FilterBuilder(FilterType filterType) {
        this.attribute = filterType.name();

    }

    public Filter equalTo(String equal) {
        return new Filter( attribute, new EqualMatcher(equal));
    }

    public Filter contains(String equal) {
        return new Filter( attribute, new ContainsMatcher(equal));
    }

    public Filter contains(Pattern equal) {
        return new Filter( attribute, new ContainsMatcher(equal));
    }

    public Filter startsWith(String equal) {
        return new Filter( attribute, new StartsWithMatcher(equal));
    }

    public Filter startsWith(Pattern equal) {
        return new Filter( attribute, new StartsWithMatcher(equal));
    }

    public Filter endsWith(String equal) {
        return new Filter( attribute, new EndsWithMatcher(equal));
    }

    public Filter endsWith(Pattern equal) {
        return new Filter( attribute, new EndsWithMatcher(equal));
    }

    public Filter notContains(String equal) {
        return new Filter( attribute, new NotContainsMatcher(equal));
    }

    public Filter notContains(Pattern equal) {
        return new Filter( attribute, new NotContainsMatcher(equal));
    }

    public Filter notStartsWith(String equal) {
        return new Filter( attribute, new NotStartsWithMatcher(equal));
    }

    public Filter notStartsWith(Pattern equal) {
        return new Filter( attribute, new NotStartsWithMatcher(equal));
    }

    public Filter notEndsWith(String equal) {
        return new Filter( attribute, new NotEndsWithMatcher(equal));
    }

    public Filter notEndsWith(Pattern equal) {
        return new Filter( attribute, new NotEndsWithMatcher(equal));
    }


}
