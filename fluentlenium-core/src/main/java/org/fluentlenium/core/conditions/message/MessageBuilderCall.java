package org.fluentlenium.core.conditions.message;

import lombok.Getter;
import lombok.Setter;

/**
 * Represent a call on the {@link MessageProxy} builder.
 */
@Getter
@Setter
public class MessageBuilderCall {
    private boolean negation;
    private String context;
    private String message;
    private String notMessage;
    private Object[] args;
}
