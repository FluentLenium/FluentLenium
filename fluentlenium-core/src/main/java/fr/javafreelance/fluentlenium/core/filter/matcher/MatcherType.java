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

/**
 * Different integration actually supported by the framework.
 * PreFilter are integration than are supported by WebDriver as CSS Selector
 * PostFilter are used after the webdriver selection to integration the collection
 */
public enum MatcherType {
    CONTAINS("*"), START_WITH("^"), END_WITH("$"), EQUAL(""), NOT_CONTAINS(null), NOT_START_WITH(null), NOT_END_WITH(null);

    private String cssRepresentations;

    MatcherType(String cssRepresentations) {
        this.cssRepresentations = cssRepresentations;
    }

    /**
     * Return the css representations of the matcher
     *
     * @return
     */
    public String getCssRepresentations() {
        return cssRepresentations;
    }
}