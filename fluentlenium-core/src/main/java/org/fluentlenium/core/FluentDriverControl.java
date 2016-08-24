package org.fluentlenium.core;

import org.fluentlenium.configuration.Configuration;
import org.openqa.selenium.internal.WrapsDriver;

import java.util.concurrent.TimeUnit;

/**
 * Control of the Fluent WebDriver
 */
@Deprecated
public interface FluentDriverControl extends FluentControl, WrapsDriver {

}
