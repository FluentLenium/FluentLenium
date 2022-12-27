package io.fluentlenium.core.domain;

import io.fluentlenium.core.conditions.FluentConditions;
import io.fluentlenium.core.conditions.message.MessageProxy;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;

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
    public static NoSuchElementException noSuchElementException(String messageContext) {
        FluentConditions messageBuilder = MessageProxy.builder(FluentConditions.class, messageContext);
        messageBuilder.present();
        String message = MessageProxy.message(messageBuilder);
        return new NoSuchElementException(message);
    }

    /**
     * Returns the wrapped {@link WebElement} from the argument element.
     *
     * @param element the element to get the wrapped element of
     * @return the wrapped webelement
     */
    public static WebElement getWrappedElement(WebElement element) {
        return ((WrapsElement) element).getWrappedElement();
    }
}
