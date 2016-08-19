package org.fluentlenium.configuration;

import org.atteo.classindex.IndexSubclasses;
import org.openqa.selenium.WebDriver;

import javax.inject.Named;

/**
 * Factory of {@link WebDriver}, that can be registered in {@link WebDrivers} registry.
 */
@IndexSubclasses
public interface WebDriverFactory {
    /**
     * Creates a new instance of {@link WebDriver}.
     *
     * @return
     */
    WebDriver newWebDriver();

    /**
     * Primary name of this factory.
     *
     * To register it with alternative name, use {@link AlternativeNames}.
     *
     * @return Primary name.
     */
    String getName();
}
