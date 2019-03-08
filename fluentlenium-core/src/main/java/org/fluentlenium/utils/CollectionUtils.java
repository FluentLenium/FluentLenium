package org.fluentlenium.utils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Utility methods for working with collections.
 */
public final class CollectionUtils {

    private CollectionUtils() {
        //Util class
    }

    /**
     * Checks whether the argument field is a {@link List}.
     *
     * @param field the field to check the type of
     * @return true if the field is a List, false otherwise
     */
    public static boolean isList(Field field) {
        return List.class.isAssignableFrom(field.getType());
    }
}
