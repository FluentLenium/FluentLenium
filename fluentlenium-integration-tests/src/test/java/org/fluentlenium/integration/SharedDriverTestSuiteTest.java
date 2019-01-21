package org.fluentlenium.integration;

import net.jcip.annotations.NotThreadSafe;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages("org.fluentlenium.integration.shareddriver.ignore")
@NotThreadSafe
public class SharedDriverTestSuiteTest {
}
