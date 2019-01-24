package org.fluentlenium.integration;

import net.jcip.annotations.NotThreadSafe;
import org.fluentlenium.integration.shareddriver.SharedDriverDeleteCookies;
import org.fluentlenium.integration.shareddriver.SharedDriverOnce1;
import org.fluentlenium.integration.shareddriver.SharedDriverOnce2;
import org.fluentlenium.integration.shareddriver.SharedDriverPerClass1;
import org.fluentlenium.integration.shareddriver.SharedDriverPerClass2;
import org.fluentlenium.integration.shareddriver.SharedDriverPerMethodByAnnotation;
import org.fluentlenium.integration.shareddriver.SharedDriverPerMethodByDefault;
import org.fluentlenium.integration.shareddriver.SharedDriverSuperClassTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(value = {SharedDriverDeleteCookies.class, SharedDriverOnce1.class, SharedDriverOnce2.class,
        SharedDriverPerClass1.class, SharedDriverPerClass2.class, SharedDriverPerMethodByAnnotation.class,
        SharedDriverPerMethodByDefault.class, SharedDriverSuperClassTest.class})
@NotThreadSafe
public class SharedDriverTestSuite {
}
