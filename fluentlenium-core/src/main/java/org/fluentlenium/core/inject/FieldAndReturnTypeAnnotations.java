package org.fluentlenium.core.inject;

import org.fluentlenium.core.page.ClassAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;
import org.openqa.selenium.support.pagefactory.Annotations;

import java.lang.reflect.Field;

public class FieldAndReturnTypeAnnotations extends AbstractAnnotations {
    private final ClassAnnotations classAnnotations;
    private final Annotations fieldAnnotations;

    public FieldAndReturnTypeAnnotations(Field field) {
        classAnnotations = new ClassAnnotations(field.getType());
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
