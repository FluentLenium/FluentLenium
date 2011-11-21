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

/**
 * Static class that are in charge of analyzed the filter and matcher.
 */
public final class CalculateService {
    private CalculateService() {
    }

    /**
     * check if the current value contains the patternValue or the referenceValue
     *
     * @param patternValue
     * @param referenceValue
     * @param currentValue
     * @return
     */
    public static boolean contains(Pattern patternValue, String referenceValue, String currentValue) {
        if (currentValue == null) {
            return false;
        }
        if (patternValue == null) {
            return currentValue.contains(referenceValue);
        }
        return patternValue.matcher(currentValue).find();
    }

    /**
     * check if the current value is equal the patternValue or the referenceValue
     *
     * @param patternValue
     * @param referenceValue
     * @param currentValue
     * @return
     */
    public static boolean equal(Pattern patternValue, String referenceValue, String currentValue) {
        if (currentValue == null) {
            return false;
        }
        if (patternValue == null) {
            return currentValue.equals(referenceValue);
        }
        return patternValue.matcher(currentValue).matches();
    }

    /**
     * check if the current value starts with the patternValue or the referenceValue
     *
     * @param patternValue
     * @param referenceValue
     * @param currentValue
     * @return
     */

    public static boolean startsWith(Pattern patternValue, String referenceValue, String currentValue) {
        if (currentValue == null) {
            return false;
        }
        if (patternValue == null) {
            return currentValue.startsWith(referenceValue);
        }
        java.util.regex.Matcher m2 = patternValue.matcher(currentValue);
        return m2.find() && 0 == m2.start();
    }

    /**
     * check if the current value ends with the patternValue or the referenceValue
     *
     * @param patternValue
     * @param referenceValue
     * @param currentValue
     * @return
     */

    public static boolean endsWith(Pattern patternValue, String referenceValue, String currentValue) {
        if (currentValue == null) {
            return false;
        }
        if (patternValue == null) {
            return currentValue.endsWith(referenceValue);
        }
        java.util.regex.Matcher m2 = patternValue.matcher(currentValue);
        int end = 0;
        while (m2.find()) {
            end = m2.end();
        }
        return currentValue.length() == end;
    }

}
