package io.fluentlenium.adapter;

import io.fluentlenium.adapter.exception.AnnotationNotFoundException;import io.fluentlenium.adapter.exception.AnnotationNotFoundException;

import java.lang.annotation.Annotation;

public interface TestRunnerAdapter {

    /**
     * @return Class of currently running test
     */
    Class<?> getTestClass();

    /**
     * @return method name (as String) of currently running test
     */
    String getTestMethodName();

    /**
     * Allows to access Class level annotation of currently running test
     *
     * @param annotation interface you want to access
     * @param <T>        the class annotation
     * @return Annotation instance
     * @throws AnnotationNotFoundException when annotation you want to access couldn't be find
     */
    <T extends Annotation> T getClassAnnotation(Class<T> annotation);

    /**
     * Allows to access method level annotation of currently running test
     *
     * @param annotation interface you want to access
     * @param <T>        the method annotation
     * @return Annotation instance
     * @throws AnnotationNotFoundException of annotation you want to access couldn't be found
     */
    <T extends Annotation> T getMethodAnnotation(Class<T> annotation);
}
