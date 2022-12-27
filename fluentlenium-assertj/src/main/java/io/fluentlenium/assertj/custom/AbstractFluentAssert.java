package io.fluentlenium.assertj.custom;

import org.assertj.core.api.AbstractAssert;

import java.util.Arrays;
import java.util.List;

import static io.fluentlenium.assertj.custom.HtmlConstants.CLASS_DELIMITER;

/**
 * Abstract base class for FluentLenium assertion implementations.
 *
 * @param <SELF>   the "self" type of this assertion class
 * @param <ACTUAL> an actual implementation of this interface
 */
abstract class AbstractFluentAssert<SELF extends AbstractAssert<SELF, ACTUAL>, ACTUAL> extends AbstractAssert<SELF, ACTUAL>
        implements FluentAssert {

    AbstractFluentAssert(ACTUAL actual, Class<?> selfType) {
        super(actual, selfType);
    }

    /**
     * Returns the class list from the argument class attribute value.
     *
     * @param classString the class attribute value
     * @return the list of classes
     */
    List<String> getClasses(String classString) {
        String[] primitiveList = classString.split(CLASS_DELIMITER);
        return Arrays.asList(primitiveList);
    }
}
