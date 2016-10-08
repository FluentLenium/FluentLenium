package org.fluentlenium.core.inject;

import org.fluentlenium.core.label.FluentLabelProvider;
import org.fluentlenium.core.page.ClassAnnotations;
import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;
import org.openqa.selenium.support.pagefactory.Annotations;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Inspired by {@link org.openqa.selenium.support.pagefactory.Annotations}, but also supports annotations defined on
 * return type class.
 */
public class InjectionAnnotations extends AbstractAnnotations implements FluentLabelProvider {
    private final ClassAnnotations classAnnotations;
    private final Annotations fieldAnnotations;
    private final LabelAnnotations labelFieldAnnotations;

    private static boolean isList(final Field field) {
        return List.class.isAssignableFrom(field.getType());
    }

    private static Class<?> getEffectiveClass(final Field field) {
        if (isList(field)) {
            final Class<?> effectiveClass = ReflectionUtils.getFirstGenericType(field);
            if (effectiveClass != null) {
                return effectiveClass;
            }
        }
        return field.getType();
    }

    /**
     * Creates a new injection annotations object
     *
     * @param field field to analyze
     */
    public InjectionAnnotations(final Field field) {
        classAnnotations = new ClassAnnotations(getEffectiveClass(field));
        fieldAnnotations = new Annotations(field);
        labelFieldAnnotations = new LabelAnnotations(field);
    }

    @Override
    public By buildBy() {
        final By fieldBy = fieldAnnotations.buildBy();
        final By classBy = classAnnotations.buildBy();
        if (classBy != null && fieldBy instanceof ByIdOrName) {
            return classBy;
        }
        return fieldBy;
    }

    @Override
    public boolean isLookupCached() {
        return classAnnotations.isLookupCached() || fieldAnnotations.isLookupCached();
    }

    @Override
    public String getLabel() {
        return labelFieldAnnotations.getLabel();
    }

    @Override
    public String[] getLabelHints() {
        return labelFieldAnnotations.getLabelHints();
    }
}
