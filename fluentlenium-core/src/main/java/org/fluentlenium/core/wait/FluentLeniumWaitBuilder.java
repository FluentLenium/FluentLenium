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
package org.fluentlenium.core.wait;

import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.filter.FilterType;
import org.fluentlenium.core.filter.matcher.*;

import java.util.regex.Pattern;


public class FluentLeniumWaitBuilder {

    FluentWaitBuilder fluentLeniumWait;
    String attribute;

    public FluentLeniumWaitBuilder(FluentWaitBuilder fluentWaitBuilder, FilterType filterType) {
        this.fluentLeniumWait = fluentWaitBuilder;
        this.attribute = filterType.name();

    }

    public FluentLeniumWaitBuilder(FluentWaitBuilder fluentWaitBuilder, String attribute) {
        this.fluentLeniumWait = fluentWaitBuilder;
        this.attribute = attribute;

    }


    public FluentWaitBuilder equalTo(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new EqualMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitBuilder contains(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new ContainsMatcher(equal)));
        return fluentLeniumWait;
    }


    public FluentWaitBuilder contains(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new ContainsMatcher(equal)));
        return fluentLeniumWait;
    }


    public FluentWaitBuilder startsWith(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new StartsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitBuilder startsWith(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new StartsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitBuilder endsWith(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new EndsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitBuilder endsWith(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new EndsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitBuilder notContains(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotContainsMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitBuilder notContains(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotContainsMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitBuilder notStartsWith(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotStartsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitBuilder notStartsWith(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotStartsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitBuilder notEndsWith(String equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotEndsWithMatcher(equal)));
        return fluentLeniumWait;
    }

    public FluentWaitBuilder notEndsWith(Pattern equal) {
        fluentLeniumWait.addFilter(new Filter(attribute, new NotEndsWithMatcher(equal)));
        return fluentLeniumWait;
    }


}
