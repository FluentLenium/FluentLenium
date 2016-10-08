package org.fluentlenium.core.conditions.message;

import org.fluentlenium.core.conditions.Negation;
import org.fluentlenium.utils.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageBuilderInvocationHandler implements InvocationHandler {
    private Object instance;
    private List<MessageBuilderCall> calls;

    public MessageBuilderInvocationHandler(final String context) {
        this(new ArrayList<>());
        final MessageBuilderCall messageBuilderCall = new MessageBuilderCall();
        messageBuilderCall.setContext(context);
        calls.add(messageBuilderCall);
    }

    public <T> MessageBuilderInvocationHandler(final String context, final Object instance) {
        this(context);
        this.instance = instance;
    }

    public MessageBuilderInvocationHandler(final List<MessageBuilderCall> calls) {
        this.calls = calls;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        Object instanceReturn = null;
        if (instance != null) {
            instanceReturn = method.invoke(instance, args);
        }
        final MessageBuilderCall callItem = new MessageBuilderCall();

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

    public String buildMessage() {
        final StringBuilder messageBuilder = new StringBuilder();
        for (final MessageBuilderCall call : calls) {
            if (call.getContext() != null) {
                if (messageBuilder.length() > 0) {
                    messageBuilder.append(' ');
                }
                messageBuilder.append(call.getContext());
            }
        }

        boolean negation = false;
        for (final MessageBuilderCall call : calls) {
            if (call.isNegation()) {
                negation = !negation;
            }
        }

        final List<MessageBuilderCall> reversedCalls = new ArrayList<>(calls);
        Collections.reverse(reversedCalls);

        for (final MessageBuilderCall call : reversedCalls) {
            String validationMessage = negation ? call.getMessage() : call.getNotMessage();

            if (validationMessage == null) {
                continue;
            }

            validationMessage = MessageFormat.format(validationMessage, call.getArgs());

            messageBuilder.append(' ');
            messageBuilder.append(validationMessage);

            return messageBuilder.toString(); // NOPMD AvoidBranchingStatementAsLastInLoop
        }

        throw new IllegalStateException("No @Message/@NotMessage annotation found in the calls.");

    }
}
