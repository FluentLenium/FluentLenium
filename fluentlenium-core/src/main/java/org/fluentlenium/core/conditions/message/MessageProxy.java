package org.fluentlenium.core.conditions.message;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Provides message proxy implementations of interface that records calls to build message from methods annotated with
 * {@link Message}, {@link NotMessage} and {@link MessageContext}.
 */
public final class MessageProxy {
    private MessageProxy() {
        // Utility class
    }

    /**
     * Wrap an object into a message proxy supporting {@link Message}, {@link NotMessage} and {@link MessageContext}.
     *
     * @param messageClass class to wrap in the proxy.
     * @param instance     original instance.
     * @param context      initial context for generated message.
     * @param <T>          type of the class to wrap.
     * @return a proxy generating message from annotations.
     */
    public static <T> T wrap(Class<T> messageClass, Object instance, String context) {
        return (T) Proxy.newProxyInstance(MessageProxy.class.getClassLoader(), new Class<?>[] {messageClass},
                new MessageBuilderInvocationHandler(context, instance));
    }

    /**
     * Wrap an object into a message proxy supporting {@link Message}, {@link NotMessage} and {@link MessageContext}.
     *
     * @param messageClass class to wrap in the proxy.
     * @param instance     original instance.
     * @param calls        call stack of the proxy.
     * @param <T>          type of the class to wrap.
     * @return a proxy generating message from annotations.
     */
    public static <T> T wrap(Class<T> messageClass, Object instance, List<MessageBuilderCall> calls) {
        return (T) Proxy.newProxyInstance(MessageProxy.class.getClassLoader(), new Class<?>[] {messageClass},
                new MessageBuilderInvocationHandler(calls));
    }

    /**
     * Build a message proxy supporting {@link Message}, {@link NotMessage} and {@link MessageContext}.
     *
     * @param messageClass class to wrap in the proxy.
     * @param context      initial context for generated message.
     * @param <T>          type of the class to wrap.
     * @return a proxy generating message from annotations.
     */
    public static <T> T builder(Class<T> messageClass, String context) {
        return (T) Proxy.newProxyInstance(MessageProxy.class.getClassLoader(), new Class<?>[] {messageClass},
                new MessageBuilderInvocationHandler(context));
    }

    /**
     * Build a message proxy supporting {@link Message}, {@link NotMessage} and {@link MessageContext}.
     *
     * @param messageClass class to wrap in the proxy.
     * @param calls        call stack of the proxy.
     * @param <T>          type of the class to wrap.
     * @return a proxy generating message from annotations.
     */
    public static <T> T builder(Class<T> messageClass, List<MessageBuilderCall> calls) {
        return (T) Proxy.newProxyInstance(MessageProxy.class.getClassLoader(), new Class<?>[] {messageClass},
                new MessageBuilderInvocationHandler(calls));
    }

    /**
     * Build the message from a proxy
     *
     * @param proxy message builder proxy
     * @return generated message.
     */
    public static String message(Object proxy) {
        MessageBuilderInvocationHandler invocationHandler = (MessageBuilderInvocationHandler) Proxy
                .getInvocationHandler(proxy);
        if (invocationHandler == null) {
            return null;
        }
        return invocationHandler.buildMessage();
    }
}
