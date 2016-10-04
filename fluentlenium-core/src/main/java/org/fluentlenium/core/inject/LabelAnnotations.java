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
        final Label labelAnno = field.getAnnotation(Label.class);
        if (labelAnno != null) {
            this.label = labelAnno.value();
            if (this.label.isEmpty()) {
                this.label = field.getDeclaringClass().getSimpleName() + "." + field.getName();
            }
        }

        final LabelHint labelHint = field.getAnnotation(LabelHint.class);
        if (labelHint != null) {
            this.labelHints = labelHint.value();
        }
    }

    public String getLabel() {
        return label;
    }

    @SuppressWarnings("PMD.MethodReturnsInternalArray")
    public String[] getLabelHints() {
        return labelHints;
    }
}
