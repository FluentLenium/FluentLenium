package org.fluentlenium.utils;

import com.google.common.base.Objects;
import com.google.common.base.Supplier;

/**
 * Equivalent to {@link com.google.common.base.Suppliers#ofInstance(Object)}, but with toString() implementation calling
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
            return Objects.equal(instance, that.instance);
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
