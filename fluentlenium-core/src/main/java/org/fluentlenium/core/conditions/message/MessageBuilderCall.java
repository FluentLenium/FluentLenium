package org.fluentlenium.core.conditions.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageBuilderCall {
    private boolean negation;
    private String context;
    private String message;
    private String notMessage;
    private Object[] args;
}
