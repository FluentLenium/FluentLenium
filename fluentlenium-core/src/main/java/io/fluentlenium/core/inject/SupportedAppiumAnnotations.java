package io.fluentlenium.core.inject;

import io.appium.java_client.pagefactory.*;

import java.lang.annotation.Annotation;

final class SupportedAppiumAnnotations {

    private SupportedAppiumAnnotations() {
    }

    static boolean isSupported(Annotation annotation) {
        return annotation instanceof iOSXCUITFindBy
                || annotation instanceof iOSXCUITFindBys
                || annotation instanceof iOSXCUITFindAll
                || annotation instanceof iOSXCUITFindByAllSet
                || annotation instanceof iOSXCUITFindByChainSet
                || annotation instanceof iOSXCUITFindBySet

                || annotation instanceof AndroidFindBy
                || annotation instanceof AndroidFindBys
                || annotation instanceof AndroidFindAll
                || annotation instanceof AndroidFindByAllSet
                || annotation instanceof AndroidFindByChainSet
                || annotation instanceof AndroidFindBySet

                || annotation instanceof WindowsFindBy
                || annotation instanceof WindowsFindBys
                || annotation instanceof WindowsFindAll
                || annotation instanceof WindowsFindByAllSet
                || annotation instanceof WindowsFindByChainSet
                || annotation instanceof WindowsFindBySet;
    }

}
