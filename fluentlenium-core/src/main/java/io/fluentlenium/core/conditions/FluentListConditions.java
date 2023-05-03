package io.fluentlenium.core.conditions;

import io.fluentlenium.core.conditions.message.Message;
import io.fluentlenium.core.conditions.message.MessageContext;
import io.fluentlenium.core.conditions.message.NotMessage;
import io.fluentlenium.core.domain.FluentWebElement;

import java.util.function.Predicate;

/**
 * Conditions API for list of elements.
 */
public interface FluentListConditions extends FluentConditions {
    /**
     * Negates this condition object.
     *
     * @return a new negated condition object
     */
    @Override
    @Negation
    FluentListConditions not();

    /**
     * Check that the given predicate is verified.
     *
     * @param predicate    predicate to check
     * @param defaultValue default value if input is not present
     * @return true if the predicate is verified, false otherwise
     */
    @Message("verifies predicate {0}")
    @NotMessage("does not verify predicate {0}")
    boolean verify(Predicate<FluentWebElement> predicate, boolean defaultValue);

    /**
     * Check that the currently validated element of this list is present.
     *
     * @return true if the element is present, false otherwise
     */
    @Message("is present")
    @NotMessage("is not present")
    boolean present();

    /**
     * Check that this element list has the given size.
     *
     * @param size size of the list
     * @return true if it has the given size, false otherwise
     */
    @Message("has size == {0}")
    @NotMessage("has size != {0}")
    boolean size(int size);

    /**
     * Check that this element list has the given size.
     *
     * @return an object to configure advanced conditions on size
     */
    @MessageContext("size")
    AbstractIntegerConditions size();
}
