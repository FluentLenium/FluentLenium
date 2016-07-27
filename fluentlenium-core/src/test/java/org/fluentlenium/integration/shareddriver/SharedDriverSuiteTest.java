package org.fluentlenium.integration.shareddriver;

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
