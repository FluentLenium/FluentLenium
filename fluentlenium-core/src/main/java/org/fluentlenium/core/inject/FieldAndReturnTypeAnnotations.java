package org.fluentlenium.core.inject;

import org.fluentlenium.core.page.ClassAnnotations;
import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;
import org.openqa.selenium.support.pagefactory.Annotations;

import java.lang.reflect.Field;
import java.util.List;

public class FieldAndReturnTypeAnnotations extends AbstractAnnotations {
    private final ClassAnnotations classAnnotations;
    private final Annotations fieldAnnotations;

    private static boolean isList(Field field) {
        return List.class.isAssignableFrom(field.getType());
    }

    private static Class<?> getEffectiveClass(Field field) {
        if (isList(field)) {
            Class<?> effectiveClass = ReflectionUtils.getFirstGenericType(field);
            if (effectiveClass != null) return effectiveClass;
        }
        return field.getType();
    }

    public FieldAndReturnTypeAnnotations(Field field) {
        classAnnotations = new ClassAnnotations(getEffectiveClass(field));
        fieldAnnotations = new Annotations(field);

    }

    @Override
    public By buildBy() {
        By fieldBy = fieldAnnotations.buildBy();
        By classBy = classAnnotations.buildBy();
        if (classBy != null && fieldBy instanceof ByIdOrName) return classBy;
        return fieldBy;
    }

    @Override
    public boolean isLookupCached() {
        return classAnnotations.isLookupCached() || fieldAnnotations.isLookupCached();
    }
}
