package org.fluentlenium.core.inject;

import org.fluentlenium.core.annotation.Label;
import org.fluentlenium.core.annotation.LabelHint;
import org.fluentlenium.core.label.FluentLabelProvider;

import java.lang.reflect.Field;

/**
 * Parse {@link Label} and {@link LabelHint} annotation from field.
 */
public class LabelAnnotations implements FluentLabelProvider {
    private String label;
    private String[] labelHints;

    /**
     * Creates a  new label annotations object.
     *
     * @param field field to parse
     */
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

    @Override
    public String getLabel() {
        return label;
    }

    @SuppressWarnings("PMD.MethodReturnsInternalArray")
    @Override
    public String[] getLabelHints() {
        return labelHints;
    }
}
