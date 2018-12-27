package org.fluentlenium.configuration;

import org.openqa.selenium.Capabilities;

/**
 * A no-op {@link BaseConfiguration} for testing purposes that also implements {@link ConfigurationMutator}.
 */
final class DummyBaseConfiguration extends BaseConfiguration implements ConfigurationMutator {

    @Override
    public void setWebDriver(String webDriver) {
    }

    @Override
    public void setBrowserTimeout(Long timeout) {
    }

    @Override
    public void setBrowserTimeoutRetries(Integer retriesNumber) {
    }

    @Override
    public void setRemoteUrl(String remoteUrl) {
    }

    @Override
    public void setCapabilities(Capabilities capabilities) {
    }

    @Override
    public void setConfigurationFactory(Class<? extends ConfigurationFactory> configurationFactory) {
    }

    @Override
    public void setDriverLifecycle(ConfigurationProperties.DriverLifecycle driverLifecycle) {
    }

    @Override
    public void setDeleteCookies(Boolean deleteCookies) {
    }

    @Override
    public void setBaseUrl(String baseUrl) {
    }

    @Override
    public void setPageLoadTimeout(Long pageLoadTimeout) {
    }

    @Override
    public void setImplicitlyWait(Long implicitlyWait) {
    }

    @Override
    public void setAwaitAtMost(Long awaitAtMost) {
    }

    @Override
    public void setAwaitPollingEvery(Long awaitPollingEvery) {
    }

    @Override
    public void setScriptTimeout(Long scriptTimeout) {
    }

    @Override
    public void setEventsEnabled(Boolean eventsEnabled) {
    }

    @Override
    public void setScreenshotPath(String screenshotPath) {
    }

    @Override
    public void setScreenshotMode(ConfigurationProperties.TriggerMode screenshotMode) {
    }

    @Override
    public void setHtmlDumpPath(String htmlDumpPath) {
    }

    @Override
    public void setHtmlDumpMode(ConfigurationProperties.TriggerMode htmlDumpMode) {
    }

    @Override
    public void setCustomProperty(String key, String value) {
    }
}
