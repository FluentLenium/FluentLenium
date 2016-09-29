package org.fluentlenium.core.label;

public interface FluentLabel<T> {
    /**
     * Apply a label that will be displayed as the representation of this object for error message.
     *
     * @param label label to use
     * @return reference to this object to chain calls
     */
    T withLabel(String label);

    /**
     * Add a label hint that will be appended to the representation of this object for error message.
     *
     * @param labelHint label hints to add
     * @return reference to this object to chain calls
     */
    T withLabelHint(String... labelHint);
}
