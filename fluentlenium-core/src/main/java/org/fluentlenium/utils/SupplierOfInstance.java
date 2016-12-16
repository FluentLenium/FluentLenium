package org.fluentlenium.utils;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Equivalent to {@link java.util.function.Suppliers#ofInstance(Object)}, but with toString() implementation calling
 * toString() on the instance without wrapping it.
 *
 * @param <T> type of instance
 */
public class SupplierOfInstance<T> implements Supplier<T> {

    private final T instance;

    /**
     * Creates a new instance supplier
     *
     * @param instance instance ot wrap
     */
    public SupplierOfInstance(T instance) {
        this.instance = instance;
    }

    @Override
    public T get() {
        return instance;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SupplierOfInstance) {
            SupplierOfInstance that = (SupplierOfInstance) obj;
            return Objects.equals(instance, that.instance);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(instance);
    }

    @Override
    public String toString() {
        return String.valueOf(get());
    }
}
