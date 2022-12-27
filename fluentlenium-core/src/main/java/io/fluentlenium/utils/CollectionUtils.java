package io.fluentlenium.utils;

import java.lang.reflect.Field;
import java.util.Collection;
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

    /**
     * Returns whether a collection is null or empty.
     *
     * @param collection the collection to check
     * @return true if the collection is either null or empty, false otherwise
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
