package org.fluentlenium.integration;

import org.fluentlenium.integration.ignore.SharedDriverDeleteCookies;
import org.fluentlenium.integration.ignore.SharedDriverOnce1;
import org.fluentlenium.integration.ignore.SharedDriverOnce2;
import org.fluentlenium.integration.ignore.SharedDriverPerClass1;
import org.fluentlenium.integration.ignore.SharedDriverPerClass2;
import org.fluentlenium.integration.ignore.SharedDriverPerMethodByAnnotation;
import org.fluentlenium.integration.ignore.SharedDriverPerMethodByDefault;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(value = {
        SharedDriverDeleteCookies.class,
        SharedDriverOnce1.class,
        SharedDriverOnce2.class,
        SharedDriverPerClass1.class,
        SharedDriverPerClass2.class,
        SharedDriverPerMethodByAnnotation.class,
        SharedDriverPerMethodByDefault.class
})
public class SharedDriverSuiteTest {
}
