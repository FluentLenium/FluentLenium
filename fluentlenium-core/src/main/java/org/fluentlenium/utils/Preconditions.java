package org.fluentlenium.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Utility methods for validating objects' status.
 */
public final class Preconditions {

    private Preconditions() {
        //Utility class
    }

    /**
     * Validates if the argument object is null, and throws an {@link IllegalArgumentException} if it is.
     *
     * @param object  the object to validate
     * @param message the error message to throw the exception with
     * @return the argument object if it is not null
     * @throws IllegalArgumentException if the argument object is null
     */
    public static <T> T checkArgument(T object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
        return object;
    }

    /**
     * Validates if the argument String is blank, and throws an {@link IllegalArgumentException} if it is.
     *
     * @param object  the object to validate
     * @param message the error message to throw the exception with
     * @return the argument String if it is not blank
     * @throws IllegalArgumentException if the argument String is blank
     */
    public static String checkArgumentBlank(String object, String message) {
        if (StringUtils.isBlank(object)) {
            throw new IllegalArgumentException(message);
        }
        return object;
    }

    /**
     * Validates if the argument object is null, and throws an {@link IllegalStateException} if it is.
     *
     * @param object  the object to validate
     * @param message the error message to throw the exception with
     * @return the argument object if it is not null
     * @throws IllegalStateException if the argument object is null
     */
    public static <T> T checkState(T object, String message) {
        if (object == null) {
            throw new IllegalStateException(message);
        }
        return object;
    }

}
