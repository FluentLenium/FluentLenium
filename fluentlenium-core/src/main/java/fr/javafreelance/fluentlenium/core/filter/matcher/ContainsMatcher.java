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

package fr.javafreelance.fluentlenium.core.filter.matcher;


import java.util.regex.Pattern;

//TODO Remove matching pattern from there
public class ContainsMatcher extends Matcher {

    public ContainsMatcher(String value) {
        super(value);
    }

    public ContainsMatcher(Pattern value) {
        super(value);
    }

    public MatcherType getMatcherType() {
        return MatcherType.CONTAINS;
    }

    public boolean isSatisfiedBy(String o) {
        return CalculateService.contains(getPattern(), getValue(), o);
    }


}
