package org.fluentlenium.core.inject;

import org.fluentlenium.core.annotation.Label;
import org.fluentlenium.core.annotation.LabelHint;

import java.lang.reflect.Field;

/**
 * Parse {@link Label} and {@link LabelHint} annotation from field.
 */
public class LabelAnnotations {
    private String label;
    private String[] labelHints;

    public LabelAnnotations(final Field field) {
        Label labelAnnotation = field.getAnnotation(Label.class);
        if (labelAnnotation != null) {
            this.label = labelAnnotation.value();
            if (this.label.isEmpty()) {
                this.label = field.getDeclaringClass().getSimpleName() + "." + field.getName();
            }
        }

        LabelHint labelHintAnnotation = field.getAnnotation(LabelHint.class);
        if (labelHintAnnotation != null) {
            this.labelHints = labelHintAnnotation.value();
        }
    }

    public String getLabel() {
        return label;
    }

    public String[] getLabelHints() {
        return labelHints;
    }
}
