package org.fluentlenium.core.domain;

import org.fluentlenium.core.conditions.FluentConditions;
import org.fluentlenium.core.conditions.message.MessageProxy;
import org.openqa.selenium.NoSuchElementException;

/**
 * Utility class for elements.
 */
public final class ElementUtils {
    private ElementUtils() {
        // Utility class
    }

    /**
     * Builds a {@link NoSuchElementException}.
     *
     * @param messageContext message context.
     * @return no such element exception
     */
    public static NoSuchElementException noSuchElementException(final String messageContext) {
        final FluentConditions messageBuilder = MessageProxy.builder(FluentConditions.class, messageContext);
        messageBuilder.present();
        final String message = MessageProxy.message(messageBuilder);
        return new NoSuchElementException(message);
    }
}
