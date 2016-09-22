package org.fluentlenium.core.conditions;


import com.google.common.base.Predicate;
import org.fluentlenium.core.conditions.message.Message;
import org.fluentlenium.core.conditions.message.MessageContext;
import org.fluentlenium.core.domain.FluentWebElement;

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
    FluentListConditions not();

    /**
     * Check that the given predicate is verified.
     *
     * @param predicate    predicate to check
     * @param defaultValue default value if input is not present
     * @return true if the predicated is verified, false otherwise
     */
    @Message("should [not ]verify predicate {0}")
    boolean verify(Predicate<FluentWebElement> predicate, boolean defaultValue);

    /**
     * Check that this element is present.
     *
     * @return true if the element is present, false otherwise
     */
    @Message("should [not ]be present {0}")
    boolean present();

    /**
     * Check that this element list has the given size.
     *
     * @param size size of the list
     * @return true if it has the given size, false otherwise
     */
    @Message("should [not ]have size {0}")
    boolean hasSize(int size);

    /**
     * Check that this element list has the given size.
     *
     * @return an object to configure advanced conditions on size
     */
    @MessageContext("size")
    IntegerConditions hasSize();
}
