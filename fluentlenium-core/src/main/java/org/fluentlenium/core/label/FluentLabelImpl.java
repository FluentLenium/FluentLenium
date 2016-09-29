package org.fluentlenium.core.label;

import com.google.common.base.Supplier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FluentLabelImpl<T> implements FluentLabel<T>, FluentLabelProvider {

    private final T reference;
    private final Supplier<String> defaultLabelSupplier;

    private String label;
    private List<String> labelHints = new ArrayList<>();

    public FluentLabelImpl(T reference, Supplier<String> defaultLabelSupplier) {
        this.reference = reference;
        this.defaultLabelSupplier = defaultLabelSupplier;
    }

    @Override
    public T withLabel(String label) {
        this.label = label;
        return reference;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public T withLabelHint(String... labelHint) {
        if (labelHint != null) {
            this.labelHints.addAll(Arrays.asList(labelHint));
        }
        return reference;
    }

    public String[] getLabelHints() {
        return labelHints.toArray(new String[labelHints.size()]);
    }

    @Override
    public String toString() {
        StringBuilder toStringBuilder = new StringBuilder();
        if (this.label != null) {
            toStringBuilder.append(this.label);
        } else {
            toStringBuilder.append(defaultLabelSupplier.get());
        }

        if (labelHints.size() > 0) {
            toStringBuilder.append(" [");
            boolean notFirst = false;
            for (String labelHint : labelHints) {
                if (notFirst) {
                    toStringBuilder.append(", ");
                }
                toStringBuilder.append(labelHint);
                notFirst = true;
            }
            toStringBuilder.append("]");
        }

        return toStringBuilder.toString();
    }
}
