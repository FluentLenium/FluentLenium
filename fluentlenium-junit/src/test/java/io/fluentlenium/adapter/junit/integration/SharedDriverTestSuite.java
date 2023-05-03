package io.fluentlenium.adapter.junit.integration;

import io.fluentlenium.adapter.junit.integration.shareddriver.SharedDriver;
import io.fluentlenium.adapter.junit.integration.shareddriver.SharedDriverOnce1;
import io.fluentlenium.adapter.junit.integration.shareddriver.SharedDriverOnce2;
import io.fluentlenium.adapter.junit.integration.shareddriver.SharedDriverPerClass1;
import io.fluentlenium.adapter.junit.integration.shareddriver.SharedDriverPerClass2;
import io.fluentlenium.adapter.junit.integration.shareddriver.SharedDriverPerMethodByAnnotation;
import io.fluentlenium.adapter.junit.integration.shareddriver.SharedDriverPerMethodByDefault;
import io.fluentlenium.adapter.junit.integration.shareddriver.SharedDriverSuperClassTest;
import net.jcip.annotations.NotThreadSafe;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(value = {SharedDriver.class, SharedDriverOnce1.class, SharedDriverOnce2.class,
        SharedDriverPerClass1.class, SharedDriverPerClass2.class, SharedDriverPerMethodByAnnotation.class,
        SharedDriverPerMethodByDefault.class, SharedDriverSuperClassTest.class})
@NotThreadSafe
public class SharedDriverTestSuite {
}
