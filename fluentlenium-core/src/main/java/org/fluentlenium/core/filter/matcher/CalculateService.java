package org.fluentlenium.core.filter.matcher;


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
     * @param patternValue   pattern
     * @param referenceValue reference value
     * @param currentValue   current value
     * @return boolean value for contains check
     */
    public static boolean contains(final Pattern patternValue, final String referenceValue, final String currentValue) {
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
     * @param patternValue   pattern
     * @param referenceValue reference value
     * @param currentValue   current value
     * @return boolean value for equal check
     */
    public static boolean equal(final Pattern patternValue, final String referenceValue, final String currentValue) {
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
     * @param patternValue   pattern
     * @param referenceValue reference value
     * @param currentValue   current value
     * @return boolean value for startsWith check
     */

    public static boolean startsWith(final Pattern patternValue, final String referenceValue, final String currentValue) {
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
     * @param patternValue   pattern
     * @param referenceValue reference value
     * @param currentValue   current value
     * @return boolean value for endsWith check
     */

    public static boolean endsWith(final Pattern patternValue, final String referenceValue, final String currentValue) {
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
