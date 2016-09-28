package org.fluentlenium.core.label;

/**
 * Provide label and label hints definitions.
 */
public interface FluentLabelProvider {
    String getLabel();

    String[] getLabelHints();
}
