package io.fluentlenium.core.inject;

import static io.appium.java_client.remote.AutomationName.IOS_XCUI_TEST;
import static io.appium.java_client.remote.MobilePlatform.ANDROID;
import static io.appium.java_client.remote.MobilePlatform.IOS;
import static io.appium.java_client.remote.MobilePlatform.WINDOWS;
import static java.util.Optional.ofNullable;
import static io.fluentlenium.utils.CollectionUtils.isList;

import io.appium.java_client.pagefactory.DefaultElementByBuilder;
import io.fluentlenium.utils.CollectionUtils;import io.fluentlenium.utils.ReflectionUtils;import io.fluentlenium.configuration.ConfigurationException;
import io.fluentlenium.core.label.FluentLabelProvider;
import io.fluentlenium.core.page.ClassAnnotations;
import io.fluentlenium.utils.ReflectionUtils;
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

    /**
     * Creates a new injection annotations object
     *
     * @param field        field to analyze
     * @param capabilities Selenium capabilities
     */
    public InjectionAnnotations(Field field, Capabilities capabilities) {
        classAnnotations = new ClassAnnotations(getEffectiveClass(field));
        fieldAnnotations = new Annotations(field);
        labelFieldAnnotations = new LabelAnnotations(field);
        String platform = getPlatform(capabilities);
        String automation = getAutomation(capabilities);
        defaultElementByBuilder = new DefaultElementByBuilder(platform, automation);
        if (isAnnotatedWithSupportedMobileBy(field)) {
            checkCapabilities(platform, automation);
            defaultElementByBuilder.setAnnotated(field);
            mobileElement = true;
        } else {
            mobileElement = false;
        }
    }

    private void checkCapabilities(String platform, String automation) {
        boolean correctConfiguration = isAndroid(platform) || isIos(platform, automation) || isWindows(platform);
        if (!correctConfiguration) {
            throw new ConfigurationException("You have annotated elements with Appium @FindBys"
                    + " but capabilities are incomplete. Please use one of these configurations:\n"
                    + "platformName:Windows\n"
                    + "plaformName:Android\n"
                    + "plaformName:iOS, automationName:XCUITest");
        }
    }

    private boolean isWindows(String platform) {
        return WINDOWS.equalsIgnoreCase(platform);
    }

    private boolean isIos(String platform, String automation) {
        return IOS.equalsIgnoreCase(platform) && IOS_XCUI_TEST.equalsIgnoreCase(automation);
    }

    private boolean isAndroid(String platform) {
        return ANDROID.equalsIgnoreCase(platform);
    }

    private String getAutomation(Capabilities capabilities) {
        return ofNullable(capabilities)
                .map(capability -> capability.getCapability("automationName"))
                .map(String::valueOf)
                .orElse(null);
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
                .anyMatch(SupportedAppiumAnnotations::isSupported);
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

    private static Class<?> getEffectiveClass(Field field) {
        if (CollectionUtils.isList(field)) {
            Class<?> effectiveClass = ReflectionUtils.getFirstGenericType(field);
            if (effectiveClass != null) {
                return effectiveClass;
            }
        }
        return field.getType();
    }
}
