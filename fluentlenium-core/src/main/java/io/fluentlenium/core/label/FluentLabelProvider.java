package io.fluentlenium.core.label;

/**
 * Provides label and label hints definitions.
 */
public interface FluentLabelProvider {
    /**
     * Get defined definition.
     *
     * @return defined label
     */
    String getLabel();

    /**
     * Get defined label hints.
     *
     * @return array of label hints
     */
    String[] getLabelHints();
}
