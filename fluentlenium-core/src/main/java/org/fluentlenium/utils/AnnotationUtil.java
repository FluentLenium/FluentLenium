package org.fluentlenium.utils;

import org.fluentlenium.adapter.exception.AnnotationNotFoundException;
import org.fluentlenium.adapter.exception.MethodNotFoundException;

import java.lang.annotation.Annotation;

public final class AnnotationUtil {

    private AnnotationUtil() {
    }

    public static <T extends Annotation> T getClassAnnotationForClass(
            Class<T> annotation, Class<?> classFromThread) {
        T definedAnnotation = classFromThread.getAnnotation(annotation);

        if (definedAnnotation == null) {
            throw new AnnotationNotFoundException();
        }

        return definedAnnotation;
    }

    public static  <T extends Annotation> T getMethodAnnotationForMethod(
            Class<T> annotation, Class<?> classFromThread, String methodNameFromThread) {
        T definedAnnotation;
        try {
            definedAnnotation = classFromThread.getDeclaredMethod(methodNameFromThread)
                    .getAnnotation(annotation);
        } catch (NoSuchMethodException e) {
            throw new MethodNotFoundException(e);
        }

        if (definedAnnotation == null) {
            throw new AnnotationNotFoundException();
        }

        return definedAnnotation;
    }

}
