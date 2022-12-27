package io.fluentlenium.core.inject;

import io.fluentlenium.core.annotation.Label;
import io.fluentlenium.core.annotation.LabelHint;
import io.fluentlenium.core.annotation.Label;
import io.fluentlenium.core.annotation.LabelHint;
import io.fluentlenium.core.label.FluentLabelProvider;

import java.lang.reflect.Field;

/**
 * Parses and stores the values of the {@link Label} and {@link LabelHint} annotations of a given field.
 */
public class LabelAnnotations implements FluentLabelProvider {
    private String label;
    private String[] labelHints;

    /**
     * Creates a new label annotations object.
     * <p>
     * If the {@code @Label} annotation is present than it either uses that value as the label,
     * of if it's the default empty string value, then sets the label as the concatenation of the field's declaring
     * class and the field's name, for example for:
     * <pre>
     * public class Homepage {
     *
     *      &#064;FindBy(css = ".teaser img")
     *      &#064;Label
     *      private FluentWebElement teaserImage;
     * }
     * </pre>
     * <p>
     * the label is set to {@code Homepage.teaserImage}.
     * <p>
     * If the {@code @LabelHint} annotation is present then it simply sets its value in this object.
     *
     * @param field field to parse
     */
    public LabelAnnotations(Field field) {
        if (field.isAnnotationPresent(Label.class)) {
            label = field.getAnnotation(Label.class).value();
            if (label.isEmpty()) {
                label = field.getDeclaringClass().getSimpleName() + "." + field.getName();
            }
        }

        if (field.isAnnotationPresent(LabelHint.class)) {
            labelHints = field.getAnnotation(LabelHint.class).value();
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
