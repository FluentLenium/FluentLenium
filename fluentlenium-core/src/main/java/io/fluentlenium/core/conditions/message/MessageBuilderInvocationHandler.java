package io.fluentlenium.core.conditions.message;

import io.fluentlenium.core.conditions.Negation;
import io.fluentlenium.utils.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Invocation handler that builds message based on called performed.
 */
public class MessageBuilderInvocationHandler implements InvocationHandler {
    private Object instance;
    private final List<MessageBuilderCall> calls;

    /**
     * Creates a new message builder invocation handler.
     *
     * @param context base context of the generated message
     */
    public MessageBuilderInvocationHandler(String context) {
        this(new ArrayList<>());
        MessageBuilderCall messageBuilderCall = new MessageBuilderCall();
        messageBuilderCall.setContext(context);
        calls.add(messageBuilderCall);
    }

    /**
     * Creates a new message builder invocation handler.
     *
     * @param context  base context of the generated message
     * @param instance underlying wrapped instance. If not null, calls will also be performed on this instance.
     */
    public MessageBuilderInvocationHandler(String context, Object instance) {
        this(context);
        this.instance = instance;
    }

    /**
     * Creates a new message builder invocation handler, with initial calls.
     *
     * @param calls initial calls.
     */
    public MessageBuilderInvocationHandler(List<MessageBuilderCall> calls) {
        this.calls = calls;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object instanceReturn = null;
        if (instance != null) {
            instanceReturn = method.invoke(instance, args);
        }
        MessageBuilderCall callItem = new MessageBuilderCall();

        if (method.isAnnotationPresent(Message.class)) {
            callItem.setMessage(method.getAnnotation(Message.class).value());
        }
        if (method.isAnnotationPresent(NotMessage.class)) {
            callItem.setNotMessage(method.getAnnotation(NotMessage.class).value());
        }
        if (method.isAnnotationPresent(MessageContext.class)) {
            callItem.setContext(method.getAnnotation(MessageContext.class).value());
        }
        callItem.setArgs(args);
        if (method.isAnnotationPresent(Negation.class)) {
            callItem.setNegation(true);
        }

        calls.add(callItem);

        if (!method.getReturnType().isPrimitive()) {
            return MessageProxy.wrap(method.getReturnType(), instanceReturn, calls);
        }

        if (instance == null) {
            return ReflectionUtils.getDefault(method.getReturnType());
        }
        return instanceReturn;
    }

    /**
     * Build the message based on annotations from methods called previously.
     *
     * @return built message
     * @throws IllegalStateException if one of the recorded call has no @Message/@NotMessage annotation.
     */
    public String buildMessage() {
        StringBuilder messageBuilder = new StringBuilder();
        for (MessageBuilderCall call : calls) {
            if (call.getContext() != null) {
                if (messageBuilder.length() > 0) {
                    messageBuilder.append(' ');
                }
                messageBuilder.append(call.getContext());
            }
        }

        boolean negation = false;
        for (MessageBuilderCall call : calls) {
            if (call.isNegation()) {
                negation = !negation;
            }
        }

        List<MessageBuilderCall> reversedCalls = new ArrayList<>(calls);
        Collections.reverse(reversedCalls);

        for (MessageBuilderCall call : reversedCalls) {
            String validationMessage = negation ? call.getMessage() : call.getNotMessage();

            if (validationMessage == null) {
                continue;
            }

            validationMessage = MessageFormat.format(validationMessage, call.getArgs());

            return messageBuilder.append(' ').append(validationMessage).toString(); // NOPMD AvoidBranchingStatementAsLastInLoop
        }

        throw new IllegalStateException("No @Message/@NotMessage annotation found in the calls.");

    }
}
