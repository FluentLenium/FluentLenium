package org.fluentlenium.core.inject;

import static java.util.Optional.ofNullable;
import static org.fluentlenium.utils.CollectionUtils.isList;

import io.appium.java_client.pagefactory.DefaultElementByBuilder;
import io.appium.java_client.pagefactory.iOSXCUITFindAll;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindByAllSet;
import io.appium.java_client.pagefactory.iOSXCUITFindByChainSet;
import io.appium.java_client.pagefactory.iOSXCUITFindBySet;
import io.appium.java_client.pagefactory.iOSXCUITFindBys;
import org.fluentlenium.core.label.FluentLabelProvider;
import org.fluentlenium.core.page.ClassAnnotations;
import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;
import org.openqa.selenium.support.pagefactory.Annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Inspired by {@link org.openqa.selenium.support.pagefactory.Annotations}, but also supports annotations defined on
 * return type class.
 */
public class InjectionAnnotations extends AbstractAnnotations implements FluentLabelProvider {
    private final ClassAnnotations classAnnotations;
    private final Annotations fieldAnnotations;
    private final LabelAnnotations labelFieldAnnotations;
    private final DefaultElementByBuilder defaultElementByBuilder;
    private final boolean mobileElement;

    private static Class<?> getEffectiveClass(Field field) {
        if (isList(field)) {
            Class<?> effectiveClass = ReflectionUtils.getFirstGenericType(field);
            if (effectiveClass != null) {
                return effectiveClass;
            }
        }
        return field.getType();
    }

    /**
     * Creates a new injection annotations object
     *
     * @param field        field to analyze
     * @param capabilities
     */
    public InjectionAnnotations(Field field, Capabilities capabilities) {
        classAnnotations = new ClassAnnotations(getEffectiveClass(field));
        fieldAnnotations = new Annotations(field);
        labelFieldAnnotations = new LabelAnnotations(field);
        String platform = getPlatform(capabilities);
        String automation = getAutomation(capabilities);
        defaultElementByBuilder = new DefaultElementByBuilder(platform, automation);
        if (isAnnotatedWithSupportedMobileBy(field)) {
            defaultElementByBuilder.setAnnotated(field);
            mobileElement = true;
        } else {
            mobileElement = false;
        }
    }

    private String getAutomation(Capabilities capabilities) {
        if (capabilities == null) {
            return null;
        }

        return ofNullable(capabilities.getCapability("automationName"))
                .map(String::valueOf).orElse(null);
    }

    private String getPlatform(Capabilities capabilities) {
        if (capabilities == null) {
            return null;
        }

        Object platformName = ofNullable(capabilities.getCapability("platformName"))
                .orElseGet(() -> capabilities.getCapability("platform"));
        return ofNullable(platformName).map(String::valueOf).orElse(null);
    }

    private boolean isAnnotatedWithSupportedMobileBy(Field field) {
        Annotation[] annotations = field.getAnnotations();
        return Arrays.stream(annotations)
                .anyMatch(this::isSupported);
    }

    private boolean isSupported(Annotation annotation) {
        return annotation instanceof iOSXCUITFindBy ||
                annotation instanceof iOSXCUITFindBys ||
                annotation instanceof iOSXCUITFindAll ||
                annotation instanceof iOSXCUITFindByAllSet ||
                annotation instanceof iOSXCUITFindByChainSet ||
                annotation instanceof iOSXCUITFindBySet;
    }

    @Override
    public By buildBy() {
        if (mobileElement) {
            return defaultElementByBuilder.buildBy();
        }
        By fieldBy = fieldAnnotations.buildBy();
        By classBy = classAnnotations.buildBy();

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
