package io.fluentlenium.adapter.junit.integration;

import io.fluentlenium.adapter.junit.integration.shareddriver.*;
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
