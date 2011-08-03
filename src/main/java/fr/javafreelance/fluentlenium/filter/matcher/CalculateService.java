package fr.javafreelance.fluentlenium.filter.matcher;


import java.util.regex.Pattern;

public final class CalculateService {
    private CalculateService() {
    }

    public static boolean contains(Pattern patternValue, String referenceValue, String currentValue) {
        if (currentValue == null) {
            return false;
        }
        if (patternValue == null) {
            return currentValue.contains(referenceValue);
        }
        return patternValue.matcher(currentValue).find();
    }

    public static boolean equal(Pattern patternValue, String referenceValue, String currentValue) {
        if (currentValue == null) {
            return false;
        }
        if (patternValue == null) {
            return currentValue.equals(referenceValue);
        }
        return patternValue.matcher(currentValue).matches();
    }

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
