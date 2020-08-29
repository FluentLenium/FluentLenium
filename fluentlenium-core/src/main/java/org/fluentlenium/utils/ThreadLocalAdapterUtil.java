package org.fluentlenium.utils;

import org.apache.commons.lang3.StringUtils;
import org.fluentlenium.adapter.SharedMutator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public final class ThreadLocalAdapterUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadLocalAdapterUtil.class);

    private ThreadLocalAdapterUtil() {
    }

    public static Class<?> getClassFromThread(ThreadLocal<Class<?>> testClass) {
        Class<?> currentTestClass = testClass.get();
        if (currentTestClass == null) {
            LOGGER.warn("Current test class is null. Are you in test context?");
        }
        return currentTestClass;
    }

    public static String getMethodNameFromThread(ThreadLocal<String> methodName) {
        String currentTestMethodName = methodName.get();
        if (currentTestMethodName == null) {
            LOGGER.warn("Current test method name is null. Are you in text context?");
        }
        return currentTestMethodName;
    }

    public static void setTestClassAndMethodValues(
            ThreadLocal<SharedMutator.EffectiveParameters<?>> parametersThread,
            ThreadLocal<Class<?>> classThread,
            ThreadLocal<String> methodNameThread) {
        Optional.ofNullable(parametersThread.get()).ifPresent((params) -> {
            Optional.ofNullable(params.getTestClass()).ifPresent(classThread::set);
            Optional.ofNullable(params.getTestName()).ifPresent(methodName -> setMethodName(methodName, methodNameThread));
        });
    }

    private static void setMethodName(String methodName, ThreadLocal<String> methodNameThread) {
        String className = StringUtils.substringBefore(methodName, "(");
        methodNameThread.set(className);
    }

    public static void clearThreadLocals(
            ThreadLocal<SharedMutator.EffectiveParameters<?>> parametersThread,
            ThreadLocal<Class<?>> classThread,
            ThreadLocal<String> methodNameThread) {
        parametersThread.remove();
        classThread.remove();
        methodNameThread.remove();
    }

}
