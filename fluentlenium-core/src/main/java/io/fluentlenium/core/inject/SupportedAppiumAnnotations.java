package io.fluentlenium.core.inject;

import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindByAllSet;
import io.appium.java_client.pagefactory.AndroidFindByChainSet;
import io.appium.java_client.pagefactory.AndroidFindBySet;
import io.appium.java_client.pagefactory.AndroidFindBys;
import io.appium.java_client.pagefactory.WindowsFindAll;
import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.pagefactory.WindowsFindByAllSet;
import io.appium.java_client.pagefactory.WindowsFindByChainSet;
import io.appium.java_client.pagefactory.WindowsFindBySet;
import io.appium.java_client.pagefactory.WindowsFindBys;
import io.appium.java_client.pagefactory.iOSXCUITFindAll;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindByAllSet;
import io.appium.java_client.pagefactory.iOSXCUITFindByChainSet;
import io.appium.java_client.pagefactory.iOSXCUITFindBySet;
import io.appium.java_client.pagefactory.iOSXCUITFindBys;

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
