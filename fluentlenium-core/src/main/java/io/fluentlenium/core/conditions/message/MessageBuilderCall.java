package io.fluentlenium.core.conditions.message;

/**
 * Represent a call on the {@link MessageProxy} builder.
 */
public class MessageBuilderCall {
    private boolean negation;
    private String context;
    private String message;
    private String notMessage;
    private Object[] args;

    public boolean isNegation() {
        return negation;
    }

    public String getContext() {
        return context;
    }

    public String getMessage() {
        return message;
    }

    public String getNotMessage() {
        return notMessage;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setNegation(boolean negation) {
        this.negation = negation;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setNotMessage(String notMessage) {
        this.notMessage = notMessage;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
