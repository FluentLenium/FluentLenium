package org.fluentlenium.configuration;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.json.Json;
import org.openqa.selenium.json.JsonException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Optional;

/**
 * Provides {@link Capabilities} object based on the value and the kind of the value of the capabilities configuration
 * property.
 */
class CapabilitiesConfigurationPropertyRetriever {

    private final Json jsonConverter = new Json();

    /**
     * Returns a {@link Capabilities} object based on the argument property value.
     * <p>
     * It goes through the following fallback logic:
     * <ul>
     * <li>first it tries to retrieve the capabilities handling the argument property as a URL,</li>
     * <li>then if there's a {@link CapabilitiesFactory} registered for that capabilities value, it will create
     * and return the Capabilities object from that factory, optionally using the {@code globalConfiguration}</li>
     * <li>finally, if factory was found, it will try to handle the argument as a JSON string and convert ot
     * to a {@link DesiredCapabilities} object.</li>
     * </ul>
     *
     * @param capabilities        capabilities property value
     * @param globalConfiguration global configuration properties
     * @return a Capabilities object
     */
    Capabilities getCapabilitiesProperty(String capabilities, ConfigurationProperties globalConfiguration) {
        String property = readCapabilitiesFromUrl(capabilities);
        return Optional.ofNullable(createCapabilitiesFromFactory(property, globalConfiguration))
                .orElseGet(() -> convertJsonObjectToCapabilities(property));
    }

    private String readCapabilitiesFromUrl(String property) {
        try {
            URL url = newURL(property);
            try {
                property = IOUtils.toString(url, Charset.defaultCharset());
            } catch (IOException e) {
                throw new ConfigurationException("Can't read Capabilities defined at " + url, e);
            }
        } catch (MalformedURLException e) { // NOPMD EmptyCatchBlock PreserveStackTrace
            // This is not an URL. Consider property as JSON.
        }
        return property;
    }

    private URL newURL(String url) throws MalformedURLException {
        return new URL(url);
    }

    private Capabilities createCapabilitiesFromFactory(String property, ConfigurationProperties globalConfiguration) {
        CapabilitiesFactory factory = CapabilitiesRegistry.INSTANCE.get(property);
        if (factory != null) {
            return factory.newCapabilities(globalConfiguration);
        }
        return null;
    }

    private Capabilities convertJsonObjectToCapabilities(String property) {
        try {
            return jsonConverter.toType(property, DesiredCapabilities.class);
        } catch (JsonException e) {
            throw new ConfigurationException("Can't convert JSON Capabilities to Object.", e);
        }
    }
}
