package org.fluentlenium.core.proxy;

import org.fluentlenium.core.conditions.FluentConditions;
import org.fluentlenium.core.conditions.message.MessageProxy;
import org.openqa.selenium.NoSuchElementException;

/**
 * Utility class for element locators.
 */
public final class ElementLocatorUtils {
    private ElementLocatorUtils() {
        // Utility class
    }

    /**
     * Builds a {@link NoSuchElementException}.
     *
     * @param messageContext message context
     * @return no such element exception
     */
    public static NoSuchElementException noSuchElementException(final String messageContext) {
        final FluentConditions messageBuilder = MessageProxy.builder(FluentConditions.class, messageContext);
        messageBuilder.present();
        final String message = MessageProxy.message(messageBuilder);
        return new NoSuchElementException(message);
    }

    /**
     * Builds a {@link NoSuchElementException}.
     *
     * @param messageContext message context
     * @param cause          exception cause
     * @return no such element exception
     */
    public static NoSuchElementException noSuchElementException(final String messageContext, final Throwable cause) {
        final FluentConditions messageBuilder = MessageProxy.builder(FluentConditions.class, messageContext);
        messageBuilder.present();
        final String message = MessageProxy.message(messageBuilder);
        return new NoSuchElementException(message, cause);
    }
}
